Key Differences:

Instantiation:

Virtual class: Can be instantiated
Abstract class: Cannot be instantiated
Interface: Cannot be instantiated


Method Implementation:

Virtual class: All methods must have implementation
Abstract class: Can have both implemented and abstract methods
Interface: Cannot have implemented methods


Multiple Inheritance:

Virtual/Abstract classes: Apex doesn't support multiple inheritance (can't extend multiple classes)
Interfaces: A class can implement multiple interfaces


Properties and Variables:

Virtual/Abstract classes: Can have properties and variables
Interface: Cannot have properties or variables


Abstract method is acts like a template and has no logic in it. Any subclass needs to implement its methods. Whereas a virtual class has a default implementation for its methods which can be overriden by subclass. An abstract class can have a virtual method and a virtual class can have an abstract method.