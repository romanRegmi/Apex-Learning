/*
 * FOR UPDATE: Whenever someone acquires a resource, they put a lock on it. So that no one can modify it unless the resource is unlocked. 
 * ALL ROWS
 * TIME
*/

/*
   Keep in mind the following best practices when using `FOR UPDATE`:
1. Use it sparingly, only when you have a legitimate need to lock records to prevent data inconsistencies.
2. Avoid running DML operations (e.g., updates, inserts, deletes) within a `FOR UPDATE` query block, as this can lead to performance issues and potential deadlocks.
3. Keep transactions short and ensure that they don't take an excessive amount of time to complete to prevent lock contention.
*/
List<Account> acc = [SELECT Name FROM Account FOR UPDATE];


// This will return all the account object records including the deleted ones. I
List<Account> accList7 = [Select Name from Account ALL ROWS];

// This will return all the deleted account object records.
List<Account> accList8 = [Select Name from Account where isDeleted = true ALL ROWS];

// This will not return any record as we have not used the ALL ROWS keyword.
List<Account> accList9 = [Select Name from Account where isDeleted = true];



List<Account> accList;

// yesterday
accList = [Select Name from Account where createdDate = yesterday];

Date d = Date.newInstance(1995, 09, 14);

// today
accList = [Select Name from Account where createdDate = TODAY];

// tomorrow
accList = [Select Name from Account where EndDate__c = Tomorrow];

// last week
accList = [Select Name from Account where createdDate = last_week];

// last month
accList = [Select Name from Account where createdDate = last_month];

// this week
accList = [Select Name from Account where createdDate = THIS_WEEK];



