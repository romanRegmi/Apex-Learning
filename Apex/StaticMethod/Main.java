public class Main {
  public static void main(String[] args) {
    Car c = new Car();
    System.out.println(c.maxSpeed); // 120 (if the maxSpeed isn't defined in the Car class)
    c.display();
    c.vroom();
  }
}

/*
Final output
  This is the vehicle constructor
  This is the car constructor
  100
  100
  120
  Vroom vroom
*/