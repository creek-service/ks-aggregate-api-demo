/*
 * Copyright 2021-2022 Creek Contributors (https://github.com/creek-service)
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
import static io.github.creek.service.ks.aggregate.api.demo.internal.TopicDescriptors.outputTopic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.AggregateDescriptor;
import org.creekservice.api.platform.metadata.ComponentInput;
import org.creekservice.api.platform.metadata.ComponentOutput;
import org.creekservice.api.platform.metadata.OwnedResource;

// begin-snippet: topic-resources
public final class KsAggregateApiDemoAggregateDescriptor implements AggregateDescriptor {

    private static final List<ComponentInput> INPUTS = new ArrayList<>();
    private static final List<ComponentOutput> OUTPUTS = new ArrayList<>();

    public static final OwnedKafkaTopicOutput<String, Integer> TweetHandleUsageStream =
            register(
                    outputTopic(
                            "twitter.handle.usage",
                            String.class, // Twitter handle
                            Integer.class, // Usage count
                            withPartitions(6)));
    // end-snippet

    public KsAggregateApiDemoAggregateDescriptor() {}

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
