package org.yaourtcorp.kafka.producer.example.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaourtcorp.kafka.producer.example.EventObject;
import org.yaourtcorp.kafka.producer.example.EventSerializerForKafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("eventSerializerForKafka")
public class JsonSerializerForKafka<T extends EventObject> implements EventSerializerForKafka<T> {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public String serialize(T eventObject) throws JsonProcessingException {
		return objectMapper.writeValueAsString(eventObject);
	}

}
