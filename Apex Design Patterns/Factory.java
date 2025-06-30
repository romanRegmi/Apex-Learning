// Factory Pattern :The Factory pattern is used to create objects without specifying the exact class of the object that will be created.
public interface Notification {
    void sendNotification(String message);
}

public class EmailNotification implements Notification {
    public void sendNotification(String message) {
        System.debug('Sending email: ' + message);
    }
}
public class SMSNotification implements Notification {
    public void sendNotification(String message) {
        System.debug('Sending SMS: ' + message);
    }
}
public class NotificationFactory {
    public static Notification getNotification(String type) {
        if (type == 'Email') {
            return new EmailNotification();
        } else if (type == 'SMS') {
            return new SMSNotification();
        } else {
            throw new IllegalArgumentException('Invalid Notification Type');
        }
    }
}

/* DIP without factory pattern */
// DIP - depending on abstraction
interface PaymentProcessor {
    void processPayment(double amount);
}

class CreditCardProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment: $" + amount);
    }
}

class PayPalProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
    }
}

// High-level module following DIP
class OrderService {
    private PaymentProcessor processor;
    
    // Constructor injection - DIP without Factory
    public OrderService(PaymentProcessor processor) {
        this.processor = processor;
    }
    
    public void processOrder(double amount) {
        processor.processPayment(amount);
    }
}

// Usage - manually creating dependencies
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new CreditCardProcessor(); // Direct instantiation
        OrderService orderService = new OrderService(processor);
        orderService.processOrder(100.0);
    }
}


/* DIP with factory pattern */
// Same interfaces and implementations as above...

// Factory pattern for creating payment processors
class PaymentProcessorFactory {
    public static PaymentProcessor createProcessor(String type) {
        switch (type.toLowerCase()) {
            case "creditcard":
                return new CreditCardProcessor();
            case "paypal":
                return new PayPalProcessor();
            default:
                throw new IllegalArgumentException("Unknown processor type: " + type);
        }
    }
}

// OrderService remains the same - still follows DIP

// Usage with Factory
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = PaymentProcessorFactory.createProcessor("creditcard");
        OrderService orderService = new OrderService(processor);
        orderService.processOrder(100.0);
    }
}