---
title: Managing api changes
permalink: /managing-api-changes
description: Suggestions and notes on manging api changes and schema evolution across microservice versions
layout: single
---

Though not the focus of this tutorial, not talking about how to manage changes to an aggregate's api would be amiss of us.

Microservices don't stand still. New requirements emerge, bugs lead to data consistency issues, errors can be found in data models.
All of these things can require changes to the api of an aggregate.

Making changes to a service, _within_ an aggregate, is relatively straight forward. The service is _private_ to the aggregate.
Engineers can make whatever changes they required to the internal services and Kafka topics,
so long as they leave the aggregate's api unchanged. 

However, making changes to an aggregate's existing api is non-trivial. 
The aggregate's api forms a _contract_ with its users.  
Changes to that contact need to be well-thought-out, and potentially communicated, so that they don't negatively impact users.

## What constitutes an API change?

What constitutes a change will vary depending on the technology being used.
Some examples of changes in the Kafka world are listed below. 

- Adding a topic.
- Removing a topic.
- Changes to the topic name.
- Changes to the data types stored in the topic record's keys and values. See [schema evolution](#schema-evolution) below.
- Changes to the number of topic partitions.
- Changes to topic config, such as topic retention. While not immediately obvious, even something like increasing
  or decreasing topic retention _could_ have unforeseen negative impact on downstream users.

Adding new topics / data products is clearly not going to affect clients. However, everything else in the list
has the potential to, and therefore should be _carefully_ thought through and testing.

### Schema evolution

This tutorial uses very simple data types in the keys and values of its Kafka topics.
A real-world example would have more complex data objects, especially in the values.
(Creek is [working on supporting this][jsonSupportIssue]).

One of the most common changes to existing data products is changes its schema: adding, changing and removing fields.

A naive approach to evolving a schema would be to simply make arbitrary changes. 
This is impractical for production applications, unless you are okay with production outages?

Schema evolution a large topic in its own right. If you don't yet understand forward and backwards schema compatability, 
it's 100% worth reading up on and getting to grips with, especially when it comes to working with Kafka,
as topics can contain long-lived data, written using very old schemas. Likewise, its guaranteed there's an 
application running somewhere using v1 of the API. 
See [here][fwdBackCompat] for a good overview, and [here][confluentAvroEvo] for something more Kafka-centric.

**ProTip**: Different clients will likely be using different versions of the aggregate's api, i.e. different versions 
of the api, and therefore different versions topic schemas. Trying to require or control client upgrades is a challenge. 
For this reason, ensuring all changes are both _transitively_ forward and backwards compatible will make life a lot easier.
{: .notice--info}

### Managing deprecation

If a topic's schema gets to the point where it contains too much technical debt, or a requirement necessitates
an unavoidable breaking change, create a new topic, with a fresh new schema, to run alongside the existing one.

The old topic can be marked as `@deprecated` in the `api` jar, with details of the new topic to switch to and
the timeframe before the old topic will be removed. Though actually removing the old topic can still be an organisational
challenge.

In terms of architecture for the deprecated vs new topic, the best pattern is to update internal services to build
the new topic. Additional logic, either in the same or a separate service, can continue to build the data for the old 
topic by consuming and transforming the new one. When the time comes to turn off the old topic, the additional logic or 
service can be removed, with minimal chance of impacting the new topic.

[jsonSupportIssue]: https://github.com/creek-service/creek-kafka/issues/25
[fwdBackCompat]: https://stevenheidel.medium.com/backward-vs-forward-compatibility-9c03c3db15c9
[confluentAvroEvo]: https://docs.confluent.io/platform/current/schema-registry/avro.html