// Only one object of a class is instantiated.
// Singleton Pattern
public class SingletonExample {
    private static SingletonExample instance;
    
    private SingletonExample() {}
    
    public static SingletonExample getInstance() {
        if(instance == null) {
            instance = new SingletonExample();
        }
        return instance;
    }
}

