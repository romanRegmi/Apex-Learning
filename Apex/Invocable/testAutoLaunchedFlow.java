@isTest
public class testAutoLaunchedFlow {
    public testMethod static void testAutoLaunchedFlowMethod(){        
        Object__A objA = new Object__A(Name = 'Supplier 01', Type__c = 'Supplier');
        insert objA;
        
        Object__B objB = new Object__B();
        objB.Object__A = objA.Id;
        
        Map<String, Object> parms = new Map<String, Object>();
        parms.put('inRecord', objB); // inRecord variable is defined in flow as an input variable.
        
        Flow.Interview.Flow_API_Name actionFlow = new Flow.Interview.Flow_API_Name(parms);
        actionFlow.start();
        
        Object__B result = (Object__B) actionFlow.getVariableValue('outRecord'); // outRecord variable is defined in flow as an output variable.
        System.assertEquals();        
    }
}