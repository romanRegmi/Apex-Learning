# Future Methods

Future methods in Salesforce allow you to execute tasks asynchronously in a separate thread. They are a powerful feature used for various scenarios, including:

- **Callouts to External Web Services**: Interacting with external services, APIs, and endpoints. This is particularly useful when you need to make HTTP requests to external systems.

- **Processes Requiring Separate Threads**: Executing processes that need to run independently from the main transaction, enabling improved application responsiveness.

- **Isolating DML Operations**: Using future methods to perform DML (Data Manipulation Language) operations on different sObject types, which helps prevent mixed DML errors.

- **Long-Running Callouts from Triggers**: Future methods provide the ability to make long-running callouts from triggers without blocking the main transaction.

## Key Points

- Future methods are always declared as `static` and return `void`.

- You cannot pass non-primitive data types as parameters to future methods. For example, you cannot pass sObjects or lists of sObjects as arguments. The reason for this limitation is that sObjects might change between the time you call the method and the time it executes.

## Best Practices 
Ensure future methods execute as fast as possible.
In case of Web service callouts, try to bundle all the callouts together from the same  future method, rather that using a separate future method for each callout.
Test that a trigger enqueuing the @future calls is able to handle a trigger collection of
200 records.
To process large number of records asynchronously, use Batch Apex instead of future
methods.

When will we use future methods instead Of Queueable?
You use future methods instead of queueable is when your functionality is
sometimes executed synchronously, and sometimes asynchronously. It's
much easier to refactor a method in this manner than converting to a
queueable class. This is handy when you discover that part of your existing
code needs to be moved to async execution. You can simply create a similar
future method that wraps your synchronous method.

How many jobs can you chain from executing a job?
You can add only one job from an executing job, which means that only one
child job can exist for each parent job.

How can I use this Job ld to trace the Job?
Just perform a SOQL query on AsyncApexJob by filtering on the job ID.
AsyncApexJob joblnfo = [SELECT Status,NumberOfErrors FROM
AsyncApexJob WHERE

Can I do callouts from a Queueable Job?
Yes, you have to implement the Database.AllowsCallouts interface to do
callouts from Queueable Jobs.
If I have written more than one System.enqueueJob call, what will happen?
System will throw LimitException stating "Too many queueable jobs added to
the queue: N"

Q. How can I use the Job ld to trace the Queuable Job?
Just perform a SOQL query on AsyncApexJob by filtering on the job ID.
AsyncApexJob joblnfo - (SELECT Status.NumberOfErrors FROM AsyncApexJob WHERE ld-iobiDJ:

Q. I have 200 records to be processed using Queueable Apex, How Can I divide the
execution Context for every 100 records?
Similar to future jobs, queueable jobs don't process batches, so you can't divide the
execution Context. It will process all 200 records, in atiingle execution Context 