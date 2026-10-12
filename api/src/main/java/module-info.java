import io.github.creek.service.ks.aggregate.api.demo.api.KsAggregateApiDemoAggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.aggregate.api.demo.api {
    requires transitive creek.kafka.metadata;
    requires static creek.base.annotation;
    requires static com.fasterxml.jackson.annotation;
    requires static io.swagger.v3.oas.annotations;

    exports io.github.creek.service.ks.aggregate.api.demo.api;
    // begin-snippet: model-export
    exports io.github.creek.service.ks.aggregate.api.demo.api.model;
    // end-snippet
    exports io.github.creek.service.ks.aggregate.api.demo.internal to
            ks.aggregate.api.demo.services,
            ks.aggregate.api.demo.handle.occurrence.service;

    // begin-snippet: model-open
    opens io.github.creek.service.ks.aggregate.api.demo.api.model;
    // end-snippet

    provides ComponentDescriptor with
            KsAggregateApiDemoAggregateDescriptor;
}
