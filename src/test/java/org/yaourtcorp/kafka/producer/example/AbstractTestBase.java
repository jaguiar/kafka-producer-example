/**
 * 
 */
package org.yaourtcorp.kafka.producer.example;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author jaguiar
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringConfig.class})
public abstract class AbstractTestBase {

	@BeforeClass
	public static void setUpBeforeClass(){
		System.setProperty(SpringConfig.EVENT_TO_PRODUCE_ENV_PROPERTY, "yogurt");
		System.setProperty(SpringConfig.SERIALIZATION_FORMAT_ENV_PROPERTY, "json");
	}
}
