// Observer Pattern
public interface Observer {
    void update(String message);
}

public class EmailObserver implements Observer {
    public void update(String message) {
        // Send email
    }
}

public class Subject {
    private List<Observer> observers = new List<Observer>();
    
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    public void notifyObservers(String message) {
        for(Observer obs : observers) {
            obs.update(message);
        }
    }
}

// Strategy Pattern
public interface PaymentStrategy {
    void pay(Decimal amount);
}

public class CreditCardPayment implements PaymentStrategy {
    public void pay(Decimal amount) {
        // Process credit card payment
    }
}

public class PayPalPayment implements PaymentStrategy {
    public void pay(Decimal amount) {
        // Process PayPal payment
    }
}