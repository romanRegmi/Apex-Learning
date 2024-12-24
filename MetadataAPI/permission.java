public class createPS {
    
    public static String definePS(String Name, String Label){
        
        PermissionSet ps = new PermissionSet();
        ps.Name = Name;
        ps.Label = Label;
        
        insert ps;
        
        return ps.Id;
    }
    
    
    public static void defineObjectPS(String objAPIName, Id psId) {
        ObjectPermissions objPs = new ObjectPermissions();
        objPs.ParentId = psId;
        objPs.SobjectType = objAPIName;
        objPs.PermissionsRead = true;
        objPs.PermissionsCreate = true;
        objPs.PermissionsEdit = true;
        objPs.PermissionsDelete = true;
        objPs.PermissionsViewAllRecords = true;
        objPs.PermissionsModifyAllRecords = true;
        
        // Insert the object permissions
        insert objPs;    
    }
    
    
    public static void defineFieldPermissions(String objectApiName, Id permissionSetId) {
        // List to hold the FieldPermissions records to be inserted
        List<FieldPermissions> fieldPermissionsList = new List<FieldPermissions>();
        
        // Retrieve the SObject type using the objectApiName
        Schema.SObjectType sObjectType = Schema.getGlobalDescribe().get(objectApiName);
        
        // Get the field map for the SObject
        Schema.DescribeSObjectResult describeResult = sObjectType.getDescribe();
        Map<String, Schema.SObjectField> fieldMap = describeResult.fields.getMap();
        
        
        // Iterate through all the fields of the SObject
        for (String fieldName : fieldMap.keySet()) {
            // Create a new FieldPermissions record for each field
            FieldPermissions fieldPermission = new FieldPermissions();
            fieldPermission.ParentId = permissionSetId;  // Set the PermissionSet Id
            fieldPermission.SobjectType =  objectApiName; // Set the Object API Name
            fieldPermission.Field = objectApiName + '.' + fieldName;           // Set the Field Name
            fieldPermission.PermissionsRead = true;      // Grant read access
            fieldPermission.PermissionsEdit = true;      // Grant edit access
            
            // Add the FieldPermissions record to the list
            fieldPermissionsList.add(fieldPermission);
        } 
        
        //System.assert(false, fieldPermissionsList);
        
        // Insert the FieldPermissions records in bulk
        if (!fieldPermissionsList.isEmpty()) {
            Database.insert(fieldPermissionsList, false);
        }
    }


    public static void assignPSToCurrentUser(Id psId){
        // Assign the permission set to a user
        PermissionSetAssignment psa = new PermissionSetAssignment();
        psa.AssigneeId = UserInfo.getUserId();
        psa.PermissionSetId = psId;
        
        // Insert the permission set assignment
        insert psa;
    }    
    
    

}