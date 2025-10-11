public with sharing class Extract_ZipFiles {
    public static void extractZip(String staticResourceName) {
        // Step 1: Upload Zip File as Static Resource, for practice purpose to see how to extract the file.
        StaticResource staticRes = [SELECT Id, Name, Body FROM StaticResource WHERE Name = :staticResourceName LIMIT 1];
        Blob fileContent = staticRes.Body;

        // Step 2: Using the Apex Zip Class to extract the file.
        Compression.ZipReader reader = new Compression.ZipReader(fileContent);
        Compression.ZipEntry frTranslation = reader.getEntry('Dummy Pdf.pdf'); // getEntry(String name): gets the specified file from the ZIP.
        Blob frTranslationData = reader.extract(frTranslation); // extract(Compression.ZipEntry entry): Extracts and decompresses the content of a specified ZIP entry(specified file).

        // Step 3: Save the blob data file to the as files on an opportunity.

        // Step 1: Create a ContentVersion record
        ContentVersion contentVersion = new ContentVersion();
        contentVersion.Title = 'Test Dummy Pdf';
        contentVersion.PathOnClient = 'Test_Dummy_Pdf.pdf';
        contentVersion.VersionData = frTranslationData;
        contentVersion.IsMajorVersion = true;
        insert contentVersion;

        // Step 2: Fetch the newly created ContentVersion Id
        Id contentVersionId = [SELECT Id, ContentDocumentId FROM ContentVersion WHERE Id = :contentVersion.Id].ContentDocumentId;

        // Step 3: Link the File to the Opportunity via ContentDocumentLink
        ContentDocumentLink link = new ContentDocumentLink();
        link.ContentDocumentId = contentVersionId;
        link.LinkedEntityId = '006N0000007n30YAB'; // Associate with the Opportunity
        link.ShareType = 'V';
        link.Visibility = 'AllUsers';
        insert link;
    }
}