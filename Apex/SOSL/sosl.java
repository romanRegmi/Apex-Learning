/*
 * SOSL - Salesforce Object Search Language
 * RETURN TYPE : List of list of sobject
*/

List<List<sObject>> l2 = [Find 'John' Returning Account(Name, Phone, NumberOfEmployees), Contact(LastName), student__c(Name, Student_Name__c)];

// Access Records
List<Account> acc = l2[0];

List<Contact> con = l2[1];

/* WILD-CARDS */

// 1) * (Asterisk) => 0 or N number of characters
List<List<sObject>> sList1 = [FIND 'Univ*' IN NAME FIELDS RETURNING Account, Contact];
// Will return the records which have "Univ" appended by anything

// 2) ? (Question Mark) => A single character
List<List<sObject>> sList2 = [FIND 'Jo?n' RETURNING Account, Contact];
// Will return the records which have any character at the place of ? in "Jo?n"

/* WHERE CLAUSE */
List<List<sObject>> l2 = [Find 'John' Returning Account(Name, Phone, NumberOfEmployees WHERE NumberOfEmployees = null), Contact];


/* Dynamic SOSL */
String s = 'Find \'John\' Returning Account, Contact';
List<List<sObject>> l1 = Search.query(s);

/* SOSL Keywords */

// 1. OR
List<List<sObject>> sList1 = [Find 'John OR Test' returning Account, Contact];
System.debug(sList1);

// 2. AND
List<List<sObject>> sList2 = [Find 'John AND Test' returning Account, Contact];
System.debug(sList2);