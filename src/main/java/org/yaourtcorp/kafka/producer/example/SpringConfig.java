package org.yaourtcorp.kafka.producer.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.yaourtcorp.kafka.producer.example.conditions.ConditionalOnEnvRequiredProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Global configuration for the application.
 * We need applicationContext.xml because:
 * <li>
 * <ul>there is no equivalent to "util:properties" with Spring annotations</ul>
 * <ul>{@link PropertySourcesPlaceholderConfigurer} shall be created explicitly as a static bean in the spring config in order to be able to use placeholders in the configuration itself (refer to annotation '@PropertySource("classpath:${event.to.produce}.properties")' below)</ul>
 * </li>
 * 
 * @author jennifer aguiar
 * ${event.to.produce}.properties (e.g. yogurt.properties) contains any configuration to be used for producing/sending a specific event.
 */
@Configuration
@PropertySource("classpath:${event.to.produce}.properties")
@ImportResource("classpath:applicationContext.xml")
public class SpringConfig {
	
	@Autowired
    Environment env;
	
	public static final String EVENT_TO_PRODUCE_ENV_PROPERTY = "event.to.produce";
	public static final String SERIALIZATION_FORMAT_ENV_PROPERTY = "serialization.format";
	
	/**
	 * Spring Configuration for producing yogurts events.
	 * This configuration is conditioned by a flag "event.to.produce" set to "yogurt" in the context
	 * @author jennifer aguiar
	 * @see YogurtCondition
	 */
	@Configuration
	@ConditionalOnEnvRequiredProperty(propertyName=EVENT_TO_PRODUCE_ENV_PROPERTY, value="yogurt")
//	@Conditional(value=YogurtCondition.class)
	@ComponentScan(basePackages = {"org.yaourtcorp.kafka.producer.example.yogurt"})
	static class YogurtSpringConfig {
		
	}

	/**
	 * Spring Configuration for serializing in json data to be sent in kafka.
	 * This configuration is conditioned by a flag "serialization.format" set to "json" in the context
	 * @author jennifer aguiar
	 * @see JsonSerializationCondition
	 */
	@Configuration
	@ConditionalOnEnvRequiredProperty(propertyName=SERIALIZATION_FORMAT_ENV_PROPERTY, value="json")
//	@Conditional(value=JsonSerializationCondition.class)
	@ComponentScan(basePackages = {"org.yaourtcorp.kafka.producer.example.json"})
	static class JsonSerializationSpringConfig {
		/**
		 * may need to refactor this part.
		 * @return an instance of {@link ObjectMapper}
		 */
		@Bean(name="objectMapper")
		public ObjectMapper objectMapper(){
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JodaModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false); // will use ISO-8601 format
			return objectMapper;
		}
	}
}
