/*
 * GROUP BY : Groups results based on a particular field's value.
 * RETURN TYPE : List of aggreageted result
*/

// Will return all the values we have in StageName field of opportunity
List<AggregateResult> agrOpp1 = [SELECT StageName From Opportunity GROUP BY StageName];


// Will return the total number of opportunities grouped by Stage
// Count() cannot be used with GROUP BY. Id or any other field must be added
List<AggregateResult> agrOpp1 = [SELECT COUNT(ID), StageName From Opportunity GROUP BY StageName];

/*
 * COUNT(ID)     |     StageName
    6                  Prospecting
    1                  Qualification
*/

// Will return the maximum amount within that particular stage
List<AggregateResult> agrOpp1 = [SELECT StageName, Max(Amount) From Opportunity GROUP BY StageName];