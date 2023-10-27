
public class VerifyPhoneNumbersBatch implements Database.Batchable<sobject>, Database.AllowsCallouts {
    public String objectName;
    public String phoneField;
    public String statusField;
    
    public VerifyPhoneNumbersBatch(String objectName, String phoneField, String statusField){
        this.objectName = objectName;
        this.phoneField = phoneField;
        this.statusField = statusField;
    }
    
    public Database.QueryLocator start(Database.BatchableContext bc){
        String Query = 'Select Id, '+phoneField+' , '+statusField+' From '+objectName;
        return Database.getQueryLocator(Query);
    }
    public void execute(Database.BatchableContext BC, List<sObject> scope){

        System.debug(' *** ' + scope);
        Integer size = scope.size();

        /*
        * @Note : Can make upto 100 callouts in the for loop
        * Use batchsize if you believe that you'll make more than 100 callouts
        */
        for(Integer i=0; i < size; i++){
            String response = VerifyPhoneNumbers.verifyPhone(scope.get(i), phoneField);
            scope.get(i).put(statusField, response); // Each list returned is a dictionary.
        }

        update scope;
    }
    
    public void finish(Database.BatchableContext BC){
        
    }

}

/*
* VerifyPhoneNumbersBatch batch = new VerifyPhoneNumbersBatch('Contact', 'Phone', 'Phone_Status__c');
* Database.executeBatch(batch);
*/