/* This test isn't related to the UnitOfWork class*/

@isTest
public class UnitOfWorkTest {
	@isTest
    public static void challengeComplete(){
        fflib_SObjectUnitOfWork uow = new fflib_SObjectUnitOfWork(new List<SObjectType> { Account.SObjectType, Contact.SObjectType, Note.SObjectType });

		for(Integer o=0; o<100; o++) {
            Account acc = new Account();
            acc.Name = 'UoW Acc Name ' + o;
            uow.registerNew(acc);
        }
        
        for(Integer o=0; o<500; o++) {
            Contact con = new Contact();
            con.LastName = 'UoW Con Name ' + o;
            uow.registerNew(con);
            Note cd = new Note();
            cd.Title = 'Sample Note ' + o;
			cd.Body = 'This is the note content.';
            uow.registerRelationship(cd, Note.ParentId, con);
            uow.registerNew(cd);
            
        }
        
        // Commit the work to the database!
		uow.commitWork();
        
        System.assertEquals(100, [Select Id from Account].size());
        System.assertEquals(500, [Select Id from Contact].size());
        System.assertEquals(500, [Select Id from Note].size());
        
    } 
}