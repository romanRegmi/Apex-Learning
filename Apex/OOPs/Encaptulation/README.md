## Difference between Virtual class, Abstract class and Interface

### Instantiation

1. Virtual class: Can be instantiated
2. Abstract class: Cannot be instantiated
3. Interface: Cannot be instantiated


### Method Implementation:

1. Virtual class: All methods must have implementation
2. Abstract class: Can have both implemented and abstract methods
3. Interface: Cannot have implemented methods


### Multiple Inheritance:

1. Virtual/Abstract classes: Apex doesn't support multiple inheritance (can't extend multiple classes)
2. Interfaces: A class can implement multiple interfaces


### Properties and Variables:

1. Virtual/Abstract classes: Can have properties and variables
2. Interface: Cannot have properties or variables


Abstract method is acts like a template and has no logic in it. Any subclass needs to implement its methods. Whereas a virtual class has a default implementation for its methods which can be overriden by subclass. An abstract class can have a virtual method and a virtual class can have an abstract method.