/*
* Abstract nature: Interfaces cannot be instantiated directly. They provide a blueprint for classes to implement.
* Method declaration: Interface methods have declarations but no implementations. The implementing class must provide the method body.
* Method overriding: All interface methods must be overridden by the implementing class.
* Method modifiers: Interface methods are implicitly public and abstract.
* Attribute modifiers: Interface attributes are implicitly public, static, and final.
* Constructor absence: Interfaces cannot have constructors.
* Multiple inheritance: Interfaces allow a class to implement multiple interfaces, achieving a form of multiple inheritance.
------------------------------------------------------------------------------------------------------------------------------
Reasons to use interfaces:
* Security: Interfaces can hide unnecessary details, providing a secure and controlled way to interact with objects.
* Multiple inheritance: By implementing multiple interfaces, a class can inherit behavior from different sources, enhancing its functionality and flexibility.
* When you want to define a contract that multiple unrelated classes should follow.
*/

public class Flyable {
    public void fly() {
        System.debug('Fly');
    }

    public void land() {
        System.debug('Land');
    }
}


public class Swimmable {
    public void swim() {
        System.debug('Swim');
    }
}


public class Duck extends Flyable, Swimmable {
    // Apex doesn't support multiple inheritance
}


// Implemented using the concept of interface.

public interface Flyable {
    void fly();
    void land();
}

public interface Swimmable {
    void swim();
}

// Class implementing multiple interfaces
public class Duck implements Flyable, Swimmable {
    public void fly() {
        System.debug('Duck is flying');
    }
    
    public void land() {
        System.debug('Duck landed');
    }
    
    public void swim() {
        System.debug('Duck is swimming');
    }
}