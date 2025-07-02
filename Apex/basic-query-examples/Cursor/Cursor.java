// Process large SOQL query results in smaller customizable batches within a single transaction

/*
* Pagination Support 
* Segment the processing of a large cursor result across multiple Queueable jobs
*/


Database.Cursor locator = Database.getCursor('SELECT Id From Contact');
List<Contact> scope = locator.fetch(0, 200); // Start position, Number of records

// We can jump to the 800th record 
scope = locator.fetch(800, 120);

// The locator is fully serializable and can be stored and reused across multiple transactions.
// Valid for 2 days. 



public with sharing class QueryCursorDemo {
    @AuraEnabled(cacheable=true)
    public static List<Lead> getLeads(Integer startPosition, Integer numRecords){
        try{
            if(startPosition != null && numRecords != null){
                return [SELECT Id, Name, Email FROM Lead WHERE Status = 'Open' WITH USER_MODE OFFSET:startPosition];
            }
            return null;
        } catch (Exception e){
            throw new AuraHandledException(e.getMessage());
        }
    }
}

//Database.Cursor locator = Database.getCursor('SELECT Id, Name, Email FROM Lead');

// We can searilize the cursor and save it and use it later.
Database.Cursor cursor = (Database.Cursor)JSON.deserialize('{"queryId":"0r8xx3IqiVfh7k3"}', Database.Cursor.class);

public with sharing class QueryCursorDemo {
    @AuraEnabled(cacheable=true)
    public static List<Lead> getLeads(Integer startPosition, Integer numRecords){
        try{
            if(startPosition != null && numRecords != null){
                Database.Cursor cursor = Database.getCursor('SELECT Id, Name, Email FROM Lead WHERE Status = 'Open' WITH USER_MODE');
                return cursor.fetch(startPosition, numRecords);
            }
            return null;
        } catch (Exception e){
            throw new AuraHandledException(e.getMessage());
        }
    }
}

// Best practice would be to store the cursor in a platform cache when using it to dispaly data in the frontend for pagination
// Instead of creating the cursor again and again

public with sharing class QueryCursorDemo {
    @AuraEnabled(cacheable=true)
    public static List<Lead> getLeads(Integer startPosition, Integer numRecords){
        try{
            if(startPosition != null && numRecords != null){
                Database.Cursor cursor;
                if(Cache.Session.contains('leadCursor')) {
                    cursor = (Database.Cursor)Cache.Session.get('leadCursor');
                } else {                 
                    cursor = Database.getCursor('SELECT Id, Name, Email FROM Lead WHERE Status = 'Open' WITH USER_MODE');
                    Cache.Session.put('leadCursor', cursor);
                }        
                return cursor.fetch(startPosition, numRecords);
            }
            return null;
        } catch (Exception e){
            throw new AuraHandledException(e.getMessage());
        }
    }
}



// For Queueable apex
public class QueueableCursor implements Queueable{
    private Database.Cursor locator;
    private integer positionl;
    private integer numRecords;

    public QueueableCursor() { // Constructor
        //cleanup
        delete([SELECT Id FROM Process_Log__c]);

        //Create a cursor for the query
        locator = Database.getCursor('SELECT Id, Name FROM Lead');


        position = 0;
        numRecords = 0;
    }

    public void execute(QueueableContext ctx){
        // Calculate the number of records to query based on the records left to process
        Integer recordsLeft = locator.getNumRecords() - position;
        Integer recordsToQuery = (recordsLeft > numRecords) ? numRecords : recordsLeft;

        // Fetch a chunk of records
        List<Lead> scope = locator.fetch(position, recordsToQuery);


        // Process the records
        
        position += scope.size();
        numRecords = 200;

        if(position < locator.getNumRecords()){
            System.enqueueJob(this); // Call the class itself.
        }

    }
}