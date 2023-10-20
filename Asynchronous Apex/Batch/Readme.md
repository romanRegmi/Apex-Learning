<h1>Batch Apex</h1>
Batch Apex is a powerful Salesforce feature that allows you to process large jobs efficiently. It's designed to handle thousands or even millions of records, making it ideal for data cleansing, archiving, or any operation that requires the processing of a massive amount of data.

<h2>Key Features</h2>
* Batch Apex processes records asynchronously in batches.
* Each batch is processed using the same execution logic defined in the batch class.
* Batch Apex is an excellent solution when dealing with large datasets, as it can break down the job into manageable chunks.

<h2>Advantages</h2>
1. Governor Limits: Each batch execution starts with a new set of governor limits, providing better control over resource usage.

2. Error Handling: If one batch fails to process successfully, it does not affect the other successful batch transactions, ensuring data integrity.

<h2>Batch Apex Structure</h2>
A Batch Apex class must implement the Database.Batchable interface and include the following three methods:

1. start: This method collects the records or objects to be processed and is called once at the beginning of a Batch Apex Job. It returns a Database.QueryLocator object or an Iterable that contains the records or objects passed to the job. When using a QueryLocator object, you can bypass the governor limit for the total number of records retrieved by SOQL queries (up to 50 million records). If you use an Iterable, the governor limit for SOQL queries is enforced.

2. execute: The execute method performs the actual processing for each batch of data passed. The default batch size is 200 records, and batches can execute in any order, regardless of the order they were received from the start method. This method takes a reference to the Database.BatchableContext object and a List<sObject> or a list of parameterized types.

3. finish: The finish method executes post-processing operations, such as sending emails, once all batches are processed.

'''
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
'''