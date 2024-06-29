public class UpdateAccountClass {
    public void publishEvent(Id AccountId){
        Account accObj = [SELECT id, CustomerPriority_c from Account WHERE Id =: AccountId];
        accObj.CustomerPriority_c = 'High';
        update accObj;
    
        Update_Case_on_Account_Update_e eventObj = new Update_Case_on_Account_Update_e(Account_Id_c = accObj.Id);
        Database.SaveResult sr = EventBus.publish(eventObj);
        
        if (sr.isSuccess()) {
            System.debug('Successfully queued event.');
        } else {
            System.debug('Error queuing event.');
        }
    }
}

