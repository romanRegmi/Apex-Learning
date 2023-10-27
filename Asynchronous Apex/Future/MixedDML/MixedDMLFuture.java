public class MixedDMLFuture {
	public static void useFutureMethod() {
	Account a = new Account(Name='Acme');
    insert a;
    Util.insertUserWithRole(
        'regmi@future.ceb.com', 'Roman',
		'romanregmi251@gmail.com', 'Regmi');
    }
    
    public static void useNoFutureMethod() {
        Account a = new Account(Name='Acme');
    	insert a;
        Util.insertUserWithNoRole(
        'regmi@nofuture.ceb.com', 'Roman',
		'romanregmi251@gmail.com', 'Regmi');
    }
}

/*
* MixedDMLFuture.useFutureMethod();
*/