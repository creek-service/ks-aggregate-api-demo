import io.github.creek.service.ks.aggregate.api.demo.services.HandleOccurrenceServiceDescriptor;
import io.github.creek.service.ks.aggregate.api.demo.services.external.IngestionAggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

// begin-snippet: class-name
module ks.aggregate.api.demo.services {
    // end-snippet

    requires transitive ks.aggregate.api.demo.api;

    exports io.github.creek.service.ks.aggregate.api.demo.services;

    // formatting:off
// begin-snippet: provides
    provides ComponentDescriptor with
            HandleOccurrenceServiceDescriptor,
            IngestionAggregateDescriptor;
// end-snippet
    // formatting:on
}
