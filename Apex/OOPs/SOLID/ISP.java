/*
* INTERFACE SEGREGATION PRINCIPLE : Classes should not be forced to implement interfaces that it doesn't use.
* ISP aims to achieve loose coupling by creating multiple smaller interfaces than larger interfaces
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