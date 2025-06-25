/*
* Can be instantiated (you can create objects)
* Methods marked as virtual can be overridden (optional)
* Can have implemented methods
* Can have non-virtual methods that can't be overridden
* Use Case : When you want to provide a base implementation that might need to be customized in some cases
*/

public virtual class Animal {
    public virtual void makeSound() {
        System.debug('Some generic sound');
    }
    
    // Non-virtual method - can't be overridden
    public void sleep() {
        System.debug('Sleeping');
    }
}

public class Dog extends Animal {
    // Override the virtual method
    public override void makeSound() {
        
        System.debug('Woof!');
    }
}