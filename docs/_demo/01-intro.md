---
title: Kafka Streams Aggregate API Tutorial
permalink: /
layout: single
header:
   overlay_color: "#000"
   overlay_filter: "0.5"
   overlay_image: /assets/images/tutorial-ks-aggregate-api.svg
excerpt: Learn how to declare the API of an aggregate, defining the data products the aggregate exposes to the organisation, and how to interop with non-Creek parts of an architecture.
toc: true
---

This tutorial, the third and final tutorial in the quick-start series, will show how to define the API of an aggregate,
how to reference that API from another aggregate, and how to interact with parts of a system that predate Creek.

**ProTip:** An _aggregate_ is simply a logical grouping of services that, together, provide some business function
via a defined api. i.e. An aggregate is a level of abstraction above a single microservice.
This is also known as a [_Bounded Context_ in DDD nomenclature][bcDDD].
{: .notice--info}

The tutorial will extend the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo/).
As a quick recap, that tutorial defined a `handle-occurrence-service` which
consumed data from the `twitter.tweet.text` Kafka topic, searched each Tweet for Twitter handles,
and output any occurrences encountered to the `twitter.handle.usage` Kafka topic.

In the original tutorial, the `handle-occurrence-service` declared it _owned_ the `twitter.tweet.text` input topic.
This kept the tutorial self-contained, but was noted as usual. In this tutorial, the ownership will be changed 
to an imagined upstream _Twitter ingestion system_.  

**ProTip:** The concept of topic _ownership_ defines which service, or aggregate, and hence which team within an organisation,
is responsible for a topic, its configuration, and the data it contains.
{: .notice--info}

The ingestion system, which, for the sake of example, doesn't use Creek, will be defined as the `ingestion aggregate`. 
This allows the tutorial to demonstrate how to integrate with non-Creek systems. 

## Features covered

The key features this tutorial is designed to highlight are:
* How to declare the API of a Creek aggregate.
  If you wish to jump straight to this, see the [Creek aggregate API][creekApi] section.
* How to interop with parts of the system that predate or don't use Creek.
  If you wish to jump straight to this, see the [Non-Creek aggregate API][nonCreekApi] section.
* Why defining aggregates is a powerful architectural pattern.
  If you wish to jump straight to this, see the [Why Aggregates?][whyAggregates] section.

## Prerequisites

The tutorial requires the following:

* A [GitHub <i class="fas fa-external-link-alt"></i>](https://github.com/join){:target="_blank"} account.
* [Git <i class="fas fa-external-link-alt"></i>](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git){:target="_blank"} installed for source code control.
* [Docker desktop <i class="fas fa-external-link-alt"></i>](https://docs.docker.com/desktop/){:target="_blank"} installed for running containerised system tests.

## Design

The design changes covered by this tutorial fall into two main tasks:

The first task is to define the public API of the tutorial's own aggregate. 
The existing `handle-occurrence-service` will see its `twitter.handle.usage` output topic promoted to being part of the aggregating public API.
The topic will be conceptually _owned_ by the aggregate.

The second task is to define the public API of the `ingestion aggregate`. 
As noted above, this aggregate doesn't use Creek. 
Maybe the system predates Creek, or maybe its managed by an unenlightened team that doesn't use Creek... yet :wink:. 

If the aggregate did use Creek, it would define its own public API. As it doesn't, the tutorial will show how to codify its API,
allowing services to more easily interact with it.  

The `ingestion aggregate` will define a single `twitter.tweet.text` output topic, as consumed by the existing `handle-occurrence-service`.

The `handle-occurrence-service` will be updated to make use of these two aggregate APIs. 

The target architecture looks like:

{% include figure image_path="/assets/images/creek-demo-design.svg" alt="Service design" %}

## Complete solution

The completed tutorial can be viewed [on GitHub <i class="fas fa-external-link-alt"></i>][demoOnGh]{:target="_blank"}.

[<i class="fab fa-fw fa-github"/>&nbsp; View on GitHub][demoOnGh]{: .btn .btn--success}{:target="_blank"}

[demoOnGh]: https://github.com/creek-service/ks-aggregate-api-demo
[bcDDD]: https://martinfowler.com/bliki/BoundedContext.html
[creekApi]: {{ "/creek-aggregate-descriptor" | relative_url }}
[nonCreekApi]: {{ "/non-creek-aggregate-descriptor" | relative_url }}
[whyAggregates]: {{ "/why-aggregates" | relative_url }}
[FurtherReadingStep]: {{ "/further-reading" | relative_url }}
