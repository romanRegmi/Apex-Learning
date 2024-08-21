public with sharing class CurrencyAPIService {
    public static void getExchangeRates(){
        HttpRequest httpReq = new HttpRequest();
        httpReq.setEndPoint('https://open.er-api.com/v6/latests/USD'); // URL must be added in the Remote Site Settings
        
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