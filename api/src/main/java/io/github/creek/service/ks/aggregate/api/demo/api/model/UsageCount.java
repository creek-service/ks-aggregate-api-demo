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

import io.swagger.v3.oas.annotations.media.Schema;
import org.creekservice.api.base.annotation.schema.GeneratesSchema;

// begin-snippet: usage-count
/** The number of times a Twitter handle was encountered within a single occurrence record. */
@GeneratesSchema
public record UsageCount(@Schema(minimum = "1") int count) {

    public UsageCount {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than zero");
        }
    }
}
// end-snippet
