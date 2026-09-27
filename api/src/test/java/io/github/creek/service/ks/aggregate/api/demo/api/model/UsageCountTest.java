/*
 * Copyright 2021-2025 Creek Contributors (https://github.com/creek-service)
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsageCountTest {

    @Test
    void shouldExposeCount() {
        assertThat(new UsageCount(3).count(), is(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void shouldThrowOnNonPositiveCount(final int count) {
        // When:
        final Exception e =
                assertThrows(IllegalArgumentException.class, () -> new UsageCount(count));

        // Then:
        assertThat(e.getMessage(), is("count must be greater than zero"));
    }
}
