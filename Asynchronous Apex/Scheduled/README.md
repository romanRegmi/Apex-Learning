
# Scheduled Apex
Scheduled Apex allows you to run Apex classes at specific times, enabling you to perform maintenance tasks on a daily or weekly basis. To implement Scheduled Apex, you need to create an Apex class that implements the Schedulable interface.

## Syntax
```apex
global class SomeClass implements Schedulable {
    global void execute(SchedulableContext ctx) {
        // Write your code here
    }
}
```

## Scheduling Using the UI:
To schedule the class you've created using the Salesforce UI, follow these steps:

1. Navigate to the quick find menu.
2. Search for "Apex class."
3. Select "Schedule Apex."

Please note that when using the UI, you'll have options to schedule jobs on a monthly, daily, or weekly basis. If you need to schedule your job more frequently, such as every hour, you'll need to use a CRON expression.

## CRON Expression
A CRON expression is a string of five or six fields representing a set of times for executing routines. For example:
```apex
String sch = '0 20 30 10 2 6 2022';
```

* 0 = Seconds
* 20 = Minutes
* 30 = Day of Month
* 10 = Month (1 for Jan, 12 for Dec)
* 2 = Day of Week (1 for Sun, 7 for Sat)
* 2022 = Year (Optional)

## Editing Scheduled Classes:
Once a class is scheduled, you cannot edit the classes the scheduled method calls. This includes editing the classes inside the scheduled class.

## Limitations:

1. You can have a maximum of 100 scheduled jobs at one time.
2. If you schedule a class from a trigger, ensure that the trigger won't exceed the limit of scheduled jobs.
3. Synchronous web service callouts are not supported from scheduled Apex. To make a synchronous callout, place the callout in a method annotated with **@future(callout=true)** and call this method from scheduled Apex. If scheduled Apex executes a batch job, callouts are supported from the batch class.


 11. What are the way of monitor the schedule job ?
 Ans: There are two way of monitior the job
 
 1. Apex Jobs Page:
Setup > Apex Jobs to see a list of Apex jobs, including scheduled jobs, their status, and
execution details.
 2. Developer Console:
You can use the Developer Console to monitor and debug scheduled jobs. In the Developer
Console, go to the "Query Editor" tab and execute the following query to retrieve scheduled
jobs:
 SELECT Id, CronExpression, NextFireTime, PreviousFireTime, StartTime, EndTime, Status, 
 JobItemsProcessed, TotalJobItems, NumberOfErrors, ExtendedStatus FROM CronTrigger 
 WHERE CronJobDetail.JobType = '7'


12. How can we stop scheduling job ?
 Ans: // Retrieve the CronTrigger Id of the scheduled job String jobName = 'YourScheduledJobName';
 CronTrigger jobTrigger = [SELECT Id FROM CronTrigger WHERE CronJobDetail.Name = :jobName
 LIMIT 1];
 // Abort the job using the CronTrigger Id
 System.abortJob(jobTrigger.Id)


 Can we call batch from schedule apex ?
 Ans - Yes, We can call batch from schedule apex.
public class ScheduledBatchClassPractice implements schedulable{
 
 public void execute(SchedulableContext sc){
 try{
BatchClassPractice btch = new BatchClassPractice(); // You can call batch class from schedule apex
 database.executeBatch(btch,400);
 
 // Call the future method in schedulable apex
 futureMethodExample.MyFutureMethod1();
 }catch(exception e){
 System.debug('Exception in ScheduledBatchClassPractice: ' + e.getMessage());
 }
 
 }
}

Q. What are limitations of Scheduled Apex?

1. We can schedule only 100 jobs at a time.
2. Max no. of apex schedule jobs in 24 hours is 2,50,000 number of jobs (can change with
salesforce updates).
3. Synchronous WebService callouts are not supported in schedulable Apex.

Does the apex scheduler run in system mode?
The scheduler runs in system mode. All classes are executed, whether or not the user has permission to execute the class. 


A scheduled class can be scheduled from code and UI. 


In Apex, when you create a schedulable class (a class that implements the `Schedulable` interface), it must be defined with the `global` access modifier for the `execute` method. This is a Salesforce requirement, and there's a specific reason for it:

1. **Access from Different Contexts**: The `global` access modifier for the `execute` method allows the schedulable class to be invoked from different contexts, including managed packages. When a class is defined as `global`, it can be accessed and executed from contexts outside of its own namespace. This is essential for providing flexibility and allowing Salesforce administrators and developers to schedule or run jobs regardless of the code's origin.

For example, if a managed package includes a schedulable class, it must be `global` to be scheduled by the org's administrators, even if the package is installed by a different organization.

Here's a simplified example of a schedulable class:

```apex
global class MySchedulableClass implements Schedulable {
    global void execute(SchedulableContext sc) {
        // Your scheduled logic here
    }
}
```

By defining the `execute` method as `global`, it ensures that the class can be scheduled and executed in various Salesforce contexts. This access level aligns with Salesforce's security and encapsulation model, providing controlled access to the class's functionality from external contexts when needed.


What is the governot limit of schedule apex?
We can have 100 scheduled apex jobs at one time.

We cannot make callouts from a scheduled class. We need to use future methods if we want to.

We can schedule a class using CORN Expression or Standard Wizard.

What is an apex Scheduler?

The Apex Scheduler lets you delay execution so that you can run Apex
classes at a specified time. This is ideal for daily or weekly maintenance tasks
using Batch Apex.

What happens after the class is scheduled?

After a class has been scheduled, a CronTrigger object is created that represents the scheduled
job. It provides a getTriggerld method that returns the ID of a CronTrigger API object.
What are the arguments of the System.Schedule method?
The System.Schedule method takes three arguments:
Name for the job
CRON expression used to represent the time and date the job is scheduled to run
Name of the class.


How to monitor Scheduled Jobs?

After an Apex job has been scheduled, you can obtain more information about it
by running a SOQL query on CronTrigger.
CronTrigger job = [SELECT Id, CronJobDetail.Id, CronJobDetail.Name,
CronJobDetail.JobType FROM CronTrigger ORDER BY CreatedDate DESC
LIMIT 1];


Q. Write an expression to schedule an operation on Jan Tuesday 12:30 ?

'0 30 12 ? 1 TUES'
'0 30 12 ? 1 3'

'Seconds': 0
'Min' : 30
'Hours' : 12
'Day-Months' : ?
'Month' : 1
'Day-Week' : 3
'Optional Year': -


Q. How to Call batch apex from schedulable class?

Create instance of batchClass and then pass the instance in database.executebatch

batchable b = new batchable();
dacabase.executebatch(b);

An easier way to schedule a batch job is to call the System.scheduleBatch method without
having to implement the Schedulable interface.



We can Pause/Resume Scheduled Jobs via Apex 