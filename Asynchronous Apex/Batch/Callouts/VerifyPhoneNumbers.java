public with sharing class VerifyPhoneNumbers {

    public static String API_KEY = '265A5DCDCF0143F5B246E20127FA1038';

    /*
    *
    * @params : sObject (The code should handle every object)
    * @params : phoneField (API name of the phone field of the object being used)
    *
    */
    
    public static String verifyPhone(sObject sobj, String phoneField){
        String phone = (String)sobj.get(phoneField);  
        String endpoint = 'https://api.veriphone.io/v2/verify?phone=' + phone + '&key=' + API_KEY;

        HttpRequest request = new HttpRequest();
        request.setMethod('GET');
        request.setEndpoint(endpoint);
        request.setHeader('Content-Type', 'application/json'); // return type (JSON/XML)
        Http http = new Http();
        HttpResponse httpRes = new HttpResponse();

        /*
        * DML shouldn't happen inbetween callouts
        */

        try{ 
            httpRes = http.send(request);
            if(httpRes.getStatusCode() == 200){
                String response = httpRes.getBody();
                System.debug(' ### '+response);
                return response;
            } else {
                String response = httpRes.getBody();
                System.debug(' ### '+response);
                return response;
            }     	
        } catch(System.CalloutException ex){
            System.debug(' ### '+ex.getStackTraceString());

        }
        return null;
    }
}