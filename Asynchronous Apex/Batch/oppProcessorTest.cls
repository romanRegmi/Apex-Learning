@isTest
public class OppProcessorTest {
    @isTest
    static void testOppProcessorBatch() {
        // Create test Opportunities
        List<Opportunity> testOpportunities = new List<Opportunity>();
        for (Integer i = 0; i < 200; i++) {
            Opportunity opp = new Opportunity(
                Name = 'Test Opportunity ' + i,
                LeadSource = 'Other',
                StageName = 'Prospecting',
                CloseDate = System.today()
            );
            testOpportunities.add(opp);
        }
        insert testOpportunities;

        // Start the batch
        Test.startTest();
        OppProcessor batchInstance = new OppProcessor();
        Database.executeBatch(batchInstance);
        Test.stopTest();

        // Verify that the Opportunities have been updated
        List<Opportunity> updatedOpportunities = [SELECT Id, LeadSource FROM Opportunity WHERE Id IN :testOpportunities];
        for (Opportunity opp : updatedOpportunities) {
            System.assertEquals('Web', opp.LeadSource, 'Opportunity LeadSource should be updated to Web');
        }
        
        System.assertEquals(200, updatedOpportunities.size());
    }
}
