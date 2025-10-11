/*
🔹 𝗦𝘁𝗿𝗮𝘁𝗲𝗴𝘆 𝗣𝗮𝘁𝘁𝗲𝗿𝗻 𝗶𝗻 𝗔𝗽𝗲𝘅 – 𝗠𝗮𝗸𝗲 𝗬𝗼𝘂𝗿 𝗖𝗼𝗱𝗲 𝗠𝗼𝗿𝗲 𝗙𝗹𝗲𝘅𝗶𝗯𝗹𝗲! 🔹

Are you using if-else statements to handle multiple behaviors in Apex? This can make your code rigid and hard to maintain.

✅ 𝗦𝗼𝗹𝘂𝘁𝗶𝗼𝗻? 𝗨𝘀𝗲 𝘁𝗵𝗲 𝗦𝘁𝗿𝗮𝘁𝗲𝗴𝘆 𝗣𝗮𝘁𝘁𝗲𝗿𝗻!

🔹 What is the Strategy Pattern?
The Strategy Pattern allows you to define a family of algorithms, encapsulate each one, and make them interchangeable without modifying the original code.

🔥 𝗪𝗵𝘆 𝗨𝘀𝗲 𝗦𝘁𝗿𝗮𝘁𝗲𝗴𝘆 𝗶𝗻 𝗔𝗽𝗲𝘅?
🔹 Eliminates complex if-else conditions.
🔹 Supports dynamic behavior selection at runtime.
🔹 Improves code maintainability and flexibility.

🔥 𝗞𝗲𝘆 𝗧𝗮𝗸𝗲𝗮𝘄𝗮𝘆𝘀:
✅ Avoids if-else clutter.
✅ Easily adds new strategies without modifying existing code.
✅ Promotes clean, scalable, and modular design.
*/

// Without Strategy
public class PaymentService {
    public void processPayment(String type){
        if (type == 'CreditCard') {
            System.debug('Processed Credit Card Payment');
        } else if (type == 'PayPal') {
            System.debug('Processed PayPal Payment');
        } else {
            System.debug('Invalid Payment Type');
        }
    }
}

/* With Strategy */

// Step 1 : Create an interface for payment strategies
public interface PaymentStrategy {
    void processPayment();
}

// Step 2 : Implement different payment strategies
public class CreditCardPayment implements PaymentStrategy {
    public void processPayment(){
        System.debug('Processed Credit Card Payment');
    }
}

public class PayPalPayment implements PaymentStrategy {
    public void processPayment(){
        System.debug('Processed PayPal Payment');
    }
}

// Step 3 : Use a context class to handle payments dynamically
public class PaymentProcessor {
    private PaymentStrategy strategy;

    public PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void process() {
        strategy.processPayment();
    }
}


// Usage 
PaymentProcessor processor =  new PaymentProcessor(new CreditCardPayment());
processor.process();