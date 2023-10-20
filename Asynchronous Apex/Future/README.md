# Future Methods

Future methods in Salesforce allow you to execute tasks asynchronously in a separate thread. They are a powerful feature used for various scenarios, including:

- **Callouts to External Web Services**: Interacting with external services, APIs, and endpoints. This is particularly useful when you need to make HTTP requests to external systems.

- **Processes Requiring Separate Threads**: Executing processes that need to run independently from the main transaction, enabling improved application responsiveness.

- **Isolating DML Operations**: Using future methods to perform DML (Data Manipulation Language) operations on different sObject types, which helps prevent mixed DML errors.

- **Long-Running Callouts from Triggers**: Future methods provide the ability to make long-running callouts from triggers without blocking the main transaction.

## Key Points

- Future methods are always declared as `static` and return `void`.

- You cannot pass non-primitive data types as parameters to future methods. For example, you cannot pass sObjects or lists of sObjects as arguments. The reason for this limitation is that sObjects might change between the time you call the method and the time it executes.

Best Practices : Future Method
Ensure future methods execute as fast as possible.
In case of Web service callouts, try to bundle all callouts together from the same
future method, rather that using a separate future method for each callout.
Test that a trigger enqueuing the @future calls is able to handle a trigger collection of
200 records.
To process large number of records asynchronously, use Batch Apex instead of future
methods.