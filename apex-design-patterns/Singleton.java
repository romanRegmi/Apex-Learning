// Singleton Pattern - It's a creational design pattern which restricts the instantiation of a class to one “single” instance only within a single transaction context.

// Singleton Pattern : Only one object of a class is instantiated.
 
public class Logger {
    private static Logger instance = null;
    
    // Private constructor
    private Logger() { }
    
    // Public method to return the single instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        System.debug(message);
    }
}