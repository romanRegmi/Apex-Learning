public class UpdateParentAccount implements Queueable {
    private List<Account> accList;
    private Id parentAccId;

    public UpdateParentAccount(List<Account> accRecords, Id parentAccountId) {
        this.accList = accRecords;
        this.parentAccId = parentAccountId;
    }

    public void execute(QueueableContext context) {
        for (Account acc : accList) {
            acc.ParentId = parentAccId;
        }
        update accList;
    }
}
