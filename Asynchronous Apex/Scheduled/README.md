
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