package com.nshimiye.messaging;

import java.io.Serializable;

public class Envelope implements Serializable {
	
	/**
	 * required by the akka remoting system
	 */
	private static final long serialVersionUID = 1L;
	
	public final String topic;
	public final Object payload;

	public Envelope(String topic, Object payload) {
		this.topic = topic;
		this.payload = payload;
	}
	
	@Override
	public String toString() {
		return String.format("Envelope[topic= %s, payload= %s]", topic, payload);
	}
}