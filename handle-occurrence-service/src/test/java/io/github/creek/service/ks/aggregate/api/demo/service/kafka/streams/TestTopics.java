/*
 * Copyright 2022-2025 Creek Contributors (https://github.com/creek-service)
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

package io.github.creek.service.ks.aggregate.api.demo.service.kafka.streams;

import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.creekservice.api.kafka.extension.KafkaClientsExtension;
import org.creekservice.api.kafka.extension.resource.KafkaTopic;
import org.creekservice.api.kafka.metadata.topic.KafkaTopicDescriptor;
import org.creekservice.api.service.context.CreekContext;

/**
 * Test helper methods for creating input and output topics when using Creek and the {@link
 * TopologyTestDriver}.
 *
 * <p>{@code org.creekservice.api.kafka.streams.test.TestTopics} is deprecated for removal, with
 * each repo asked to provide its own version, so this is that version for this repo.
 */
final class TestTopics {

    private TestTopics() {}

    @SuppressWarnings("resource")
    static <K, V> TestInputTopic<K, V> inputTopic(
            final KafkaTopicDescriptor<K, V> topicDescriptor,
            final CreekContext ctx,
            final TopologyTestDriver testDriver) {
        final KafkaTopic<K, V> topic =
                ctx.extension(KafkaClientsExtension.class).topic(topicDescriptor);
        return testDriver.createInputTopic(
                topicDescriptor.name(),
                topic.keySerde().serializer(),
                topic.valueSerde().serializer());
    }

    @SuppressWarnings("resource")
    static <K, V> TestOutputTopic<K, V> outputTopic(
            final KafkaTopicDescriptor<K, V> topicDescriptor,
            final CreekContext ctx,
            final TopologyTestDriver testDriver) {
        final KafkaTopic<K, V> topic =
                ctx.extension(KafkaClientsExtension.class).topic(topicDescriptor);
        return testDriver.createOutputTopic(
                topicDescriptor.name(),
                topic.keySerde().deserializer(),
                topic.valueSerde().deserializer());
    }
}
