// Factory Pattern
public interface Animal {
    void makeSound();
}

public class Dog implements Animal {
    public void makeSound() {
        System.debug('Woof!');
    }
}

public class Cat implements Animal {
    public void makeSound() {
        System.debug('Meow!');
    }
}

public class AnimalFactory {
    public static Animal createAnimal(String type) {
        if(type == 'Dog') return new Dog();
        if(type == 'Cat') return new Cat();
        return null;
    }
}