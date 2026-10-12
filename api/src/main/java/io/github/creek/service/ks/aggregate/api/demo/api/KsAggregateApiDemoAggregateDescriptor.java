/*
 * Copyright 2021-2026 Creek Contributors (https://github.com/creek-service)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.creek.service.ks.aggregate.api.demo.api;

import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicConfigBuilder.withPartitions;
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.JSON_FORMAT;
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.KAFKA_FORMAT;
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.outputTopic;

import io.github.creek.service.ks.aggregate.api.demo.api.model.HandleUsage;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.topic.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.AggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentOutput;

// begin-snippet: all
public final class KsAggregateApiDemoAggregateDescriptor implements AggregateDescriptor {

    public static final OwnedKafkaTopicOutput<String, HandleUsage> TweetHandleUsageStream =
            outputTopic(
                    "twitter.handle.usage",
                    String.class, // Twitter handle
                    KAFKA_FORMAT,
                    HandleUsage.class, // Usage count, JSON schema validated
                    JSON_FORMAT,
                    withPartitions(6).withRetentionTime(Duration.ofHours(12)));

    public KsAggregateApiDemoAggregateDescriptor() {}

    @Override
    public Collection<ComponentOutput> outputs() {
        return List.of(TweetHandleUsageStream);
    }
}
// end-snippet
