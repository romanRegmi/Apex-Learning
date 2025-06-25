/*
* LISKOV SUBSTITUTION PRINCIPLE : All subclasses of a super class need to be able to implement all the super class methods without an error occuring.
* Objects of a derived class should be substitutable for objects of the base class without affecting the correctness of the program.
*/

class Bird {
    void fly() {
        // common flying behavior
    }
}

class Eagle extends Bird {
    // implementation of fly() for eagles
}

class Penguin extends Bird {
    void fly() {
        throw new UnsupportedOperationException("Penguins cannot fly");
    }
}


// 3. Liskov Substitution Principle (LSP)
// House Analogy: Any room in the house should fulfill basic room requirements
// - All rooms must have walls, floor, ceiling
// - Specialized rooms add features without breaking basic room functionality

// Bad Example: Breaks room contract
class Room {
    void addWalls() { /* add walls */ }
    void addCeiling() { /* add ceiling */ }
}

class Balcony extends Room {
    @Override
    void addCeiling() {
        throw new UnsupportedOperationException("Balcony has no ceiling");
        // Breaks LSP because it doesn't fulfill Room contract
    }
}

// Good Example: Proper inheritance hierarchy
interface RoomStructure {
    void addWalls();
}

class EnclosedRoom implements RoomStructure {
    public void addWalls() { /* add walls */ }
    void addCeiling() { /* add ceiling */ }
}

class OpenRoom implements RoomStructure {
    public void addWalls() { /* add partial walls */ }
}
