package org.yaourtcorp.kafka.producer.example;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventSerializerForKafka<T extends EventObject> {

	String serialize(T eventObject) throws JsonProcessingException;
}
