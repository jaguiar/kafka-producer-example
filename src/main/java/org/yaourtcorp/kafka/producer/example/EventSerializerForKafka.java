package org.yaourtcorp.kafka.producer.example;

import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * Interface for serializer for "event objects" (will be called just before sending event to kafka)
 * @see EventObject
 */
public interface EventSerializerForKafka<T extends EventObject> {

	String serialize(T eventObject) throws JsonProcessingException;
}
