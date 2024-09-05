public with sharing class ZendeskTicketUtils{
    

    public class TicketWrapper {
        public String body;
        public String subject;
        public String priority;
    }

    public static void createTicket(TicketWrapper wrapper){
        String Zendesk_UserName = '';
        String Zendesk_API_TOKEN = '';
        String header = Zendesk_UserName + ':' + Zendesk_API_TOKEN;
        HttpRequest httpReq = new HttpRequest();
        httpReq.setEndpoint(''); // URL
        httpReq.setMethod('POST'); // POST method

        // Paste the Example body from the docs in JSON2APEX
        String requestBody = '{'+
		'  "ticket": {'+
		'    "comment": {'+
		'      "body": "'+wrapper.body+'"'+
		'    },'+
		'    "priority": "'+wrapper.priority+'",'+
		'    "subject": "'+wrapper.subject+'"'+
		'  }'+
		'}';


        httpReq.setBody(requestBody);
        httpReq.setHeader('Content-Type', 'application-json');
        httpReq.setHeader('Accept', 'application-json'); //JSON, XML, Text, HTML
        httpReq.setHeader('Authorization', 'Basic '+EncodingUtil.base64Encode( Blob.valueOf(header)));

        Http http = new Http();
        HttpResponse httpRes = new HttpResponse();
        try{
            httpRes = http.send(httpReq);
            if(httpRes.getStatusCode() == 201){
                String response = httpRes.getBody();
                System.debug(' ### '+response);            
            } else {
                String response = httpRes.getBody();
                System.debug(' ### '+ response);
            }   

        }catch(System.CalloutException calloutEx){
            if(String.valueof(calloutEx).startsWith('System.CalloutException: Unauthorized endpoint')){
                //Remote Site Missing Error
                System.debug('Remote Site Setting Error');
            }
        }catch(Exception ex){
            System.debug(ex);
        }

    }
}

/* Implementation */
ZendeskTicketUtils.TicketWrapper wrapper = new ZendeskTicketUtils.TicketWrapper();
wrapper.body = 'Dynamic Body';
wrapper.priority = 'Urgent';
wrapper.subject = 'Subject';

ZendeskTicketUtils.createTicket(wrapper);

