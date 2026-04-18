// Create Parent and Child Together
Child__c childRecord = new Child__c();

// Create a parent instance, only for foreign key reference. 
// Here you are not supposed to include any other fields except External- 
// Id field. This has to be assigned for foriegn key reference on child
Parent__c parentRefRecord = new Parent__c(External_ID__c = 'Parent0001');                
childRecord.Parent__r = parentRefRecord;

// Create a new instance of parent record, this is to insert along with child record. 
// You can include any other fields along with external id here as needed.
Parent__c parentRecord = new Parent__c(External_ID__c = 'Parent0001',Active__c = true);    

// Insert parent and child records together.
Database.insert(new SObject[] {parentRecord, childRecord }); 

--------------------------------------------------------------------------------------------------------------------

// Debugging
System.debug(JSON.serializePretty([SELECT Id, NAME FROM Account LIMIT 100]));

--------------------------------------------------------------------------------------------------------------------
// UUID
String uuid = (String)((Map<String, Object>) JSON.deserializeUntyped(new Auth.JWT().toJSONString())).get('jti');

Id recId = '0055g00000IU82tAAD';
String recAPIName = recId.getSObjectType().getDescribe().getName();

--------------------------------------------------------------------------------------------------------------------

Map<String, Map<String, String>> countryStateCityMap = new Map<String, Map<String, String>>
{'India'=>new Map<String, String>{'Gujarat'=>'Surat'}};

//WITH USING OPERATORS
String myCity = countryStateCityMap?. get('India')?. get('Gujarat') ?? 'Delhi';

--------------------------------------------------------------------------------------------------------------------

/*
* Suppose you have two lists of custom objects, one representing products and the other representing orders, and you want to associate orders with the products they contain based on a common product ID.
*/

/* Without using Map */
List<Product__c> products = [SELECT Id, Name FROM Product__c];
List<Order__c> orders = [SELECT Id, Product__c FROM Order__c];

Map<Id, Product__c> productMap = new Map<Id, Product__c>();
for (Product__c product : products) {
    productMap.put(product.Id, product);
}

for (Order__c order : orders) {
    for (Product__c product : products) {
        if (order.Product__c == product.Id) {
            // Associate the order with the product
            System.debug('Order ID: ' + order.Id + ' contains Product: ' + product.Name);
        }
    }
}

/* With Map */
List<Product__c> products = [SELECT Id, Name FROM Product__c];
List<Order__c> orders = [SELECT Id, Product__c FROM Order__c];

Map<Id, Product__c> productMap = new Map<Id, Product__c>();
for (Product__c product : products) {
    productMap.put(product.Id, product);
}

for (Order__c order : orders) {
    if (productMap.containsKey(order.Product__c)) {
        Product__c product = productMap.get(order.Product__c);
        // Associate the order with the product
        System.debug('Order ID: ' + order.Id + ' contains Product: ' + product.Name);
    }
}

--------------------------------------------------------------------------------------------------------------------

List<PermissionSetAssignment> assignmentsToDelete = [SELECT Id FROM PermissionSetAssignment 
                                                    WHERE PermissionSet.Name = :permissionSetName 
                                                    AND AssigneeId = :assigneeId];

--------------------------------------------------------------------------------------------------------------------