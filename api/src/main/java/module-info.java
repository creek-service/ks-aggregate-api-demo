import io.github.creek.service.ks.aggregate.api.demo.api.OccurrenceAggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.aggregate.api.demo.api {
    requires transitive creek.kafka.metadata;
    requires creek.base.annotation;
    requires com.fasterxml.jackson.annotation;
    requires static io.swagger.v3.oas.annotations;

    exports io.github.creek.service.ks.aggregate.api.demo.api;
    exports io.github.creek.service.ks.aggregate.api.demo.api.model;
    exports io.github.creek.service.ks.aggregate.api.demo.internal to
            ks.aggregate.api.demo.services,
            ks.aggregate.api.demo.handle.occurrence.service;

    // begin-snippet: opens-model
    opens io.github.creek.service.ks.aggregate.api.demo.api.model;

    // end-snippet

    provides ComponentDescriptor with
            OccurrenceAggregateDescriptor;
}
