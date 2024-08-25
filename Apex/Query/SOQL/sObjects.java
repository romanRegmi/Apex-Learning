sObject s1 = new Account();
s1.put('NumberOfEmployees', 3000);
integer a = s1.get('NumberOfEmployees'); // Will Work
integer a = s1.NumberOfEmployees; // Will not work