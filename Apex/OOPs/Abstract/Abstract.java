/*
 * An interface is a class in which none of the methods have been implemented.
 * To use an interface, another class must implement it by providing a body for all of the methods contained in the interface.
 * An interface is private by default.
 * Methods in an interface are by default abstract.
 * Abstract methods must be implemented by the classes that extend it.
 * Abstract methods aren't defined. 
*/
interface DogInterface {
    void bark();
    void poop();
}

// Create an abstract class to represent general dog characteristics
abstract class Dog {
    /**
     * Provides a default bark implementation for all dogs. This can be overridden
     * by subclasses for breed-specific barks.
     */
    public void bark() {
        System.out.println("Bark!");
    }

    /**
     * An abstract method must be implemented in every class that extends the abstract class
     * An abstract method does not have a body.
     */
    public abstract void poop();
}

// Extend Dog to create a concrete Chihuahua class
class Chihuahua extends Dog {
    @Override
    public void poop() {
        System.out.println("Small poop");  // Implement Chihuahua-specific pooping
    }
}

class GermanShepherd extends Dog { 
    // error: GermanShepherd is not abstract and does not override abstract method poop() in Dog
}

public class AbstractDogDemo {  // More descriptive class name

    public static void main(String[] args) {

        // Abstract classes cannot be directly instantiated
        // Dog d = new Dog();

        // Create a Chihuahua object and call its bark and poop methods
        Chihuahua chi = new Chihuahua();
        chi.bark();   // Output: Bark! (default bark)
        chi.poop();  // Output: Small poop

    }
}