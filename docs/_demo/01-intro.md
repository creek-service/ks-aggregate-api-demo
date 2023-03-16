---
title: Kafka Streams Aggregate API Tutorial
permalink: /
layout: single
header:
   overlay_color: "#000"
   overlay_filter: "0.5"
   overlay_image: /assets/images/tutorial-ks-aggregate-api.svg
excerpt: Learn how to declare the API of an aggregate, defining the data products the aggregate exposes to the organisation.
toc: true
---

This tutorial will lead you through extending the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo/)
to declare the [data products][dataProductDef] the aggregate makes available to the rest of the organisation, i.e. its API. 

As a quick recap, the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo/) defined a `handle-occurrence-service` which
consumed data from the `twitter.tweet.text` Kafka topic, searched each Tweet for Twitter handles,
and output any occurrences encountered to the `twitter.handle.usage` Kafka topic. 

This tutorial will add an aggregate descriptor: a Java class that captures the metadata about the aggregate, including its inputs and outputs.
In this case, the aggregate will expose a single output: the `twitter.handle.usage` output topic, developed in the previous tutorial. 
In [Data Mesh][dataMeshDef] terminology, this output topic constitutes a [data product][dataProductDef]: 
a dataset, with a defined schema, owned and managed by the aggregate and, by extension, the team that is responsible for it.

**Note:** This is a deliberately simplistic service, allowing the tutorial to focus on demonstrating Creek's features.
{: .notice--warning}

## Features covered

This tutorial will touch on many of Creek's features and techniques cover by previous tutorials.
However, the key features this tutorial is designed to highlight are:
* How to declare the API, a.k.a. the set of data products, that an aggregate exposes.
  If you wish to jump straight to this, see the [Aggregate descriptor][aggDescStep] section.
* Why defining aggregates is a powerful architectural pattern.
  If you wish to jump straight to this, see the [Further reading][FurtherReadingStep] section.

**Note:** The [Basic Kafka Streams tutorial](/basic-kafka-streams-demo/) covers how to use a topic managed by aggregates.
{: .notice--info}

## Prerequisites

The tutorial requires the following:

* A [GitHub <i class="fas fa-external-link-alt"></i>](https://github.com/join){:target="_blank"} account.
* [Git <i class="fas fa-external-link-alt"></i>](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git){:target="_blank"} installed for source code control.
* [Docker desktop <i class="fas fa-external-link-alt"></i>](https://docs.docker.com/desktop/){:target="_blank"} installed for running containerised system tests.

## Design

To keep things simple, the aggregate descriptor added in this tutorial will take the existing `twitter.handle.usage`
Kafka topic, previously defined in the `handle-occurrence-service`'s descriptor, and promote it to being part of the aggregate's api.
The topic will be _owned_ by the aggregate.

**ProTip:** The concept of topic _ownership_ defines which service, or aggregate, and hence team within an organisation,
is responsible for a topic, its configuration, and the data it contains.
{: .notice--info}

The tutorial will lead you through defining the aggregate api and updating the `handle-occurrence-service`'s descriptor to make
use of the topic's declared in the aggregate api.

{% include figure image_path="/assets/images/creek-demo-design.svg" alt="Service design" %}

## Complete solution

The completed tutorial can be viewed [on GitHub <i class="fas fa-external-link-alt"></i>][demoOnGh]{:target="_blank"}.

[<i class="fab fa-fw fa-github"/>&nbsp; View on GitHub][demoOnGh]{: .btn .btn--success}{:target="_blank"}

[demoOnGh]: https://github.com/creek-service/ks-aggregate-api-demo
[dataProductDef]: https://data.world/blog/what-is-a-data-product-and-why-does-it-matter-to-data-mesh/
[dataMeshDef]: https://data.world/blog/what-is-data-mesh/
[aggDescStep]: {{ "/descriptor" | relative_url }}
[FurtherReadingStep]: {{ "/further-reading" | relative_url }}
