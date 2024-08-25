| SOQL  |  SOSL |
|---|---|
| We know in which object the data resides in | We are unsure in which object the data resides in |
| SOQL queries can be used in Classes and Triggers | Only supported in Apex Classes and Anonymous Block |
| We can perform DML operations on the queried results | We cannot perform dml operations |
| It returns records | It returns fields |
| We can count the retrieved records | We cannot count the retrieved records |

SOQL injection
Avoid using dynamic SOQL where possible, instead use static queries and binding variables
If you must use dynamic SOQL, use the escapeSingleQuotes method to sanitize user-supplied input.

1. Building Efficient & Selective Queries
For best performance, SOQL queries must be selective. You may receive an error message when a non-selective query in a trigger executes against an object that contains more than 100,000 records. To avoid this error, ensure that the query is selective. A query is selective when one of the query filters is on an indexed field and the query filter reduces the resulting number of rows below a system-defined threshold.
For all standard and custom tables, certain fields are automatically flagged to be indexed. These fields include the following:
Id
Name
OwnerId
CreatedDate
SystemModStamp
RecordType
Master-Detail Fields
Loopup Field
Unique Fields
External ID Fields
Anytime you use one of above indexed fields in your query’s WHERE clause, you’re increasing the chance that your query is considered selective and an index used as opposed to a full table scan. Salesforce.com Support can add custom indexes on request for customers.
2. Common Causes of Non-Selective SOQL Queries
Using an indexed field in your query doesn’t always make it golden. There are several factors that can prevent your SOQL queries from being selective. When building your queries, always strive to avoid these things.
Querying for null rows
Queries that look for records in which the field is empty or null. For example: SELECT Id, Name FROM Account WHERE Custom_Field__c = null
Negative filter operators
Using operators such as !=, NOT LIKE, or EXCLUDES in your queries. For example: SELECT CaseNumber FROM Case WHERE Status != ‘Closed’ Please use like this SELECT CaseNumber FROM Case WHERE Status IN (‘Open’ , 'In Progress')
Leading wildcards
A LIKE condition with a leading % wildcard does not use an index. such as this: SELECT Id, LastName, FirstName FROM Contact WHERE LastName LIKE ‘%smi%’
Text fields with comparison operators
Using comparison operators, such as >, <, >=, or <=, with text-based fields. For example: SELECT AccountId, Amount FROM Opportunity WHERE Order_Number__c > 10
3. Query Optimizer
Developer Console contains a neat little tool to speed up your queries. It gives you a behind-the-scenes peek into how the Query Optimizer works. The Query Plan tool isn’t enabled by default. Enable it by doing the following.
From Setup, select Your Name > Developer Console to open Developer Console.
In theDeveloper Console, select Help > Preferences.
Select Enable Query Plan and make sure that it’s set to true.
Click Save.
In the Query Editor tab, confirm that the Query Plan button is now next to the Execute button.
4. Avoiding querying on formula fields
Avoid filtering with formula fields that contain dynamic, non-deterministic references. By default, formula fields don’t have underlying indexes, so they require full scans to find target records. Since the Winter ’13 release, you have been able to contact salesforce.com Customer Support to create a custom index on a formula field, provided that the function that defines the formula field is deterministic.
But here are examples of common non-deterministic formulas. Force.com cannot index fields that:
Reference other entities (i.e., fields accessible through lookup fields)
Include other formula fields that span over other entities
Use dynamic date and time functions (e.g., TODAY, NOW)
5. Custom Indexes Containing null Rows
You can connect with salesforce.com customer support to create custom indexes that include null rows. Even if you already have custom indexes on your custom fields, they need to be explicitly enabled and rebuilt to get the empty-value rows into index tables. Note that this option does not apply to picklists, external IDs, and foreign key fields. If you need to query on a null external ID field, you can work with salesforce.com Customer Support to create a two-column (compound) index instead.
