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


# Salesforce Asynchronous Apex Reference

## 1. Batch Apex: QueryLocator vs. Iterable
The `start` method defines the execution scope. Use the table below to choose the correct implementation.

| Feature | Database.QueryLocator | Iterable |
| :--- | :--- | :--- |
| **Record Limit** | 50 Million Records | No hard limit (Theoretical) |
| **Max Batch Size** | 2,000 | No hard limit (Constrained by Heap/CPU) |
| **Data Source** | Standard SOQL Query | Lists, Sets, or Custom Logic |
| **Governor Limits** | Bypassed (for total records) | Fully Enforced |
| **Best Use Case** | Standard SObject processing | Complex filtering or non-SObject data |

---

## 2. Queueable Apex vs. @future
Choose the right asynchronous tool based on the complexity of the task.

### Use `@future` When:
* **Simplicity is key:** Quick, straightforward tasks.
* **Non-Serializable Data:** You need to work with data types that cannot be passed between transactions.
* **Isolated Processing:** No need to chain multiple jobs together.

### Use `Queueable` When:
* **Complex Workflows:** You need to chain jobs (up to 50 deep) or manage dependencies.
* **SObject Support:** You want to pass full SObjects or Collections directly into the job.
* **Monitoring:** You require a `JobId` to track progress via the `AsyncApexJob` table.
* **Callouts:** You need to perform web service calls by implementing `Database.AllowsCallouts`.

---

## 3. Advanced Implementation Details

### Queueable Context & Chaining
* **Execution Context:** Unlike Batch Apex, Queueable jobs do not process in batches. 200 records passed to a Queueable job are processed in a single execution context.
* **Testing Chaining:** Job chaining is not supported in Apex Unit Tests. Use `Test.isRunningTest()` to guard your `System.enqueueJob()` calls.
* **Async Calls:** Queueable jobs can successfully call `@future` methods, Batch Apex, and other Queueable jobs and vice-versa.

```java

public class QueuableApexExample implements Queueable {
    public static void execute(QueueableContext QC){
        Account act = new Account(Name='', Phone='',
        isAddress__c=true);
        insert act;
        
        //Call future method
        futureClass.futureMethod();
    }
}
```

### Transaction Finalizers
Finalizers allow you to attach post-action logic to a Queueable job to handle success or failure scenarios (e.g., retrying a failed callout).
* **Max Retries:** A failed job can be re-enqueued up to **5 times**.
* **Core Methods:**
  * `getResult()`: Returns `SUCCESS` or `UNHANDLED_EXCEPTION`.
  * `getException()`: Retrieves the error details during failure.
  * `getAsyncApexJobId()`: Links the finalizer to the parent job.

Example: We try to make a callout to an extemnal platform, because of network issue, if the callout fails, how do we make sure that the callout is made again and re-enqueued?
We need to get the jobId after the callout is made, then check the status of the Job, if it failed then we need to re-enqueue it manually.

---

## 4. Governor Limits Cheat Sheet
* **Batch Apex Callouts:** 100 callouts per `execute` method run.
* **Flex Queue:** Maximum 100 jobs.
* **Queueable Chaining:** 50 jobs per transaction (1 in Developer Edition).
* **Start Method Timeout:** 10 minutes for both `QueryLocator` and `Iterable`.









Can I chain a job that has implemented allowCalloutsfrom a Job that doesn't have?
Yes, callouts are also allowed in chained queueable jobs.





We cannot re-enqueue a job more than 5 times

max job in execution stage --> 1

batch apex max number of call outs --> From every method, we can make 100 callouts. The execute method is executed multiple times in a batch apex. So, for every time the method runs, we can make 100 call outs


The System.FinalizerContext interface contains these four methods.
1) global Id getAsyncApexJobId {} - Returns the ID of the Queueable job for which this finalizer is defined.

2) global String   {} - Returns the request ID, a string that uniquely identifies the request, and can be correlated with Event Monitoring logs.

3) global System.ParentJobResult getResult {} - Returns the System.ParentJobResult enum, which represents the result of the parent asynchronous Apex Queueable job to which the finalizer is attached. The enum takes these values: SUCCESS, UNHANDLED_EXCEPTION.

4) global System.Exception getException {} - Returns the exception with which the Queueable job failed when getResult is UNHANDLED_EXCEPTION, null otherwise.

Here's how you can attach a finalizer with the Queueable jobs
- Define a class that implements the System.Finalizer interface.
- Attach a finalizer within a Queueable job’s execute method. To attach the finalizer, invoke the System.attachFinalizer method, using as argument the instantiated class that implements the System.Finalizer interface.