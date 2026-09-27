module ks.aggregate.api.demo.service {
    requires ks.aggregate.api.demo.services;
    requires creek.service.context;
    requires creek.kafka.streams.extension;
    // Provides the JSON schema Kafka serde, required as the twitter.handle.usage topic's
    // value is JSON schema validated:
    requires creek.kafka.serde.json.schema;
    requires org.apache.logging.log4j;
}
