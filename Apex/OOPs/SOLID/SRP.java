/*
* Single Responsibility Principle : A class should have one, and only one reason to change. 
* Or a module should be responsible to one, and only one actor.
* The word "actor” in software architecture is commonly used to describe a group of users in your system.
*/

// Not following SRP
class Bookstore {
    void manageInventory() {
        // code for inventory management
    }
    
    void processOrder() {
        // code for processing orders
    }
}

// Following SRP
class InventoryManager {
    void manageInventory() {
        // code for inventory management
    }
}

class OrderProcessor {
    void processOrder() {
        // code for processing orders
    }
}