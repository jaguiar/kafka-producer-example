package org.yaourtcorp.kafka.producer.example.yogurt;

/**
 * YogurtEventsProducer class simulates the real time yogurts event generation.
 *
 */
import org.springframework.stereotype.Component;
import org.yaourtcorp.kafka.producer.example.EventsProducer;

@Component(value="eventsProducer")
public class YogurtEventsProducer extends EventsProducer<Yogurt> {

}
