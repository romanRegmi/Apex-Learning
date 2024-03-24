/* 
* The Invocable method must be static and public or global, and its class must be an outer class.
* Only one method in a class can have the InvocableMethod annotation.
* Triggers can't reference Invocable methods.
* Invocable method will not accept more than one argument as a method parameter.
* Other annotations can't be used with the InvocableMethod annotation.
* The invocable method will always return a List.
* The running user must have the corresponding Apex class enabled in their profile or permission set.
*/

/* Limitations for each flow interview.
* SOQL Query Limit is 100 which is same as Single Transaction in sync Apex.
* Only 150 DML Statements can be used in a Single flow including all the Sub-Flows.
* CPU timeout is same as 10 seconds.
* 12 duplicates per batch (The same record can be updated a max of 12 times)
* 2000 executed elements at runtime. (Elements in a for loop)
* Map<Key, Value> is not supported in the flows.
* Date operations are not supported in flows.
*/

public class WelcomeClass {

    @InvocableMethod(label='Displays a Welcome Message' description='This method will retutn a welcome message' category='RomanRegmi')
        public static List<String> welcomeMessage(List<String> inputs) { // Method will always recieve a list.
            String userName = inputs.get(0);
            /* The invocable method will always return a List.
             * Welcome Message typeof - String => List<String> (If you want to return a string, you'll have to return a list of string)
             * List<String> => List<List<String>> (If you want to return a string, you'll have to return a list of list of string)
            */
        }
}

public class DateClassUtility {

    @InvocableMethod(label='Date Operations' 
                     description='Operations related to date class'
                     category='Date Utils'
                     iconName='slds:standard:date_time' // Changes the icon of the action element in the flow.
                    )
    // iconName - <slds:category:name> // see
    // iconName - <resource:namespace:resourceId> // managed packages
    public static List<Date> dateUtils(List<InputWrapper> inputs){
        // Date, Operation (addDays), NumberOfDays
        Date outputDate;
        if(inputs != null && inputs.size()>0){
            InputWrapper input = inputs.get(0);
            if(input.operationType == 'addDays'){
                outputDate = input.inputDate.addDays(input.numberOfDays);
            }else if(input.operationType == 'addMonths'){
                outputDate = input.inputDate.addMonths(input.numberOfMonths);
            }
        }
        return new List<Date>{outputDate};
    }
    
    public class InputWrapper{
        
        @InvocableVariable(label='Provide Date on which you want to perform operation'
                           required=true description='Provide Date on which you want to perform operation')
        public Date inputDate;
        
        @InvocableVariable(label='Provide the Operation that you want to perform' required=true
                           description='The valid operation types are addDays, addMonths, addYears')
        public String operationType;
        
        @InvocableVariable(label='Number of Months' required=false description='Number of Months for the operation')
        public Integer numberOfMonths;
        
        @InvocableVariable(label='Number of days' required=false description='Number of days for the operation')
        public Integer numberOfDays;
    }
}




