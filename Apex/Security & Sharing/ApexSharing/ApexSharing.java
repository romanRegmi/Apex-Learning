/*
* While deleting shared object records with apex sharing reason, if we try to delete using List<Id> it doesn't work. We can use List<CustomShareObject> to delete the records.
*/

List<Id> shareRecIds = new List<Id>();
shareRecIds.add('');
Database.delete(shareRecIds); // System.TypeException: Invalid id : 

List<Course_Share> shareRecIds = new List<Course_Share>();
shareRecIds.add(new Course_Share(Id=''));
Database.delete(shareRecIds); // Works