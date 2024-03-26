public with sharing class PublishPlatformEventCallback implements EventBus.EventPublishSuccessCallback, EventBus.EventPublishFailureCallback{
    public void onSuccess(EventBus. SuccessResult result) {
        List<String> EventUUILDList = result.getEventUuids();
    }

    public void onFailure(EventBus.FailureResult result){
        List<String> EventUUILDList = result.getEventUuids();
    }

    public void insertTask(List<String> EventUUILDList, Boolean isSuccess){
        String EventUUILDString = '';
        for(String EventUUID : EventUUILDList){
            EventUUILDList = EventUUILDString + EventUUID + '';
        }

        Task taskDetail = new Task();
        if(isSuccess == true){
            taskDetail.Subject = 'Platform Event Published Successfully';
            taskDetail.Description = 'Total Platform Events Published ' + EventUUILDList.size() + ' List of Event UUID Published '
        } else {
            taskDetail.Subject = 'Platform Event Failed';
        }

        taskDetail.ActivityDate = Date.today();
        User userDetail = [SELECT Id FROM USER WHERE UserName = ''];
        taskDetail.OwnerId = userDetail.Id; //By default : Automated User

        try {
            insert taskDetail;
        }catch(Exception ex) {
            System.debug('Task insertion failed '+ex.getMessage());
        }
    }
}