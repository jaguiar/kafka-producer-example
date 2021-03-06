package org.yaourtcorp.kafka.producer.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Do nothing, just load spring config
 */
public class Main {

	public static void main(String[] args) throws Exception {
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();){
			ctx.register(SpringConfig.class);
			ctx.refresh();
			ctx.getBean("eventsProducer", EventsProducer.class).execute();
		}
	}
}
