import io.github.creek.service.ks.aggregate.api.demo.api.OccurrenceAggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.aggregate.api.demo.api {
    requires transitive creek.kafka.metadata;
    requires creek.base.annotation;
    requires static io.swagger.v3.oas.annotations;
    requires static com.github.spotbugs.annotations;

    exports io.github.creek.service.ks.aggregate.api.demo.api;
    exports io.github.creek.service.ks.aggregate.api.demo.api.model;
    exports io.github.creek.service.ks.aggregate.api.demo.internal to
            ks.aggregate.api.demo.services,
            ks.aggregate.api.demo.service;

    // Required so Jackson (used by the JSON serde) can reflectively access the record's
    // canonical constructor and component accessors at runtime.
    opens io.github.creek.service.ks.aggregate.api.demo.api.model;

    provides ComponentDescriptor with
            OccurrenceAggregateDescriptor;
}
