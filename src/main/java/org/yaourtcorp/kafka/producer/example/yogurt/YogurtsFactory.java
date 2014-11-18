package org.yaourtcorp.kafka.producer.example.yogurt;

import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaourtcorp.kafka.producer.example.EventsFactory;

@Component(value="eventsFactory")
public class YogurtsFactory implements EventsFactory<Yogurt>{

	@Value( "${number.of.manfacturers}" )
	private int numberOfManufacturers;
	
	private final Random randomManufacturerIds = new Random();
	private final RandomNameGenerator rndNames = new RandomNameGenerator(0);
	


	@Override
	public Yogurt createNewEvent() {
		return new Yogurt(
				UUID.randomUUID().toString(),
				rndNames.next(),
				randomManufacturerIds.nextInt(numberOfManufacturers)+1,
				DateTime.now());
	}

	

}
