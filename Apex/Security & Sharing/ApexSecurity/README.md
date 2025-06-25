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



⏩𝐇𝐨𝐰 𝐭𝐨 𝐄𝐧𝐟𝐨𝐫𝐜𝐞 𝐬𝐞𝐜𝐮𝐫𝐢𝐭𝐲 𝐭𝐨 𝐚 𝐬𝐩𝐞𝐜𝐢𝐟𝐢𝐜 𝐦𝐞𝐭𝐡𝐨𝐝 𝐨𝐫 𝐥𝐢𝐧𝐞 𝐨𝐟 𝐜𝐨𝐝𝐞❓

#Apex doesn’t enforce object-level and field-level permissions by default. 

Object permissions & FLS of running user can be enforced by calling the sObject describe result methods (of 𝐒𝐜𝐡𝐞𝐦𝐚.𝐃𝐞𝐬𝐜𝐫𝐢𝐛𝐞𝐒𝐎𝐛𝐣𝐞𝐜𝐭𝐑𝐞𝐬𝐮𝐥𝐭) and the field describe result method (of 𝐒𝐜𝐡𝐞𝐦𝐚.𝐒𝐜𝐡𝐞𝐦𝐚.𝐃𝐞𝐬𝐜𝐫𝐢𝐛𝐞𝐅𝐢𝐞𝐥𝐝𝐑𝐞𝐬𝐮𝐥𝐭)

We can verify whether the 𝐥𝐨𝐠𝐠𝐞𝐝 𝐢𝐧 𝐮𝐬𝐞𝐫 has permissions to perform the DML or query.

Enforcing these methods require more work as a developer and occupy more lines of code.

The following methods from the Schema.DescribeSObjectResult can be called to verify whether the user has 𝐫𝐞𝐚𝐝, 𝐜𝐫𝐞𝐚𝐭𝐞, 𝐝𝐞𝐥𝐞𝐭𝐞 𝐨𝐫 𝐮𝐩𝐝𝐚𝐭𝐞 𝐚𝐜𝐜𝐞𝐬𝐬 𝐨𝐧 𝐭𝐡𝐞 𝐨𝐛𝐣𝐞𝐜𝐭.

🔯 𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞
🔯 𝐢𝐬𝐂𝐫𝐞𝐚𝐭𝐞𝐚𝐛𝐥𝐞
🔯 𝐢𝐬𝐔𝐩𝐝𝐚𝐭𝐞𝐚𝐛𝐥𝐞
🔯 𝐢𝐬𝐃𝐞𝐥𝐞𝐭𝐚𝐛𝐥𝐞


𝙃𝙚𝙧𝙚’𝙨 𝙖 𝙨𝙖𝙢𝙥𝙡𝙚 𝙘𝙤𝙣𝙛𝙞𝙜𝙪𝙧𝙖𝙩𝙞𝙤𝙣:

if (Schema.SObjectType.Account.𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞() &&
 Schema.SObjectType.Account.fields.Name.𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞() &&
 Schema.SObjectType.Account.fields.Id.𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞() &&
 Schema.SObjectType.Account.fields.OwnerId.𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞() &&
 Schema.SObjectType.Account.fields.Rating.𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞() &&
 Schema.SObjectType.Account.fields.OwnerId.getReferenceTo().get(0).getDescribe().𝐢𝐬𝐀𝐜𝐜𝐞𝐬𝐬𝐢𝐛𝐥𝐞()) {
 return [SELECT Id, Name, Rating, Owner.Name FROM Account];
 } else {
 throw new AuraHandledException('Access Denied');
 }



🧠 Mastering Apex Security in Salesforce — WITH USER_MODE vs WITH SECURITY_ENFORCED vs stripInaccessible() 🔐⚙️

🔷 1️⃣ Understanding Field & Object-Level Security — Why It Matters 🔎
✅ Apex runs in system mode by default, meaning it ignores field- and object-level security (FLS/OLS) unless explicitly handled
✅ To comply with data visibility rules and avoid exposing sensitive data, you must enforce these manually
💡 Tip for Admin: Apex bypasses security unless you instruct it otherwise. This is critical in managed packages, public sites, or API integrations.

🔷 2️⃣ stripInaccessible() — Clean Your Data After You Query 🧹
✅ Filters out fields not accessible to the current user (FLS/OLS) after a query or DML
✅ Used for read, create, and update operations

// Example: Read-safe query
```
Account acc = [SELECT Id, Name, AnnualRevenue FROM Account LIMIT 1];
SObjectAccessDecision decision = Security.stripInaccessible(AccessType.READABLE, acc);
Account secureAcc = (Account)decision.getRecords()[0];
🧠 Best Insight: Bulk-safe sanitization of queried records before sending to UI or downstream logic.
```

```
public static Opportunity getFinDetails(Id oppId){
    String soql = 'SELECT Purchase_Order_No__c, Discount_Percentage__c';
    if (Schema.sObjectType.Opportunity.fields.Credit_Card_No__c.isAccessible()) {
        soql += ', Credit_Card_No__c';
    }
    soql += ' from Opportunity WHERE Id =: oppId';
    return (Database.query(String.escapeSingleQuotes(soql))); //prevent injection attack
}
```


🔷 3️⃣ WITH SECURITY_ENFORCED 🔐
✅ Enforces FLS and OLS at the SOQL level
✅ Throws a runtime exception if a field or object is inaccessible
✅ Available in Apex SELECT queries only
- WITH SECURITY_ENFORCED is not applied for the fields used in the WHERE clause.
- WITH SECURITY_ENFORCED is not applied for polymorphic fields (Owner, Task.WhatId, etc).
- WITH SECURITY ENFORCED finds only the first error.
WITH SECURITY_ENFORCE cannot enforce sharing rules.
If any of the fields or objects access is not there for the user, then a query Exception is thrown. No data is returned.

// Safe SOQL
Account[] accs = [SELECT Id, Name FROM Account WITH SECURITY_ENFORCED];
⚠️ Caution: This throws an exception if the user doesn’t have access. Use try-catch to gracefully handle failures in custom UIs. When applying field-level security in a SOQL query, don't use WITH SECURITY_ENFORCED rather use WITH USER_MODE.
🧠 Best Insight: Precise Apex queries with explicit access enforcement and failure detection.

🔷 4️⃣ WITH USER_MODE
✅ Used with Database.insert(), update(), delete() and similar methods
✅ Enforces FLS, OLS, and sharing rules automatically — mimics running “as user”
✅ Prevents insertion/update of inaccessible fields without manual checks
1. It applies to the fields in the WHERE class as well.
2. It applies to SOSL and polymorphic queries as well.
3. This can capture all the fields that the user doesn't have access to, rather than throwing an exception the moment it encounters the first inaccessible field.
4. This can enforce sharing rules.
5. The WITH USER_MODE supports lots of new innovations like restriction rules, scoping rules, and any other security operations for data access and CRUD/FLS that may be added by the platform in the future.

// Safe insert with user-mode DML
Account newAcc = new Account(Name='Acme', AnnualRevenue=2000000);
Database.insert(newAcc, new Database.InsertOptions(Database.UserMode.ENFORCED));
🧠 Best Insight: Declarative-safe DML operations where system mode isn’t appropriate — great for triggers, flows, LWC controller classes.
🧠 My Best Practices for Apex Developers:
✔️ Always use FLS checks in Apex — Apex ignores security by default
✔️ Prefer WITH SECURITY_ENFORCED for clean query failures, and stripInaccessible() for silent sanitization
✔️ Use UserMode.ENFORCED in controller classes or flow-triggered invocable Apex
✔️ Add test methods to assert proper enforcement of user visibility (runAs scenarios)
✔️ Document which security method is used where — make your code auditable


What is a Trace Flag?
A Trace Flag is a special setting in Salesforce that captures detailed information about the execution of Apex code. It records various events, such as the execution of Apex methods, SOQL queries, and DML operations, and writes them to a log file. Developers can then use this log file to analyze the performance of their code and identify any issues or bottlenecks. Trace Flags can be enabled for a specific user, set of users, or for the entire organization.


Where to use Trace Flags?
Trace Flags are commonly used for debugging and troubleshooting Apex code in the following scenarios:


Debugging Apex code — Trace Flags help developers identify and resolve issues with Apex code by providing a detailed log of its execution.
Analyzing performance — Trace Flags allow developers to monitor the performance of Apex code and identify any bottlenecks or inefficiencies.
Debugging issues with integrations — Trace Flags can be used to debug issues with integrations between Salesforce and other systems.



In Salesforce development, ensuring data security and proper permissions are essential to maintaining the integrity and confidentiality of your data. Three key methods to enforce security are isAccessible(), with User Mode, and with SECURITY_ENFORCED. This article provides a detailed comparison of these methods, exploring their use cases, advantages, and limitations.


1. Using isAccessible()
Overview
isAccessible() is a method used to check if the current user has access to a specific field on an SObject. This method is part of the Salesforce schema class and is used extensively in Apex code to ensure field-level security.


Usage
To use isAccessible(), you need to perform an explicit check before accessing the field value. This method returns a Boolean value indicating whether the field is accessible.


Example:


if (Schema.SObjectType.Account.fields.Name.isAccessible()) {
String accountName = myAccount.Name;
}
Pros
Granular Control: isAccessible() provides fine-grained control over field-level security, allowing you to check permissions on a per-field basis.
Flexibility: It can be used in various contexts, including triggers, controllers, and batch classes.
Explicit Security Checks: By using isAccessible(), developers can ensure that security checks are explicitly performed, making the code’s intent clear.
Cons
Verbosity: Requires explicit checks for each field access, which can lead to verbose and cluttered code.
Potential for Human Error: Since each field access needs to be checked manually, there is a risk of missing some checks, leading to potential security vulnerabilities.
2. Using with User Mode
Overview
with User Mode is a keyword used in Salesforce SOQL and SOSL queries to ensure that all field- and object-level security is enforced when accessing data. This mode ensures that the query results only include data that the current user is permitted to see.


Usage
with User Mode is appended to a SOQL or SOSL query to enforce security rules automatically.


Example:


List<Account> accounts = [SELECT Name FROM Account WITH USER_MODE];
Pros
Automatic Enforcement: Automatically enforces security rules, reducing the likelihood of missing checks.
Simplifies Code: Removes the need for explicit security checks, resulting in cleaner and more maintainable code.
Consistent Security: Ensures that all queries respect the user’s permissions consistently.
Cons
Limited Scope: Only applicable to SOQL and SOSL queries, so it cannot be used in all code contexts.
Less Granular Control: Does not provide the same level of granular control as isAccessible() for field-level security checks outside of queries.
3. Using with SECURITY_ENFORCED
Overview
with SECURITY_ENFORCED is a clause that can be added to SOQL queries to enforce both field- and object-level security. It ensures that the query respects the security settings configured in the Salesforce org.


Usage
with SECURITY_ENFORCED is appended to a SOQL query to enforce security rules automatically.


Example:


List<Account> accounts = [SELECT Name FROM Account WITH SECURITY_ENFORCED];
Pros
Comprehensive Security: Ensures that both field- and object-level security are enforced, providing a robust security mechanism.
Automatic Enforcement: Similar to with User Mode, it automatically applies security checks, reducing the risk of human error.
Simplifies Code: Helps keep the code clean and maintainable by removing the need for explicit security checks.
Cons
Limited Scope: Only applicable to SOQL queries, so it cannot be used for non-query contexts.
Less Flexibility: Does not provide control over individual field-level security checks outside of queries.
Comparison Summary


Recommendation
For comprehensive security coverage and minimal risk of missing checks, it is often best to combine the use of isAccessible() with with SECURITY_ENFORCED. This approach ensures that both field-level and object-level security are consistently enforced:


Use isAccessible() for field-level security checks in non-query contexts.
Use with SECURITY_ENFORCED in SOQL queries to automatically enforce security rules.
Combined Example:


if (Schema.SObjectType.Account.fields.Name.isAccessible()) {
List<Account> accounts = [SELECT Name FROM Account WITH SECURITY_ENFORCED];
for (Account acc : accounts) {
// Process account
}
}
By combining these methods, you can create a robust security framework that protects your Salesforce data and adheres to best practices, ensuring compliance and reducing vulnerabilities in your org.