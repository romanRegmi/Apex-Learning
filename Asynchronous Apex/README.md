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
1. When will we use future methods instead Of Queueable?
- You can use future methods instead of implementing a Queueable class when your functionality is sometimes executed synchronously and sometimes asynchronously. It's much easier to refactor a method in this manner than converting to a queueable class. This is handy when you discover that part of your existing code needs to be moved to async execution. You can simply create a similar future method that wraps your synchronous method.

2. Can I do callouts from a Queueable Job?
- Yes, you have to implement the Database.AllowsCallouts interface to do callouts from Queueable Jobs.


Q. I have 200 records to be processed using Queueable Apex, How Can I divide the
execution Context for every 100 records?
Similar to future jobs, queueable jobs don't process batches, so you can't divide the execution Context. It will process all 200 records, in atiingle execution Context 

Can we call a future method from a future?
No, it isn't possible. Chaining is only possible in Queueable.

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
