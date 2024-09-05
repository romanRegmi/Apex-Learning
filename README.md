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