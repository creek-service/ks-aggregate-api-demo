---
title: Define the aggregate's API
permalink: /creek-aggregate-descriptor
description: Learn how to create a repository's aggregate descriptor, defining the aggregate's API, and the data products the aggregate exposes.
layout: single
snippet_comment_prefix: "//"
toc: true
---

An aggregate's API defines its inputs and outputs. The types of inputs and outputs the API exposes will depend on the
[Creek extensions][creekExts] installed. The quick-start tutorial series focuses on the [Kafka Streams extension][ksExt], 
and the aggregate's API will be defined by the Kafka topics it exposes.

**ProTip:** In [Data Mesh][dataMeshDef] terminology, each aggregate output topic, and to a lesser extent, input topic,
may define a [data product][dataProductDef]: a defined data set, with a documented schema, the aggregate manages and 
exposes to the rest of the architecture and organisation.
{: .notice--info}

In this case, the aggregate will expose a single output: the `twitter.handle.usage` output topic, defined during the 
first quick-start tutorial.

An aggregate's API is defined in an aggregate descriptor: a Java class that captures the metadata about the aggregate,
including its inputs and outputs. 

The [aggregate template](/aggregate-template/) repository, used to bootstrap a new repository during the
[Basic Kafka Streams tutorial](/basic-kafka-streams-demo/), provides an empty aggregate descriptor. 
So far, this descriptor has been left untouched by quick-start tutorial series. 
The aggregate descriptor can be found in the `api` module.

**Note:** To avoid descriptor name clashes, the name of the aggregate descriptor is derived from the aggregate name, 
i.e. the repository name. <br><br>
For example, a repository named `basic-kafka-streams-demo` would contain an aggregate descriptor named `BasicKafkaStreamsDemoAggregateDescriptor`.
Whereas, in a repository named `ks-aggregate-api-demo` would contain a `KsAggregateApiDemoAggregateDescriptor`.
{: .notice--warning}

The steps below define the API of the tutorial's aggregate: 

## Define the JSON payload

Rather than exposing the raw `Integer` handle count used internally by the first tutorial, the aggregate's
`twitter.handle.usage` topic will expose a schema validated JSON payload. This gives consumers a documented,
enforced contract for the data, rather than an opaque, undocumented primitive.

The payload is defined as a plain Java `record` in a new `model` package in the `api` module, annotated with
`@GeneratesSchema`:

{% highlight java %}
{% include_snippet usage-count from ../api/src/main/java/io/github/creek/service/ks/aggregate/api/demo/api/model/UsageCount.java %}
{% endhighlight %}

**Note:** Keep validation, such as the `count must be greater than zero` check above, in the record's compact
constructor. This is the validation the generated JSON schema is checked for consistency against.
{: .notice--info}

The `@GeneratesSchema` annotation marks the type for schema generation by Creek's JSON schema Gradle plugin.
Enable the plugin, and configure it to scan the `api` module for annotated types, in `api/build.gradle.kts`:

{% highlight kotlin %}
{% include_snippet schema-plugin from ../api/build.gradle.kts %}
{% endhighlight %}

Running `./gradlew :api:generateJsonSchema` generates a JSON schema file per annotated type under
`api/build/generated/resources/schema/main/`, which is packaged as a resource in the `api` jar and used, at
runtime, by the JSON serde to validate records and register schemas with the Schema Registry.

Finally, the record's package needs opening to reflection, in `api/src/main/java/module-info.java`, so that
Jackson, used by the JSON serde, can reflectively access the record's canonical constructor and component
accessors at runtime:

```java
// Required so Jackson (used by the JSON serde) can reflectively access the record's canonical
// constructor and component accessors at runtime.
opens io.github.creek.service.ks.aggregate.api.demo.api.model;
```

## Define a Creek aggregate API

Locate the aggregate's descriptor: this is a class named `<something>AggregateDescriptor` in the `api` module.

To keep things consistent, rename the class to `OccurrenceAggregateDescriptor`. This should help avoid confusion 
later due to potentially different class names. 

Locate the `handle-occurrence-service`'s descriptor: declared in the `HandleOccurrenceServiceDescriptor` 
class, within the `services` module. Copy it's `TweetHandleUsageStream` declaration into  `OccurrenceAggregateDescriptor`. 
It should look like the following:

{% highlight java %}
{% include_snippet class-name from ../api/src/main/java/io/github/creek/service/ks/aggregate/api/demo/api/OccurrenceAggregateDescriptor.java %}

    ...

{% include_snippet topic-resources from ../api/src/main/java/io/github/creek/service/ks/aggregate/api/demo/api/OccurrenceAggregateDescriptor.java %}

    ...
}
{% endhighlight %}

This adds an output topic and `register`s it with the descriptor.

`outputTopic` defaults to a Kafka-native key and a JSON-schema-validated value, using the
`UsageCount` type defined above. Use its explicit-format overload when the value is a native
Kafka type instead, as for the external ingestion aggregate's tweet-text topic.

## Update the service descriptor

There are now two definitions of the same topic: one in the aggregate descriptor and one in the service descriptor.
This code duplication is to be avoided.

Update the `TweetHandleUsageStream` declaration in the `HandleOccurrenceServiceDescriptor` class to use the aggregate's topic declaration:
 
{% highlight java %}
{% include_snippet class-name from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/HandleOccurrenceServiceDescriptor.java %}

    ...

{% include_snippet output-topic-resources from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/HandleOccurrenceServiceDescriptor.java %}
    ...
}
{% endhighlight %}

Referencing the aggregate's topic descriptor, defines in code, that the service's output topic is part of the aggregate's api.

## Testing the changes

To ensure that changes are correct, run the build by running:

```
./gradlew
```

This will compile the changes and run the tests. The build should be green.

## A word about dependencies

Before moving on, its worth having a quiet word about the dependencies on the `api` module.
If you were to look at the Gradle build file in the `api` module: `api/build.gradle.kts`.
The `dependencies` block looks like the following: 

{% highlight kotlin %}
{% include_snippet dependencies from ../api/build.gradle.kts %}
{% endhighlight %}

The module has two direct production dependencies: the `creek-kafka-metadata` jar, that contains the topic
descriptor and config types used within the aggregate's descriptor, and `creek-base-annotation`, that provides
the `@GeneratesSchema` annotation used to mark JSON payload types above. The `jsonSchemaGenerator` dependency
is used only at build time, by the JSON schema Gradle plugin, to generate the schema files, and does not end
up as a runtime dependency of the `api` jar.

As the API module is shared code, as the comment states, it is _strongly_ advised to avoid adding production dependencies
to this module, other than other _metadata_ jars for specific Creek extensions.

The Creek extension metadata jars themselves deliberately do not provide implementations for the types they define.
Instead, the aggregate template repository provides a default implementation, which aggregates are free to customise
as needed, _without_ any risk of causing dependency conflicts in projects that include `api` jars from multiple aggregates.

**ProTip:** Creek deliberately minimises the _surface area_ of the shared code used in `api` jars.
To avoid [dependency hell][depHell], it is strongly recommended that you do the same.
There's nothing worst that not being able to patch a production issue due to a dependency conflict!
{: .notice--info}

[creekExts]: https://www.creekservice.org/extensions/
[ksExt]: https://www.creekservice.org/creek-kafka
[dataMeshDef]: https://data.world/blog/what-is-data-mesh/
[dataProductDef]: https://data.world/blog/what-is-a-data-product-and-why-does-it-matter-to-data-mesh/
[aggDescriptor]: https://www.creekservice.org/docs/descriptors/#aggregate-descriptor
[serviceDescriptors]: https://www.creekservice.org/docs/descriptors/#service-descriptor
[depHell]: https://en.wikipedia.org/wiki/Dependency_hell
[sysTestStep]: {{ "/system-testing" | relative_url }}
