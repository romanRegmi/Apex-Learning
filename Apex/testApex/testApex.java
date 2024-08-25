④ Use @testSetup
The @testSetup annotation is used to define a method that will be run before all test methods in the class. This is useful for setting up test data that is needed by multiple test methods. By using the @testSetup annotation, you can ensure that your test data is created only once, which can improve the performance of your tests.


We can't use @istest(SeeAllData=true) and @testsetup at same class because if we set "SeeAllData=true" then during the test it will check for the database(if program containing any soql or sosl) and if we create setup data using "@testsetup"  then it will rollback after testing. Means data will not get stored in the database. This is the reason the compiler is not allowed to add both annotations in the same class.

Example:

@isTest
public class MyTestClass {
    @testSetup static void setup() {
        Account a = new Account(Name='Test Account');
        insert a;
    }

    static testMethod void testMyMethod() {
        Account a = [SELECT Name FROM Account WHERE Name='Test Account'];
        System.assertEquals('Test Account', a.Name);
    }
}

⑤ Use @isTest(SeeAllData=false)
The @isTest annotation is used to define a class or method as a test class or method. The SeeAllData parameter can be used to specify whether or not the test class or method can access all data in the organization, or just data that is created by the test itself. By setting SeeAllData to false, you can ensure that your tests are not affected by data that is not related to the test, and that your tests will be more reliable.

Example:

@isTest(SeeAllData=false)
public class MyTestClass {
    static testMethod void testMyMethod() {
        Account a = new Account(Name='Test Account');
        insert a;
        System.assertEquals('Test Account', a.Name);
    }
}

⑥ Use the @testVisible annotation
The @testVisible annotation is used to make an Apex class or method visible to test classes, even if it is defined as private. This is useful for testing methods or classes that are not accessible to test classes by default. By using the @testVisible annotation, you can ensure that your tests have access to the necessary methods and classes for testing.

Example:

public class MyClass {
    @testVisible private static Integer myMethod() {
        return 5;
    }
}

@isTest
public class MyTestClass {
    static testMethod void testMyMethod() {
        Integer result = MyClass.myMethod();
        System.assertEquals(5, result);
    }
}
view rawMyTestClass.cls hosted with ❤ by GitHub
⑦ Use the Test.setFixedSearchResults() method
The Test.setFixedSearchResults() method is used to set the search results that will be returned by a search query in a test class. This is useful for testing code that makes use of search queries, as it allows you to control the data that is returned by the query. By using the Test.setFixedSearchResults() method, you can ensure that your tests are not affected by changes in the data that is returned by the query.

Example:

@isTest
public class MyTestClass {
    static testMethod void testMyMethod() {
        List<Account> accounts = new List<Account>{
            new Account(Name='Test Account 1'),
            new Account(Name='Test Account 2')
        };
        insert accounts;
        Test.setFixedSearchResults(accounts);
        List<Account> results = [FIND 'Test Account*' IN ALL FIELDS RETURNING Account(Name)];
        System.assertEquals(2, results.size());
    }
}
view rawMyTestClass.cls hosted with ❤ by GitHub
⑧ Use the Test.loadData() method
The Test.loadData() method is used to load test data from a CSV file into your test class. This is useful for creating large amounts of test data, as it allows you to easily import data from a spreadsheet or other external source. By using the Test.loadData() method, you can ensure that your tests are not affected by changes in the data that is used by the test, and that your tests will be more reliable.

Example:

@isTest
public class MyTestClass {
    static testMethod void testMyMethod() {
        List<sObject> records = Test.loadData(Account.sObjectType, 'test_accounts');
        System.assertEquals(10, records.size());
    }
}
view rawMyTestClass.cls hosted with ❤ by GitHub
⑨ Use the Test.createStub() method
The Test.createStub() method is used to create a test stub for an Apex class or interface. This is useful for testing code that makes use of classes or interfaces that are not accessible in a test context, such as web service callouts or external Apex classes. By using the Test.createStub() method, you can ensure that your tests are not affected by changes in the behavior of the class or interface, and that your tests will be more reliable.

Example:

@isTest
public class MyTestClass {
    static testMethod void testMyMethod() {
        MyClass.MyWebService ws = Test.createStub(MyClass.MyWebService.class);
        Test.setMock(WebServiceMock.class, ws);
        // test code that makes use of the MyWebService class
    }
}


The @TestVisible annotation in Salesforce is used to increase the visibility of private class members in test methods. Normally, private members of a class are not accessible outside of that class. However, when you apply the @TestVisible annotation to a private class member, it becomes accessible to Test Classes.