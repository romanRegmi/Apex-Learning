**Queueable Apex**

Queueable is a class. Future is a method. It can be tracked unlike future.

Queueable Apex is a powerful feature in Salesforce that allows you to perform asynchronous processing in a more flexible and versatile manner compared to future methods. Below is an overview of Queueable Apex along with additional benefits and syntax:

**Queueable Apex Overview:**
- Queueable Apex is a superset of future methods, offering additional features.
- It combines the capabilities of future methods and Batch Apex, allowing for more advanced asynchronous processing.
- Queueable Apex can work with non-primitive argument types, making it more versatile for complex data processing.
- You can execute Queueable Apex jobs using the `System.enqueueJob()` method.
- `System.enqueueJob()` returns a job ID that can be used to monitor the progress of the job.

**Queueable Syntax:**
```apex
public class SomeClass implements Queueable {
    public void execute(QueueableContext context) {
        // Write your code here
    }
}
```

**Additional Benefits:**

1. **Non-primitive Types:** Queueable Apex allows you to work with non-primitive data types, making it suitable for more complex and data-rich operations.

2. **Monitoring:** You can easily monitor the progress and status of Queueable Apex jobs using the job ID returned by `System.enqueueJob()`. This makes it simpler to track and manage asynchronous processes.

3. **Chaining Jobs:** You can chain Queueable jobs, allowing one job to trigger another. (Output of one job is the input of another.) When chaining jobs, you can add only one job from an executing job using `System.enqueueJob()`. There is no strict limit on the depth of chained jobs.

**Additional Notes:**

- The execution of a queued job counts once against the shared limit for asynchronous Apex method executions. This means that Queueable Apex is subject to limits, such as the maximum number of allowed asynchronous Apex executions.

- There is a possibility of data loss in queueable apex as well.

- When calling/chaining jobs with System.enqueueJob method, you can add only one job from a parent job.

- You can add up to 50 jobs to the queue with `System.enqueueJob()` in a single transaction, which provides more flexibility for parallel processing of tasks. Note : Not to confuse with job chaining. --> Job chaining happens when another queueable is called in the execute method of a queueable. When this happens, the chained method is a part of a different transaction. 

Here, we are talking about enqueuing jobs in the anynomous window. Say you have 80 queueable. None of them are chained. You call all of them at once jobId1, jobId2.... jobId80. Then, this will not work. 

Although queueable can accept non-primitive, we don't usually pass the records. We pass their ids. and query the records. This prevents data loss. 


2. Can I do callouts from a Queueable Job?
- Yes, you have to implement the Database.AllowsCallouts interface to do callouts from Queueable Jobs.


6. Can we call future and batch from Queueable ?
Ans: Yes we can call future and batch from Queueable and vice-versa.
public class QueuableApexExample implements Queueable {
public static void execute(QueueableContext QC){
Account act = new Account(Name = 'Tested Accctt 97', Phone='0523124578',
isAddress__c=true);
insert act;
futureMethodExample.MyFutureMethod1();
}
}

Q. I have 200 records to be processed using Queueable Apex, How Can I divide the execution Context for every 100 records?
Similar to future jobs, queueable jobs don't process batches, so you can't divide the execution Context. It will process all 200 records, in a single execution Context 




`@future` methods and Queueable Apex both provide asynchronous processing in Salesforce, but they have different use cases and considerations. Here are situations when you might choose one over the other:

**Use `@future` Methods When:**

1. **Simplicity**: If you have a simple, straightforward task that doesn't require chaining multiple jobs or complex logic, `@future` methods are easier to implement.

2. **Governor Limits**: `@future` methods have their own set of governor limits, which are separate from the synchronous limits. This can be beneficial when you want to perform additional processing within the same transaction and need to stay within the same set of limits.

3. **Non-Serializable Data**: If your task involves non-serializable data (e.g., non-primitive data types or objects that can't be passed between transactions), `@future` methods allow you to work with these data types directly.

4. **Simple Error Handling**: If you have simple error handling needs (e.g., you don't need to manage retries or complex error scenarios), `@future` methods are straightforward to work with.

**Use Queueable Apex When:**

1. **Complex Processing**: If your task requires more complex processing, such as chaining multiple jobs together, managing dependencies between jobs, or passing data between them, Queueable Apex provides more flexibility.

2. **Error Handling and Retry**: Queueable Apex allows you to handle errors, implement retry mechanisms, and manage more complex error scenarios.

3. **Bulk Processing**: If you need to process a large volume of records efficiently and want to implement bulk processing, Queueable Apex is a better choice. It allows you to divide the work into multiple Queueable jobs, which can run concurrently.

4. **Long-Running Jobs**: For long-running jobs or tasks that are expected to run for a significant amount of time, Queueable Apex is preferred. It's more suitable for tasks that may span several minutes or even hours.

5. **Monitoring and Debugging**: Queueable Apex provides more visibility and monitoring options, making it easier to track and manage long-running jobs or complex processing flows.



Q. How to test Chaining?

You can't chain queueable jobs in an Apex test.
So you have to write separate test cases for each
chained queueable job. Also, while chaining the
jobs, add a check of Test.isRunningTest() before
calling the enqueueJob.

Test.isRunningTest() Returns true if the currently
executing code was called by code contained in a
test method, false otherwise

Can I chain a job that has implemented allowCalloutsfrom a Job that doesn't have?
Yes, callouts are also allowed in chained queueable jobs.


Q. What is Transaction Finalizers (Beta) ?

Transaction Finalizers feature enables you to attach actions, using
the System.Finalizer interface, to asynchronous Apex jobs that use
the Queueable framework.

Before Transaction Finalizers, there was no direct way for you to
specify actions to be taken when asynchronous jobs succeeded or
failed. With transaction finalizers, you can attach a post-action
sequence to a Queueable job and take relevant actions based on
the job execution result.

A Queueable job that failed due to an unhandled exception can be
successively re-enqueued five times by a Transaction.

Example: We try to make a callout to an extemnal platform, because
of network issue, if the callout fails, how do we make sure that the
callout is made again and re-enqueued?

We need to get the Jobld after the callout is made, then check the
status of the Job, if it failed then we need to re-enqueue it manually.

Salesforce team launched something called Finalizers which will
make the whole process of re-enqueueing the failed queueable job
so easy.

We cannot re-enqueue a job more than 5 times

Max job in flex queue -> 100
max job in execution stage --> 1

batch apex max number of call outs --> From every method, we can make 100 callouts. The execute method is executed multiple times in a batch apex. So, for every time the method runs, we can make 100 call outs


Advantage of queueable apex

- directly pass sobject or a list of sobject
- chain the job
- query jobid and check its status


The System.FinalizerContext interface contains these four methods.
1) global Id getAsyncApexJobId {} - Returns the ID of the Queueable job for which this finalizer is defined.

2) global String getRequestId {} - Returns the request ID, a string that uniquely identifies the request, and can be correlated with Event Monitoring logs.

3) global System.ParentJobResult getResult {} - Returns the System.ParentJobResult enum, which represents the result of the parent asynchronous Apex Queueable job to which the finalizer is attached. The enum takes these values: SUCCESS, UNHANDLED_EXCEPTION.

4) global System.Exception getException {} - Returns the exception with which the Queueable job failed when getResult is UNHANDLED_EXCEPTION, null otherwise.

Here's how you can attach a finalizer with the Queueable jobs
- Define a class that implements the System.Finalizer interface.
- Attach a finalizer within a Queueable job’s execute method. To attach the finalizer, invoke the System.attachFinalizer method, using as argument the instantiated class that implements the System.Finalizer interface.