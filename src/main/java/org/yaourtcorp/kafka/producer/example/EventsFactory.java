package org.yaourtcorp.kafka.producer.example;


public interface EventsFactory<T extends EventObject> {

	/**
	 * @return a new event object
	 */
	T createNewEvent();
}
