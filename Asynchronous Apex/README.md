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
