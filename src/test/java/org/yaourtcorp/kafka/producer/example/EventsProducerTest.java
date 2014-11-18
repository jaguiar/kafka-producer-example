/**
 * 
 */
package org.yaourtcorp.kafka.producer.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.MockTime;
import kafka.utils.TestUtils;
import kafka.utils.TestZKUtils;
import kafka.utils.Time;
import kafka.utils.VerifiableProperties;
import kafka.utils.ZKStringSerializer$;
import kafka.zk.EmbeddedZookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.yaourtcorp.kafka.producer.example.yogurt.Yogurt;
import org.yaourtcorp.kafka.producer.example.yogurt.YogurtEventsProducer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


/**
 * @author jaguiar
 * probably to refactor
 */
@RunWith(MockitoJUnitRunner.class)
public class EventsProducerTest {

	private final static int BROKER_ID = 0;
	private final static String TOPIC = "test-yogurt";
	private final static String NO_MORE_YOGURT_MSG = "NoMoreYogurtException";

	@Mock
	private EventSerializerForKafka<Yogurt> eventSerializerForKafka;

	@Mock
	private EventsFactory<Yogurt> eventsFactory;

	// embedded kafka + zookeeper
	private KafkaServer kafkaServer;
	private ZkClient zkClient;
	private EmbeddedZookeeper zkServer;
	private ConsumerConnector consumerConnector;
	private KafkaStream<String,String> topicMessageStreams;
	

	@InjectMocks
	private EventsProducer<Yogurt> eventsProducer = new YogurtEventsProducer();


	@Test
	public void testMain() throws Exception {
		//1. prepare data
		Yogurt yogurt = new Yogurt("uuddlrlrba", "konami_code", 42, DateTime.parse("2014-11-18T10:39:28.983Z"));
		String expected = "{\"uuid\":\"uuddlrlrba\",\"productName\":\"constructive_carrot\",\"manufacturerId\":42,\"creationTime\":\"2014-11-18T10:39:28.983Z\"}";
		
		//2. mock
		when(eventsFactory.createNewEvent()).thenReturn(yogurt).thenThrow(new IllegalStateException(NO_MORE_YOGURT_MSG));
		when(eventSerializerForKafka.serialize(yogurt)).thenReturn(expected);
		
		//3. test
		try {
			eventsProducer.execute();
		} catch(IllegalStateException e){
			if(!NO_MORE_YOGURT_MSG.equals(e.getMessage())){
				e.printStackTrace();
				fail(e.toString());
			}
		}
		
		//4. check
		verify(eventsFactory,times(2)).createNewEvent();
		verify(eventSerializerForKafka,times(1)).serialize(yogurt);
		
		final ConsumerIterator<String,String> iterator = topicMessageStreams.iterator();
		assertTrue(iterator.hasNext());
		final MessageAndMetadata<String, String> actual = iterator.next();
		assertThat(actual.message(), is(expected));
//		assertFalse(iterator.hasNext()); => kafka.consumer.ConsumerTimeoutException
	}
	
	@Before
	public void setUp() {
		// setup Zookeeper
		final String zkConnect = TestZKUtils.zookeeperConnect();
		zkServer = new EmbeddedZookeeper(zkConnect);
		zkClient = new ZkClient(zkServer.connectString(), 30000, 30000,  ZKStringSerializer$.MODULE$);

		// setup Broker
		final int port = TestUtils.choosePort();
		final Properties props = TestUtils.createBrokerConfig(BROKER_ID, port);

		final KafkaConfig config = new KafkaConfig(props);
		final Time mock = new MockTime();
		kafkaServer = TestUtils.createServer(config, mock);

		// create topic
		AdminUtils.createTopic(zkClient, TOPIC, 1, 1, new Properties());

		final List<KafkaServer> servers = ImmutableList.of(kafkaServer);
		TestUtils.waitUntilMetadataIsPropagated(scala.collection.JavaConversions.asScalaBuffer(servers), TOPIC, 0, 5000);

		// setup properties for producer and add it to object under test
		final Properties kafkaProperties= TestUtils.getProducerConfig("localhost:" + port, "kafka.producer.DefaultPartitioner");
		ReflectionTestUtils.setField(eventsProducer, "kafkaProperties", kafkaProperties);
		ReflectionTestUtils.setField(eventsProducer, "topicName", TOPIC);
		ReflectionTestUtils.setField(eventsProducer, "threadSleepInMs", 10);
		
		// setup consumer
		final ConsumerConfig consumerConfig = new ConsumerConfig(TestUtils.createConsumerProperties(zkConnect, "group1", "smallConsumer",200));
		consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
		final StringDecoder decoder = new StringDecoder(new VerifiableProperties());
		topicMessageStreams = consumerConnector.createMessageStreams(ImmutableMap.of(TOPIC, 1), decoder, decoder).get(TOPIC).get(0);
	}

	@After
	public void tearDown(){
		// cleanup
		consumerConnector.shutdown();
		kafkaServer.shutdown();
		zkClient.close();
		zkServer.shutdown();
	}


}
