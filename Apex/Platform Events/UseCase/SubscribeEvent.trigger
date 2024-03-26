// Custom Account_Id__c field in the platform event.
trigger SubscribeEvent on Update_Case_on_Account_Update_e (after insert) {
    List<Id> accIds = new List<Id>();
    List<Case>caseUpdateList = new List<Case>();
    
    for(Update_Case_on_Account_Update_e eventObj : Trigger.new){
        accIds.add(eventObj.Account_Id_c);
    }

    List<Account> AccWithCases =[SELECT Id,CustomerPriority_c,(SELECT Id, Priority FROM Cases) FROM Account Where Id In : accIds];

    for(Account accObj : AccWithCases){
        for(Case caseObj : accObj.cases){
            caseObj.Priority = accObj.CustomerPriority_c;
            caseUpdateList.add(caseObj);
        }
    }

    update caseUpdateList;
}