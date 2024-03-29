public class ExceptionHandling {
    public static void myMethod(integer i) {
      try {
          if (i == 0) {
              integer a;
              integer b = a * 5; // NullPointer Exception
          }

          else {
            Account acc = new Account();
            insert acc; // DML Exception
          }
      }

        catch(DmlException d) {
            System.debug('DML Exception occured');
        }

        catch(Exception e) {
            System.debug('Exception other than DmlException occured');
        }
        finally {
            System.debug('This block executes');
        }
    }
}