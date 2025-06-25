// Service Layer Pattern
public class AccountService {
    private AccountRepository repository;
    
    public AccountService(AccountRepository repo) {
        this.repository = repo;
    }
    
    public void createAccount(String name) {
        // Validate
        if(String.isBlank(name)) {
            throw new ServiceException('Name required');
        }
        
        // Create
        Account acc = new Account(Name = name);
        repository.create(acc);
    }
}