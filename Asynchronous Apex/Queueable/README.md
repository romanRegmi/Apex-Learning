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