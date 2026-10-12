package io.github.creek.service.ks.aggregate.api.demo.services.external;

import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicConfigBuilder.withPartitions;
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.KAFKA_FORMAT;
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.outputTopic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.topic.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.AggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentInput;
import org.creekservice.api.platform.metadata.ComponentOutput;
import org.creekservice.api.platform.metadata.OwnedResource;

// begin-snippet: class-name
// Aggregate descriptor for the upstream ingestion aggregate.
public final class IngestionAggregateDescriptor implements AggregateDescriptor {
    // end-snippet

    // formatting:off
// begin-snippet: topic-resources
    // Define the tweet-text output topic, conceptually owned by this aggregate:
    public static final OwnedKafkaTopicOutput<Long, String> RawTweetStream =
            outputTopic(
                    "twitter.tweet.text", // Topic name
                    Long.class, // Topic key type (Tweet id)
                    KAFKA_FORMAT,
                    String.class, // Raw tweet text
                    KAFKA_FORMAT,
                    withPartitions(5)); // Topic config
// end-snippet
    // formatting:on

    public IngestionAggregateDescriptor() {}

    @Override
    public Collection<ComponentOutput> outputs() {
        return List.of(RawTweetStream);
    }
}
