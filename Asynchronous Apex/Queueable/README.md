Queueable Apex
Superset of future methods with extra features.
Combination of future methods and Batch Apex.
Works beyond primitive arguments.
Called by a simple System.enqueueJob( ) method.
enqueueJob( ) return a job ID that can be monitored.


Additional Benefits
Non-primitive types
Monitoring 
Chaining Jobs


Queueable Syntax
public class SomeClass implements Queueable {
public void execute(QueueableContext context) {
// write some code

The execution of a queued job counts once against the shared limit for asynchronous
Apex method executions.
You can add up to 50 jobs to the queue with System.enqueueJob( ) in a single
transaction.
When chaining jobs, you can add only one job from an executing job with
System.enqueueJob( ).
No limit is enforced on the depth of chained jobs. Note, for Developer Edition and Trial
orgs, the maximum stack depth for chained jobs is 5 (including the initial parent
queueable job).