* SINGLE RESPONSIBILITY PRINCIPLE (SRP)
* OPEN/CLOSED PRINCIPLE
* LISKOV SUBSTITUTION PRINCIPLE
* INTERFACE SEGREGATION PRINCIPLE
* DEPENDENCY INVERSION PRINCIPLE


The SOLID principles are a set of five design principles that help guide software developers in creating more maintainable, flexible, and understandable code. These principles were introduced by Robert C. Martin and are widely used in object-oriented programming to achieve better software design. The SOLID acronym stands for:

1. **S - Single Responsibility Principle (SRP):**
   This principle states that a class should have only one reason to change, meaning it should have only one responsibility. In other words, a class should focus on doing one thing well and not take on multiple responsibilities.

   **Example:**
   Imagine you're building a user management system. The `User` class should handle user-related operations like authentication, profile management, and password reset. It shouldn't be responsible for handling email notifications or payment processing.

2. **O - Open/Closed Principle (OCP):**
   This principle suggests that software entities (classes, modules, functions, etc.) should be open for extension but closed for modification. This means you should be able to add new functionality without changing existing code.

   **Example:**
   Suppose you're creating a shape-drawing application. Instead of modifying the existing `Shape` class to add a new shape type, you can extend the application by creating a new class that inherits from `Shape`, like `Triangle` or `Circle`.

3. **L - Liskov Substitution Principle (LSP):**
   This principle emphasizes that objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program. In other words, subclass instances should be able to be used interchangeably with superclass instances without altering the desired behavior.

   **Example:**
   If you have a `Bird` superclass and a `Penguin` subclass, the `Penguin` class should be able to substitute for the `Bird` class wherever the `Bird` class is used, without causing unexpected behavior.

4. **I - Interface Segregation Principle (ISP):**
   The ISP states that a class should not be forced to implement interfaces it doesn't use. This principle encourages creating smaller, focused interfaces rather than large, all-encompassing ones.

   **Example:**
   Consider an application where users can have both administrative and regular user roles. Instead of having a single giant `User` interface that includes all possible methods for both roles, you could create separate interfaces, like `AdminUser` and `RegularUser`, each containing methods relevant to their respective roles.

5. **D - Dependency Inversion Principle (DIP):**
   This principle suggests that high-level modules should not depend on low-level modules, but both should depend on abstractions. It also promotes that abstractions should not depend on details; details should depend on abstractions.

   **Example:**
   In a messaging system, the high-level module (e.g., the application logic) should not directly depend on the low-level module (e.g., the specific messaging API). Instead, both should depend on an abstraction (interface or abstract class) that defines how communication should occur.




   key difference between ISP and SRP
Key differences:

Scope: SRP focuses on class responsibilities, while ISP focuses on interface design
Purpose: SRP aims to achieve high cohesion, while ISP aims to achieve loose coupling
Level: SRP operates at the class implementation level, while ISP operates at the interface level


```apex
// BEFORE - Violating both SRP and ISP
interface DocumentHandler {
    void readDocument();
    void writeDocument();
    void printDocument();
    void scanDocument();
    void faxDocument();
    void emailDocument();
}

class PrinterScanner implements DocumentHandler {
    // Has to implement ALL methods, even though it can only print and scan
    public void readDocument() { throw new UnsupportedOperationException(); }
    public void writeDocument() { throw new UnsupportedOperationException(); }
    public void printDocument() { /* Can do this */ }
    public void scanDocument() { /* Can do this */ }
    public void faxDocument() { throw new UnsupportedOperationException(); }
    public void emailDocument() { throw new UnsupportedOperationException(); }
}

// AFTER - Following both SRP and ISP
// Split interfaces based on specific roles (ISP)
interface Printable {
    void print();
}

interface Scannable {
    void scan();
}

interface Emailable {
    void email();
}

interface Faxable {
    void fax();
}

// Classes with single responsibilities (SRP)
class Printer implements Printable {
    public void print() {
        // Only printing logic here
    }
}

class Scanner implements Scannable {
    public void scan() {
        // Only scanning logic here
    }
}

// A multifunction device can implement multiple focused interfaces
class MultifunctionPrinter implements Printable, Scannable {
    public void print() {
        // Printing logic
    }
    
    public void scan() {
        // Scanning logic
    }
}

// Usage example
class DocumentWorkflow {
    private final Printable printer;
    private final Scannable scanner;
    
    public DocumentWorkflow(Printable printer, Scannable scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }
    
    public void processDocument() {
        scanner.scan();
        printer.print();
    }
}