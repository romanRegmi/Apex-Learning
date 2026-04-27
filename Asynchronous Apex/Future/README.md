# Future Methods

Future methods in Salesforce allow you to execute tasks asynchronously in a separate thread. They are a powerful feature used for various scenarios.

- **Callouts to External Web Services**: Callouts to external Web services. If you are making callouts from a trigger or after performing a DML operation, you must use a future method or implement a queueable class. A callout in a trigger would hold the database connection open for the lifetime of the callout and that is a "no-no" in a multitenant environment.

- **Processes Requiring Separate Threads**: Executing processes that need to run independently from the main transaction, enabling improved application responsiveness.

- **Isolating DML Operations**: Using future methods to perform DML (Data Manipulation Language) operations on different sObject types, which helps prevent mixed DML errors.

- **Long-Running Callouts from Triggers**: Future methods provide the ability to make long-running callouts from triggers without blocking the main transaction.

## Key Points
- Future methods are always declared as `static` and return `void`.
- Future methods are not guaranteed to execute in the same order as they are called. It is possible that two future methods can run concurrently. This could cause record locking.
- You cannot pass non-primitive data types as parameters to future methods. For example, you cannot pass sObjects or lists of sObjects as arguments. The reason for this limitation is that sObjects might change between the time you call the method and the time it executes causing data loss.

## Best Practices 
- In case of Web service callouts, try to bundle all the callouts together from the same future method, rather that using a separate future method for each callout.
- To process large number of records asynchronously, use Batch Apex instead of future methods.
- Pass an Id or a collection of Ids instead of records or record field values to the method and query the field value or records later.

## Limitations
- Future methods cannot be monitored.
- We cannot pass an sObject or a List of sObjects as paramenters.
- We canoot make more than 50 method calls per apex invocations.

## Interview Questions

## Why do future methods always return void?
Since future methods run asynchronously, attempting to use their return value immediately in your synchronous Apex code will result in an undefined value. The future method might still be executing at that point.

## Why do future methods have to be static?
1. **Statelessness:** Future methods are designed to run independently of the current state of the application. They don't have access to the instance-specific state, which makes them stateless. This is because they might be executed in a different context or even on a different server.

2. **Avoiding Data Inconsistencies:** Making future methods static helps prevent data inconsistencies. Since they can run independently of the current transaction, it ensures that data remains consistent even if multiple future methods are executing simultaneously.

3. **Encapsulation:** Static methods are easier to encapsulate and reason about. They don't rely on instance-specific data, making it clear that they operate independently and don't impact the state of the current instance.

4. **Security:** By not allowing access to instance-specific data, static future methods help maintain data security. It prevents any unintentional data exposure or modification.

5. **Predictable Execution:** Static methods ensure predictability in execution. The behavior of future methods is consistent and doesn't depend on the current instance's state, which makes it easier to design and debug.

6. **Parallel Execution:** Future methods are often used to perform tasks concurrently. Making them static allows multiple future methods to run in parallel without conflicts.

7. **Avoiding Deadlocks:** Static methods eliminate the possibility of deadlock situations where two or more methods are waiting for access to instance-specific resources.

By making future methods static, Salesforce ensures that they maintain the necessary characteristics for safe and efficient asynchronous execution within the Salesforce platform.

## Can we call a future method from a future method? Why?
No, it is not possible to call a future method from a future method. 

There are two main reasons why you can't call a future method from another future method in Salesforce Apex:

* Transaction Boundaries: Future methods are designed to run in their own separate transaction contexts. This means any changes they make to data are isolated from the transaction that triggered the future method call. Nesting future methods would create complex transaction management scenarios and could lead to unexpected behavior or data inconsistencies.

* Governor Limits and Efficiency:  Governor limits restrict resource usage in Apex to prevent performance bottlenecks. Chaining future methods could rapidly consume these limits, especially if you're dealing with large datasets Additionally, nested asynchronous calls can introduce unnecessary delays as each future method waits for the previous one to complete.

Here is an alternative approaches to consider if you need asynchronous processing within a future method:

**Queueable Class**: You can use a Queueable class to enqueue another job for asynchronous execution. Queueable classes operate within their own transaction and can be a good choice for chaining asynchronous tasks.

## Is it possible to call a future method from an apex scheduler?
Yes, it is possible to call a future method from an apex scheduler.

```java
public class Async {
    @future
    public static void futureMethodCall(){
        System.debug('>>> Future Method Called');
    }
}

public class scheduler implements Schedulable{
    public void execute(SchedulableContext sc){
        System.debug('>>> Call the Future Method');
        Async.futureMethodCall();
    }
}
```

## Can future methods be called from a flow?
We cannot directly call a future method from a flow. You can call an invokable flow. If a class has an invocable method, then it cannot have another annotation. A class cannot be invokable and future at the same time. Your invocable method can call another method and that method can be future.

## When will we use future methods instead Of Queueable?
You can use future methods instead of implementing a Queueable class when your functionality is sometimes executed synchronously and sometimes asynchronously. It's much easier to refactor a method in this manner than converting to a **Queueable** class. This is handy when you discover that part of your existing code needs to be moved to async execution. You can simply create a similar future method that wraps your synchronous method.

## Can we call batch from future ?
No, We cannot. If we do, we get `FATAL_ERROR System.AsyncException: Database.executeBatch cannot be called from a batch start,
batch execute, or future method`.

## Can we call a schedulable and queuable apex from a future method?
We can call one queueable and schedulable apex from a future method. 

# Additional Notes on Future Methods 
In Salesforce, there are certain limitations and restrictions regarding when and how you can make callouts and use asynchronous processing methods like `@future` methods, especially when it comes to different types of Apex classes. Let's address your questions one by one:

1. **Callouts from a Scheduled Class**:
   - Scheduled Apex classes, also known as Scheduled Jobs, are intended for executing code at specified times or on a recurring schedule. They are often used for tasks like data maintenance, reporting, and other background processes.
   - Scheduled classes, by default, do not allow making callouts to external services or APIs. This limitation is in place to prevent long-running processes in the background that might impact performance or timing predictability of scheduled jobs.
   - If you need to make callouts from a scheduled class, you should consider using a combination of a scheduled job and asynchronous processing techniques like Queueable Apex or future methods to delegate the callout to a more suitable context.

2. **Calling a `@future` Method from a Batch Class**:
   - Batch Apex and `@future` methods serve different purposes and have different execution contexts.
   - A `@future` method is designed for asynchronous processing and can be called from synchronous contexts, such as triggers, Visualforce pages, or Lightning components.
   - Batch Apex, on the other hand, is designed for processing large volumes of data in a batch job, and it runs in a separate asynchronous context.
   - While it is technically possible to call a `@future` method from a batch class, it is generally not recommended. The primary reason is that batch jobs themselves are asynchronous and can handle processing large datasets. Invoking a `@future` method within a batch job could lead to issues with governor limits and asynchronous behavior.

These limitations and restrictions are in place to ensure the orderly and efficient execution of code within the Salesforce platform. To achieve the desired functionality, it's often better to design your solution in a way that respects these constraints and leverages the various types of Apex classes (Batch, `@future`, Scheduled, etc.) appropriately for different use cases. If you need to make callouts or perform asynchronous tasks in a scheduled job or batch job, you should consider using Queueable Apex or other suitable techniques for those tasks.

## Can we pass wrapper to future method?
Yes, we'll need to serialize/deserialize that parameter. We can convert the Wrapper to a String which is a primitive. Once converted into a String you can them pass that string as a parameter to the future method in consideration.