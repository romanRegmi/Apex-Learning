// Whenever DML is performed in Order_Detail__e, this trigger runs
// Just like any other standard or custom object

trigger subscribePlatformEventTrigger on Order_Detail__e(after insert) {
    if (Trigger.isAfter && Trigger.isInsert) {
        subscribePlatformEvent.afterInsert(trigger.new);
    }
}