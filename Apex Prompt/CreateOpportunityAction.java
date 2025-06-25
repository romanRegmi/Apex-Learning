/*
* Apex method that is to be used in the Einstein Copilot that can create an Opportunity
*/
public with sharing class CreateOpportunityAction {
    @InvocableMethod(label='Create an Opportunity')
    public static List<Output> createOpportunity(List<Input> inputs) {
        List<Output> result = new List<Output>();

        try {
            // Collect Account names from input 
            Set<String> accountNames = new Set<String>();
            for (Input input : inputs) {
                accountNames.add(input.AccountName);
            }

            // Query Account based on names
            Map<String, Account> accountMap = new Map<String, Account>();
            for (Account acc : [SELECT Id, Name FROM Account WHERE Name IN : accountNames]) {
                accountMap.put(acc.Name, acc);
            }

            // Create Opportunities
            List<Opportunity> lstOpportunities = new List<Opportunity>();
            for (Input input : inputs) {
                Account acc = accountMap.get(input.AccountName);
                if (acc != null) {
                    Opportunity opp = new Opportunity();
                    opp.AccountId = acc.Id;
                    opp.Name = input.OpportunityName;
                    opp.StageName = input.StageName;
                    opp.CloseDate = input.CloseDate;
                    opp.Amount = input.Amount;
                    lstOpportunities.add(opp);
                } else {
                    Output errorOutput = new Output();
                    errorOutput.errorMessage = 'Account with name ' + input.AccountName + ' not found.';
                    result.add(errorOutput);
                }
            }

            // Insert Opportunities
            if(!lstOpportunities.isEmpty()) {
                insert lstOpportunities;
            }

            // Prepare output for display
            for (Opportunity opp : lstOpportunities) {
                Output successOutput = new Output();
                successOutput.Opportunity = opp;
                result.add(successOutput);

            }

        } catch (Exception e) {
            // Handle exceptions and provide error message
            Output errorOutput = new Output();
            errorOutput.errorMessage = 'Contact your admin with Error Msg : ' + e.getMessage();
            result.add(errorOutput);
        }

        return result;
    }

    public class Output {
        @InvocableVariable(description = 'Error message. Display it when the error happens and this variable has a value')
        public String errorMessage;

        @InvocableVariable(description = 'Create Opportunity Record. Display this errorMessage is blank')
        public Opportunity Opportunity;
    }

    public class Input {
        @InvocableVariable(description = 'The name of the account for which the Opportunity is being created')
        public String AccountName;

        @InvocableVariable(description = 'The name of the Opportunity')
        public Opportunity OpportunityName;

        @InvocableVariable(description = 'The stage of the opportunity')
        public String StageName;

        @InvocableVariable(description = 'The close date of the Opportunity')
        public Date CloseDate;
        
        @InvocableVariable(description = 'The amount of the opportunity')
        public Decimal Amount;

    }

}