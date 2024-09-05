public with sharing class OpenCageGeoCoderService {
    public static void reverseGeoCoding(String accountId){
        String OPENCAGE_API_URL = '';
        String OPENCAGE_API_KEY = '';
        Account accRec = [SELECT Id, Location_Latitude__s, Location_Longitude__s FROM Account
                        WHERE Id=:accountId AND Location_Latitude__s != null AND Location_Longitude__s != null LIMIT 1];


        String queryParams = accRec.Location_Latitude__s+','+accRec.Location_Longitude__s;

        HttpRequest httpReq = new HttpRequest();
        httpReq.setEndPoint(OPENCAGE_API_URL+'?key='OPENCAGE_API_KEY+'&q='+queryParams+'&pretty=1'); // URL must be added in the Remote Site Settings
        
        
        httpReq.setHeader('Content-Type', 'application-json');
        httpReq.setHeader('Accept', 'application-json'); //JSON, XML, Text, HTML
        
        /* Set the method */
        httpReq.setMethod('GET'); // GET, POST, PUT, PATCH, DELETE
        
        Http http = new Http();
        HttpResponse httpRes = new HttpResponse();
        try{
            httpRes = http.send(httpReq);
            if(httpRes.getStatusCode() == 200){
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