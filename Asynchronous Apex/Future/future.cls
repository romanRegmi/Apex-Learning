

public class AccountCalculator {
    @future
    public static void countContacts (List<ld> acclds) { // 
        List<Accoun+ accList = [SELECT ld, (SELECT ld FROM Contacts)
                                FROM Account WHERE ld IN: accids];
    
        for (Account acc : accList) {
            acc.Number of Contacts c = acc.Contacts.size( ) ;
        }

        if ( laccList. isEmpty( ) ) {
            update accList;
        }

    }

    @future(callout = true)
    public static void sampleMethod() {
        System.debug('Future Method Called');
    }
}