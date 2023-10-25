/*
* Develop a simple Batch Apex class that accomplishes the following tasks:
* a. Updates the Contact's Fax with the Account's Fax if the Contact's Fax is null.
* b. Updates the Contact's Mobile Phone with the Account's Phone if the Contact's Phone is null.
* c. Updates the Contact's Email with "romanregmi@hotmail.com".
* d. Updates the Contact's Description with a combination of Account Name, Account Industry, Rating, and Contact Email.
* e. Sends a notification to the user (romanregmi@hotmail.com) when the batch has executed successfully (for email updates only).
* f. Utilizes the Database.Stateful interface to provide information about successful and failed records.
*/

global class ContactBatch implements Database.Batchable<sObject>, Database.Stateful {

    global Integer total_size = 0;
    global Integer fail_size = 0;
    global Integer success_size = 0;

    global Database.QueryLocator start(Database.BatchableContext bc) {
        // Define and return a query locator for the records you want to process.
        String query = 'Select Id, Name, Fax, Email, MobilePhone, Description, Account.Name, Account.Rating, Account.Phone, Account.Fax, Account.Industry From Contact';


        List<AsyncApexJob> apexJobs = [SELECT Id, ApexClassId, ApexClass.Name, NumberOfErrors, JobItemsProcessed, TotalJobItems, Status, JobType
                                        FROM AsyncApexJob
                                        WHERE Id =:bc.getJobId()];


        System.debug(' #### Batch status start method ' + apexJobs); 

        return Database.getQueryLocator(query); // used with batch apex
    }

    global void execute(Database.BatchableContext bc, List<Contact> contactList) {


        List<AsyncApexJob> apexJobsChild = [SELECT Id, ApexClassId, ApexClass.Name, NumberOfErrors, JobItemsProcessed, TotalJobItems, Status, JobType
                                        FROM AsyncApexJob WHERE Id =:bc.getChildJobId()];


        System.debug(' #### Batch status execute method ' + apexJobsChild); // use debug logs

        Integer size = contactList.size();
        total_size = total_size + size;

        
        for (Integer i = 0; i < size; i++) {
            Contact con = contactList[i];
            
            if (con.Fax == null) {
                con.Fax = con.Account.Phone;
            }
            
            if (con.MobilePhone == null) {
                con.MobilePhone = con.Account.Phone;
            }
            
            con.Email = 'romanregmi@hotmail.com';
            
            con.Description = con.Account.Name + ' ' + con.Account.Rating + ' ' + con.Account.Industry + ' ' + con.Email;
        }

        //update contactList;
        Database.SaveResult[] result = Database.update(contactList, false);
        for (Integer i = 0; i < result.size(); i++) {
            Database.SaveResult sr = result.get(i);
            if (sr.isSuccess()) {
                success_size = success_size + 1;
                } else {
                    fail_size = fail_size + 1;
            }
        }
    }


    global void finish(Database.BatchableContext bc) {

        List<AsyncApexJob> apexJobs = [SELECT Id, ApexClassId, ApexClass.Name, NumberOfErrors, JobItemsProcessed, TotalJobItems, Status, JobType
                                        FROM AsyncApexJob WHERE Id =:bc.getJobId()];

        System.debug(' #### Batch status finish method ' + apexJobs); // use debug logs

        Messaging.SingleEmailMessage email = new Messaging.SingleEmailMessage();
        email.setSubject('Status of Batch Class: ContactBatch');
        email.setSenderDisplayName('Batch Class Demo');
        email.setHtmlBody('User, </br> Batch processed' + 
                            '<br/> Total Records : ' + total_size + 
                            ' <br/> Success Count : ' + success_size + 
                            ' <br/> Fail Count : ' + fail_size);
        List<String> emailTo = new List<String>();
        emailTo.add('romanregmi251@gmail.com');
        email.setToAddresses(emailTo);

        Messaging.SingleEmailMessage[] emailList = new List<Messaging.SingleEmailMessage>();
        emailList.add(email);
        Messaging.sendEmail(emailList, true); // Rollback on error.
    }

}


/*
* ContactBatch batch = new ContactBatch();
* Final Integer BATCH_SIZE = 2;
* Id batchId = Database.executeBatch(batch);
* Id batchId = Database.executeBatch(batch, BATCH_SIZE); // With batch size
*/