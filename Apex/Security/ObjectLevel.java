Schema.DescribeSObjectResult accountDescribe = Account.sObjectType.getDescribe();
System.debug('accessible:' + accountDescribe.accessible);