public static void classifyFolder(List<String> contentDocumentIds, String documentCategory){
    ContentWorkspace library  = [SELECT Id, RootContentFolderId FROM ContentWorkspace WHERE Name = 'Roman Library' LIMIT 1];
    ContentFolder workspaceFolder = [SELECT Id FROM ContentFolder WHERE Name =: library.Id];

    ContentFolder confolder = [SELECT Id FROM ContentFolder WHERE Name =: documentCategory AND ParentContentFolderId=:workspaceFolder.Id] ?? null;

    if(confolder == null){
        confolder = new ContentFolder(Name=documentCategory, ParentContentFolderId=workspaceFolder.Id);
        insert confolder;
    }
    
    List<ContentDocumentLink> cdls = new List<ContentDocumentLink>();
    for(String ContentDocumentId : contentDocumentIds){
        ContentDocumentLink cdl = new ContentDocumentLink();
        cdl.ContentDocumentId = ContentDocumentId;
        cdl.ShareType = 'I';
        cdl.Visibility = 'AllUsers';
        cdl.LinkedEntityId = library.Id; //Magic happens here           
        cdls.add(cdl);
    }

    if(cdls.size() > 0){
        insert cdls;
    }
    
    ContentFolderMember cfm = [SELECT Id, ChildRecordId, ParentContentFolderId
                                FROM ContentFolderMember
                                WHERE ParentContentFolderId =: workspaceFolder.Id];
    
    cfm.ParentContentFolderId = confolder.Id;
    update cfm;
}