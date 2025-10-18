public class Util {
    @future // Will throw the MixedDML Error if this annotation isn't used
    public static void insertUserWithRole(String uname, String al, String em, String lname){
        Profile p = [SELECT Id From Profile WHERE NAME='Standard User'];
        UserRole r = [SELECT Id From UserRole WHERE NAME='COO'];
        User u = new User(alias=al, email=em, emailencodingkey='UTF-8', lastname=lname, 
                         languagelocalekey='en_US', localesidkey='en_US', profileid = p.Id, userroleid=r.Id,
                         timezonesidkey='America/Los_Angeles',
                         username=uname);
        insert u;   
    }
    
    public static void insertUserWithNoRole(String uname, String al, String em, String lname){
        Profile p = [SELECT Id From Profile WHERE NAME='Standard User'];
        User u = new User(alias=al, email=em, emailencodingkey='UTF-8', lastname=lname, 
                         languagelocalekey='en_US', localesidkey='en_US', profileid = p.Id, 
                         timezonesidkey='America/Los_Angeles',
                         username=uname);
        insert u;   
    }

}