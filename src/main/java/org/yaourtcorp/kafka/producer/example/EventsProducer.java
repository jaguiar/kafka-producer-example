package org.yaourtcorp.kafka.producer.example;

import java.util.Properties;

import javax.annotation.Resource;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class EventsProducer<T extends EventObject> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("#{kafkaProperties}")
	private Properties kafkaProperties;
	
	@Value( "${kafka.topic}" )
	private String topicName;

	@Value( "${thead.sleep.in.ms}" )
	private int threadSleepInMs;
	
	
	@Resource(name="eventSerializerForKafka")
	private EventSerializerForKafka<T> eventSerializerForKafka;
	
	@Resource(name="eventsFactory")
	protected EventsFactory<T> eventsFactory;

	public void execute() throws Exception{

		ProducerConfig config = new ProducerConfig(kafkaProperties);

		Producer<String,  String> producer = null;

		try {
			producer =  new Producer<String, String>(config);
			while(true) {
				T event = eventsFactory.createNewEvent();
				KeyedMessage<String, String> data = new KeyedMessage<String, String>(topicName, eventSerializerForKafka.serialize(event));
				logger.info("Sending Message #:{}", event.getUuid());
				producer.send(data);
				Thread.sleep(threadSleepInMs);
			}
		} finally {
			if(producer != null){
				producer.close();
			}
		}
	}
}
