@isTest
private class AccountMgmtTest {
    @isTest
    static void testGetTopAccounts() {
        // Create test data
        Account testAccount = new Account(Name = 'Test Account', Industry = 'Energy');
        insert testAccount;
        
        RestRequest req = new RestRequest();
        RestResponse res = new RestResponse();
        
        req.requestURI = '/services/apexrest/accountmgmt/';
        req.addParameter('industry', 'Energy'); // This is because we are getting params from the URL in the main method.
        req.httpMethod = 'GET';
        
        RestContext.request = req;
        RestContext.response = res;     

        // Call the REST endpoint
        Test.startTest();        
        List<Account> acc = AccountMgmt.getTopAccounts(); //Method is run based on the parameters defined in req.addParameter
        Test.stopTest();

        // Verify the response
        System.assertEquals(1, acc.size());
        System.assertEquals(testAccount.Name, acc[0].Name);
    }

    @isTest
    static void testCreateAccount() {     
        // Call the REST endpoint
        Test.startTest();
        String msg = AccountMgmt.createAccount('Test Account', 'Hot');
        Test.stopTest();

        // Verify the response
        System.assertEquals('Account Created Successfully', msg);
    }

    @isTest
    static void testGetComplexRecord() {
        // Create test data
        Account testAccount = new Account(Name = 'Test Account', Industry = 'Test Industry');
        insert testAccount;
        
        Contact testContact = new Contact(FirstName = 'Test', LastName = 'Contact', AccountId = testAccount.Id);
        insert testContact;
        
        Case testCase = new Case(Subject = 'Test Case', AccountId = testAccount.Id);
        insert testCase;
        
        RestRequest req = new RestRequest();
        RestResponse res = new RestResponse();
        
        req.requestURI = '/services/apexrest/accountmgmt/';
        req.addParameter('industry', 'Test Industry');
        
        req.httpMethod = 'GET';
        RestContext.request = req;
        RestContext.response = res;

        // Call the REST endpoint
        Test.startTest();        
        AccountMgmt.AccountWrapper accWrapper = AccountMgmt.getComplexRecord();
        Test.stopTest();

        // Verify the response
        System.assertEquals(1, accWrapper.accList.size());
        System.assertEquals(1, accWrapper.conList.size());
        System.assertEquals(1, accWrapper.caseList.size());
    }

    @isTest
    static void testCreateAccountAndContact() {
        
        // Create test Account and Contact records
        Account testAccount = new Account(Name = 'Test Account 01');   
        Contact testContact = new Contact(FirstName = 'Test 01', LastName = 'Contact');
	
        // Create an AccountInformation object with the created Account and Contact records
        AccountMgmt.AccountInformation information = new AccountMgmt.AccountInformation();
        information.accountRecord = testAccount;
        information.contactRecord = testContact;
        
        // Call the REST endpoint
        Test.startTest();
        String msg = AccountMgmt.createAccountAndContact(information);
        Test.stopTest();

        // Verify the response
        System.assertEquals('Account and Contact Created Successfully', msg);
    }


    @isTest static void testUpdateAccountFields() {
        Account testAccount = new Account(Name = 'Test Account 01');
        insert testAccount;
        
        Id recordId = testAccount.Id;
        RestRequest request = new RestRequest();
        request.requestUri =
            '/services/apexrest/accountmgmt/'
            + recordId;
        request.httpMethod = 'PATCH';
        request.requestBody = Blob.valueOf('{"Name": "Test Account 05"}');
        RestContext.request = request;
        // Update status of existing record to Working
        String msg = AccountMgmt.UpdateAccountFields();
        // Verify record was updated
        System.assert(msg == 'Account Updated Successfully');
        Account thisAcc = [SELECT Id, Name FROM Account WHERE Id=:recordId];
        System.assert(thisAcc != null);
        System.assertEquals(thisAcc.Name, 'Test Account 05');
    }  
    
}
