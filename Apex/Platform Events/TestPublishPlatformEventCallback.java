@IsTest
public with sharing class PublishPlatformEventCallbackTest {
    @isTest
    static void testFailedEvents() {
        Order_Detail_e orderDetail = new Order_Detail_e(
            Order_Number_C = 'ORD4000',
            Generate_Invoice_c = false
        );

        PublishPlatformEventCallback cb = new PublishPlatformEventCallback();
        EventBus.publish(orderDetail, cb);
        Test.getEventBus().fail();

        List<Task> taskList = [SELECT Id FROM Task WHERE Subject = 'Platform Event Failed'];
        System.assert.areEqual(1, taskList.size());

    }

    @isTest
    static void testSuccessEvents() {
        Order_Detail_e orderDetail = new Order_Detail_e(
            Order_Number_C = 'ORD4000',
            Generate_Invoice_c = false);

        PublishPlatformEventCallback cb = new PublishPlatformEventCallback();
        Test.statTest();
        EventBus.publish(orderDetail, cb);
        Test.stopTest();
        Test.getEventBus().fail();

        List<Task> taskList = [SELECT Id FROM Task WHERE Subject = 'Platform Event Published Successfully'];
        System.assert.areEqual(1, taskList.size());

    }

}