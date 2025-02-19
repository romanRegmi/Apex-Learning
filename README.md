# Apex-Learning

Limitations of Salesforce Apex
→ SOQL Queries and Records Retrieval
→ General Apex Limits
→ DML Statements and Records Processing
→ CPU Time and Execution Time
→ CPU Time and Callouts
→ Callouts
→ Heap Size
→ Email Invocations
→ Governor Limits
→ Bulk API
→ Additional Considerations
→ Scheduled Apex
→ Optimizing Code Efficiency
SIZE() Method Will work only when Query has less than 200 Child records for each Parent.




🚀Some popular Salesforce errors and their resolutions :🚀

1️⃣.𝗦𝗢𝗤𝗟/𝗦𝗢𝗦𝗟 𝗟𝗶𝗺𝗶𝘁𝘀 𝗘𝘅𝗰𝗲𝗲𝗱𝗲𝗱 : Occurs when querying or searching for records exceeds governor limits. Resolution involves optimizing queries, reducing the number of records retrieved, or using asynchronous processing for large datasets.

2️⃣. 𝗔𝗽𝗲𝘅 𝗖𝗣𝗨 𝗧𝗶𝗺𝗲 𝗟𝗶𝗺𝗶𝘁 𝗘𝘅𝗰𝗲𝗲𝗱𝗲𝗱 : Happens when Apex code execution exceeds the CPU time limit. Resolution involves optimizing code, reducing loops, and offloading heavy processing to asynchronous methods like Batch Apex.

3️⃣. 𝗗𝗠𝗟 𝗘𝘅𝗰𝗲𝗽𝘁𝗶𝗼𝗻𝘀 (𝗲.𝗴., 𝗗𝗨𝗣𝗟𝗜𝗖𝗔𝗧𝗘_𝗩𝗔𝗟𝗨𝗘): Thrown when performing Data Manipulation Language (DML) operations like insert, update, or upsert encounters issues like duplicate values. Resolution involves handling exceptions, implementing duplicate management rules, or using upsert operations with external IDs.

4️⃣. 𝗚𝗼𝘃𝗲𝗿𝗻𝗼𝗿 𝗟𝗶𝗺𝗶𝘁 𝗘𝘅𝗰𝗲𝗽𝘁𝗶𝗼𝗻𝘀 (𝗲.𝗴., 𝗤𝗨𝗘𝗥𝗬_𝗧𝗢𝗢_𝗖𝗢𝗠𝗣𝗟𝗜𝗖𝗔𝗧𝗘𝗗): Thrown when Apex code exceeds various Salesforce governor limits like query rows, heap size, or CPU time. Resolution involves optimizing code, using efficient algorithms, and considering bulkification techniques.

Bulkification describes the concept of making sure automated operations are optimized
to handle multiple records in order to avoid hitting govenor limits.
. When large sets of data are passed to Salesforce (e.g. through Data Load) they will be
processed in batches (max. batch size = 200 records)
. Each batch will be processed within one transaction, which is bound by the govenor limits

17. What are Apex Best practices in Salesforce?

Apex code is used to write custom and robust business logic. As with any language, there are key coding principles
and best practices that will help you write efficient, scalable code. Check our Apex best practices of Salesforce.

1. Bulkify Apex Code
2. Avoid SOQL & DML inside for Loop
3. Querying Large Data Sets
4. Use of Map of Sobject
5. Use of the Limits Apex Methods
6. Avoid Hardcoding IDs
7. Use Database Methods while doing DML operation
8. Exception Handling in Apex Code
9. Write One Trigger per Object per event
10. Use Asynchronous Apex


When using the with sharing keyword you are enforcing record access, unlike the WITH_SECURITY_ENFORCED clause which is used to enforce Field and Object level security.


NOTE : Salesforce may update functionalities and limitations in every release. Therefore, we must also refer to the official documentation regarding most of what is presented here.

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



In Salesforce, the ContentDocumentLink object is used to associate a content document (such as a file or attachment) with another Salesforce object. The ContentDocumentLink object has two important fields related to access control: ShareType and Visibility. Here's a breakdown of the differences between these two fields:

ShareType
Definition: The ShareType field specifies the level of access that an entity (such as a user or group) has to a content document via the link.
Values:
V (Viewer): The user or group can view the document but cannot make changes.
C (Collaborator): The user or group can both view and edit the document.
I (Inferred Read): Indicates that the user's access to the document is inferred by an existing permission model. This means the access is determined by the sharing rules or hierarchy.
P (Inferred Edit): Similar to I, but implies inferred edit permissions.
Use Case: Use ShareType to control the access rights (view or edit) of the document for different users or groups.
Visibility
Definition: The Visibility field governs the access level of the content document to the users who have access to the linked object.
Values:
AllUsers: The document is visible to all users who have access to the linked record.
InternalUsers: The document is only visible to internal users. This setting hides the document from external users, even if the external user has access to the linked record.
SharedUsers: The document is visible to users who have been explicitly granted access.
Use Case: Use Visibility to control the exposure of the document in contexts such as external sharing scenarios or when certain documents should only be visible to a subset of users who can access the associated record.
Summary
ShareType is primarily concerned with the permissions related to viewing or editing the document itself.
Visibility affects who among those with access to the linked record can see the document in the first place.
These fields allow you to finely tune who can see and interact with documents linked to records in Salesforce, supporting a range of sharing and collaboration scenarios.