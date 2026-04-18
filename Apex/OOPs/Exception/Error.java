try{
    Account acc = new Account(Name='Acc 000000');
    insert acc; // Is inserted
    
    Contact con = new Contact(FirstName = 'fffff');
    insert con; // Fails
} catch (Exception ex){
    System.debug(ex.getMessage()); // Logged
}

// TOO MANY DML - Entire transaction is rolled back because governor limit exceptions cannot be caught

Account myAccount = new Account(Name='MyAccount');
insert myAccount; // succeeds

for(Integer x = 0; x < 150; x++) {
    Account newAccount = new Account(Name='Acc-' + x);
    try {
        insert newAccount;
    }catch(Exception ex) {
        System.Debug(ex);
    }
}
insert new Account(Name='Acc');