---
title: Further reading
permalink: /further-reading
description: Next steps and recommended reading for what comes after you've completed the aggregate api Creek tutorial.
layout: single
---

This is the last in the quick-start series of tutorials.
The series was designed to give a brief introduction to the core features and design principles of Creek. 
If you've not already read the other parts, they're recommended. Part one, [Basic Kafka Streams Tutorial][basicKsDemo], is the 
"hello world" of Creek, giving a tour of the core features. Part two, [Kafka Streams: Connecting Services][ksConnectSvsDemo],
covers connecting services _within_ an aggregate together.

Additional tutorials will be added over time. These can be found on the [tutorials page]({{ site.url | append: "/tutorials/" }}).

If you're ready to jump in and start using Creek, then we'd suggest you head on over a read the documentation on the
[aggregate template][aggTemp] next and then get building.

The payloads used in this tutorial were simple types like `Integer` and `String`.
Obviously, this massively limits Creek's utility and is why Creek is still in alpha release.
Work to extend this to more complex types using, schema validated, JSON serialization, will be
[starting soon <i class="fas fa-external-link-alt"></i>](https://github.com/creek-service/creek-kafka/issues/25){:target="_blank"}.
{: .notice--info}

## Aggregate's define data products

This tutorial focused on a simple example of defining the API of an aggregate in its descriptor.
An aggregate's descriptor defines the [data products][dataProductDef] the aggregate exposes to the rest 
of the organisation, i.e. other aggregates.

In this example, the aggregate defines a single data product: the `twitter.handle.usage` topic. 
In real world examples, it's common for an aggregate to expose many data products. These might be Kafka topics or,
with future Creek [extentions][extentions], other technologies such as AWS S3 buckets, rest endpoints, you name it.

In less common situations, aggregates may also declare inputs too. For example, a monitoring aggregate might have 
input topics for metrics and alerts. These would be input topics owned by the aggregate, i.e. it's responsible for 
the topics and their schemas. Services from other aggregates could then produce metrics or alerts to the monitoring aggregate.

In the future, Creek will [provide integration points][dataCatIssue] to allow tooling the opportunity to do cool things with 
the data product metadata declared in aggregate and service descriptors, such as publish them to [data catalogs][dataCatDef] 
like [Data Hub][dataHub]. That's when things start to get interesting. If you're not familiar with [data mesh][dataMeshDef],
it's worth a Google.

Using Creek won't immediately create a data mesh in your organisation, but with the design patterns Creek provides,
combined with the planned work covering [data catalog integration][dataCatIssue] and [provisioning access control][aclIssue], 
it can help you on your journey.

## Aggregate's define architectural boundaries

Aggregates provide a layer of encapsulation and abstraction around the microservices they contain.
They provide architectural boundaries: service's from other aggregates are not allowed to access the service's within
an aggregate; they are only allowed to interact with the inputs and outputs the aggregate declares. 

In Domain Driven Development, these aggregates are known as [Bounded Contexts][bcDDD].
In other methodologies they probably have different names. The key, though, is they enable organisations to build
large-scale microservice architectures without things decaying into a chaos of interdependent microservice spaghetti.

More complex systems or organisations may require more layers of abstraction, [like ogres][orges]. 
This can be achieved by aggregating smaller aggregates into larger ones, i.e. creating aggregate-of-aggregates. 
The outer aggregate encapsulates the inner aggregates and defines the higher-level API / data products.

[dataProductDef]: https://data.world/blog/what-is-a-data-product-and-why-does-it-matter-to-data-mesh/
[extentions]: https://www.creekservice.org/extensions/
[bcDDD]: https://martinfowler.com/bliki/BoundedContext.html
[dataCatIssue]: https://github.com/creek-service/creek-platform/issues/98
[aclIssue]: https://github.com/creek-service/creek-kafka/issues/252
[dataCatDef]: https://data.world/blog/what-is-a-data-catalog/
[dataMeshDef]: https://martinfowler.com/articles/data-mesh-principles.html
[dataHub]: https://datahubproject.io/
[basicKsDemo]: https://www.creekservice.org/basic-kafka-streams-demo/
[ksConnectSvsDemo]:  https://www.creekservice.org/ks-connected-services-demo/
[orges]: https://quotegeek.com/quotes-from-movies/shrek/7316/
[aggTemp]: {{ site.url | append: "/aggregate-template/" }}