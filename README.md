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


What Are Apex Design Patterns?
Design patterns are best practices and proven solutions to to recurring design problems encountered during software development. These design patterns serve as guiding principles, aiding in structuring and organizing code to enhance efficiency, maintainability, and scalability of applications. The patterns not only streamline the development process but also contribute to building a robust foundation for future modifications and expansions.

Design patterns can be grouped into three main categories based on their purpose and usage.

Creational Patterns - Creational Patterns focus on object creation mechanisms, providing flexible ways to create instances of classes or objects enhancing reusability and control during instance creation. Eg: Singleton, Fatory, Abstract Factory etc

Structural Patterns - Structural patterns focus on organizing classes and objects into larger, clear structures, promoting better code organization and flexibility. Eg : Adapter, Bridge etc

Behavioral Patterns - Behavioral patterns concentrate on the communication between classes and objects, optimizing delegation of responsibilities and enhancing system collaboration and flexibility. Eg : Chain of Responsibility, Command, Interpreter. 