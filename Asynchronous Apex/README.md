# Asynchronous Processing Basics in Salesforce

Asynchronous processing allows you to execute tasks in the background, freeing the user from having to wait for the task to finish. It is a powerful feature used for various scenarios, including:

* **Callouts to external systems:** Interacting with external services and APIs.
* **Operations that require higher limits:** Handling tasks that exceed normal synchronous processing limits.
* **Scheduled Code:** Code that needs to run at a certain time or specified intervals.

---

## Types of Asynchronous Processing

### 1. Future Methods (`@future`)
Methods annotated with `@future` run in their own thread and do not start execution until resources become available. They are commonly used for tasks like simple web service callouts.

### 2. Batch Apex
Batch Apex is designed for running large jobs that would exceed normal processing limits. It's an excellent choice for data cleansing, archiving records, and other operations involving significant data processing.

### 3. Queueable Apex
Queueable Apex functions similarly to `@future` methods but provides additional **job chaining** capabilities. It also allows for more complex data types (like Objects or Collections), making it suitable for sequential processing operations.

### 4. Scheduled Apex
Allows you to schedule the execution of Apex code at specific times (e.g., daily or weekly tasks).

---

## Advantages of Asynchronous Apex

* **Higher Governor Limits:** SOQL query limits are doubled from 100 to 200.
* **Increased Resources:** Larger total heap size and maximum CPU time allocations.
* **Independence:** Governor limits in asynchronous requests are independent of the limits in the synchronous request that initially queued the job.

---

## The Apex Flex Queue & Apex Job

**Apex Flex Queue** : Monitor the status of Apex jobs in the Apex flex queue, and reorder them to control which jobs are processed first.

**Apex Job** : Monitor the status of all Apex jobs, and optionally, abort jobs that are in progress.

### Monitoring & Reordering
Jobs are generally processed **First-In, First-Out (FIFO)**. However, you can move an important job to the front of the Flex Queue using Apex:

```apex
Boolean isSuccess = System.FlexQueue.moveBeforeJob(jobToMoveId, jobInQueueId);
```

# Interview Questions on Asynchronous Apex

## What is the Apex Flex Queue?
The Apex Flex queue enables you to submit up to 100 batch jobs for execution. Any jobs that are submitted for execution are in holding status and are placed in the Apex Flex Queue. Up to 100 batch jobs can be in the holding status.

## Can you change order of job in Apex Flex Queue?
Yes, it can be done.

```apex
Boolean isSuccess = System.FlexQueue.moveBeforeJob(jobToMoveId, jobInQueueId);
```

### Important
* The user who initiates the queueable call will be the running user. This is generally true for the other asynchronous types, as well (e.g. Scheduable, Batchable). 
* Platform Event Triggers (will be the Automated User).
* Every asynchronous operation has two forms of memory. Apex queue and Flex queue. 
* Async processes will be available in Quick Find → Apex Jobs