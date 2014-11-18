/**
 * 
 */
package org.yaourtcorp.kafka.producer.example.yogurt;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.yaourtcorp.kafka.producer.example.AbstractTestBase;

/**
 * @author jaguiar
 *
 */
public class YogurtsFactoryTest extends AbstractTestBase{

	@Resource(name="eventsFactory")
	private YogurtsFactory yogurtsFactory;

	/**
	 * Test method for {@link org.yaourtcorp.kafka.producer.example.yogurt.YogurtsFactory#createNewEvent()}.
	 */
	@Test
	public void testCreateNewEvent() throws Exception {
		// 1. prepare data

		// 2. test
		Yogurt actual = yogurtsFactory.createNewEvent();

		//3. check
		assertThat(actual.getUuid(), is(notNullValue()));
		assertThat(actual.getCreationTime(), is(notNullValue()));
		assertThat(actual.getManufacturerId(), anyOf(is(1),is(2)));
		assertThat(actual.getProductName(), is(notNullValue()));
	}

}
