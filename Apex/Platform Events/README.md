# Streaming API

Streaming events are instant notification messages that one system(the publisher) sends to another (the subscriber).

Streaming API - > Legacy : Generic & Push topic
              - > Current : Platform Event and Change Data Capture (CDC)



Streaming API vs REst API

* Using a REST API is sometimes compared to a conversation, while making requests with Streaming
APIs is more like watching a film.

* Client-server architecture based on requests and responses. 
* connection is closed after response is recieved
* statless (can be cached)

flexible, suitable for many applications

various response formats



Streaming APIs are totally the opposite of REST APIs. They are simply a long-running request, left
open, so data can be pushed into it.

presist connection with the streaming server based on long-running request and events.

connection is not closed by client or server

stateful : can see the last request![alt text](DineStream.jpg)

specific use cases

limited response format

One example of Streaming APIs can be those used by social media
platforms (e.g. Twitter and Facebook), which provide users with real-
time data by updating information automatically.


