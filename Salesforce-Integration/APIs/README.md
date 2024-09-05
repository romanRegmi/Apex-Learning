In the world of APIs, there are two main ways to receive updates from a data source : Polling and Webhooks.

𝗔𝗣𝗜 𝗣𝗼𝗹𝗹𝗶𝗻𝗴 is a pull-based approach, where the client periodically requests data from the server.

This can be done at regular intervals, or it can be triggered by a specific event.

𝗔𝗣𝗜 𝗪𝗲𝗯𝗵𝗼𝗼𝗸𝘀 are a push-based approach, where the server sends a notification to the client when there is new data available.

This is more efficient than polling, as the client only needs to be notified when there is something new to see.

So, which one should you use?

It depends on your specific needs.

𝗣𝗼𝗹𝗹𝗶𝗻𝗴 𝗶𝘀 𝗮 𝗴𝗼𝗼𝗱 𝗰𝗵𝗼𝗶𝗰𝗲 𝗶𝗳:

• You need to receive updates from a data source that is not frequently updated.
• You are on a limited budget, as polling is a simpler and less expensive solution than webhooks.
• You want to have more control over when you receive updates.

𝗪𝗲𝗯𝗵𝗼𝗼𝗸𝘀 𝗮𝗿𝗲 𝗮 𝗴𝗼𝗼𝗱 𝗰𝗵𝗼𝗶𝗰𝗲 𝗶𝗳:

• You need to receive updates from a data source that is frequently updated.
• You want to be notified as soon as new data is available.
• You want to reduce the number of API requests you make.
• You want to decouple your systems and make them more scalable.

In general, webhooks are a more efficient and scalable solution than polling.


1. An API is an Application Programming Interface. It is an interface for the machine. 
2. API provides the information, how to communicate with different software components. Defining the
1. Operations ( What to Call ) . For Example, GET,
POST, DELETE, PUT, PATCH
2. Inputs ( What to send with a Call ) . Example, Id in
the URL or a complete json/ xml body
3. Output ( What you get back from the call ).
4. Object Structure ( Object Data Types ) . JSON/XML
data structure
3. API is a URL which is consumed by Machine ( System ) communicating between 2 or more bodies ( machines ).


1. The Actual Implementations of the API to make the Calls we use Web Services. 2. Web Services is a method of communication between two/more devices ( machine to machine ) . 3. It’s a set of standard protocols which is used to exchange the information between two/more machines. 4. WS is an interface not for humans but for machines to communicate with them for exchanging the information

Possible Ways to Integrate with Salesforce Platform 1. User Interface 2. Business Logic 3. Data

CORS, or Cross-Origin Resource Sharing, is a security feature implemented in web browsers that allows web applications running at one origin (domain) to make requests for resources from a different origin. In the context of Salesforce, CORS is used to control and manage cross-origin requests to Salesforce services and APIs.


Authentication and Authorization are two fundamental concepts in the field of security, often used in the context of controlling access to resources and systems. While they are related, they serve distinct purposes:

Authentication:
Authentication is the process of verifying the identity of a user, system, or entity attempting to access a resource or perform an action. It ensures that the entity claiming to be a particular user or system is, in fact, who they say they are. Authentication is typically based on providing credentials such as a username/password combination, a security token, biometric data, or a digital certificate.

For example, when you log in to a website with your username and password, the website authenticates you by comparing the provided credentials with those stored in its database. If the credentials match, you are granted access, and if they don't, you are denied access.

Authorization:
Authorization is the process of determining what actions or resources a user or entity is allowed to access or perform after successful authentication. Once a user is authenticated, they are granted access only to the resources or operations that they have been authorized to use. Authorization is often based on roles, permissions, or access control lists (ACLs).

For example, after you log in to a system successfully, your access rights may be limited to specific features or data based on your user role. A regular user might have read-only access to certain records, while an administrator may have full access to create, read, update, and delete records.
