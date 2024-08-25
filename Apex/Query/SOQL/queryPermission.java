List<PermissionSet> permissions =
[SELECT profile. NAME
FROM permissionset
WHERE


isownedbyprofile = true
AND id IN (SELECT parentid
FROM objectpermissions
WHERE


permissionscreate = true
AND permissionsread = true
AND permissionsedit = true
AND permissionsdelete = true
AND permissionsread = true
AND sobjecttype = 'Account' )

ORDER BY profile.NAME ];

System. debug ('

+permissions);