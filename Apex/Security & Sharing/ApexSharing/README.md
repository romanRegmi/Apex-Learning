<h1>Apex Sharing</h1>

Normally we share record using the following configurations
* Record ownership OWD
* Role Hierarchy
* Sharing rules
* Manual Sharing

Apex sharing is nothing but sharing the records programmatically. When all the above config options to share the record does not work for our scenario then we use apex sharing.

<h3>Role Hierarchy</h3>

* Automatically grants record access to users above the record owner in the Hierarchy.
* Users who need access to the same type of data can be grouped together.

<h3>Sharing Rules</h3>

* Sharing rules are automatic exceptions to the OWD settings for users who do not own the record. 

1. What is a sharing rule?

Ans: A sharing rule is a setting that allows you to give access to specific records to a group
of users or roles. Sharing rules can be used to extend access to records beyond the
organization-wide default settings, which control the visibility of records to all users in an
organization.

There are two types of sharing rules based on which records are shared.
- Owner Based Sharing rules : Share records owned by a particular set of users to another user.
- Criteria Based Sharing rules : Share records that meet a particular criteria. 

Public groups can be created to implement sharing rules.

Public groups - A set of users that may have different profiles/roles.  

<h3>Manual Sharing</h3>

Share records individually with other users.
A user can only share the records 
he/she owns
A user in a role above the owner in the role hierarchy.
Users with full access to the record.
Admin


Q - If apex runs in the "without sharing" mode by default, then why do we have the "without sharing" keyword?

This is mostly used for inner class. Or in a situation where we are calling methods from multiple classes. (If any of the keywords is used in the child, then the child will run in that mode only.)

<table class="border">
<thead>
<tr>
<th>Class Name</th>
<th>Sharing</th>
<th>Invocation</th>
<th>Result</th>
</tr>
</thead>
<tbody>
<tr>
<td>Class A</td>
<td>with sharing</td>
<td>Class A calls a method in class B</td>
<td>Both the classes run in the with sharing context because the parent class has the "with sharing" keyword</td>
</tr>
<tr>
<td>Class B</td>
<td>Neither of the sharing keyword is used</td>
<td>Class B calls a method in class A</td>
<td>Class A runs in the with sharing context but class B runs in the without sharing context</td>
</tr>
<tr>
<td>Class C</td>
<td>without sharing</td>
<td>Class A calls a method in class C</td>
<td>Class A runs in the with sharing context while class C runs in the without sharing context</td>
</tr>
</tbody>
</table>


Inherited sharing → invoked from another class. Works as whatever the context (with / without sharing) of the class it is invoked from. If it is not invoked from another class, then it works in user mode


