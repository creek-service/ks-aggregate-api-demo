plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

// begin-snippet: dependencies
dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")

    compileOnlyApi("com.fasterxml.jackson.core:jackson-annotations:${property("jacksonAnnotationsVersion")}")
    compileOnlyApi("org.creekservice:creek-base-annotation:${property("creekVersion")}")
    compileOnlyApi("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")
}
// end-snippet

creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
