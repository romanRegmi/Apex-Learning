//Did you know you can dynamically include merge fields in the email body when sending emails through Salesforce Apex? 

Messaging.SingleEmailMessage email = new Messaging.SingleEmailMessage();
// now merge fields will be treated as templates
email.setTreatBodiesAsTemplate(true);

// the below values will be merged in once the email is sent
email.setSubject('Hi {!Contact.Name}' );
email. setPlainTextBody('Sending a test to {!Contact. Name}');

// Setting Contact's Id as Target Object Id
email.setTargetObjectId('0035g0000177LnJAAU');

Messaging.sendEmail(new List<Messaging. Email> { email });