public with sharing class MaintenanceRequestHelper {
    public static void updateworkOrders(List<Case> updWorkOrders, Map<Id, Case> nonUpdCaseMap) {
        
        Set<Id> validIds = new Set<Id>();
        for (Case c : updWorkOrders){
            if (nonUpdCaseMap.get(c.Id).Status != 'Closed' && c.Status == 'Closed'){
                if (c.Type == 'Repair' || c.Type == 'Routine Maintenance'){
                    validIds.add(c.Id);        
                }
            }
        }
        
        if (!validIds.isEmpty()){
            AggregateResult[] results = [SELECT Maintenance_Request__c, MIN(Equipment__r.Maintenance_Cycle__c)cycle // cycle : Alias
										FROM Equipment_Maintenance_Item__c 
										WHERE Maintenance_Request__c IN :ValidIds 
										GROUP BY Maintenance_Request__c];
            
            Map<Id, Decimal> maintenanceCycles = new Map<Id, Decimal>();
            
            Map<Id,Case> closedCasesM = new Map<Id, Case>([SELECT Id, Vehicle__c, ProductId, Product.Maintenance_Cycle__c, (SELECT Id, Equipment__c FROM Equipment_Maintenance_Items__r) 
                                                         FROM Case WHERE Id IN :validIds]);
            
            for (AggregateResult ar : results){ 
                maintenanceCycles.put((Id) ar.get('Maintenance_Request__c'), (Decimal) ar.get('cycle'));
            }
            
            List<Case> newCases = new List<Case>();
            
            for(Case cc : closedCasesM.values()){
                Case nc = new Case (
                    ParentId = cc.Id,
                	Status = 'New',
                    Subject = 'Routine Maintenance',
                    Type = 'Routine Maintenance',
                    Vehicle__c = cc.Vehicle__c,
                    ProductId = cc.ProductId,
                    Origin = 'Web',
                    Date_Reported__c = Date.Today()                   
                );
            
                If (maintenanceCycles.containskey(cc.Id)){
                    nc.Date_Due__c = Date.today().addDays((Integer) maintenanceCycles.get(cc.Id));
                }else {
                    nc.Date_Due__c = Date.today().addDays((Integer) cc.Product.Maintenance_Cycle__c);
                }            
                newCases.add(nc);
            }
            
           	insert newCases;
            List<Equipment_Maintenance_Item__c> clonedWPs = new List<Equipment_Maintenance_Item__c>();
            for (Case nc : newCases){
                for(Equipment_Maintenance_Item__c wp : closedCasesM.get(nc.ParentId).Equipment_Maintenance_Items__r){
                    Equipment_Maintenance_Item__c wpClone = wp.clone();
                    wpClone.Maintenance_Request__c = nc.Id;
                    ClonedWPs.add(wpClone);                    
                }
            }
            insert ClonedWPs;
        }           
    }
}