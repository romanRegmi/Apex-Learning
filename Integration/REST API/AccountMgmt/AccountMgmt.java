@RestResource(urlMapping = '/accountmgmt/')
global class AccountMgmt {
    @httpGet
    global static List<Account> getTopAccounts(){
        /*
        * @params : If parameters are sent from the request
        * @syntax : ({_endpoint})/services/apexrest/:urlMapping?industry=Energy
        */

        Map<String, String> paramsMap = RestContext.request.params;
        String accIndustry = paramsMap.get('industry');
        List<Account> accList = [SELECT Id, Name, Industry FROM Account WHERE Industry =: accIndustry];
        return accList;
    }

    /*
    * @complex-data
    */
    global class AccountWrapper {
        global List<Account> accList;
        global List<Contact> conList;
        global List<Case> caseList; 
    }

    @httpGet
    global static AccountWrapper getComplexRecord(){

        Map<String, String> paramsMap = RestContext.request.params;
        Set<Id> accIdSet = new Set<Id>();
        String accIndustry = paramsMap.get('industry');
        List<Account> accList = [SELECT Id, Name, Industry FROM Account WHERE Industry =: accIndustry];
        for (Account acc : accList){
            accIdSet.add(acc.Id);
        }

        List<Contact> conList = [Select Id, FirstName FROM Contact WHERE AccountId IN : accIdSet];
        List<Case> caseList = [Select Id, Subject FROM Case WHERE AccountId IN : accIdSet];
    
        AccountWrapper accWrapper = new AccountWrapper();
        accWrapper.accList = accList;
        accWrapper.conList = conList;
        accWrapper.caseList = caseList;
        return accWrapper;
    }


    @httpPost
    /*
    * @body : Values defined in the parameters must be sent from the body from where the request is sent. 
    * The client will send the request
    * @syntax : { "accName" : "Account-01", :accRating : "Hot"} , ({_endpoint})/services/apexrest/:urlMapping
    */
    global static String createAccount(String accName, String accRating) { // We can get this from the parameters as well
        Account acc = new Account();
        acc.Name = accName;
        acc.Rating = accRating;

        try {
            insert acc;
            return 'Account Created Successfully';
        } catch (exception ex){
            return ex.getMessage();
        }
    }

    global class AccountInformation {
        global Account accountRecord {get; set;}
        global Contact contactRecord {get; set;}
    }
    
    @httpPost
    /*
    * @syntax : { "information" : {"accountRecord": {"Name" : "Acc-01", ....}, "contactRecord" : {"Firstname" : "Roman", ...}}} , ({_endpoint})/services/apexrest/:urlMapping
    * @Example : In this example, you can pass any number of fields for both account and contact. The fields will we passed using their API names.
    */
    global static String createAccountAndContact(AccountInformation information){
        Account accrecord = information.accountRecord;
        Contact conrecord = information.contactRecord;
        try{
            insert accrecord;
            conrecord.accountId = accrecord.Id;
            insert conrecord;
            return 'Account and Contact Created Successfully';
        } catch (exception ex){
            return ex.getMessage();
        }
    }


    @HttpPatch
    global static String updateAccountFields() {
        RestRequest request = RestContext.request;
        String accId = request.requestURI.substring(
            request.requestURI.lastIndexOf('/') + 1);
        Account thisAccount = [SELECT Id FROM Account WHERE Id = :accId];
        
        // Deserialize the JSON string into name-value pairs
        Map<String, Object> params = (Map<String, Object>)JSON.deserializeUntyped(request.requestbody.tostring());
        
        // Iterate through each parameter field and value
        for(String fieldName : params.keySet()) {
            // Set the field and value on the Case sObject
            thisAccount.put(fieldName, params.get(fieldName));
        }
        update thisAccount;
        return 'Account Updated Successfully';
    } 

    
}