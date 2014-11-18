package org.yaourtcorp.kafka.producer.example.yogurt;

import org.joda.time.DateTime;
import org.yaourtcorp.kafka.producer.example.EventObject;

public class Yogurt implements EventObject {

	private String uuid;
	private String productName;
	private int manufacturerId;
//	private Long energyInFor100g; 
//	private Long saturatedFatFor100g; 
//	private Long sugarsFor100g;
//	private Long numberOfAdditives;
	private DateTime creationTime;

	public Yogurt() {
		super();
	}

	public Yogurt(String uuid, String productName, int manufacturerId, DateTime creationTime) {
		this();
		this.uuid = uuid;
		this.productName = productName;
		this.manufacturerId = manufacturerId;
		this.creationTime = creationTime;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	public String getProductName() {
		return productName;
	}


	public int getManufacturerId() {
		return manufacturerId;
	}

	public DateTime getCreationTime() {
		return creationTime;
	}

}
