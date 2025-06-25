# Apex-Learning
Governor Limits

1. Per-Transaction Apex Limits.

| Description                                   | Synchronous Limit | Asynchronous Limit |
| :-------------------------------------------- | :---------------- | :----------------- |
| Total number of SOQL queries issued           | 100               | 200                |
| Total number of records retrieved by SOQL queries | 50,000          | -                  |
| Total number of records retrieved by Database.getQueryLocator | 10,000       | -                  |
| Total number of SOSL queries issued           | 20                | -                  |
| Total number of records retrieved by a single SOSL query | 2,000        | -                  |
| Total number of DML statements issued         | 150               | -                  |
| Total number of records processed as a result of DML statements, Approval.process, or database.emptyRecycleBin | 10,000       | -
| Total stack depth for any Apex invocation that recursively fires triggers | 100 | - |
