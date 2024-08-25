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


Use string.escapeSingleQuotes(qr); in dynamic SOQL. To prevent injection attack

When applying field-level security in a SOQL query, don't use WITH SECURITY_ENFORCED rather use WITH USER_MODE.

Here are the reasons

- WITH SECURITY_ENFORCED is not applied for the fields used in the WHERE clause.
- WITH SECURITY_ENFORCED is not applied for polymorphic fields (Owner, Task.WhatId, etc).
- WITH SECURITY ENFORCED finds only the first error.


If you have to enforce the FLS on data do not follow the legacy approach of using WITH SECURITY_ENFORCED.

Rather, use the new addition to the framework which is WITH USER_MODE (or WITH SYSTEM_MODE).

Here are the reasons.

1. WITH SECURITY_ENFORCED does not apply to the fields in the WHERE clause.
2. It does not apply to Polymorphic fields
3. Throws exception the moment it encounters the first field the user does not have access to. If you want to show the list of all the fields the user does not have access to then this cannot be helpful.
4. It cannot enforce sharing rules.
​
Likewise, using WITH USER_MODE can help us with all these cases.

1. It applies to the fields in the WHERE class as well.
2. It applies to polymorphic fields as well.
3. This can capture all the fields that the user doesn't have access to, rather than throwing an exception the moment it encounters the first inaccessible field.
4. This can enforce sharing rules. 

Access Modifiers in Apex
Private: Can be called inside the same class only.
Public: Can be called inside another class also. 
Protected : If a protected variable or method is declared in class A, the variable can be called in class B iff B extends class A. 

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