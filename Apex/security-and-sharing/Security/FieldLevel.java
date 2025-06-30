if (Schema.sObjectType.Contact.fields.Email.isUpdateable()) {

   // Update contact phone number

}

if (Schema.sObjectType.Contact.fields.Email.isCreateable()) {

   // Create new contact

}

if (Schema.sObjectType.Contact.fields.Email.isAccessible()) {

   Contact c = [SELECT Email FROM Contact WHERE Id= :Id];

}

if (Schema.sObjectType.Contact.isDeletable()) {

   // Delete contact

}


public static Opportunity getFinDetails(Id oppId){
   List<Opportunity> opList = [SELECT Id, Credit_Card_No__c, DP__c FROM Opportunity ];
   SObjectAccessDecision secDesc = Security.stripInaccessible(AccessType.READABLE, opList);
   // Also check object level security
   SObjectAccessDecision secDesc = Security.stripInaccessible(AccessType.READABLE, opList, true);
   Opportunity op = (Opportunity)secDesc.getRecords()[0];
   return op;
}