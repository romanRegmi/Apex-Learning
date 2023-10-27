@isTest
public class ContactBatchTest {
    @isTest
    static void testContactBatch() {
        
        // Create test Contact
        List<Account> accountList = new List<Account>();
        List<Contact> contactList = new List<Contact>();

        for (Integer i = 1; i <= 5; i++) {
            Account newAccount = new Account(Name = 'Account ' + i);
            accountList.add(newAccount);

            // Create 5 contacts for each account
            for (Integer j = 1; j <= 5; j++) {
                Contact newContact = new Contact(
                    FirstName = 'Contact ' + j,
                    LastName = 'of Account ' + i,
                    AccountId = newAccount.Id
                );
                contactList.add(newContact);
            }
        }

        // Insert accounts and contacts in a single transaction
        try {
            insert accountList;
            insert contactList;
            System.debug('Accounts and Contacts created successfully.');
        } catch (Exception e) {
            System.debug('Error: ' + e.getMessage());
        }


        // Start the batch
        Test.startTest();
        ContactBatch batchInstance = new ContactBatch();
        Database.executeBatch(batchInstance);
        Test.stopTest();

        // Verify that the Contacts have been updated
        List<Contact> updatedContacts = [SELECT Id, Email FROM Contact WHERE Id IN :contactList];
        for (Contact con : updatedContacts) {
            System.assertEquals('romanregmi@hotmail.com', con.Email, 'Contact Email should be updated to romanregmi@hotmail.com');
        }
        
        System.assertEquals(25, updatedContacts.size());
    }
}
