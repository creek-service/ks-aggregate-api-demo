---
title: Publishing the API
permalink: /publish-api
description: Suggestions and notes on publishing an aggregate's api.
layout: single
snippet_comment_prefix: "//"
---

The aggregate's API is the set of resources, topics in this case, that the aggregate is exposing to the rest of
the organisation. Serves in other aggregates can access the resource metadata via the aggregate descriptor. 
To enable this, the api needs to be published somewhere!

**Note:** The `IngestionAggregateDescriptor` added to the `services` module should _not_ be published.
The `servies` jar should be private to the repository. If the ingestion aggregate is accessed by more than one Creek
aggregate, then the `IngestionAggregateDescriptor` should be declared in a shared location and packaged in its own
jar file.
{: .notice--warning}


How and where an organisation publishes jars will vary, so configuring the publishing is outside the scope of this tutorial. 
However, Creek doesn't leave you completely stranded either.

## Publishing to GitHub packages

The [aggregate template][aggTemp], and hence the [completed tutorial available on GitHub][demoOnGh], comes pre-configured
to publish the `api` module to [GitHub packages][ghPackages], which provides a [Maven repository][ghPackagesMaven] where jars can be published.

Publishing is configured in the `buildSrc/src/main/kotlin/publishing-convention.gradle.kts` file. 
You'll notice a `ChangeMe` comment in there, encouraging users to think about where their `api` jar should be published.

{% highlight java %}
{% include_snippet gh-packages from ../buildSrc/src/main/kotlin/publishing-convention.gradle.kts %}
{% endhighlight %}

The published `api` can be viewed [in GitHub][aggPackages] alongside the service's docker container.

## Are GitHub packages right for your organisation? 

At the time of writing GitHub packages aren't meant as a way of publishing public artifacts, 
(though there is a [workaround](https://github.com/orgs/community/discussions/26634#discussioncomment-3252638)), 
but they are perfect for sharing artifacts _within_ an organisation.
That said, if your organisation already has a standard way of sharing Java artifacts, the `api` jar should be published there, 
ready for others to download and use.

[demoOnGh]: https://github.com/creek-service/ks-aggregate-api-demo
[ghPackages]: https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages
[ghPackagesMaven]: https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry
[aggPackages]: https://github.com/orgs/creek-service/packages?repo_name=ks-aggregate-api-demo
[aggTemp]: {{ site.url | append: "/aggregate-template/" }}
