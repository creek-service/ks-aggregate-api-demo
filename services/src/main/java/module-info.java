import io.github.creek.service.ks.aggregate.api.demo.services.HandleOccurrenceServiceDescriptor;
import org.creekservice.api.platform.metadata.ComponentDescriptor;

module ks.aggregate.api.demo.services {
    requires transitive ks.aggregate.api.demo.api;

    exports io.github.creek.service.ks.aggregate.api.demo.services;

    provides ComponentDescriptor with
            HandleOccurrenceServiceDescriptor;
}
