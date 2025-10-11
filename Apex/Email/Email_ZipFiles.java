/* Compress Email Attachment into a Zip File */
public with sharing class Email_ZipFiles {
    public static void compressEmail(String opportunityId) {
        Compression.ZipWriter writer = new Compression.ZipWriter();
        // Step 1: Get ContentDocumentId values linked to the Opportunity.
        List<Id> contentDocumentIds = new List<Id>();
        for (ContentDocumentLink cdl : [SELECT ContentDocumentId FROM ContentDocumentLink WHERE LinkedEntityId = :opportunityId]) {
            contentDocumentIds.add(cdl.ContentDocumentId);
        }

        // Step 2: Add IDs of documents to be compressed to contentDocuments.
        for (ContentVersion cv : [SELECT PathOnClient, VersionData FROM ContentVersion WHERE ContentDocumentId IN :contentDocumentIds]) {
            writer.addEntry(cv.PathOnClient, cv.VersionData); // adds a file to the ZIP with a name and content
        }

        Blob zipAttachment = writer.getArchive(); // creates a compressed ZIP file from added entries.

        // Step 3: attaches a ZIP file to an email.
        Messaging.EmailFileAttachment efa = new Messaging.EmailFileAttachment();
        efa.setFileName('attachment.zip');
        efa.setBody(zipAttachment);

        List<Messaging.EmailFileAttachment> fileAttachments = new List<Messaging.EmailFileAttachment>();
        fileAttachments.add(efa);

        // Step 4: sends an email with a ZIP file attachment
        Messaging.SingleEmailMessage email = new Messaging.SingleEmailMessage();
        email.setFileAttachments(fileAttachments);
        email.setSubject('Opportunity Attachments compressed and Shared as ZIP.');
        email.setToAddresses(new List<String>{'roman.regmi@gmail.com'});
        email.setPlainTextBody('The attachment for opportunity has been compressed and emailed successfully. Please check the ZIP');
        Messaging.sendEmail(new Messaging.SingleEmailMessage[] { email });
    }
}