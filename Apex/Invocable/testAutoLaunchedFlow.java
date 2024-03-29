@IsTest
public class testAutoLaunchedFlow {
    public testMethod static void testAutoLaunchedFlowMethod(){        
        Supplier__c supp = new Supplier__c(Name = 'Supplier 01', Type__c = 'Supplier');
        insert supp;
        
        Qualification__c partQuali = new Qualification__c();
        partQuali.Supplier__c = supp.Id;
        
        Map<String, Object> parms = new Map<String, Object>();
        parms.put('InRecord', partQuali);
        
        Flow.Interview.Flow_API_Name actionFlow = new Flow.Interview.Flow_API_Name(parms);
        actionFlow.start();
        
        Qualification__c result = (Qualification__c) actionFlow.getVariableValue('OutRecord');
        System.assertEquals();           
    }
}