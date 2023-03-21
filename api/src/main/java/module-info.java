import io.github.creek.service.ks.aggregate.api.demo.api.OccurrenceAggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.aggregate.api.demo.api {
    requires transitive creek.kafka.metadata;

    exports io.github.creek.service.ks.aggregate.api.demo.api;
    exports io.github.creek.service.ks.aggregate.api.demo.internal to
            ks.aggregate.api.demo.services,
            ks.aggregate.api.demo.service;

    provides ComponentDescriptor with
            OccurrenceAggregateDescriptor;
}
