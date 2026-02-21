/* SOQL RETURN TYPES 
    * List<sObject>
    * sObject (List of records)
    * Integer
    * AggregateResult */

/* SOQL AGGREGATE FUNCTIONS */

// Will display the sum of amount in all the opportunities
AggregateResult ar1 = [SELECT SUM(Amount) FROM Opportunity];

// Will return the total number of opportunities
Integer i = [SELECT COUNT() FROM Opportunity];
AggregateResult ar2 = [SELECT COUNT(Id) FROM Opportunity];

// COUNT in dynamic query
Integer i = Database.countQuery('SELECT count() from Account');

/* Will return the number of opportunities having value in the amount field
 * Will not include the opportunities having amount as null
*/
AggregateResult ar3 = [SELECT COUNT(Amount) FROM Opportunity];

/* Count distinct */
/* Will return the number of opportunities having unique amount value
 * Will not include the opportunities having amount as null
*/
AggregateResult ar3 = [SELECT Count_Distinct(Amount) FROM Opportunity];

// Access value from AggregateResult Object (Aggregate value is a keyValue pair.)
Integer i2 = (Integer)ar3.get('expr0');

/* LIKE */
List<Account> acc = [SELECT Name FROM Account WHERE Name Like 'GenePoint'];

/* WILDCARDS */
/* 
 * % (Percentage) => 0 or multiple characters
 * _ (Underscore) => Single character
*/

List<Account> acc = [SELECT Name FROM Account WHERE Name Like 'GenePoint'];
List<Account> acc = [SELECT Name FROM Account WHERE Name Like '%GenePoint%'];
List<Account> acc = [SELECT Name FROM Account WHERE Name Like '_GenePoint'];