// Replace 'Custom_Object__c' with the API name of your custom object
String customObjectName = 'Custom_Object__c';

// Get the current list of tabs
List<Schema.DescribeTabSetResult> tabSetDesc = Schema.describeTabs();

// Iterate through the tab sets
for (DescribeTabSetResult tsr : tabSetDesc) {
    // Get the tabs for the current tab set
    List<Schema.DescribeTabResult> tabDesc = tsr.getTabs();

    // Check if the custom object tab already exists
    for (DescribeTabResult tr : tabDesc) {
        if (tr.getSobjectName() == customObjectName) {
            // If the tab already exists, return
            return;
        }
    }

    // If the custom object tab doesn't exist, create a new tab
    try {
        // Create a new custom object tab
        MetadataService.CustomTab customTab = new MetadataService.CustomTab();
        customTab.fullName = customObjectName;
        customTab.label = customObjectName.removeEnd('__c').replace('_', '');
        customTab.mobileReady = true;
        customTab.sobjectName = customObjectName;
        customTab.availability = 'Both';

        // Add the custom tab to the list of tabs
        MetadataService.MetadataPort service = createService();
        List<MetadataService.SaveResult> results =
            service.createMetadata(
                new MetadataService.Metadata[] { customTab });

        // Check the results
        handleSaveResults(results[0]);
    } catch (Exception e) {
        System.debug('Error creating custom tab: ' e.getMessage());
    }
}

// Method to create a MetadataService instance
private static MetadataService.MetadataPort createService() {
    MetadataService.MetadataPort service = new MetadataService.MetadataPort();
    service.SessionHeader = new MetadataService.SessionHeader_element();
    service.SessionHeader.sessionId = UserInfo.getSessionId();
    return service;
}

// Method to handle save results
private static void handleSaveResults(MetadataService.SaveResult saveResult) {
    // Check for errors
    if (saveResult == null || saveResult.success) {
        return;
    }

    // Construct error message
    if (saveResult.errors!= null) {
        List<String> messages = new List<String>();
        messages.add(
            (saveResult.errors.size() == 1? 'Error ' 'Errors ') +
            'occured processing component ' saveResult.fullName + '.');
        for (MetadataService.Error error : saveResult.errors) {
            messages.add(
                error.message + '' + error.statusCode + ').' +
                ( error.fields!= null && error.fields.size() > 0?
                    'ields ' String.join(error.fields, ',') + '.' : '' ) );
        }
        if (messages.size() > 0) {
            throw new MetadataServiceException(String.join(messages, ''));
        }
    }
    if (!saveResult.success) {
        throw new MetadataServiceException('Request failed with no specified error.');
    }
}

// Custom exception class
public class MetadataServiceException extends Exception { }




public class tabCreation {
    
    public static Boolean doesTabExist(String objectAPIName){
        // Get the current list of tabs
        List<Schema.DescribeTabSetResult> tabSetDesc = Schema.describeTabs();
        
        
        boolean tabExists = false;
        // Iterate through the tab sets
        for (DescribeTabSetResult tsr : tabSetDesc) {
            // Get the tabs for the current tab set
            List<Schema.DescribeTabResult> tabDesc = tsr.getTabs();
        
            // Check if the custom object tab already exists
            for (DescribeTabResult tr : tabDesc) {
                if (tr.getSobjectName() == objectAPIName) {
                    tabExists = true;
                }
            }
        }
        
        return tabExists;
        
        
    }
    
    
    public static void createTab(String objectAPIName){
        MetadataService.CustomTab customTab = new MetadataService.CustomTab();
        customTab.fullName = objectAPIName;
        customTab.label = objectAPIName.removeEnd('__c').replace('_', ' ');
        customTab.mobileReady = true;
        customTab.customObject = true;
        customTab.motif = 'Custom20:Airplane';
        
        // Add the custom tab to the list of tabs
        MetadataService.MetadataPort service = createService();
        try{
        List<MetadataService.SaveResult> results =
            service.createMetadata(
                new MetadataService.Metadata[] { customTab });
            
            //System.assert(false, results);
        } catch(Exception ex){
            System.assert(false, ex);
        }
    }
    
    // Method to create a MetadataService instance
    private static MetadataService.MetadataPort createService() {
        MetadataService.MetadataPort service = new MetadataService.MetadataPort();
        service.SessionHeader = new MetadataService.SessionHeader_element();
        service.SessionHeader.sessionId = UserInfo.getSessionId();
        return service;
    }
    

}