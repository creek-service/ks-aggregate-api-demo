---
title: Why aggregates?
permalink: /why-aggregates
description: Why would you want to decompose an architecture into _aggregates_? What are the benefits? 
layout: single
---

You may be wondering why Creek encourages this pattern of breaking architectures down into aggregates?

The aggregate pattern encourages [defining architectural boundaries](#aggregates-define-architectural-boundaries),
with defined APIs comprised of the [data products](#aggregates-define-data-products) the aggregate exposes.

The intention is that a single aggregate is _owned_ by a single team with in organisation.
Though, a single team may own more than one aggregate. This concept of _ownership_ can help define roles
and responsibilities within an organisation, especially around the data products an aggregate exposes.  

Publishing an aggregate's API is designed to encourage self-service within an organisation.
An aggregate's API defines the data products other teams can consume, without the source team necessarily being involved.

Finally, though the mechanism of deployment is outside the scope of Creek, aggregates can also help define 
_deployable units_, i.e. the set of microservices that are always deployed and upgraded together.

## Aggregate's define architectural boundaries

Aggregates provide a layer of encapsulation and abstraction around the microservices they contain.
They provide architectural boundaries: service's from other aggregates are not allowed to access the service's within
an aggregate; they are only allowed to interact with the inputs and outputs the aggregate declares.

In Domain Driven Development, aggregates are known as [Bounded Contexts][bcDDD].
In other methodologies they probably have different names. The key, though, is they enable organisations to build
large-scale microservice architectures without things decaying into a chaos of interdependent microservice spaghetti.

More complex systems or organisations may require more layers of abstraction, [like ogres][orges].
This can be achieved by creating an aggregate-of-aggregates, i.e. aggregating several smaller aggregates into a larger
one, with a defined higher-level API / data product set.

## Aggregate's define data products

An aggregate's descriptor defines the [data products][dataProductDef] the aggregate exposes to the rest
of the organisation, i.e. other aggregates.

In [Data Mesh][dataMeshDef] terminology, the output topics, and to a lesser extent, the input topics that form 
an aggregate's API constitute the set of [data products][dataProductDef] the aggregate exposes to the rest of the

In this tutorial, the aggregate defines a single data product: the `twitter.handle.usage` topic.
In real world examples, it's common for an aggregate to expose many data products. These might be Kafka topics or,
with future Creek [extentions][extentions], other technologies such as AWS S3 buckets, data accessible through rest endpoints, 
you name it.

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

[dataProductDef]: https://data.world/blog/what-is-a-data-product-and-why-does-it-matter-to-data-mesh/
[extentions]: https://www.creekservice.org/extensions/
[bcDDD]: https://martinfowler.com/bliki/BoundedContext.html
[dataCatIssue]: https://github.com/creek-service/creek-platform/issues/98
[aclIssue]: https://github.com/creek-service/creek-kafka/issues/252
[dataCatDef]: https://data.world/blog/what-is-a-data-catalog/
[dataMeshDef]: https://martinfowler.com/articles/data-mesh-principles.html
[dataHub]: https://datahubproject.io/
[orges]: https://quotegeek.com/quotes-from-movies/shrek/7316/
