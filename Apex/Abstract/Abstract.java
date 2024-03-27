interface DogInterface { // interface is private by default
    // Methods in an interface are by default abstract.
    // We have to implement them. 
    // They do not have a body.
    void bark();
    void poop();
}



abstract class Dog {
    String breed;

    // abstract classes can have methods that are already implemented. Interfaces can't 
    public void bark() {
        System.out.println("Bark!");
    }

    public abstract void poop(); // Abstract method must be implemented in every class that extends the abstract class
    // Abstract method does not have a body.
}

class Chihuahua extends Dog {
    public void poop() {
        System.out.println("Poop");
    }
}

public class Abstract {
    public static void main(String[] args) {
        Dog d = new Dog(); // Abstract classes do not work like regular classes. A class must extend it.
        d.bark();

        Chihuahua d = new Chihuahua();
        d.bark();

    }
}