/*
* Asynchronous Processing Basics
* An asynchronous process executes a task in the background.
* User does not  have to wait for the task to finish.
* Use Asynchronous Apex for:
  o Callouts to external systems
  o Operations that require higher limits
  o Code that needs to run at a certain time.
*/

/*
* Types of Asynchronous Processing
  @Future Methods - Run in their own thread, and do not start until resources are available - Web service callout.
  @Batch Apex - Run large jobs that would exceed normal processing limits - Data cleansing or archiving of records
  @Queueable Apex - Similar to future methods, but provide additional job chaining and allow more complex data types to be used - Performing sequential processing operations with external Web Services.
  @Scheduled Apex - Schedule Apex to run at a specified time - Daily or weekly tasks.
*/

/*
* Asynchronous apex provides higher governor and execution limits.
* Number of SOQL is doubled from 100 to 200.
* Total heap size and maximum CPU time are similarly larger for asynchronous calls.
* As you get higher limits with async, also those governor limits are independent of the limits in the synchronous request that queued the async request initially.
*/







