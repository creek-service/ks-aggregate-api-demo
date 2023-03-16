---
title: Define the aggregate's API
permalink: /descriptor
description: Learn how to create an aggregate descriptor, defining the aggregate's API / the data products the aggregate exposes.
layout: single
snippet_comment_prefix: "//"
toc: true
---

The [aggregate template](/aggregate-template/) repository, used in the [Basic Kafka Streams tutorial](/basic-kafka-streams-demo/) 
to quickly bootstrap a new repository, provides an empty aggregate descriptor. 
Though, so far, this descriptor has been ignored by quick-start tutorial series. 
The aggregate descriptor can be found in the `api` module.

**Note:** To avoid descriptor name clashes, the class name of the aggregate descriptor is derived from the aggregate name, 
i.e. the repository name. <br><br>
For example, a repository named `basic-kafka-streams-demo` would contain an aggregate descriptor named `BasicKafkaStreamsDemoAggregateDescriptor`.
Whereas, in a repository named `ks-aggregate-api-demo` would contain a `KsAggregateApiDemoAggregateDescriptor`.
{: .notice--warning}

The steps below copy the output topic's declaration to the aggregate's descriptor. 

## Define the aggregate's API

Locate the aggregate's descriptor: this is a class named `<something>AggregateDescriptor` in the `api` module,
and the `HandleOccurrenceServiceDescriptor` in the `services` module.

Copy the `TweetHandleUsageStream` declaration from the `HandleOccurrenceServiceDescriptor` class to the aggregate 
descriptor. It should look like the following, though the class name may vary.

{% highlight java %}
{% include_snippet topic-resources from ../api/src/main/java/io/github/creek/service/ks/aggregate/api/demo/api/KsAggregateApiDemoAggregateDescriptor.java %}
    ...
}
{% endhighlight %}

## Update the service descriptor

Update the `TweetHandleUsageStream` declaration in the `HandleOccurrenceServiceDescriptor` class to use the aggregate's topic declaration:
 
{% highlight java %}
{% include_snippet topic-resources from ../services/src/main/java/io/github/creek/service/ks/aggregate/api/demo/services/HandleOccurrenceServiceDescriptor.java %}
    ...
}
{% endhighlight %}

Referencing the aggregate's topic descriptor from the service's descriptor makes it explicit in the code that the 
service's output is part of the aggregate's api.

[creekExts]: https://www.creekservice.org/extensions/
[aggDescriptor]: https://www.creekservice.org/docs/descriptors/#aggregate-descriptor
[serviceDescriptors]: https://www.creekservice.org/docs/descriptors/#service-descriptor
[sysTestStep]: {{ "/system-testing" | relative_url }}