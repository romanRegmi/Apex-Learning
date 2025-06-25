/*
* DIP : The Dependency Inversion Principle emphasizes that high-level modules should not depend on low-level modules; both should depend on abstractions.
* Additionally, abstractions should not depend on details; details should depend on abstractions.
*/

// Not following DIP
class UserService {
    Database database;

    UserService() {
        database = new MySQLDatabase(); // tight coupling
    }
    
    // ...
}

// Following DIP
interface Database {
    void save(User user);
    User findById(int id);
}

class MySQLDatabase implements Database {
    // ...
}

class UserService {
    Database database;

    UserService(Database database) {
        this.database = database; // loose coupling via dependency injection
    }
    
    // ...
}