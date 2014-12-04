package org.yaourtcorp.kafka.producer.example;
/*
 * Interface for "event" objects aka objects to send to kafka (and hope it will arrive somewhere)
 */
public interface EventObject {

	/**
	 * @return an uuid for the object
	 */
	public String getUuid();
}
