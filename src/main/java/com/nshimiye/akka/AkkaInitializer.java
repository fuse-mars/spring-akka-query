package com.nshimiye.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;

import com.nshimiye.cqrs.reader.akka.ReadWorker;

/**
 * Akka systems have built-in Event Bus called "EventStream"
 * 
 * @author mars Design followed
 *         https://lostechies.com/jimmybogard/files/2012/08/image4.png
 */
public class AkkaInitializer {

	private ActorRef readWorker = null;

	// only for subscription purposes
	private ActorRef denomalizer = null;
	
	// Akka configuration values
	private final String BROKER_URL = "akka.tcp://AKKAREMOTESystem@192.168.99.101:2555/user/brokerWorker";
	
	

	public void subscribeActors() {

		System.out.println("[subscribeActors]  starting subscription process");
		
		// Subscribe the reading worker
		if (this.denomalizer != null) {
			
			ActorSelection messageBrokerFinder =
					AkkaFactory.getActorSystem(SystemType.REMOTE)
					.actorSelection(BROKER_URL);
			
			// ask broker to subscribe denomalizer
			messageBrokerFinder.tell(denomalizer, denomalizer);
			
			System.out.println("[messageBrokerFinder] Actor reference: " + messageBrokerFinder.toString());
			System.out.println("[messageBrokerFinder] Actor path: " + messageBrokerFinder.pathString());
			System.out.println("[messageBrokerFinder] Actor path name: " + messageBrokerFinder.anchor());
	
		}

		if (this.readWorker != null) {
			System.out.println("[readWorker] initialized but not subscribed - "
							+ readWorker.path().toSerializationFormat());

			System.out.println("Actor reference: " + readWorker.toString());
			System.out.println("Actor path: " + readWorker.path().toString());
			System.out.println("Actor path address: "
					+ readWorker.path().address().toString());
			System.out.println("Actor path name: " + readWorker.path().name());
		}

	}
	
	/**
	 * here we initialize two actors
	 * The actor in charge of processing user queries
	 * Another actor for listening to events from command handling actor(remote)
	 * 		and then adding new entries to the data view
	 */
	public AkkaInitializer() {
		this.readWorker = AkkaFactory.getActorSystem(SystemType.LOCAL).actorOf(
				ReadWorker.createWorker(), "readWorker");
		
		this.denomalizer = AkkaFactory.getActorSystem(SystemType.REMOTE).actorOf(
				Props.create(DenomalizerWorker.class), "denomalizer");
	}
	
	
}
