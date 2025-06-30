public class Person {
    private string name;
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