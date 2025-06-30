/*
* Serialization is the process of converting the state of an object into a format that can be easily stored or transmitted, like a stream of bytes. 
* This allows you to save the object's data (e.g., to a file or database) and later recreate it (deserialization). 
*/

/*
* Use cases for transient keyword:
*   Sensitive Information: If your class contains sensitive information (like passwords, tokens, etc.), you might want to mark those variables as transient to avoid accidentally exposing them when the object is serialized or transferred.
*   Calculated Fields: If your class has fields that are calculated based on other fields and don't need to be persisted, you can mark them as transient to avoid unnecessary serialization.
*/

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

// Implementation
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