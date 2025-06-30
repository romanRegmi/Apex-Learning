/*
* While deleting shared object records with apex sharing reason, if we try to delete using List<Id> it doesn't work. We can use List<CustomShareObject> to delete the records.
*/

List<Id> shareRecIds = new List<Id>();
shareRecIds.add('');
Database.delete(shareRecIds); // System.TypeException: Invalid id : 

List<Course_Share> shareRecIds = new List<Course_Share>();
shareRecIds.add(new Course_Share(Id=''));
Database.delete(shareRecIds); // Works


public with sharing class OpportunityController{
    public static void getOpportunites(){
        // Will run in without sharing mode
        // Even though the main class is in with sharing
        // As the OpportunityHelper is in without sharing 
        List<Opportunity> opp OpportunityHelper.getRecords(); 

        // Processes below will start running in the with sharing context

    }
}

public without sharing class OpportunityHelper{
    public static List<Opportunity> getRecords(){
        return [SELECT Id, Name FROM Opportunity];
    }
}