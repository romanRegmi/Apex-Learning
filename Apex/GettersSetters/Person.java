/*
* NOTES : Notes about getters and setters
* Making something private isn't about making it inaccessible — it's about controlling how it's accessed.
* When a variable is public, anyone can read or write to it freely — with no rules, no validation, no checks. A getter (and setter) lets you put a gatekeeper in front of that data.
* Suppose you store a temperature internally in Celsius, but your getter returns Fahrenheit. The outside world doesn't need to know how you store it.
*/

public class Person {
    private String name;
    private Integer age = 50;

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getAge() {
        return age;
    }
    
    // You can add validation
    public void setAge(Integer age) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.debug('Age cannot be negative.');
        }
    }

}

Person person = new Person();
person.setName('John Doe');
person.setAge(-30); // Will debug the error

// Can access private variables
System.debug('Name: ' + person.getName()); // John Doe
System.debug('Age: ' + person.getAge()); // 50