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


1. The Actual Implementations of the API to make the Calls we use Web Services. 
2. Web Services is a method of communication between two/more devices ( machine to machine ) . 
3. It’s a set of standard protocols which is used to exchange the information between two/more machines. 4. WS is an interface not for humans but for machines to communicate with them for exchanging the information

Possible Ways to Integrate with Salesforce Platform 
1. User Interface 
2. Business Logic 
3. Data
