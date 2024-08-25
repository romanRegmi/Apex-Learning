/*
* ?. safe navigation : if not null then only take BillingCity
* ?? This is used for assignment - if not null aninteger else 100
*/

Integer notNullReturnValue = anInteger ?? 100;

// If there isn't a matching Account or the Billing City is null, replace the value
string city = [Select BillingCity 
    From Account 
    Where Id = '001xx000000001oAAA']?.BillingCity;
System.debug('Matches count: ' + city?.countMatches('San Francisco') ?? 0 );