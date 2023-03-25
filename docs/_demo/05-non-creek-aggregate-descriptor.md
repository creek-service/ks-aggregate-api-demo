---
title: Integrating with non-Creek systems
permalink: /non-creek-aggregate-descriptor
description: Learn how to use Creek to integrate with parts of a system that don't use Creek. 
layout: single
snippet_comment_prefix: "//"
toc: true
---

Sometimes, there may be parts of an architecture that pre-date the introduction of Creek, or are managed by less enlightened
teams who don't use Creek, or are written in languages or technologies Creek does not _yet_ support.

Don't worry though. Creek's got you covered.

**Note:** Creek is currently very Java, or JVM language, and Kafka focused.
This is understandable considering its [history][creekStory].
In the future, the hope is that Creek moves [beyond Java and JVM languages][beyondJava] and
[beyond Kafka][beyondKafka].
{: .notice--info}

Even parts of an architecture that don't leverage Creek can, conceptually, be broken down into aggregates.
Aggregates are just a pattern for decomposing complex architectures into areas of functionality.

In this tutorial, the `twitter.tweet.text` Kafka topic is managed and populated by an existing system. 
A system that predates Creek. So how do we integrate with such a system?

In a fully Creek based architecture this upstream _aggregate_ would have its own descriptor.
Where no such descriptor is available... create one!
This allows Creek based components to easily reference that metadata.

## Define a non-Creek aggregate API

To define an aggregate descriptor for a non-Creek system, copy and rename the existing 
`OccurrenceAggregateDescriptor` class from the `api` module into the existing `services` module. 
The new class should be called `IngestionAggregateDescriptor` and should be created in the 
`io.github.creek.service.basic.kafka.streams.demo.services.external` package.

**ProTip:** The aggregate descriptor of non-Creek system can live in the `services` of an aggregate.
However, if multiple aggregates interact with the same system, it is often cleaner to define the system's 
aggregate descriptor, once, in a shared location, e.g. it's own jar file.
This descriptor should ideally be managed by the team that owns that system.
{: .notice--info}

Replace the `TweetHandleUsageStream` constant in the new `IngestionAggregateDescriptor` class with
a `TweetTextStream` constant, copied from the `HandleOccurrenceServiceDescriptor`.
The `TweetTextStream` constant will need tweaking to convert it from an input to an output topic:

{% highlight java %}
{% include_snippet class-name from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/external/IngestionAggregateDescriptor.java %}

    ...

{% include_snippet topic-resources from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/external/IngestionAggregateDescriptor.java %}
    ...
}
{% endhighlight %}

This defines a class constant that captures the metadata about the system's topic. 
It defines the topic name, the key and value types of the records stored in the topic and the topic config, 
which in this case is just the number of partitions.
This constant will be used below when updating the `handle-occurrence-service`'s descriptor.

The `register` method wrapping the `outputTopic` method call ensures the resource descriptor is registered with the outer
aggregate descriptor.

## Update the service descriptor

There are now two definitions of the same topic: one in the aggregate descriptor and one in the service descriptor.
This code duplication is to be avoided.

Update the `TweetTextStream` declaration in the `HandleOccurrenceServiceDescriptor` class to use the aggregate's topic declaration:

{% highlight java %}
{% include_snippet class-name from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/HandleOccurrenceServiceDescriptor.java %}

    ...

{% include_snippet input-topic-resources from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/HandleOccurrenceServiceDescriptor.java %}
    ...
}
{% endhighlight %}

Note how the _type_ of the topic descriptor has changed from an _owned_ input topic, to _unowned_, as the ownership
of the topic has conceptually moved to the `ingestion-aggregate`.

Referencing the aggregate's topic descriptor, defines in code, that the service is consuming the aggregate's output.

## Testing the changes

As before, ensure the changes are correct by running the build:

```
./gradlew
```

This will compile the changes and run the tests.

The build should fail with an error similar to: 

```
basic suite: Suite setup failed for test suite: basic suite, cause: No component provided a creatable descriptor for resource id: kafka-topic://default/twitter.tweet.text, 
 known_creatable_descriptors: [(file:/Users/andy/dev/github.com/creek/ks-aggregate-api-demo/api/build/libs/ks-aggregate-api-demo-api-0.1.2-SNAPSHOT.jar) io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors$OutputTopicDescriptor$1@4fb392c4]
```

So what's going on here then? The system tests are failing because `No component provided a creatable descriptor for resource id`
for the `kafka-topic://default/twitter.tweet.text` resource. 

The resource in question is the `twitter.tweet.text` topic, (on the `default` logical cluster), that was moved to the 
new `IngestionAggregateDescriptor` above.  

The `known_creatable_descriptors` lists all the _creatable_ resources, i.e. _owned_ topics, the system tests knows about.
This list contains only a single descriptor, (with a rubbish `toString` impl :wink:), from the repository's `api` jar.

The system tests discover component descriptors on the class and module path using the Java [ServiceLoader][serviceLoader].
The new aggregate descriptor isn't currently discoverable by the Java `ServiceLoader`...

## Discoverable Descriptors

How to make a service or aggregate descriptor discoverable will depend on whether it is on the JVM's class or module path.

**Note:** The [System test Gradle plugin][sysTestGradle] currently runs the tests from the class path, 
as Gradle doesn't play nicely with Java modules... yet.
{: .notice--info}

**ProTip:** We suggest registering component descriptors for use on both the class-path and module-path,
ensuring the work today _and_ tomorrow.
{: .notice--info}

**ProTip:** Only descriptors for non-Creek aggregate's need manually registering in this way.
The registration of the repository's aggregate and service descriptors is handled by the 
[aggregate template repository][aggTemp] and its scripts.
{: .notice--info}

### Descriptors on the module path

If the descriptor resides in a module, the descriptor needs to be declared in the module as a provider of the
`ComponentDescriptor` type.

Update the `services` module's descriptor, located at `services/src/main/java/module-info.java`, to include the
new aggregate descriptor, alongside the existing descriptor for the `handle-occurrence-service`:

{% highlight java %}
{% include_snippet class-name from ../services/src/main/java/module-info.java %}

    ...

{% include_snippet provides from ../services/src/main/java/module-info.java %}
    ...
}
{% endhighlight %}

### Descriptors on the class path

If the descriptor does not reside in a module, or the jar is on the class-path, the descriptor is registered
by placing a _provider-configuration_ file in the `META-INF/services` resource directory.

Update the `services` module's `ComponentDescriptor` provider-configuration file, located at
`services/src/main/resources/META-INF/services/org.creekservice.api.platform.metadata.ComponentDescriptor`,
to include the new aggregate descriptor, alongside the existing descriptor for the `handle-occurrence-service`.
The files content should be:

```
i{% include_snippet existing from ../services/src/main/resources/META-INF/services/org.creekservice.api.platform.metadata.ComponentDescriptor %}
i{% include_snippet new from ../services/src/main/resources/META-INF/services/org.creekservice.api.platform.metadata.ComponentDescriptor %}
```

## Testing the fix

To test the descriptor is now discoverable by the system tests, and the rest of the code is correct, run the build again:

```
./gradlew
```

The build should now be green!

This concludes the coding for the tutorial.

[bcDDD]: https://martinfowler.com/bliki/BoundedContext.html
[beyondKafka]: https://github.com/creek-service/creek-service/issues/18
[beyondJava]: https://github.com/creek-service/creek-service/issues/17
[creekStory]: https://www.creekservice.org/about/#creek-story
[serviceLoader]: https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/ServiceLoader.html
[ksExt]: https://www.creekservice.org/creek-kafka
[sysTestGradle]: https://github.com/creek-service/creek-system-test-gradle-plugin
[aggTemp]: {{ site.url | append: "/aggregate-template/" }}
[todo]: switch about links to proper creekservice.org links once each repo publishes docs.

