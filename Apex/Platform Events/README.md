# Streaming API

Streaming events are instant notification messages that one system(the publisher) sends to another (the subscriber).

Streaming API - > Legacy : Generic & Push topic
              - > Current : Platform Event and Change Data Capture (CDC)



Streaming API vs Rest API

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

stateful : can see the last request

specific use cases

limited response format

One example of Streaming APIs can be those used by social media
platforms (e.g. Twitter and Facebook), which provide users with real-
time data by updating information automatically.



Platform Event

Platform Events is an integration
capability) that use the Streaming API.

Platform Event is based on "event-
driven architectures", it follow a
Publish/Subscribe model (aka pub/sub)
which reduces the number of point-to-
point integrations required within your
tech stack - reducing the need for an
integration layer to connect Salesforce
with external systems.


Platform events exchange event data in real-time within the Salesforce platform and salesforce and external platforms. 


Event Bus

Event producer: An event producer creates an event.

Event bus: The event gets added onto the event bus
(aka channel), which operates as a queue, with a strict
chronological order, and executes each event one
after the other.

Event consumers: Event consumers subscribe to an
event. The moment that event gets put onto the
event bus, the event consumer will be notified.