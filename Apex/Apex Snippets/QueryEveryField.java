// This is the object for which we required data.
Map<String, Schema.SObjectField> fieldMap = Opportunity.sObjectType.getDescribe().fields.getMap();
  
// Get all of the fields on the object
Set<String> fieldNames = fieldMap.keySet();
 
// Build a Dynamic Query String.
List<Opportunity> opps = Database.query('select ' + string.join(fieldNames, ',') + ' from Opportunity');

--------------------------------------------------------------------------------------------------------------------

// Best way to convert list of Ids to a set
List<Contact> contacts = [SELECT Id FROM Contact LIMIT 10];
Set<Id> contactIds = (new Map<Id, Contact>(contacts)).keySet().clone();

// How to identify the Relationship type(Master Or Lookup) of a field using its API Name in Apex?
// Getting All the Fields For Lead Object
Map<string, sobjectField> mapFieldSchema = Lead.getsobjectType().getDescribe().fields.getMap();
for(string fieldName : mapFieldschema.keyset()){
  sobjectField field = mapFieldASchema.get (fieldName);
  //getRelationshiporder() will return 0 or 1 for Master Detail Relationship
  // and NULL for Lookup Relationship
  if(field.getDescribe().getRelationshiporder() == 0){
    // your custom logic
 }
  
// Optimize the following code
Opportunity op = [Select StageName, Account.OwnerId From Opportunity Where Id =: opId];
List<OpportunityLineItem> olis = [Select Id, Product2Id from OpportunityLineItem Where OpportunityId =: op.Id];

Opportunity op = [Select StageName, Account.OwnerId, (Select id, Product2Id From OpportunityLineItems) From Opportunity Where id=: opId];

--------------------------------------------------------------------------------------------------------------------

/*
* In a parent to child relationship queries, we write down an inner query. 
* In the inner Query, instead of writing down the object name, we write down the child relationship name.
* If it is a custom object, then we will append this child relationship name with "__r" . 
*/

/*
* Parent - Child
* Inner query inside an inner query is not allowed.
*/

List<Account> accList = [Select Name, NumberOfEmployees, (Select LastName, Phone From Contacts) From Account];
System.debug(accList[3]);
System.debug(accList[3].Contacts); // To access the inner query elements

/* 
* Child - Parent
*/
List<Contact> conList = [Select firstName, lastName, Account.Name, Account.NumberOfEmployees from Contact]; // Account is the relationship field name
System.debug(conList[3].Account.Name);

/*
* Child - Parent
* Fetch Every Opportunity Line Items with the associated Opportunity Amount and Opportunity close date as well as the Account Name and Account Number of Employees.
*/

List<OpportunityLineItem> oppItem =[Select Name, Quantity, Opportunity.Name, Opportunity.Amount, Opportunity.Account.Name, Opportunity.Account.NumberOfEmployees 
                                    From OpportunityLineItems];