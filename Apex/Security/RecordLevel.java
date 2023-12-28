SELECT RecordId, HasEditAccess 
FROM UserRecordAccess 
WHERE UserId = [single ID] AND RecordId = [single ID]


SELECT RecordId 
FROM UserRecordAccess 
WHERE UserId=:UserInfo.getUserId() 
    AND HasReadAccess = true 
    AND RecordId IN :allRecordIds 