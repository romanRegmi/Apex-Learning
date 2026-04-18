// Get record type Id
Id recordTypeId = Schema.SObjectType.Account.getRecordTypeInfosByName().get('Supplier').getRecordTypeId();

// Prevent cross site scripting in VF pages
Apexpages.currentpages().getParameter().get('url_param').escapeHtml4();

// Get label
Supplier__c.Activity_Code__c.getdescribe().getlabel();