/*
* INTERFACE SEGREGATION PRINCIPLE : Classes should not be forced to implement interfaces that it doesn't use.
* It is better to have multiple smaller interfaces than larger interfaces
*/

public interface IVehicle {
    void Drive();
    void Fly();
}

class Car implements IVehicle {
    public void Drive() {

    }

    public void Fly() {
        // A normal car doesn't have to implement this
    }
}


// Create two interfaces
public interface IDrive {
    void Drive();
}

public interface IFly {
    void Fly();
}


// Not following ISP
interface MediaPlayer {
    void playAudio();
    void playVideo();
    void showImage();
}

// Following ISP
interface AudioPlayer {
    void playAudio();
}

interface VideoPlayer {
    void playVideo();
}

interface ImageViewer {
    void showImage();
}




// 5. Dependency Inversion Principle (DIP)
// House Analogy: Standardized connections between systems
// - Pipes fit standard sizes regardless of manufacturer
// - Electrical outlets follow standard specifications

// Bad Example: High-level module depends on low-level module
class House {
    CopperPipe waterPipe = new CopperPipe();
    
    void supplyWater() {
        waterPipe.flow();
    }
}

// Good Example: Both depend on abstraction
interface WaterPipe {
    void flow();
}

class CopperPipe implements WaterPipe {
    public void flow() { /* copper pipe implementation */ }
}

class PEXPipe implements WaterPipe {
    public void flow() { /* PEX pipe implementation */ }
}

class House {
    private final WaterPipe waterPipe;
    
    House(WaterPipe waterPipe) {
        this.waterPipe = waterPipe;
    }
    
    void supplyWater() {
        waterPipe.flow();
    }
}