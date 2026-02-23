Savepoint sp = Database.setSavepoint();

try {
    Account acc = new Account(Name='Roman Acc');
    insert acc; // Will insert as it is inside a try block
    
    Contact c = new Contact(FirstName = 'Roman');
    insert c; // Will throw an error

} catch(Exception e){
    Database.rollback(sp);

    Database.releaseSavepoint(sp); // Callout is allowed because uncommitted work is rolled back and savepoints are released

    makeACallout();
}