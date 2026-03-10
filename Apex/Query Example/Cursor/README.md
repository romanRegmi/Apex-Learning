## What is Apex Cursor?
* Apex Cursor are a feature that allows us to work with large datasets retrieved using SOQL queries. Unlike SOQL which returns the entire dataset at once, cursors retieve data in manageable chunks, improving performance and memory usage.

* Cursors provide us with the ability to work with large query result sets, while not actually returning the entire result set. We can traverse a query result in parts, with the flexibility to navigate forward and back in the result set.

* Can work synchronously and asynchronously. 

```java
Database.Cursor locator = Database.getCursor('SELECT Id FROM Contact');
List<Contact> scope = locator.fetch(0, 200);  // Start position, Number of records (Max 2000 records)
```

* Apex cursors are stateless and generate results from the offset position that is specified in the cursor.fetch method. The offsets or positions of the results must be tracked within the particular processing scenario. 

* Apex cursors are fully serializable and can be stored and reused acrosss multiple transacations.

### Advantages
#### Reduced Memory Usage
Large SOQL queries can return massive datasets that overload memory. Cursors fetch data in smaller batches, reducing the amount of data loaded into memory at once. This frees up resources and improves overall system performance.

#### Faster Processing
By processing data in manageable chunks, Apex Cursors can iterate through large datasets quicker compared to loading everything at once. This translates to faster execution times for your Apex code that utilizes cursors.

#### Transaction Boundaries
Cursors always operate within a single transactions. This means developers can write Apex cursors to process large amount of data without worrting about exceeding transaction limits.

#### Forward and Backward Navigation
Developers can move forward and backward through the data set. This brings flexibility to manipulate data effectively.

### Pagination Support
Bypass SOQL Query offset limit of 2000.

### Limits
* Max Rows : 50 million per transaction (Sync or Async). 100 million per day
* Max 10k cursors per day.
* Max 10 fetch calls per transaction.
* Cursor is valid for 2 days.

### Interview Questions
#### Does reusing the same CursorId count toward the 100 milion daily limit?
No. The daily limit (100 million records) is calculated when the cursor is first created. That is when Salesforce determines how many records the cursor points to (maximum 50 million per cursor, 100 million per day total).

Calling fetch() does not count toward the 100 million record limit. However, fetch() calls do count toward the 10 fetch calls per transaction limit.

#### What happens if records are deleted between creation ang fetch?
If a record is:
* Updated > You will get the updated values when you call fetch().
* Deleted > That record will no longer be returned.
Example:
- You fetch 5 records.
- One of them gets deleted.
- If you fetch the same range again, you will only receive 4 records.
- If new records are inserted after the cursor was created, they will not be included.

The cursor behaves like a pointer to record Ids. If a record no longer exists, it won't be returned.