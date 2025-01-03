/*
* Cannot be instantiated (Objects cannot be created)
* Can have both abstract and implemented methods
* Abstract methods must be implemented by child classes
* Can have constructor
* Can have member variables
* Use Case : When you have some common functionality but want to force certain methods to be implemented by child classes
*/

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


/*
*
*/


class Laptop {
    public void code(){
        System.out.println("code");
    }
}

class Desktop {
    public void code(){
        System.out.println("code : desktop");
    }
}

class Developer {
    public void devApp(Laptop lap) {
        lap.code();
    }
}

Laptop lap = new Laptop();
Desktop desk = new Desktop();

Developer dev = new Developer();
dev.devApp(desk); // Will not work as the devApp only takes the Laptop object as a parameter


// solution

abstract class Computer {
    public abstract void code();
}

class Laptop extends Computer {
    public void code(){
        System.out.println("code : laptop");
    }
}

class Desktop extends Computer {
    public void code(){
        System.out.println("code : desktop");
    }
}

class Developer {
    public void devApp(Computer comp) {
        lap.code();
    }
}

Computer lap = new Laptop();
Computer desk = new Desktop();

Developer dev = new Developer();
Laptop lap = new Laptop();
Desktop desk = new Desktop();

Developer dev = new Developer();
dev.devApp(desk);
dev.devApp(lap);

// We can achieve the same using interfaces

interface Computer {
    void code();
}

class Laptop implements Computer {
    public void code(){
        System.out.println("code : laptop");
    }
}

class Desktop implements Computer {
    public void code(){
        System.out.println("code : desktop");
    }
}

class Developer {
    public void devApp(Computer comp) {
        lap.code();
    }
}

Computer lap = new Laptop();
Computer desk = new Desktop();

Developer dev = new Developer();
Laptop lap = new Laptop();
Desktop desk = new Desktop();

Developer dev = new Developer();
dev.devApp(desk);
dev.devApp(lap);