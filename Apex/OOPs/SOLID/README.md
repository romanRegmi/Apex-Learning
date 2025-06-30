The SOLID principles are a set of five design principles that help guide software developers in creating more maintainable, flexible, and understandable code. These principles were introduced by Robert C. Martin and are widely used in object-oriented programming to achieve better software design. The SOLID acronym stands for:


4. **I - Interface Segregation Principle (ISP):**
   The ISP states that a class should not be forced to implement interfaces it doesn't use. This principle encourages creating smaller, focused interfaces rather than large, all-encompassing ones.

   **Example:**
   Consider an application where users can have both administrative and regular user roles. Instead of having a single giant `User` interface that includes all possible methods for both roles, you could create separate interfaces, like `AdminUser` and `RegularUser`, each containing methods relevant to their respective roles.


key difference between ISP and SRP

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