<h1>Apex Security<h3>

<h3>Private access modifiers</h3>

* Can only be called inside the same class.
* This is the default access modifier for inner class. If we don't specify any access modifier for an inner class, it is considered Private.
* Top level classes can have either public or global but cannot have private access modifiers.
* Top level classes can have a private access modifier if it is used with `@isTest` annotation.

<h3>Public access modifiers</h3>

* Public class is visible in the application or namespace and can be called from other classes in the same namespace.

<h3>Protected access modifiers</h3>

* If a protected variable or method is declared in class A, the variable can be called in class B iff B extends class A. 

<h3>Global access modifiers</h3>

* Global class is visible everywhere in the org.
* A class with webservice method must be declared as global.
* If a method or inner class is declared as global then the outer, top-level class must also be defined as global.

When applying field-level security in a SOQL query, don't use WITH SECURITY_ENFORCED rather use WITH USER_MODE.

Here are the reasons

- WITH SECURITY_ENFORCED is not applied for the fields used in the WHERE clause.
- WITH SECURITY_ENFORCED is not applied for polymorphic fields (Owner, Task.WhatId, etc).
- WITH SECURITY ENFORCED finds only the first error.
4. It cannot enforce sharing rules.


If you have to enforce the FLS on data do not follow the legacy approach of using WITH SECURITY_ENFORCED.

Rather, use the new addition to the framework which is WITH USER_MODE (or WITH SYSTEM_MODE).

​
Likewise, using WITH USER_MODE can help us with all these cases.

1. It applies to the fields in the WHERE class as well.
2. It applies to SOSL and polymorphic queries as well.
3. This can capture all the fields that the user doesn't have access to, rather than throwing an exception the moment it encounters the first inaccessible field.
4. This can enforce sharing rules.
5. The WITH USER_MODE supports lots of new innovations like restriction rules, scoping rules, and any other security operations for data access and CRUD/FLS that may be added by the platform in the future.


If any of the fields or objects access is not there for the user, then a query Exception is thrown. No data is returned.




Use string.escapeSingleQuotes(qr); in dynamic SOQL. To prevent injection attack




```
public void doNothing(){

}

@AuraEnabled
public static Opportunity getFinDetails(Id oppId){
    String soql = 'SELECT Purchase_Order_No__c, Discount_Percentage__c';
    if (Schema.sObjectType.Opportunity.fields.Credit_Card_No__c.isAccessible()) {
        soql += ',Credit_Card_No__c';
    }
    soql += ' from Opportunity WHERE Id =: oppId';
    return (Database.query(soql));
}
```
