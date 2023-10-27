
Scheduled Apex
• You can run Apex classes at a specified time.
• Run Maintenance tasks on Daily or Weekly basis.
• Implements Schedulable interface in Apex class.


Syntax
global class SomeClass implements Schedulable {
global void execute(SchedulableContext ctx) {
// write some code



To schedule the class that you wrote using the UI
go to quick find --> Apex class --> Schedule Apex
: only options using this was monthly, daily ir weekly.

If you want to make it every hour use CRON


Once a class is scheduled, we cannot edit the classes the scheduled method calls. We cannot even edit the classes inside the classes.



A CRON expression is basically a string of five or six fields that represents a set of times, normally as
a schedule to execute some routine.
Example:
String sch = '
o
20 =
Secoo<js
30
: Minutes
10 = Day of Month
2022' ;
2 = Month (1 for Jan and 12 for Dec)
6 = Day of Week (1 for Sun and 7 for Sat)
2022 = Year (Optional - null or 1970 - 2099)

You can only have 100 scheduled jobs at one time.
While scheduling a class from a trigger then you must guarantee that the trigger won't
add more scheduled jobs than the limit.
Synchronous web service callouts are not supported from scheduled Apex.
Make an Asynchronous callout by placing the callout in a method annotated with
@future(callout=true) and call this method from scheduled Apex.
If scheduled apex executes a batch job then callouts are supported from the batch class.0