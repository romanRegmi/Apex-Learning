# Salesforce Platform Events Guide

Platform Events are a powerful **event-driven integration** feature in Salesforce. They enable real-time, secure, and scalable communication between internal Salesforce processes and external systems using a publish/subscribe (pub/sub) model.

## What Are Streaming APIs?

Streaming APIs provide instant notification messages from a **publisher** to one or more **subscribers**.

Salesforce offers several Streaming API options:

- **Legacy Streaming API** — Generic Streaming and PushTopic events (older approach, still supported but less recommended for new development)
- **Current Streaming APIs** — Platform Events and Change Data Capture (CDC)

### REST API vs Streaming API

| Aspect                  | REST API                                      | Streaming API                                  |
|-------------------------|-----------------------------------------------|------------------------------------------------|
| Communication style     | Request-response (like a conversation)        | Long-lived connection (like watching a live stream) |
| Connection behavior     | Connection closes after each response         | Persistent connection — server pushes data     |
| State                   | Stateless (responses can be cached)           | Stateful (server remembers the subscription)   |
| Best for                | On-demand data retrieval                      | Real-time updates and notifications            |
| Data push               | Client must poll                              | Server pushes events immediately               |
| Response format         | Flexible (JSON, XML, etc.)                    | More limited (CometD/Bayeux protocol, JSON/Avro) |

Streaming APIs keep a long-running HTTP connection open, allowing the server to push events as they occur. This is common in social media platforms (e.g., real-time feeds on X/Twitter or Facebook).

## Platform Events Overview

Platform Events are built on Salesforce's **Streaming API** infrastructure and follow an **event-driven architecture** (EDA) with a **publish/subscribe** model.

Key characteristics:
- Publishers create and publish events.
- Subscribers listen on channels and receive events in near real-time.
- Events are **immutable** — once published, they cannot be modified or deleted.
- Platform Events **cannot be queried** using standard SOQL on the event object itself (they are not stored like regular records).

## Platform Event Definition
* Publish After Commit : To have the event message published only after a transaction commits successfully.
* Publish Immediately : To have the event message published when the publish call executes.
* ReplayId : This field is populated by the system when the event is delivered to subscribers, refers to the position of the event in the event stream. A subscribe can store a replay Id value and use it on resubscription. 
*EventUuId : A universally unique identifier (UUID) that identifies a platform event message. The system populates the EventUuid field, and you can't overwrite its value.

## Important Notes
* Order of Event Processing : A trigger processes platform event notifications sequentially in the order they're received. The order of events is based on the event replay ID.

* Asynchronous Trigger Execution : A platform event trigger runs in its own process asynchronously and isn't part of the transaction that published the event. There might be a delay between when an event is published and when the trigger processes the event.

* Automated Process System User : Platform event triggers don't run under the user who executes them (the running user) but under the Automated Process system user.

### Architecture: The Event Bus

1. **Event Producer** — Creates and publishes the event (via Apex `EventBus.publish()`, Flow, Process Builder, REST API, etc.).
2. **Event Bus** (Channel/Queue) — Salesforce's internal message bus. Events are added in strict chronological order and processed sequentially.
3. **Event Consumer** — Subscribes to the channel (Apex triggers, external apps via CometD/Pub/Sub API, flows, etc.) and gets notified instantly when a matching event arrives.

This decouples systems — producers don't need to know about consumers, reducing point-to-point integrations and eliminating the need for heavy middleware in many cases.

Platform Events support **real-time data exchange**:
- Inside Salesforce (internal decoupling and async processing)
- Between Salesforce and external systems

## Key Benefits

### Internal Processing
- **Asynchronous operations** — Offload heavy work from transactions
- **Decoupled architecture** — Components communicate without direct dependencies
- **Reliable message delivery** — At-least-once semantics
- **Transaction independence** — Events can be published even if the transaction rolls back (configurable behavior)

### External Integration
- **Real-time notifications** to external apps
- **Standardized messaging** format
- **Secure communication** (OAuth, certificates)
- **High-scale handling** (especially with High-Volume Platform Events)

## Best Practices

### Event Design
- Keep events focused and single-purpose
- Include audit fields (e.g., CreatedById, CreatedDate, UUID for idempotency)
- Choose fields wisely — only supported types: Checkbox, Date, Date/Time, Number, Text, Text Area (Long), etc.
- Consider expected volume when selecting field types

### Publishing
- Use bulk publishing (`List<SObject>`) when possible
- Implement retry logic for failures
- Handle errors gracefully (e.g., log failed publishes)

### Subscribing
- Design handlers to be **idempotent** (safe to process duplicates)
- Process events efficiently to avoid timeouts
- Implement robust error handling and dead-letter patterns

### Performance & Monitoring
- Monitor usage via `PlatformEventUsageMetric` object
- Consider event retention (default 24 hours)
- Use batch/bulk publishing for high-volume scenarios

Example query to monitor published events:
```sql
SELECT Name, Id, ExternalId, StartDate, EndDate, Value
FROM PlatformEventUsageMetric
WHERE Name IN ('PLATFORM_EVENTS_PUBLISHED')
  AND StartDate >= 2024-11-26T00:00:00.000Z
ORDER BY StartDate
```