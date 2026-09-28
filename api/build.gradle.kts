plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

// begin-snippet: dependencies
dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")
    implementation("org.creekservice:creek-base-annotation:${property("creekVersion")}")
    jsonSchemaGenerator("org.creekservice:creek-json-schema-generator:${property("creekVersion")}")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")
}
// end-snippet

// begin-snippet: schema-plugin
creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
// end-snippet