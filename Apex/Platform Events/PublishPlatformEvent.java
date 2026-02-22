// Publish the event when changes are made in a custom or standard object.
// From a trigger or flow. 
// Or when something is clicked. (Done using LWC)

public with sharing class PublishPlatformEvent {
    public static void PublishPlatformEventDemo() {
        List<Order_Detail_e> orderDetailListEvent = new List<Order_Detail_e>();
        orderDetailListEvent. add(
            new Order_Detail_e(
                Order_Number_c = 'ORD2000',
                Generate_Invoice_c = false
            )
        );


        orderDetailListEvent.add(
            new Order_Detail_e(
                Order_Number_c = 'ORD3000',
                Generate_Invoice_c = true
            )
        );

        orderDetailListEvent.add(
            new Order_Detail_e(
                Order_Number_C = 'ORD4000',
                Generate_Invoice_c = false
            )
        );

        /*
        * This is used for monitoring if the platform event published successfully.
        * If you don't need to check that, then this is not required. This parameter need not be sent in the EventBus.publish. 
        */
        PublishPlatformEventCallback callbackInstance = new PublishPlatformEventCallback(); 

        List<Database.SaveResult> srList = EventBus.publish(orderDetailListEvent, callbackInstance);

        for(Database.SaveResult srItem : srList){
            if (srItem.isSuccess()) {
                System.debug('Platform Event Published Successfully');
            } else {
                //Error Handling
            }
        }
    }
}