# Batch Apex
Batch Apex is a powerful Salesforce feature that allows you to process large jobs efficiently. It's designed to handle thousands or even millions of records, making it ideal for data cleansing, archiving, or any operation that requires the processing of a massive amount of data.

## Key Features
* Batch Apex processes records asynchronously in batches.
* Each batch is processed using the same execution logic defined in the batch class.   
* Batch Apex is an excellent solution when dealing with large datasets, as it can break down the job into manageable chunks.

## Advantages
1. Governor Limits: Each batch execution starts with a new set of governor limits, providing better control over resource usage.

2. Error Handling: If one batch fails to process successfully, it does not affect the other successful batch transactions, ensuring data integrity.

## Batch Apex Structure
A Batch Apex class must implement the Database.Batchable interface and include the following three methods:

1. start: This method collects the records or objects to be processed and is called once at the beginning of a Batch Apex Job. It returns a `Database.QueryLocator` object or an `Iterable` that contains the records or objects passed to the job. When using a `QueryLocator` object, you can bypass the governor limit for the total number of records retrieved by SOQL queries (up to 50 million records). If you use an `Iterable`, the governor limit for SOQL queries is enforced. 

2. execute: The `execute` method performs the actual processing for each batch of data passed. The default batch size is 200 records, and batches can execute in any order, regardless of the order they were received from the `start` method. This method takes a reference to the `Database.BatchableContext` object and a `List<sObject>` or a list of parameterized types.

3. finish: The `finish` method executes post-processing operations, such as sending emails, once all batches are processed.

```apex
public class MyBatch implements Database.Batchable<sObject> {
    public Database.QueryLocator start(Database.BatchableContext bc) {
        // Collect the batches of records or objects to be passed to the execute method
    }

    public void execute(Database.BatchableContext bc, List<sObject> records) {
        // Process each batch of records
    }

    public void finish(Database.BatchableContext bc) {
        // Execute post-processing operations
    }
}
```

## Invoking a batch class
```apex
MyBatch myBatchObj = new MyBatch();
Id batchId = Database.executeBatch(myBatchObj);
```

If needed, you can specify the batch size when invoking the batch:

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

1. Explain the different interfaces of a batch class?
 - Database.Batchable (Mandatory): This is the core interface for creating batch jobs in Apex. It defines three essential methods:

    start(Database.BatchableContext bc): This method is called at the beginning of the batch job. It's responsible for defining the scope of the job, typically by returning a Database.QueryLocator or an Iterable containing the records to be processed.
    execute(Database.JobExecution jobId, List<sObject> records): This method processes a single batch of records (obtained from the start method). It performs the actual work of the batch job on each batch of records.
    finish(Database.JobExecution jobId): This method is called after all the batches have been processed. It's used for any final cleanup tasks after the job completes.
2. Database.AllowsCallout (Optional): This interface allows the batch job to make outbound calls (callouts) to external services. It's crucial to annotate your batch class with this interface if you intend to make callouts within the execute method. Remember, callouts introduce additional governor limits and require careful handling of potential errors.

3. Database.Stateful (Optional): This interface enables you to maintain state information between batches during the execution of your batch job. It's particularly useful when you need to track or accumulate data throughout the processing of multiple batches. However, use this interface cautiously as it can impact performance and governor limits due to the need for additional data storage during the batch job.

4. Can we call future from batch ?
Ans- No, we cannot call otherwise Fatal error(System.AsyncException) error will occur.
