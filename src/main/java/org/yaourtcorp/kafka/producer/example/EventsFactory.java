package org.yaourtcorp.kafka.producer.example;

/*
 * Interface for "event objects" factories: shall find a way to create a new event object to send to kafka. 
 */
public interface EventsFactory<T extends EventObject> {

	/**
	 * @return a new event object
	 */
	T createNewEvent();
}
