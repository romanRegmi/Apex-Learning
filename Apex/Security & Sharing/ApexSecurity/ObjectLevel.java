Schema.DescribeSObjectResult accountDescribe = Account.sObjectType.getDescribe();
System.debug('accessible:' + accountDescribe.accessible);

public class AccessChecker {

    public static Boolean hasCreateAccess(String objectApiName) {
        Schema.DescribeSObjectResult objectDescribe = Schema.getGlobalDescribe().get(objectApiName).getDescribe();
        return objectDescribe.isCreateable();
    }

    public static Boolean hasEditAccess(String objectApiName) {
        Schema.DescribeSObjectResult objectDescribe = Schema.getGlobalDescribe().get(objectApiName).getDescribe();
        return objectDescribe.isUpdateable();
    }
}
