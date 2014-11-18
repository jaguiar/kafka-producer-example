/**
 * 
 */
package org.yaourtcorp.kafka.producer.example.json;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.yaourtcorp.kafka.producer.example.AbstractTestBase;
import org.yaourtcorp.kafka.producer.example.yogurt.Yogurt;

/**
 * @author jaguiar
 *
 */
public class JsonSerializerForKafkaTest extends AbstractTestBase {

	@Resource(name="eventSerializerForKafka")
	private JsonSerializerForKafka<Yogurt> jsonSerializerForKafka;

	/**
	 * Test method for {@link org.yaourtcorp.kafka.producer.example.json.JsonSerializerForKafka#serialize(org.yaourtcorp.kafka.producer.example.EventObject)}.
	 */
	@Test
	public void testSerialize() throws Exception {
		// 1. prepare data
		String expectedUid = "UIID";
		DateTime expectedCreationTime = DateTime.parse("2014-11-18T10:39:28.983Z");
		String expectedProductName = "constructive_carrot";
		int expectedManufacturerId = 1;
		Yogurt yogurt = new Yogurt(expectedUid, expectedProductName, expectedManufacturerId, expectedCreationTime);
		
		// 2. test
		String actual = jsonSerializerForKafka.serialize(yogurt);

		//3. check
		assertThat(actual, is(notNullValue()));
		assertThat(actual, is("{\"uuid\":\"UIID\",\"productName\":\"constructive_carrot\",\"manufacturerId\":1,\"creationTime\":\"2014-11-18T10:39:28.983Z\"}"));
	}
}
