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

Cannot call future from future.

Q. How can I use the Job ld to trace the Queuable Job?
Just perform a SOQL query on AsyncApexJob by filtering on the job ID.
AsyncApexJob joblnfo - (SELECT Status.NumberOfErrors FROM AsyncApexJob WHERE ld-iobiDJ:

Q. I have 200 records to be processed using Queueable Apex, How Can I divide the
execution Context for every 100 records?
Similar to future jobs, queueable jobs don't process batches, so you can't divide the
execution Context. It will process all 200 records, in atiingle execution Context 

Can we call a future method from a future?
Not, it isn't possible. Chaining is only possible in Queueable.

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

3. Can we call batch from batch ?
Ans - Yes, we can call from finish method but you cannot call in start or excute method.
 public with sharing class BatchExample2 implements Database.Batchable<sObject>{
 public Database.QueryLocator start(Database.BatchableContext context) {
 return Database.getQueryLocator('SELECT ID, Mark_For_Delete_c FROM Studentc WHERE 
Mark_For_Delete_c= \'Yes\'');
 }
 public void execute(Database.BatchableContext context, List<SObject> records)
 {
 delete records;
 }
 public void finish(Database.BatchableContext bc) {
 BatchClassPractice btch = new BatchClassPractice(); 
 database.executeBatch(btch,200);
 }
 }
 // You can call batch class from batch in finish Method


4. Can we call future from batch ?
Ans- No, we cannot call otherwise Fatal error(System.AsyncException) error will occur.
 System.AsyncException: Future method cannot be called from a future or batch method: 
 futureMethodExample.MyFutureMethod()
 /* Write a batch a class to update the Teacher's records whose specialization is math */
public class BatchClassPractice implements Database.Batchable<Sobject>, Database.stateful{
 
 public integer count = 0;
 
 public Database.Querylocator start(Database.BatchableContext BC){
 String teacherList = 'SELECT Id, Name,Specialization__c FROM Teacher__c WHERE Specialization__c
=\'Math\' OR Specialization__c =\'Physics\'';
 
 return Database.getQueryLocator(teacherList);
 }
 public void execute(Database.BatchableContext BC, List<Teacher__c> tchList){
 futureMethodExample.MyFutureMethod();
 if(!tchList.isEmpty()){
 for(Teacher__c tch: tchList){
 if(tch.Specialization__c == 'Math'){
 tch.Name = 'Sir/Madam'+' '+ tch.Name;
 }if(tch.Specialization__c == 'Physics'){
 tch.Name = 'Respected Madam'+' '+ tch.Name;
 }
 count++;
 }
 try{
 update tchList;
 system.debug('Count of Teacher'+count);
 }
 catch(exception e){
 System.debug(e);
 }
 
 }
 }
 
 public void finish(Database.BatchableContext BC){
 futureMethodExample.MyFutureMethod();
 }


 5. Can we call batch from future ?
 Ans- No, We cannot call otherwise Fatal Error(System.AsyncException) error will occur.
 FATAL_ERROR System.AsyncException: Database.executeBatch cannot be called from a batch start,
 batch execute, or future method.
public class futureMethodExample {
 
 @future
 public static void MyFutureMethod(){
 Map<Id,Account> mapacct = new Map<Id,Account>([SELECT ID,Name FROM ACCOUNT]);
 system.debug('test method'+mapacct);
 // MyFutureMethod1(); // You can try to call one future method in another future method
 }
 
 @future(callout=true)
 public static void MyFutureMethod1(){
 Http http = new Http();
 HttpRequest request = new HttpRequest();
 request.setEndpoint('https://th-apex-http-callout.herokuapp.com/animals/');
 request.setMethod('GET');
 // System.debug('>>>>>>>'+id);
 HttpResponse response = http.send(request);
 Object animals;
 String returnValue;
 
 // If the request is successful, parse the JSON response
 if (response.getStatusCode() == 200) {
 // Deserialize the JSON string into collections of primitive data types.
 Map<String, Object> result = (Map<String, Object>) JSON.deserializeUntyped(response.getBody());
 System.debug('>>>>>'+response.getBody());
 animals = result.get('animal');
 System.debug(animals);
 }
 
 BatchClassPractice bcn = new BatchClassPractice() ;
 ID batchprocessid = Database.executeBatch(bcn);
 }
}

6. Can we call future and batch from Queueable ?
Ans: Yes we can call future and batch from Queueable and vice-versa.
public class QueuableApexExample implements Queueable {
public static void execute(QueueableContext QC){
Account act = new Account(Name = 'Tested Accctt 97', Phone='0523124578',
isAddress__c=true);
insert act;
futureMethodExample.MyFutureMethod1();
}
}
7. Which interfaces are important in batch ?
Ans: Database.Batchable - It is mandatory in Batch Class
Database.AllowsCallout - It is mandatory while calling callout
Database.Stateful - This Interfaces is used to maintain the state of variable.Let’s
suppose you are inserting account and you want to count the all account that
are inserted then you can use this interfaces.
8. What are the methods in batch class?
Ans- Start Method >> Return Type ==> Database.Querylocator or Iterable
Execute Method >> Return Type ==> Void
Finish Method >> Return Type ==> Void
9.Some Interfaces in Asynchronous Apex ?
Ans- 1. Queueable,
2. Schedulable,
3. Database.Batchable
10. Can we call future method from Trigger ?
Ans- Yes, We can call future method from trigger.
public class FutureMethodExample2 {
// DML Operation performs on Setup and Non Setup Method in a single transaction
public static void insertSetWithNonSetup(){
Account act = new Account();
act.Name = 'Non Setup Record';
insert act;
//
InsertUserSetup();
System.debug(act);
}
@future
public static void insertUserSetup(){
Profile p = [SELECT Id FROM Profile WHERE Name='Cross Org Data Proxy User'];
UserRole r = [SELECT Id FROM UserRole WHERE Name='COO'];
// Create new user with a non-null user role ID
User usr = new User();
usr.Lastname ='SetupTest';
usr.Alias = 'stest';
usr.email = 'nripeshjoshi97@gmail.com';
usr.ProfileId = p.Id;
usr.UserRoleId = r.Id;
insert usr;
system.debug(usr);
}
}
Trigger AccountTrigger on Account(Before delete, Before update){
 
 if(trigger.isBefore && trigger.isUpdate){
 AccountHandlerClass.updateRelatedContactPhoneField(trigger.new, trigger.oldMap,
 trigger.newMap);
 // FutureMethodExample2.insertUserSetup(); // Calling Future Method
 }
 if(trigger.isBefore && trigger.isdelete){
 AccountHandlerClass.accountDelete(trigger.old);
 }
}
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