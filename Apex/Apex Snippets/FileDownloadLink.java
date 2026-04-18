public without sharing class FileDownloadLink {
    // Method to get content version record
    public ContentVersion fetchContentVersion(Id contentDocumentId) {
        ContentVersion contentVersion = [ SELECT Id, VersionData, Title FROM ContentVersion WHERE ContentDocumentId = :contentDocumentId AND IsLatest = true AND IsDeleted = false WITH SECURITY_ENFORCED ALL ROWS];
        return contentVersion;
    }
    // Method to get downloadUrl.
    public String getDownloadUrl(String title, Id contentVersionId) {
        ContentDistribution[] contentDistribution = [ SELECT Id, ContentDocumentId, ContentDownloadUrl FROM ContentDistribution WHERE ContentVersionId =:contentVersionId WITH SECURITY_ENFORCED];
        if(contentDistribution.size() == 0){
            ContentDistribution contDistr = new ContentDistribution(
                Name = title,
                ContentVersionId = contentVersionId,
                PreferencesAllowViewInBrowser = true
            );
            insert contDistr;
            return contDistr.ContentDownloadUrl;
        } else {
            return contentDistribution[0].ContentDownloadUrl;
        }

    }
}