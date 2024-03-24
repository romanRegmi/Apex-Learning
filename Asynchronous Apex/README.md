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


Every asynchronous operation has two forms of memory. Apex queue and flex queue. 

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


Salesforce only recommends us to schedule our batches. Otherwise, it is advised to not mix async processes in salesforce.