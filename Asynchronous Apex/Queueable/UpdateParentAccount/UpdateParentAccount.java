public class UpdateParentAccount implements Queueable {
    private List<Account> accList;
    private Id parentAccId;

    public UpdateParentAccount(List<Account> accRecords, Id parentAccountId) {
        this.accList = accRecords;
        this.parentAccId = parentAccountId;
    }

    public void execute(QueueableContext context) {

        // The context variable can be used to monitor the Queueable apex inside the method itself.
        Id jobId = context.getJobId();
        AsyncApexJob jobInfo = [SELECT Status, NumberOfErrors FROM AsyncApexJob WHERE Id =: jobId];

        for (Account acc : accList) {
           // Logic 
        }

        // Call another Queueable
        ContactsQueueable conQueueable = new ContactsQueueable();
        Id conJobId = System.enqueJob(conQueueable); // Note : Different transaction
    }
}

// To run the above class
Integer delayInMinutes = 5; // We can delay the time.
List<Account> accRecords = new List<Account>();
UpdateParentAccount updateAcc = new UpdateParentAccount(accRecords, '001XXXXXXXXXXXX');
Id accJob = System.enqueJob(updateAcc, delayInMinutes);
