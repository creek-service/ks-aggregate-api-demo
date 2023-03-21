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

[basicKsDemo]: https://www.creekservice.org/basic-kafka-streams-demo/
[ksConnectSvsDemo]:  https://www.creekservice.org/ks-connected-services-demo/
[aggTemp]: {{ site.url | append: "/aggregate-template/" }}