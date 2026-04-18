/* 
* The super keyword helps us use variables and methods from the parent class.
*/

class Vehicle {
  
  Vehicle() {
    System.out.println("This is the vehicle constructor");
  }
  
  int maxSpeed = 120;
  
  public void vroom() {
    System.out.println("Vroom vroom");
  }
  
}

class Car extends Vehicle {
  int maxSpeed = 100;
  
  Car() {
    System.out.println("This is the car constructor");
  }
  
  public void display() {
    System.out.println(maxSpeed); // 100
    System.out.println(super.maxSpeed); // 120
  }
  
  public void vroom() {
    super.vroom();
  }
}