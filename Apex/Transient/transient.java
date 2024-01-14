public class MyClass{
    // This variable will be serialized
    public String nonTransientVariable;

    // This variable is marked as transient and won't be serialized
    public transient String transientVariable;

    // Constructor
    public MyClass(String nonTransientValue, String transientValue) {
        this.nonTransientVariable = nonTransientValue;
        this.transientVariable = transientValue;
    }
}



MyClass myObject = new MyClass('Hello', 'World');

// Serialize the object
String serializedObject = JSON.serialize(myObject);

// Deserialize the object
MyClass deserializedObject = (MyClass)JSON.deserialize(serializedObject, MyClass.class);

// Access variables
String nonTransientValue = deserializedObject.nonTransientVariable;
String transientValue = deserializedObject.transientVariable;

System.debug('Non-Transient Variable: ' + nonTransientValue); // Outputs: Hello
System.debug('Transient Variable: ' + transientValue); // Outputs: null



System.assert(false, Account.sObjectType.getDescribe().getRecordTypeInfos());


Use cases for transient keyword:

Sensitive Information: If your class contains sensitive information (like passwords, tokens, etc.), you might want to mark those variables as transient to avoid accidentally exposing them when the object is serialized or transferred.

transient String password;
Calculated Fields: If your class has fields that are calculated based on other fields and don't need to be persisted, you can mark them as transient to avoid unnecessary serialization.


transient Integer calculatedField;
In summary, the transient keyword in Apex is used to exclude specific variables from the serialization process, making it useful for cases where you want to control which data is sent or stored.