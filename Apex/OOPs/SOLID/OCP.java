/*
* Open/Closed Principle : Software entities(classes, modules, functions, etc) should be open for extension, but closed (as little changes as possible) for modification. 
* Analogy : Once a house is built, we can add new features like a porch or a swimming pool without changing the existing structure.
* Explanation : Adding new functionality to the software should not need modification in the existing code.
*/


// Bad
class PaymentProcessor {
    void processPayment(String type) {
        if (type.equals("credit")) {
            // process credit
        } else if (type.equals("debit")) {
            // process debit
        }
    }
}

// Good
interface PaymentProcessor {
    void processPayment();
}
class CreditPaymentProcessor implements PaymentProcessor {
    void processPayment() { /* ... */ }
}
class DebitPaymentProcessor implements PaymentProcessor {
    void processPayment() { /* ... */ }
}