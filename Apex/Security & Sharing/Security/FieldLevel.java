if (Schema.sObjectType.Contact.fields.Email.isUpdateable()) {

   // Update contact phone number

}

if (Schema.sObjectType.Contact.fields.Email.isCreateable()) {

   // Create new contact

}

if (Schema.sObjectType.Contact.fields.Email.isAccessible()) {

   Contact c = [SELECT Email FROM Contact WHERE Id= :Id];

}

if (Schema.sObjectType.Contact.isDeletable()) {

   // Delete contact

}