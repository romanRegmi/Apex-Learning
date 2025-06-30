/*
* LISKOV SUBSTITUTION PRINCIPLE : All subclasses of a super class need to be able to implement all the super class methods without an error occuring.
* Objects of a derived class should be substitutable for objects of the base class without affecting the correctness of the program.
*/

class Bird {
    void fly() {
        // common flying behavior
    }

    void makeSound(){
        // make sound
    };
}

class Eagle extends Bird {
    // implementation of fly() for eagles
}

class Penguin extends Bird {
    void fly() {
        throw new UnsupportedOperationException("Penguins cannot fly");
    }
}

// Better approach
interface Bird {
    void eat();
    void makeSound();
}

interface Flyable {
    void fly();
}

class Eagle implements Bird, Flyable {
    public void fly() { /* flying implementation */ }
    public void eat() { /* eating implementation */ }
    public void makeSound() { /* sound implementation */ }
}

class Penguin implements Bird {
    public void eat() { /* eating implementation */ }
    public void makeSound() { /* sound implementation */ }
    // No fly() method - penguins simply don't implement Flyable
}

// Better approach using abstract classes

abstract class Bird {
    abstract void eat();
    abstract void makeSound();
}

abstract class FlyingBird extends Bird {
    abstract void fly();
}

class Eagle extends FlyingBird {
    void fly() { /* flying implementation */ }
}

class Penguin extends Bird {
    // No fly() method at all
}