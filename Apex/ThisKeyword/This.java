public class Person {
    private String name;
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // A method to set the name of the person
    public Person setName(String name) {
        this.name = name;
        return this; // Return the current object (Person) for method chaining
    }

    // A method to set the age of the person
    public Person setAge(int age) {
        this.age = age;
        return this; // Return the current object (Person) for method chaining
    }

    // A method to display the person's information
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public static void main(String[] args) {
        // Create a new Person object and use method chaining to set its properties
        Person person = new Person("Alice", 30)
            .setName("Bob")
            .setAge(25);

        // Display the person's information
        person.displayInfo();
    }
}