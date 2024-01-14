SELECT RecordId, HasEditAccess 
FROM UserRecordAccess 
WHERE UserId = [single ID] AND RecordId = [single ID]


List<UserRecordAccess> Lis = [SELECT RecordId 
FROM UserRecordAccess 
WHERE UserId=:UserInfo.getUserId() 
    AND HasReadAccess = true 
    AND RecordId IN :allRecordIds ];

