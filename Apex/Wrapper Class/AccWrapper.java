/*
* A wrapper class in an apex class with a collection of different data types.
* It allows us to combine different data types and use them for specific purposes.
*/

public with sharing class AccWrapper {
    @AuraEnabled(cacheable=true)
    public static List<AccountWrapper> getAccountWithContact(){
        List<AccountWrapper> wrapList = new List<AccountWrapper>();
        List<Account> accList = [SELECT Id,Name, (SELECT Id, FirstName, LastName FROM Contacts) FROM Account LIMIT 10];

        for (Account acc : accList){
            wrapList.add(new AccountWrapper(acc, acc.Contacts));
        }

        if(!accList.isEmpty()){
            return wrapList;
        }
    }

    //Wrapper class
    public class AccountWrapper{
        @AuraEnabled public Account accRecord{get; set;}
        @AuraEnabled public List<Contact> contactList{get; set;}

        // Constructor
        public AccountWrapper(Account accRecord, List<Contact> contactList){
            this.accRecord = accRecord;
            this.contactList = contactList;
        }
    }
}

List<AccWrapper.AccountWrapper> accList = AccWrapper.getAccountWithContact();
System.debug(accList);

for(AccountWrapperDemo.AccountWrapper acc : accList) {
    System.debug(acc.accRecord);
    System.debug(acc.contactList);
}