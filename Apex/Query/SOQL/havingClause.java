
Limitations:

Can't use HAVING without GROUP BY
Can't reference individual records
Must use aggregate functions or GROUP BY fields


// 1. Basic HAVING with COUNT
SELECT AccountId, COUNT(Id) totalOpportunities
FROM Opportunity
GROUP BY AccountId
HAVING COUNT(Id) > 5

// 2. HAVING with SUM
SELECT AccountId, SUM(Amount) totalAmount
FROM Opportunity
GROUP BY AccountId
HAVING SUM(Amount) > 100000

// 3. Multiple Conditions in HAVING
SELECT LeadSource, COUNT(Id) leadCount, SUM(Amount) totalAmount
FROM Opportunity
GROUP BY LeadSource
HAVING COUNT(Id) > 10 AND SUM(Amount) > 50000

// 4. HAVING with AVG
SELECT AccountId, AVG(Amount) avgAmount
FROM Opportunity
WHERE StageName = 'Closed Won'
GROUP BY AccountId
HAVING AVG(Amount) > 10000

// 5. HAVING with MIN and MAX
SELECT AccountId, MIN(Amount) minAmount, MAX(Amount) maxAmount
FROM Opportunity
GROUP BY AccountId
HAVING MIN(Amount) > 1000 AND MAX(Amount) < 100000

// 6. Complex Example with WHERE and HAVING
SELECT Account.Industry, 
       COUNT(Id) oppCount, 
       SUM(Amount) totalAmount,
       AVG(Amount) avgAmount
FROM Opportunity
WHERE CloseDate = THIS_YEAR
    AND StageName = 'Closed Won'
GROUP BY Account.Industry
HAVING COUNT(Id) > 5 
    AND SUM(Amount) > 250000
    AND AVG(Amount) > 50000
ORDER BY SUM(Amount) DESC

// 7. HAVING with Calendar Year
SELECT CALENDAR_YEAR(CloseDate) closeYear, 
       COUNT(Id) opportunityCount
FROM Opportunity
GROUP BY CALENDAR_YEAR(CloseDate)
HAVING COUNT(Id) > 100

// 8. HAVING with Nested Functions
SELECT AccountId, 
       ROUND(AVG(Amount), 2) avgAmount
FROM Opportunity
GROUP BY AccountId
HAVING ROUND(AVG(Amount), 2) > 10000

// 9. HAVING with Custom Fields
SELECT Custom_Group__c, 
       COUNT(Id) recordCount,
       SUM(Custom_Amount__c) totalCustomAmount
FROM Custom_Object__c
GROUP BY Custom_Group__c
HAVING SUM(Custom_Amount__c) > 5000
    AND COUNT(Id) >= 3

// 10. HAVING with Related Objects
SELECT Account.Type, 
       Account.Industry,
       COUNT(Id) contactCount
FROM Contact
WHERE Account.IsActive = true
GROUP BY Account.Type, Account.Industry
HAVING COUNT(Id) > 10
ORDER BY COUNT(Id) DESC

Key Points to Remember:

HAVING Clause Rules:

Must be used with GROUP BY
Can only reference:

Aggregate functions
Fields in the GROUP BY clause
Fields that are functionally dependent on GROUP BY fields




Common Aggregate Functions:

COUNT()
SUM()
AVG()
MIN()
MAX()


SELECT
FROM
WHERE
GROUP BY
HAVING
ORDER BY
LIMIT

