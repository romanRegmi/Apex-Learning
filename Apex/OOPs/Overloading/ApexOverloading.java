/*
 * Method Overloading
*/
public class ApexOverloading {
    
    public static void main(){
        integer result1 = addNumbers(3, 2);
        decimal  result2 = addNumbers(3.1, 2.7);
        
        System.debug(result1);
        System.debug(result2);
    }
    
    public static integer addNumbers(integer a, integer b){
        return a + b;
    }
    
    public static decimal addNumbers(decimal a, decimal b){
        return a + b;
    }

}