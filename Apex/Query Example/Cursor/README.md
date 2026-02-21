## What is Apex Cursor?
Apex Cursor are a new feature that allows us to work with large datasets retrieved using SOQL queries. Unlike SOQL which returns the entire dataset at once, cursors retieve data in manageable chunks, improving performance and memory usage.

Reduced Memory Usage
Large SOQL queries can return massive datasets that overload
memory. Cursors fetch data in smaller batches, reducing the
amount of data loaded into memory at once. This frees up
resources and improves overall system performance.

Faster Processing
By processing data in manageable chunks, Apex Cursors can
iterate through large datasets quicker compared to loading
everything at once. This translates to faster execution times for
your Apex code that utilizes cursors.

Transaction Boundaries
Cursors always operate within a single transactions. This means
developers can write Apex cursors to process large amount of
data without worrting about exceeding transaction limits.

Forward and Backyard Navigation
Developers can move forward and backyard through the data set
which brings the flexibility to manipulate data effectively.

01

Creation

A cursor is created when a SOQL
query is executed on a
Database.getCursor() or
Database.getCursorWithBinds()
call.

04

Exceptions

Apex cursors throw these new
System exceptions:
System.FatalCursorExceptionandS
ystem.TransientCursorException.
Transactions that fail
withSystem.TransientCursorExcep
tioncan be retried.

02

Fetch

When a Cursor.fetch(integer
position, integer count)method is
invoked with an offset position and
the count of records to fetch, the
corresponding rows are returned
from the cursor. The maximum
number of rows per cursor is 50
million, regardless of the operation
being synchronous or
asynchronous.

03

# of Rows

To get the number of cursor rows
returned from the SOQL query, use
Cursor.getNumRecords().

Apex Cursor
Limits

Maximum number of rows per cursor: 50 million (both
synchronous and asynchronous)

Maximum number of fetch calls per transaction: 10 (both
synchronous and asynchronous)

Maximum number of cursors per day: 10,000 (both synchronous
and asynchronous)

technical

salesfor

1

2

1

1

Maximum number of rows per day (aggregate limit): 100 million