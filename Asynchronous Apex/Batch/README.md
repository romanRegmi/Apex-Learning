# Batch Apex
Batch Apex is a powerful Salesforce feature that allows you to process large jobs efficiently. It's designed to handle thousands or even millions of records, making it ideal for data cleansing, archiving, or any operation that requires the processing of a massive amount of data. The max number of batch executions is 2,50,000 per day.

## Key Features
* Batch Apex processes records asynchronously in batches.
* Each batch is processed using the same execution logic defined in the batch class.   
* Batch Apex is an excellent solution when dealing with large datasets, as it can break down the job into manageable chunks.

## Types of batch apex
1. Query Locator
2. Iterator

## Advantages
1. Governor Limits: Each batch execution starts with a new set of governor limits, providing better control over resource usage.

2. Error Handling: If one batch fails to process successfully, it does not affect the other successful batch transactions, ensuring data integrity.

## Batch Apex Structure
A Batch Apex class must implement the Database.Batchable interface and include the following three methods:

1. start: This method collects the records or objects to be processed and is called once at the beginning of a Batch Apex Job. It returns a `Database.QueryLocator` object or an `Iterable` that contains the records or objects passed to the job. When using a `QueryLocator` object, you can bypass the governor limit for the total number of records retrieved by SOQL queries (up to 50 million records). If you use an `Iterable`, the governor limit for SOQL queries is enforced. 

2. execute: The `execute` method performs the actual processing for each batch of data passed. The default batch size is 200 records, and batches can execute in any order, regardless of the order they were received from the `start` method. This method takes a reference to the `Database.BatchableContext` object and a `List<sObject>` or a list of parameterized types.

3. finish: The `finish` method executes post-processing operations, such as sending emails, once all batches are processed.

```java
public class MyBatch implements Database.Batchable<sObject> {
    public Database.QueryLocator start(Database.BatchableContext bc) {
        // Collect the batches of records or objects to be passed to the execute method
        return Database.getQueryLocator('SELECT Id FROM Account');
    }

    public void execute(Database.BatchableContext bc, List<sObject> records) {
        // Process each batch of records
    }

    public void finish(Database.BatchableContext bc) {
        // Execute post-processing operations
    }
}
```

## Life Cycle of a Batch Apex Class
Life Cycle of Batch Apex (Status of jobs in the Apex Flex Queue)

1. Holding : Job has been submitted and is held in the Apex flex queue until system resources become available to queue the job for processing.
2. Queued : When the Jobs are tranfered from the Flex Queue to Apex Queue their status changed to **Queued**.
3. Preparing : The start method of the job has been invoked. This status can last a few minutes depending on the size of the batch of records.
4. Processing : While executing the Jobs its status becomes **Processing**.
5. Aborted : Job aborted by a user.
6. Completed : Once the Batch job is excution is over without failuers it status is changed to **Completed**.
7. Failed : Job experienced a system failure.

If 100 requests are sent, the first 5 will be sent to **Apex Queue** and the rest 95 will be sent to **Flex Queue**. If a request in **Apex Queue** fails, it is sent back to the **Flex Queue**. 

## Invoking a batch class
```apex
MyBatch myBatchObj = new MyBatch();
Id batchId = Database.executeBatch(myBatchObj);
```

If needed, you can specify the batch size when invoking the batch:

Maximum batch size: Default is 200 records per batch, but can be set up to 2,000.

```apex
MyBatch myBatchObj = new MyBatch();
Id batchId = Database.executeBatch(myBatchObj, 100);
```

## Tracking Progress
Each Batch Apex invocation creates an `AsyncApexJob` record, which helps track the progress of the job. You can query this record to monitor the status and other job-related information:

```apex
AsyncApexJob job = [SELECT Id, Status, JobItemsProcessed, TotalJobItems, NumberOfErrors FROM AsyncApexJob WHERE ID = :batchId];
```

# Interview Questions


## What are the different interface that we can use with batch apex?
1. Database.AllowsCallouts
2. Database.Statefull
3. System.Schedulable
4. System.Iterator
5. Database.Batchabe<sObject>

## Explain the different interfaces of a batch class?
- Database.Batchable : (Mandatory)

- Database.AllowsCallout : This interface allows the batch job to make outbound calls (callouts) to external services. It's crucial to annotate your batch class with this interface if you intend to make callouts within the execute method. Remember, callouts introduce additional governor limits and require careful handling of potential errors.

- Database.Stateful: This interface enables you to maintain state information between batches during the execution of your batch job. It's particularly useful when you need to track or accumulate data throughout the processing of multiple batches. However, use this interface cautiously as it can impact performance and governor limits due to the need for additional data storage during the batch job.

- System.Iterator : //TODO

- System.Schedulable : //TODO

## Can we call future from batch ?
No, we get the Fatal error(System.AsyncException).

## Can I add a additional method in a batch class?
Yes, you can add additional methods to a batch class, as long as you follow the basic structure and syntax requirements of a batch class. 

However, keep in mind that any additional methods you add to a batch class should not interfere with the standard functionality of the batch class. The main entry point of a batch class is the execute() method, which is called when the batch job is started, so any additional methods should not interfere with this method.

Additionally, if you plan to use any additional methods in your batch class from outside the class (for example, in a test class), make sure to mark them as public so they can be accessed from other classes.

## Can we call a batch class from another batch class?
Yes, we can call batch apex from batch apex in finally.

## Can we call the batch apex from triggers in salesforce?
Yes, it is possible. We can call a batch apex from triggers. However, this may cause the governor limit to exceed. We can only have 5 apex jobs queued or executing at a time.

## How to test a batch apex?
Code is run between `Test.startTest()` and `Test.stopTest()`.

## How many batch jobs can run concurrently?
The system can process upto 5 queued or active jobs simultaneously for each org.

## Can we Use FOR UPDATE in SOQL using Database.QueryLocator?
No. It will through an exception stating that "Locking is implied for each batch execution and therefore FOR UPDATE should not be specified."

## How do we track the progress of a batch apex class?
To track a particular process of a batch apex, we use Database.BatchableContext. We have two methods in this interface `getJobId` and `getChildJobId` - id of particular execute method.

## How to use Aggregate queries in Batch Apex
Aggregate queries don't work in Batch Apex.
To fix this error what we should do.

1. Create an Apex class implements Iterator<AggregateResult>.
2. Create an Apex class implements Iterable<AggregateResult>.
3. Implementing to Database.Batchable<AggregateResult>, and Using Iterable at
start execution in Batch Apex.

## What is the difference between `Database.Batchable` & `Database.BatchableContext`?

1. `Database.Batchable` is an interface.

2. `Database.BatchableContext` is a context variable which store the runtime
information. 

## Which platform event can fire from batch ?
The `BatchApexErrorEvent` object represents a platform event associated with a batch Apex class. It is possible to fire platform events from batch apex. So whenever any error or exception occurs, you can fire platform events which can be handled by different subscriber. Batch class needs to implement `Database.RaisesPlatformEvents` interface in order to fire platform event. 

`BatchApexErrorEvent` can be subscribed in LWC as well. For this, we have to import the required functions from lightning/empApi module and the channel name is `'/event/BatchApexErrorEvent'`.

## Want to schedule batch job at one time only and not again. How to do it?
`System.scheduleBatch()` is used to run a schedule a batch job only once for a future time. This method has got 3 parameters.

* param 1 : Instance of a class that implements Database.Batchable interface.
* param 2 : Job name.
* param 3 : Time interval after which the job should start executing.
* param 4 : It's an optional parameter which will define the no. of that processed at a time. 

The `system.scheduleBatch()` returns the scheduled job Id.
We can use the job Id to abort the job. This method returns the scheduled job ID also called CronTrigger ID.

```
String cronID = System.scheduleBatch(reassign, 'job example', 1);
CronTrigger ct = [SELECT Id, TimesTriggered, NextFireTime FROM CronTrigger WHERE Id = :cronID];
```

This method is available only for batch classes and doesn't require the implementation of the Schedulable interface. This makes it easy to schedule a batch job for one execution.

## Can I call Queueable from a batch?
Yes, But you're limited to just one System.enqueueJob call per execute in the Database.Batchable class. Salesforce has imposed this limitation to prevent explosive execution.

## How many records we can insert while testing batch apex?
We have to make sure that the number of records inserted is less than or equal to the batch size of 200 because test methods can execute only one batch. We must also ensure that the Iterable returned by the start method matches the batch size.

## Let's say, I have 150 Batch jobs to execute, Will I be able to queue them in one go?
Once you run `Database.executeBatch`, the Batch jobs will be placed in the **Apex Flex Queue** and its status becomes Holding. 

The **Apex Flex Queue** has the maximum number of 100 jobs, `Database.executeBatch` throws a `LimitException` and doesn't add the job to the queue. So atmost 100 jobs can be added in one go.

## How can you stop a Batch job?
The `Database.executeBatch` and `System.scheduleBatch` method returns an Id that can be used in `System.abortJob` method.

## Can I query related records using Database.QueryLocator?
Yes, You can do subquery for related records, but with a relationship subquery, the batch job processing becomes slower. A better strategy is to perform the subquery separately, from within the execute method, which allows the batch job to run faster.

## How can we make a batch job to skip the queue and get higher priority?
```apex
Id highPriorityJobId = Database.executeBatch(new HighPriorityBatchClass(), 200);
Boolean jobMovedToFrontOfQueue = FlexQueue.moveJobToFront(highPriorityJobId);
```

## What is the difference between the Stateful and Stateless batch jobs?
If your batch process needs information that is shared across transactions, one approach is to make the Batch Apex class itself stateful by implementing the Stateful interface. This instructs the system to preserve the values of your static and instance variables between transactions.
```
global class SummarizeAccountTotal implements Database.Batchable<sObject>, Database.Stateful {}
```








The default size of a batch apex is 200. The max is 2000 and the min is 1 when implementing the QueryLocator. In case of System Iterator, we don't have a limitation. 


It depends on your need , if you want to run batch on records that can be filtered by SOQL then QueryLocator is preferable, but if records that you want to bee processed by batch can not be filtered by SOQL then you will have to use iteratable. But most of the cases it will be achieved  by query locator , so query locator is preferable so just try with it if you scope is complex and can not be achieved by SOQL then go with iterable.

Iterable --> governor limit is enforced. We can perform some custom logic and then pass the data to execute. We can't do this with querylocator.

The limit for a QueryLocator is 50,000,000 records, the maximum size for any query (including API-based SOQL calls). The there is no hard limit for Iterable, though you're still limited by both CPU time (limit 60,000 ms/1 minute) and total start time (10 minutes); in practice, it might be hard to get up to even 50 million rows in that time, but it is likely theoretically possible.

In addition, Iterable return types have no hard maximum scope size, unlike QueryLocator, which will not allow more than 2,000 records per execute call. Other limits may limit how many items you can actually process (e.g. heap or CPU time), but there's no inherent hard limit for scope size.

Hypothetically speaking, using Iterable, you could process 100,000,000 items, or more, assuming you can somehow generate that many items in the time allotted.

all the queried record of a batch class will be of a single batch













# Scenario Based Question
Record A has to be processed before Record B, but Record B came in the first Batch and Record A came in the second batch. The batch picks records which are unprocessed every time it runs. How will you control the processing Order?

The Processing order can't be controlled, but we can bypass the record B processing before Record A. We can implement `Database.STATEFUL` and use one class variable to track whether Record A has processed or not. If not processed and Record B has come, don't process Record B. After all the
execution completes, Record A has already been processed so Run the batch again, to process Record B.


## How would you process data from a CSV file using a Batch class?
In Salesforce, when you want to process data from a CSV file in a Batch class, it's generally more appropriate to use the `Database.Batchable` interface rather than `Iterable`. Here's why:

1. **Scalability**: `Database.Batchable` is designed for processing large volumes of data efficiently. It allows you to divide the data into manageable chunks (batches), making it suitable for handling CSV files with a significant number of records.

2. **Governor Limits**: Batch jobs that implement `Database.Batchable` have separate governor limits from synchronous transactions, which provides flexibility for processing large datasets without hitting the same limits.

3. **Error Handling**: `Database.Batchable` provides better error handling capabilities. You can track and manage failed records, which is essential when dealing with data from external sources like CSV files.

4. **Asynchronous Processing**: Batch jobs can run asynchronously, freeing up system resources and allowing users to continue working without waiting for data processing to complete.

5. **Automatic Checkpointing**: `Database.Batchable` automatically handles checkpointing, which is useful for resuming processing if the batch job is interrupted or needs to be rerun.

To use `Database.Batchable` with a CSV file, you typically load the CSV data into a custom or standard object in Salesforce (e.g., using `Batchable`'s `start` method to query the CSV data) and then process it in batches.

On the other hand, `Iterable` is more suitable when you have a relatively small amount of data to process and can load the data directly from a collection or an external source without the need for dividing it into batches. It's typically used for in-memory processing of data rather than processing large datasets from external files like CSV.

## Handling Failures & Rollbacks in Batch Apex
1. Partial Success (Rolling back only failed records) : To prevent one failing record from rolling back an entire batch, use `Database.update()` with the `allOrNone` parameter set to false.
```java
public void execute(Database.BatchableContext bc, List<Account> scope) {
    // allOrNone = false allows successful records to commit while others fail
    List<Database.SaveResult> results = Database.update(scope, false); 
    
    for (Integer i = 0; i < results.size(); i++) {
        if (!results[i].isSuccess()) {
            System.debug('Failed to update Account: ' + scope[i].Id);
            for (Database.Error err : results[i].getErrors()) {
                System.debug('Error: ' + err.getMessage());
            }
        }
    }
}
```

## Handling Failures & Rollbacks in Batch Apex
1. Partial Success (Continuation on partial failuers ) : To prevent one failing record from halting an entire batch, use Database.update() with the allOrNone parameter set to false.

```java
public void execute(Database.BatchableContext bc, List<Account> scope) {
    // allOrNone = false allows successful records to commit while others fail
    List<Database.SaveResult> results = Database.update(scope, false); 
    
    for (Integer i = 0; i < results.size(); i++) {
        if (!results[i].isSuccess()) {
            System.debug('Failed to update Account: ' + scope[i].Id);
            for (Database.Error err : results[i].getErrors()) {
                System.debug('Error: ' + err.getMessage());
            }
        }
    }
}
```

2. Halting Execution After Failure
If you want to stop the entire batch job if a failure occurs in any single batch, you can use a custom flag or throw an exception.
    
```java
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
```