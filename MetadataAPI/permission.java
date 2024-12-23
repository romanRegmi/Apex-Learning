// Create a new permission set
PermissionSet ps = new PermissionSet();
ps.Name = 'Account_Fields';
ps.Label = 'Account Fields Permission Set';

// Insert the permission set
insert ps;

// Define the object permissions for the Account object
ObjectPermissions accountObjPermission = new ObjectPermissions();
accountObjPermission.ParentId = ps.Id;
accountObjPermission.SobjectType = 'Account';
accountObjPermission.PermissionsRead = true;
accountObjPermission.PermissionsCreate = true;
accountObjPermission.PermissionsEdit = true;
accountObjPermission.PermissionsDelete = true;
accountObjPermission.PermissionsViewAllRecords = true;
accountObjPermission.PermissionsModifyAllRecords = true;

// Insert the object permissions
insert accountObjPermission;

// Define the field permissions for the Name field
FieldPermissions nameFieldPermission = new FieldPermissions();
nameFieldPermission.ParentId = ps.Id;
nameFieldPermission.SobjectType = 'Account';
nameFieldPermission.Field = 'Name';
nameFieldPermission.PermissionsRead = true;
nameFieldPermission.PermissionsEdit = true;

// Insert the field permissions
insert nameFieldPermission;

// Assign the permission set to a user
PermissionSetAssignment psa = new PermissionSetAssignment();
psa.AssigneeId = UserInfo.getUserId();
psa.PermissionSetId = ps.Id;

// Insert the permission set assignment
insert psa;
