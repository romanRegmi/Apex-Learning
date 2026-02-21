# Salesforce SOQL & SOSL Guide

This guide covers **SOQL** (Salesforce Object Query Language) and **SOSL** (Salesforce Object Search Language), their key differences, and best practices for writing performant, **selective** queries to avoid governor limits and timeouts.

## SOQL vs SOSL Comparison

| Aspect                              | SOQL (Salesforce Object Query Language)                          | SOSL (Salesforce Object Search Language)                          |
|-------------------------------------|-------------------------------------------------------------------|-------------------------------------------------------------------|
| **Purpose**                         | Retrieve records from **one specific object** (or related objects via relationships) with precise filters. | Perform **text-based searches** across **multiple objects** simultaneously when you don't know the exact object/field. |
| **Known object location**           | Yes – you specify the object (e.g., `FROM Account`).             | No – searches across many objects at once.                        |
| **Usage contexts**                  | Apex classes, triggers, Visualforce controllers, REST/SOAP APIs, anonymous Apex, etc. | Apex classes only (not triggers), anonymous Apex blocks.          |
| **DML on results**                  | Yes – you can perform DML on queried records (with care).        | No – SOSL returns search results; no direct DML support.          |
| **What is returned**                | List of **records** (SObjects) with specified fields.            | List of **lists** – each inner list contains records from one object (up to 2,000 total records). |
| **Counting records**                | Yes – use `COUNT()` or `COUNT()` with `GROUP BY`.                | No native COUNT; results are ranked by relevance.                 |
| **Performance for text search**     | Slower for broad text searches (requires full scans if not indexed). | Faster for text/phrase searches due to search index and tokenization. |
| **Governor limits**                 | 100 queries (sync) / 200 (async); 50,000 rows total.             | 20 queries; 2,000 rows total per query.                           |

**When to use which?**
- Use **SOQL** for known objects, precise conditions, relationships, aggregations, or when you need full records.
- Use **SOSL** for global text search (e.g., find "Tesla" in Account, Contact, Opportunity names/emails/phones), especially in search UIs.

## SOQL Injection Prevention

SOQL injection occurs when user input is concatenated into dynamic SOQL strings, allowing malicious input to alter the query.

**Best practices**:
- Prefer **static SOQL** with bind variables (`:variable`).
- Avoid dynamic SOQL unless necessary.
- If dynamic SOQL is required:
  - Use `String.escapeSingleQuotes()` on user input.
  - Or better: use `Database.queryWithBinds()` (API 58.0+) for safer binding.

```java
// Good: Static SOQL with bind variable
String searchTerm = '%' + String.escapeSingleQuotes(userInput) + '%';
List<Contact> contacts = [SELECT Id, Name FROM Contact WHERE Name LIKE :searchTerm];

// Better: Dynamic with binds (safer)
String dynamicQuery = 'SELECT Id, Name FROM Contact WHERE Name LIKE :searchTerm';
List<Contact> contacts = Database.queryWithBinds(dynamicQuery, new Map<String, Object>{'searchTerm' => searchTerm}, AccessLevel.USER_MODE);
```
## Writing Efficient & Selective SOQL Queries
Non-selective queries on large objects (> ~100,000–200,000 records) can cause "Non-selective query against large object type" errors in triggers or timeouts.
A query is selective if at least one filter uses an indexed field and reduces rows returned below a system threshold (varies by object size/edition).

Salesforce automatically indexes:

* Id
* Name (most objects)
* OwnerId
* CreatedDate
* SystemModstamp (LastModifiedDate equivalent)
* RecordTypeId
* Master-detail and lookup fields (foreign keys)
* Unique fields
* External ID fields (when marked)

Custom indexes can be requested via Salesforce Support for other fields (e.g., deterministic formula fields, high-cardinality custom fields).
Common Causes of Non-Selective Queries (Avoid These)


```java
//Filtering on null valuesSQL--Bad: Full table scan
[SELECT Id FROM Account WHERE Custom_Field__c = null] //Indexes do not store NULLs by default.

//Negative operators (!=, NOT IN, NOT LIKE, EXCLUDES) SQL-- Bad
[SELECT CaseNumber FROM Case WHERE Status != 'Closed']

//Better: Positive filtersSQL
[SELECT CaseNumber FROM Case WHERE Status IN ('Open', 'In Progress', 'New')]

//Leading wildcards in LIKESQL-- Bad: Cannot use index
[SELECT Id FROM Contact WHERE LastName LIKE '%smi%']

//Good: Trailing wildcards onlySQL
[SELECT Id FROM Contact WHERE LastName LIKE 'Smi%']

//Filtering on non-deterministic formula fields. Cannot index if formula uses TODAY(), NOW(), cross-object references, or other dynamic elements.
```
## Use the Query Plan Tool
Evaluate selectivity in Developer Console.

1. Open Developer Console (from Setup → Your Name → Developer Console).
2. Go to Help > Preferences.
3. Set Enable Query Plan to true → Save.
4. In Query Editor, write your SOQL → click Query Plan button.

Interpret

1. Look for lowest cost plan.
2. Prefer plans using Index (good) over TableScan (bad).
3. Check Cardinality (rows scanned) – aim low.
4. Avoid Notes like "operator not optimizable".

## Additional Best Practices

1. Query only needed fields `(SELECT Id, Name not SELECT *)`.
2. Avoid SOQL in loops → bulkify outside loops.
3. Use relationship queries for parent-child data in one call.
4. Monitor via debug logs, Query Plan, and Event Log Files.
5. For very large data: consider Big Objects or archiving.