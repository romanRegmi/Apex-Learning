// 1. First, define the Platform Event : Setup -> Platform Events
/*
Object Name: Order_Status_Update__e
Fields:
    - Order_Number__c (Text)
    - Status__c (Text)
    - Update_Time__c (DateTime)
    - Additional_Details__c (Text)
*/

// 2. Publisher Class - Internal System
public class OrderStatusPublisher {
    public static void publishStatus(String orderNumber, String status, String details) {
        // Create event instance
        Order_Status_Update__e statusUpdate = new Order_Status_Update__e(
            Order_Number__c = orderNumber,
            Status__c = status,
            Update_Time__c = Datetime.now(),
            Additional_Details__c = details
        );
        
        // Publish event
        Database.SaveResult result = EventBus.publish(statusUpdate);
        
        // Error handling
        if (!result.isSuccess()) {
            for(Database.Error err : result.getErrors()) {
                System.debug('Error publishing event: ' + err.getMessage());
            }
            throw new OrderStatusException('Failed to publish status update');
        }
    }
}

// 3. Subscriber Trigger - Internal Processing
trigger OrderStatusUpdateTrigger on Order_Status_Update__e (after insert) {
    List<Task> tasksToCreate = new List<Task>();
    List<Order> ordersToUpdate = new List<Order>();
    
    for(Order_Status_Update__e event : Trigger.new) {
        // Create follow-up task
        Task followUp = new Task(
            Subject = 'Order Status Changed: ' + event.Status__c,
            Description = 'Order ' + event.Order_Number__c + ' updated. ' + 
                         event.Additional_Details__c,
            ActivityDate = Date.today().addDays(1),
            Priority = 'High'
        );
        tasksToCreate.add(followUp);
        
        // Update order status
        Order ord = new Order(
            OrderNumber = event.Order_Number__c,
            Status = event.Status__c
        );
        ordersToUpdate.add(ord);
    }
    
    // Process records
    if(!tasksToCreate.isEmpty()) {
        insert tasksToCreate;
    }
    if(!ordersToUpdate.isEmpty()) {
        update ordersToUpdate;
    }
}

// 4. External System Integration - REST API Endpoint
@RestResource(urlMapping='/v1/orderStatus/*')
global class OrderStatusAPI {
    @HttpPost
    global static void updateOrderStatus() {
        try {
            // Get request body
            String requestBody = RestContext.request.requestBody.toString();
            Map<String, Object> requestData = 
                (Map<String, Object>)JSON.deserializeUntyped(requestBody);
            
            // Create and publish event
            Order_Status_Update__e statusUpdate = new Order_Status_Update__e(
                Order_Number__c = (String)requestData.get('orderNumber'),
                Status__c = (String)requestData.get('status'),
                Update_Time__c = Datetime.now(),
                Additional_Details__c = (String)requestData.get('details')
            );
            
            // Publish event
            Database.SaveResult result = EventBus.publish(statusUpdate);
            
            // Handle response
            if(result.isSuccess()) {
                RestContext.response.statusCode = 200;
                RestContext.response.responseBody = 
                    Blob.valueOf('{"message": "Status update published successfully"}');
            } else {
                RestContext.response.statusCode = 500;
                RestContext.response.responseBody = 
                    Blob.valueOf('{"error": "Failed to publish status update"}');
            }
        } catch(Exception e) {
            RestContext.response.statusCode = 500;
            RestContext.response.responseBody = 
                Blob.valueOf('{"error": "' + e.getMessage() + '"}');
        }
    }
}

// 5. Batch Publisher Example
public class BatchOrderStatusPublisher implements Database.Batchable<SObject> {
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(
            'SELECT Id, OrderNumber, Status FROM Order WHERE Status = \'Pending\''
        );
    }
    
    public void execute(Database.BatchableContext bc, List<Order> scope) {
        List<Order_Status_Update__e> events = new List<Order_Status_Update__e>();
        
        for(Order ord : scope) {
            events.add(new Order_Status_Update__e(
                Order_Number__c = ord.OrderNumber,
                Status__c = 'Processing',
                Update_Time__c = Datetime.now(),
                Additional_Details__c = 'Batch processed'
            ));
        }
        
        // Publish events in bulk
        List<Database.SaveResult> results = EventBus.publish(events);
        
        // Handle errors
        for(Database.SaveResult result : results) {
            if(!result.isSuccess()) {
                System.debug('Error publishing event: ' + result.getErrors());
            }
        }
    }
    
    public void finish(Database.BatchableContext bc) {
        // Send completion notification
    }
}

// 6. Testing Platform Events
@IsTest
public class OrderStatusEventTest {
    @IsTest
    static void testEventPublishAndSubscribe() {
        // Enable event publishing for test
        Test.startTest();
        
        // Create and publish event
        Order_Status_Update__e statusUpdate = new Order_Status_Update__e(
            Order_Number__c = 'TEST-001',
            Status__c = 'Completed',
            Update_Time__c = Datetime.now(),
            Additional_Details__c = 'Test update'
        );
        
        Database.SaveResult result = EventBus.publish(statusUpdate);
        
        // Ensure event published successfully
        System.assert(result.isSuccess());
        
        // Deliver the event to trigger
        Test.stopTest();
        
        // Verify trigger actions
        Task[] tasks = [SELECT Subject FROM Task 
                       WHERE Subject LIKE '%Order Status Changed%'];
        System.assertEquals(1, tasks.size(), 'Task should be created');
    }
}