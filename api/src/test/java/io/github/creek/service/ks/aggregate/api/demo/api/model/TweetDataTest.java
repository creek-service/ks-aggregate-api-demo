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

package io.github.creek.service.ks.aggregate.api.demo.api.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TweetDataTest {

    @Test
    void shouldExposeIdAndText() {
        // When:
        final TweetData tweet = new TweetData(1L, "hello");

        // Then:
        assertThat(tweet.id(), is(1L));
        assertThat(tweet.text(), is("hello"));
    }

    @Test
    void shouldThrowOnNullText() {
        // When:
        final Exception e = assertThrows(NullPointerException.class, () -> new TweetData(1L, null));

        // Then:
        assertThat(e.getMessage(), is("text"));
    }

    @Test
    void shouldThrowOnEmptyText() {
        // When:
        final Exception e =
                assertThrows(IllegalArgumentException.class, () -> new TweetData(1L, ""));

        // Then:
        assertThat(e.getMessage(), is("text cannot be empty"));
    }
}
