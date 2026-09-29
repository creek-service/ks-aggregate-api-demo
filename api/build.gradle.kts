plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

// begin-snippet: dependencies
dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")
    implementation("org.creekservice:creek-base-annotation:${property("creekVersion")}")
    // Used to annotate schema constraints (e.g. minimum/minLength) for `generateJsonSchema`.
    // `compileOnlyApi` keeps it off the runtime classpath: `creek-kafka-json-serde`'s Confluent
    // schema-registry client transitively pulls in `swagger-annotations-jakarta`, which claims the
    // same JPMS module name.
    compileOnlyApi("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")
    jsonSchemaGenerator("org.creekservice:creek-json-schema-generator:${property("creekVersion")}")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    // The module descriptor's `requires static` needs this resolvable when compiling the test module patch too.
    testCompileOnly("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")
    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")
}
// end-snippet

// begin-snippet: schema-plugin
creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
// end-snippet