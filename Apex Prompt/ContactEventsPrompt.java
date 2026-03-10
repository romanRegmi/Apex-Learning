/*
 * This class isn't really required. We can fetch the child records and parent records in the prompt builder itself.
 * This is just an example.
 * We use this when we can't use the record data that we want as a merge field in the prompt builder itself.
*/

public class ContactEventsPrompt {
    @InvocableMethod(
    	CapabilityType='PromptTemplateType://einstein_gpt__salesEmail'
    )
    
    public static List<Response> getContactEvents(List<Request> requests) {
        Request input = requests[0];
        Contact contact = input.Recipient;
        
        List<Guest_Event__c> guestEvent = [SELECT Id, Name, Details__c, Location__c FROM Guest_Event__c WHERE Contact__c =: contact.Id];
        
        String responseData = '';
        if(guestEvent.isEmpty()) {
            responseData = 'The contact doesn\'t have any events associated with it.';
        } else {
            for (Guest_Event__c even : guestEvent) {
                responseData += 'Event Details : ';
                responseData += 'Name: ' + even.Name;
                responseData += ', ';
                responseData += 'Location: ' + even.Location__c;
                responseData += '.';
            }
        }
        
        List<Response> responses = new List<Response>();
       	Response res = new Response();
        res.Prompt = responseData;
        responses.add(res);
        return responses;
    }
    
    // All the inputs will be passed from the template to the apex class
    // The name must be the same as in the promt template
    public class Request {
        @InvocableVariable
        public User Sender;
        @InvocableVariable
        public Contact Recipient;
        
        // Not required for this example
        @InvocableVariable
        public Account RelatedObject; // You can get the relatedObject records
    }
    
    public class Response {
        @InvocableVariable(required = true)
        public String Prompt; // String that you want to add to the promot.
        
    }

}