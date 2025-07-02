Savepoint sp = Database.setSavepoint();

try {
    Opportunity o = [SELECT Id, Name, Account.Id, Amount FROM Opportunity];
    Decimal c = o.Amount * 0.20;
    o.amount = o.amount + commision;
    update o;
    Commision__c c = new Commision__c();
    c.Commision_Amount__c = commision;
    c.Agent__c = o.OwnerId;
    insert c;

    Account acc = [SELECT Name FROM Account WHERE Id=:o.accountId];
    acc.Discount_Percentage__c = 20;
    update acc;
} catch(Exception e){
    Database.rollback(sp);

    Database.releaseSavepoint(sp); // Callout is allowed because uncommitted work is rolled back and savepoints are released

    makeACallout();
}