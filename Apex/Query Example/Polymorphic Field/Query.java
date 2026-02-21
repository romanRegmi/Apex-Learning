/*
Use TYPEOF keyword when you are querying data that contains polymorphic relationships in SOQL Query. The code reads as follows : 

When What is an Opportunity, Select Name and Amount fields
When What is an Account, Select Name and Type fields
When What is a Case, Select CaseNumber and Type fields
Else If What is of any other Type, select Id and Name fields
*/

List<Task> tasks = [SELECT Id, Subject, Status, TYPEOF What
                    WHEN Opportunity THEN Name, Amount
                    WHEN Account THEN Name, Type
                    WHEN Case THEN CaseNumber, Type 
                    ELSE Id, Name 
                    END 
                    FROM Task];