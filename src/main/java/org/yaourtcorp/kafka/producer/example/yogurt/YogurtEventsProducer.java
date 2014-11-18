package org.yaourtcorp.kafka.producer.example.yogurt;

/**
 * InvoiceEventsProducer class simulates the real time invoice event generation.
 *
 */
import org.springframework.stereotype.Component;
import org.yaourtcorp.kafka.producer.example.EventsProducer;

@Component(value="eventsProducer")
public class YogurtEventsProducer extends EventsProducer<Yogurt> {

}
