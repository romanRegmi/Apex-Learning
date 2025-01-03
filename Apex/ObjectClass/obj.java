class Laptop {
    String model;
    int price;

    public String toString(){
        return model + " : " + price;
    }

    public boolean equals(Laptop that){
        if(this.model.equals(that.model) && this.price == that.price){
            return true;
        } else {
            return false;
        }
    }

}

public class Demo {
    Laptop obj = new Laptop();
    obj.model = 'Lenovo';
    obj.price = 1000;

    System.debug(obj); //Laptop@7ad041f3
    System.debug(obj.toString()); //Laptop@7ad041f3 [Same as above. The standard toString() method is called automatically.]

    // We can override the toString method.

    System.debug(obj); //Lenovo : 1000
    System.debug(obj.toString()); //Lenovo : 1000

    Laptop obj2 = new Laptop();
    obj2.model = 'Lenovo';
    obj2.price = 1000;

    boolean result = obj1 == obj2; //false
    boolean result = obj1.equals(obj2); //false

    // Implement equals method by ourselves
    boolean result = obj1 == obj2; //true
    boolean result = obj1.equals(obj2); //true


}