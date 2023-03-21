package io.github.creek.service.ks.aggregate.api.demo.services.external;

import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicConfigBuilder.withPartitions;
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.outputTopic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.AggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentInput;
import org.creekservice.api.platform.metadata.ComponentOutput;
import org.creekservice.api.platform.metadata.OwnedResource;

// begin-snippet: class-name
// Aggregate descriptor for the upstream ingestion aggregate.
public final class IngestionAggregateDescriptor implements AggregateDescriptor {
    // end-snippet

    private static final List<ComponentInput> INPUTS = new ArrayList<>();
    private static final List<ComponentOutput> OUTPUTS = new ArrayList<>();

    // formatting:off
// begin-snippet: topic-resources
    // Define the tweet-text output topic, conceptually owned by this aggregate:
    public static final OwnedKafkaTopicOutput<Long, String> TweetTextStream =
            register(
                    outputTopic(
                            "twitter.tweet.text", // Topic name
                            Long.class, // Topic key type (Tweet id)
                            String.class, // Topic value type (Tweet text)
                            withPartitions(5))); // Topic config
// end-snippet
    // formatting:on

    public IngestionAggregateDescriptor() {}

    @Override
    public Collection<ComponentInput> inputs() {
        return List.copyOf(INPUTS);
    }

    @Override
    public Collection<ComponentOutput> outputs() {
        return List.copyOf(OUTPUTS);
    }

    // Uncomment if needed
    // private static <T extends ComponentInput & OwnedResource> T register(final T input) {
    //     INPUTS.add(input);
    //     return input;
    // }

    private static <T extends ComponentOutput & OwnedResource> T register(final T output) {
        OUTPUTS.add(output);
        return output;
    }
}
