/*
* DIP : The Dependency Inversion Principle emphasizes that high-level modules should not depend on low-level modules; both should depend on abstractions.
* Abstractions should not depend on details; details should depend on abstractions.
*/

// Not following DIP
// High-level module depending directly on low-level module
class EmailService {
    public void sendEmail(String message) {
        System.out.println("Sending email: " + message);
    }
}

class NotificationManager {
    private EmailService emailService; // Direct dependency on concrete class
    
    public NotificationManager() {
        this.emailService = new EmailService(); // Tight coupling
    }
    
    public void sendNotification(String message) {
        emailService.sendEmail(message);
    }
}

// Following DIP
// Abstraction (interface)
interface NotificationSender {
    void send(String message);
}

// Low-level modules implementing the abstraction
class EmailService implements NotificationSender {
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class SMSService implements NotificationSender {
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// High-level module depending on abstraction
class NotificationManager {
    private NotificationSender sender; // Depends on interface, not concrete class
    
    public NotificationManager(NotificationSender sender) {
        this.sender = sender; // Dependency injected
    }
    
    public void sendNotification(String message) {
        sender.send(message);
    }
}

// Implementation
public class Main {
    public static void main(String[] args) {
        // Can easily switch between different implementations
        NotificationManager emailManager = new NotificationManager(new EmailService());
        NotificationManager smsManager = new NotificationManager(new SMSService());
        
        emailManager.sendNotification("Hello via Email!");
        smsManager.sendNotification("Hello via SMS!");
    }
}