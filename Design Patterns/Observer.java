/*
🔹 𝗢𝗯𝘀𝗲𝗿𝘃𝗲𝗿 𝗣𝗮𝘁𝘁𝗲𝗿𝗻 𝗶𝗻 𝗔𝗽𝗲𝘅 – 𝗥𝗲𝗮𝗰𝘁 𝘁𝗼 𝗖𝗵𝗮𝗻𝗴𝗲𝘀 𝗗𝘆𝗻𝗮𝗺𝗶𝗰𝗮𝗹𝗹𝘆! 🔹

Ever needed a way to notify multiple parts of your code when something happens? Do you find yourself writing hardcoded dependencies between classes? 😵

✅ 𝗦𝗼𝗹𝘂𝘁𝗶𝗼𝗻? 𝗨𝘀𝗲 𝘁𝗵𝗲 𝗢𝗯𝘀𝗲𝗿𝘃𝗲𝗿 𝗣𝗮𝘁𝘁𝗲𝗿𝗻!

🔹 𝗪𝗵𝗮𝘁 𝗶𝘀 𝘁𝗵𝗲 𝗢𝗯𝘀𝗲𝗿𝘃𝗲𝗿 𝗣𝗮𝘁𝘁𝗲𝗿𝗻?
The Observer Pattern is a publisher-subscriber model where one object (the Subject) notifies multiple registered Observers when a change occurs. This creates a loosely coupled system where components react dynamically to updates!

🔥 𝗪𝗵𝘆 𝗨𝘀𝗲 𝗢𝗯𝘀𝗲𝗿𝘃𝗲𝗿 𝗶𝗻 𝗔𝗽𝗲𝘅?
🔹 Avoids direct dependencies between objects.
🔹 Supports dynamic subscriptions for event-driven behavior.
🔹 Enhances scalability and flexibility in complex systems.

🔥 𝗞𝗲𝘆 𝗧𝗮𝗸𝗲𝗮𝘄𝗮𝘆𝘀:
✅ No direct dependency between OrderService and notification services.
✅ Adding new notifications (e.g., Slack, WhatsApp) requires no modification to OrderService! 🚀
✅ Supports dynamic subscription/unsubscription of observers.

💡 𝗨𝘀𝗲 𝗰𝗮𝘀𝗲𝘀:
🔹 Event-driven systems (e.g., order notifications, approvals).
🔹 Audit logging for Salesforce transactions.
🔹 Chained event handling (e.g., lead assignment, case escalations).
*/

// Without Observer
public class OrderService {
    public void placeOrder() {
        EmailService.sendEmail();
        SMSService.sendSMS();
    }
}

public class EmailService {
    public static void sendEmail() {
        System.debug('Email Sent');
    }
}


public class SMSService {
    public static void sendEmail() {
        System.debug('SMS Sent');
    }
}

OrderService service = new OrderService();
service.placeOrder(); // Directly depends on EmailService & SMSService

// With Observer
// Step 1 : Define Observer Interface
public interface OrderObserver {
    void onOrderPlaced();
}

// Step 2 : Create Objservers (Listeners)
public class EmailService implements OrderObserver {
    public void onOrderPlaced() {
        System.debug('Order Confirmation Email');
    }
}

public class SMSService implements OrderObserver {
    public void onOrderPlaced() {
        System.debug('Order Confirmation SMS');
    }
}

// Step 3 : Create Subject (Publisher)
public class OrderService {
    private List<OrderObserver> observers = new List<OrderObserver>();

    public void addObservers(OrderObserver observer) {
        observers.add(observer);
    }

    public void placeOrder() {
        // Notify all observers dynamically!
        for (OrderObserver observers : observers) {
            observer.onOrderPlaced();
        }
    }
}


// Step 4 : Use the Observer Pattern
OrderService service = new OrderService();
service.addObservers(new EmailService());
service.addObservers(new SMSService());

service.placeOrder();