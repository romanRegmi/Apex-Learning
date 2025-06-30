// Only one object of a class is instantiated.
// Singleton Pattern
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