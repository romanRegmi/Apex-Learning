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

## Life Cycle of a Batch Apex Class
Life Cycle of Batch Apex

1. Holding: - Job has been submitted and is held in the Apex flex queue until system resources become available to queue the job for processing.
2. Queued : - Job is awaiting execution.
3. Preparing: - The start method of the job has been invoked. This status can last a few minutes depending on the size of the batch of records.
4. Processing: - Job is being processed.
5. Aborted: - Job aborted by a user.
6. Completed: - Job completed with or without failures.
7. Failed: - Job experienced a system failure.

# Interview Questions

1. Explain the different interfaces of a batch class?
 - Database.Batchable : (Mandatory)

 - Database.AllowsCallout : This interface allows the batch job to make outbound calls (callouts) to external services. It's crucial to annotate your batch class with this interface if you intend to make callouts within the execute method. Remember, callouts introduce additional governor limits and require careful handling of potential errors.

 - Database.Stateful: This interface enables you to maintain state information between batches during the execution of your batch job. It's particularly useful when you need to track or accumulate data throughout the processing of multiple batches. However, use this interface cautiously as it can impact performance and governor limits due to the need for additional data storage during the batch job.

 - System.Iterator : //TODO

 - System.Schedulable : //TODO



4. Can we call future from batch ?
Ans- No, we cannot call otherwise Fatal error(System.AsyncException) error will occur.



In Salesforce, when you want to process data from a CSV file in a Batch class, it's generally more appropriate to use the `Database.Batchable` interface rather than `Iterable`. Here's why:

1. **Scalability**: `Database.Batchable` is designed for processing large volumes of data efficiently. It allows you to divide the data into manageable chunks (batches), making it suitable for handling CSV files with a significant number of records.

2. **Governor Limits**: Batch jobs that implement `Database.Batchable` have separate governor limits from synchronous transactions, which provides flexibility for processing large datasets without hitting the same limits.

3. **Error Handling**: `Database.Batchable` provides better error handling capabilities. You can track and manage failed records, which is essential when dealing with data from external sources like CSV files.

4. **Asynchronous Processing**: Batch jobs can run asynchronously, freeing up system resources and allowing users to continue working without waiting for data processing to complete.

5. **Automatic Checkpointing**: `Database.Batchable` automatically handles checkpointing, which is useful for resuming processing if the batch job is interrupted or needs to be rerun.

To use `Database.Batchable` with a CSV file, you typically load the CSV data into a custom or standard object in Salesforce (e.g., using `Batchable`'s `start` method to query the CSV data) and then process it in batches.

On the other hand, `Iterable` is more suitable when you have a relatively small amount of data to process and can load the data directly from a collection or an external source without the need for dividing it into batches. It's typically used for in-memory processing of data rather than processing large datasets from external files like CSV.


Maximum batch size: Default is 200 records per batch, but can be set up to 2,000.