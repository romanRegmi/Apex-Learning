/*
 * Enum is a user defined data type. 
 * Values other than that defined in the enum will not be accepted.
 * We use enums for constants, i.e., when we want a variable to have only a specific set of values. 
 * For instance, for weekdays enum, there can be only seven values as there are only seven days in a week. 
 * However, a variable can store only one value at a time.
*/

public class EnumClass {
    public static void myMethod() {
        Season s = Season.Winter;
        s = Season.Fall;
    }
}