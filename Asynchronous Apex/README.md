# Asynchronous Processing Basics

Asynchronous processing in Salesforce allows you to execute tasks in the background, freeing the user from having to wait for the task to finish. It's a powerful feature used for various scenarios, including:

- **Callouts to external systems**: Interacting with external services and APIs.
- **Operations that require higher limits**: Handling tasks that exceed normal processing limits.
- **Code that needs to run at a certain time**: Scheduling tasks to execute at specified intervals.

# Types of Asynchronous Processing

## @Future Methods

`@future` methods run in their own thread and do not start execution until resources become available. They are commonly used for tasks like web service callouts.

## @Batch Apex

`@Batch` Apex is designed for running large jobs that would exceed normal processing limits. It's an excellent choice for data cleansing, archiving records, and other operations involving significant data processing.

## @Queueable Apex

`@Queueable` Apex functions similarly to `@future` methods but provides additional job chaining capabilities. It also allows for more complex data types, making it suitable for performing sequential processing operations with external web services.

## @Scheduled Apex

`@Scheduled` Apex allows you to schedule the execution of Apex code at specific times, making it perfect for daily or weekly tasks.

# Advantages of Asynchronous Apex

Asynchronous Apex provides several advantages, including:

- Higher governor and execution limits, with the number of SOQL queries doubled from 100 to 200.
- Larger total heap size and maximum CPU time allocations.
- Governor limits in asynchronous requests are independent of the limits in the synchronous request that initially queued the asynchronous request.


# Interview Questions on Asynchronous Apex

Q. What is the Apex Flex Queue?

The Apex Flex queue enables you to submit up to 100 batch jobs for execution. Any jobs that are submitted for execution are in holding status and are placed in the Apex Flex Queue. Up to 100 batch jobs can be in the holding status.

Q. Can you change order of job in Apex Flex Queue?

Jobs are processed first-in first-out-in the order in which they're submitted. You can look at the current queue order and shuffle the queue, so that you could move an important job to the front, or less important ones to the back.
```apex
Boolean isSuccess = System.FlexQueue.moveBeforeJob(jobToMoveId, jobInQueueId);
```


Every asynchronous operation has two forms of memory. Apex queue and Flex queue. 

1. **Apex Job Queue**:
   - The Apex Job Queue, commonly referred to as the "Queueable Apex," allows you to asynchronously process long-running or computationally expensive operations in Salesforce. This feature is useful when you want to offload tasks that could potentially exceed execution time limits in synchronous contexts.
   - You can create classes that implement the `Queueable` interface and enqueue them for execution. The jobs are then executed by Salesforce at a suitable time, which could be immediately or later depending on system resources.
   - This is different from traditional batch processing, as Queueable Apex is designed for smaller, more immediate tasks.
   - Can store max 5 jobs

2. **Flex Queue** (or "AsyncApexJob Flex Queue"):
   - The "Flex Queue" refers to a queue where you can monitor and manage asynchronous Apex jobs, including Batch Apex, Queueable Apex, and other asynchronous operations.
   - Jobs that are submitted for execution and are waiting in the queue to be processed are often referred to as being in the "Flex Queue."
   - The "Flex Queue" is a part of Salesforce's platform resources and workload management, ensuring that jobs are executed efficiently and in a timely manner.

Flex Queue:

The Apex Queue is used to store the Batch job for execution. It can store maximum of 5 active jobs from one Organization.

On the other hand the Flex Queue can store maximum 100 Batch Jobs. From the Flex Queue the Batch Jobs are tranfered to Apex Queue.

The Batch job which are inside the Flex Queue will have the status as "Holding".

When the Jobs are tranfered from the Flex Queue to Apex Queue their status changed to "Queued".

While executing the Jobs its status becomes "Processing".

Once the Batch job is excution is over it status is changed to "Completed".

Setup -- > Monitor -- > Jobs -- > Apex Flex Queue.

If 100 requests are sent, the first 5 will be sent to apex queue and the rest 95 will be sent to flex queue. If a request in apex queue fails, it is sent back to the flex queue. 


Salesforce only recommends us to schedule our batches. Otherwise, it is advised to not mix async processes in salesforce.

You can also monitor the status of Apex jobs in the Apex Flex Queue, and reorder them to control which jobs are processed first. From Setup, enter Jobs in the Quick Find box, then select Apex Flex Queue.

Async processes will be available in Quick Find → Apex Jobs


In the third batch, if i want just the record record that failed to roll back and not the rest of the record, can I do it? Also if I want to halt the batch execution after the failure, how can I do this?

1. Rolling back only the failed record (not the entire batch):

Yes, you can achieve this using the Database.update() method with allOrNone parameter set to false:

apex
public void execute(Database.BatchableContext bc, List<Account> scope) {
    List<Database.SaveResult> results = Database.update(scope, false); // allOrNone = false
    
    // Process results to identify failures
    for (Integer i = 0; i < results.size(); i++) {
        if (!results[i].isSuccess()) {
            // Log the error for the failed record
            System.debug('Failed to update Account: ' + scope[i].Id);
            for (Database.Error err : results[i].getErrors()) {
                System.debug('Error: ' + err.getMessage());
            }
        }
    }
}
With allOrNone = false, Salesforce will:

Commit successful record updates within the batch
Skip/fail only the problematic records
Continue processing the remaining records in the batch
2. Halting batch execution after failure:

You can implement Database.RaisesPlatformEvents interface and use the finish method to check for failures:

apex
public class MyBatchClass implements Database.Batchable<sObject>, Database.RaisesPlatformEvents {
    
    public void execute(Database.BatchableContext bc, List<Account> scope) {
        List<Database.SaveResult> results = Database.update(scope, false);
        
        Boolean hasFailures = false;
        for (Database.SaveResult result : results) {
            if (!result.isSuccess()) {
                hasFailures = true;
                break;
            }
        }
        
        // If you want to abort immediately on any failure in this batch
        if (hasFailures) {
            // You can't directly abort from execute method, but you can:
            // 1. Set a flag in a custom setting/custom metadata
            // 2. Or throw an exception to fail the entire batch
            throw new BatchException('Batch execution halted due to record failures');
        }
    }
    
    public void finish(Database.BatchableContext bc) {
        // Check if the job had failures and take action
        AsyncApexJob job = [SELECT Id, Status, NumberOfErrors FROM AsyncApexJob WHERE Id = :bc.getJobId()];
        
        if (job.NumberOfErrors > 0) {
            // Implement logic to abort remaining batches if any
            // You could also send notifications or log errors
        }
    }
}
Alternative approach using custom settings:


public class MyBatchClass implements Database.Batchable<sObject> {
    
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator('SELECT Id, Name FROM Account');
    }
    
    public void execute(Database.BatchableContext bc, List<Account> scope) {
        // Check if previous batch set abort flag
        Batch_Control__c control = Batch_Control__c.getInstance();
        if (control != null && control.Abort_Execution__c) {
            return; // Skip processing
        }
        
        List<Database.SaveResult> results = Database.update(scope, false);
        
        for (Database.SaveResult result : results) {
            if (!result.isSuccess()) {
                // Set abort flag for subsequent batches
                if (control == null) {
                    control = new Batch_Control__c(SetupOwnerId = UserInfo.getOrganizationId());
                }
                control.Abort_Execution__c = true;
                upsert control;
                break;
            }
        }
    }
}
The key is using `Database.update(records, false)` for partial success and implementing custom logic with flags or exceptions to control batch execution flow.


