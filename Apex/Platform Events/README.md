# Platform Events

## Streaming APIs
Streaming events are instant notification messages that one system (the publisher) sends to another (the subscriber).

Streaming API 
* Legacy : Generic & Push topic
* Current : Platform Event and Change Data Capture (CDC)



Streaming API vs Rest API

* Using a REST API is sometimes compared to a conversation, while making requests with Streaming APIs is more like watching a film.
* Client-server architecture based on requests and responses. 
* connection is closed after response is recieved
* statless (can be cached)

flexible, suitable for many applications

various response formats



Streaming APIs are the opposite of REST APIs. They are a long-running request, left open, so that data can be pushed into it.

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

Platform events cannot be queried.



Key Benefits of Platform Events:

Internal Processing:


Asynchronous operations
Decoupled architecture
Reliable message delivery
Transaction independence


External Integration:


Real-time notifications
Standardized messaging
Secure communication
Scale handling

Best Practices:

Event Design:


Keep events focused and specific
Include necessary audit fields
Consider volume in field selection


Publishing:


Use bulk publishing when possible
Implement proper error handling
Consider retry mechanisms


Subscribing:


Handle events idempotently
Process events efficiently
Implement error handling


Performance:


Monitor event usage
Consider event retention
Use batch publishing for high volume

Limitations to Consider:

Governor Limits:


2,000 events per transaction
24-hour retention
Size limits per event


Ordering:


Events may not arrive in order
Design for eventual consistency


Delivery:


At-least-once delivery
Plan for duplicate handling



Platform events cannot be queried

SELECT Name,Id,ExternalId,  StartDate, EndDate, Value
FROM PlatformEventUsageMetric
WHERE Name IN ('PLATFORM_EVENTS_PUBLISHED')
AND StartDate>=2024-11-26T00:00:00.000Z ORDER BY StartDate