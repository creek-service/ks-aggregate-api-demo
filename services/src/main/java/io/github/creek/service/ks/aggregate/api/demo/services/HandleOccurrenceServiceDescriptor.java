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

package io.github.creek.service.ks.aggregate.api.demo.services;

import io.github.creek.service.ks.aggregate.api.demo.api.OccurrenceAggregateDescriptor;
import io.github.creek.service.ks.aggregate.api.demo.services.external.IngestionAggregateDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.creekservice.api.kafka.metadata.KafkaTopicInput;
import org.creekservice.api.kafka.metadata.OwnedKafkaTopicOutput;
import org.creekservice.api.platform.metadata.ComponentInput;
import org.creekservice.api.platform.metadata.ComponentInternal;
import org.creekservice.api.platform.metadata.ComponentOutput;
import org.creekservice.api.platform.metadata.ServiceDescriptor;

// begin-snippet: class-name
public final class HandleOccurrenceServiceDescriptor implements ServiceDescriptor {
    // end-snippet

    private static final List<ComponentInput> INPUTS = new ArrayList<>();
    private static final List<ComponentInternal> INTERNALS = new ArrayList<>();
    private static final List<ComponentOutput> OUTPUTS = new ArrayList<>();

    // formatting:off
// begin-snippet: input-topic-resources
    // Define the tweet-text input topic, managed by the ingestion aggregate:
    public static final KafkaTopicInput<Long, String> TweetTextStream =
            register(IngestionAggregateDescriptor.TweetTextStream.toInput());
    // end-snippet
// formatting:on

    // formatting:off
// begin-snippet: output-topic-resources
    // Define the service's output topic, which is part of this aggregate's API:
    public static final OwnedKafkaTopicOutput<String, Integer> TweetHandleUsageStream =
            register(OccurrenceAggregateDescriptor.TweetHandleUsageStream);
    // end-snippet
// formatting:on

    public HandleOccurrenceServiceDescriptor() {}

    @Override
    public String dockerImage() {
        return "ghcr.io/creek-service/ks-aggregate-api-demo-handle-occurrence-service";
    }

    @Override
    public Collection<ComponentInput> inputs() {
        return List.copyOf(INPUTS);
    }

    @Override
    public Collection<ComponentInternal> internals() {
        return List.copyOf(INTERNALS);
    }

    @Override
    public Collection<ComponentOutput> outputs() {
        return List.copyOf(OUTPUTS);
    }

    private static <T extends ComponentInput> T register(final T input) {
        INPUTS.add(input);
        return input;
    }

    // Uncomment if needed:
    // private static <T extends ComponentInternal> T register(final T internal) {
    //     INTERNALS.add(internal);
    //     return internal;
    // }

    private static <T extends ComponentOutput> T register(final T output) {
        OUTPUTS.add(output);
        return output;
    }
}
