Kafka Producer Example
=========

Kafka producer example is a (java) project to push events into a kafka topic.


Version
----

0.0.1-SNAPSHOT

Tech
-----------

Kafka producer example uses a number of open source projects to work properly:

* [Apache Zookeeper] - an open-source server which enables highly reliable distributed coordination
* [Apache Kafka] - a high-throughput distributed messaging system
* [Java] - huh?!

Prerequisites
--------------
* Zookeeper 3.4.5
* Kafka 0.8.1.1 (0.8.1.0 should be OK)
* Java 7+

Hortonworks Sandbox can be used, refer to http://hortonworks.com/hadoop/kafka/ for installing Kafka on it.

Settings
--------------
* When you set up your kafka, you should create a chroot folder for it in Zookeeper (note that you will have to provide the chroot folder name to all consumers then, eg. "localhost:9092/kafka"). To do so:
    * Stop Kafka
    * Edit <KAFKA_HOME>/config/server.properties: append "/kafka" (or any chroot folder name) to the urls to specify the kafka znodes (property "zookeeper.connect"), e.g. 
```sh
zookeeper.connect=localhost:2181/kafka
```
    * Create the chroot folder in zookeeper
```sh
    <ZOOKEPER_HOME>/bin/zkCli.sh -server <list of zookeeper nodes, e.g. localhost:2181>
    ls /
    #[zookeeper ...]
    create /kafka '' # create /<chroot folder name> ''
    ls /
    #[kafka, zookeeper ...]
```
    * Start kafka
* Create the kafka topic where you will push your data (by default proto.yogurts) !
```sh
<KAFKA_HOME>/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic proto.yogurts
```

Usage
--------------
The paragraph below assumes that kafka is running on the same machine (localhost) with default port (9092) and the topic name is "proto-yogurts". These settings can be changed respectively in files config/kafka.properties and kafka/yogurt.properties (see §Configure the producer below)

####Unix
```sh
git clone https://github.com/jaguiar/kafka-producer-example.git
cd kafka-producer-example
mvn clean package
cd target
tar xvzf kafka-producer-example-*-bin.tar.gz
cd kafka-producer-example-*/bin
./yoghurtsProducer
```
####Windows 

```dos
mvn clean package
```
unzip the  file ./target/kafka-producer-example-*-bin.zip

```dos
cd kafka-producer-example-*/bin
./yoghurtsProducer.bat
```

##### Configure the producer
* Kafka: kafka-producer-example-*/config/kafka.properties - set general kafka properties like kafka brokers list (default is localhost:9092), serializer to use...
* Main program: kafka-producer-example-*/config/yogurt.properties (for producing yogurts)
* Logs: kafka-producer-example-*/config/logback.xml

#### Check the produced messages
Go to kafka "bin" folder.

```sh
./kafka-console-consumer.sh --zookeeper localhost:2181/kafka --topic proto.yogurts --from-beginning
```
Again, this command assumes that:
* Zookeeper is running on the same machine (localhost) with the default port (2181)
* kafka uses a chroot folder ("kafka") in Zookeeper
* topic name is proto.yogurts

Troubleshooting
--------------
If you encounter the following error
> 17:14:23.989 [main] ERROR kafka.producer.SyncProducer - Producer connection to xxxx:9092 unsuccessful
> 
java.net.ConnectException: Connection refused: connect
> 
at sun.nio.ch.Net.connect0(Native Method) ~[na:1.7.0_67]
> 
at sun.nio.ch.Net.connect(Unknown Source) ~[na:1.7.0_67]
> 
at sun.nio.ch.Net.connect(Unknown Source) ~[na:1.7.0_67]
> 
at sun.nio.ch.SocketChannelImpl.connect(Unknown Source) ~[na:1.7.0_67]
>
at kafka.network.BlockingChannel.connect(BlockingChannel.scala:57) ~[kafka_2.10-0.8.1.1.jar:na]
> 
at kafka.producer.SyncProducer.connect(SyncProducer.scala:141) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.SyncProducer.getOrMakeConnection(SyncProducer.scala:156) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.SyncProducer.kafka$producer$SyncProducer$$doSend(SyncProducer.scala:68) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.SyncProducer.send(SyncProducer.scala:112) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.client.ClientUtils$.fetchTopicMetadata(ClientUtils.scala:53) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.BrokerPartitionInfo.updateInfo(BrokerPartitionInfo.scala:82) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.async.DefaultEventHandler$$anonfun$handle$2.apply$mcV$sp(DefaultEventHandler.scala:78) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.utils.Utils$.swallow(Utils.scala:167) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.utils.Logging$class.swallowError(Logging.scala:106) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.utils.Utils$.swallowError(Utils.scala:46) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.async.DefaultEventHandler.handle(DefaultEventHandler.scala:78) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.producer.Producer.send(Producer.scala:76) [kafka_2.10-0.8.1.1.jar:na]
>
at kafka.javaapi.producer.Producer.send(Producer.scala:33) [kafka_2.10-0.8.1.1.jar:na]
>
at org.yaourtcorp.kafka.producer.example.EventsProducer.execute(EventsProducer.java:66) [kafka-producer_example-0.0.1-SNAPSHOT.jar:na]
>
at org.yaourtcorp.kafka.producer.example.EventsProducer.main(EventsProducer.java:43) [kafka-producer-example-0.0.1-SNAPSHOT.jar:na]

* #####check your hosts file ! (Add a line in your host file to map the server hostname with its IP).
    * Windows: C:\WINDOWS\system32\drivers\etc\hosts
    * Linux: /etc/hosts
* check that your zookeper and kafka are running
* check your kafka and zookeeper ports !

Notes
--------------
Ok, I admit it, since I wanted to play with some new features of Spring 4+ (like [@Conditional]), this project is just completely overkill. 

**Free example, Hell Yeah! ;-P**

[Apache Zookeeper]:http://zookeeper.apache.org/
[Apache Kafka]:http://kafka.apache.org/
[Java]:https://www.java.com/
[@Conditional]:http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html

