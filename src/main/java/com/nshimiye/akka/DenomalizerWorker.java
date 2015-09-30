package com.nshimiye.akka;

import com.nshimiye.domain.Database;
import com.nshimiye.domain.Spending;
import com.nshimiye.messaging.Envelope;

import akka.actor.UntypedActor;

/**
 * Listens to events published by the writing actors and then read the newly
 * saved value from Data store (writing side) to Data View (reading side)
 * 
 * @author mars
 *
 */
public class DenomalizerWorker extends UntypedActor {

	@Override
	public void onReceive(Object message) {
		if (message instanceof Envelope) {
			//
			System.out.println("[ Denomalizer ] received an envelop");
			
			System.out.println(((Envelope) message).toString());
			Spending payload =
			(Spending) ((Envelope) message).payload;
			
			// read data from data store
			// TODO use an actual persistent data store instead of in memory
			// TODO compare data in db with the payload
			Spending spending = payload;
//					Database.read(payload.getId());
			
			
			// write this data into the data view
			if(spending != null){
				Spending newSpending = 
						new com.nshimiye.domain.Spending(
								spending.getId(), 
								spending.getName(), 
								spending.getAmount());
				
				Database.write(newSpending);
			}

			
			System.out.println("[ Denomalizer ] done writing to read db");

		} else if (message instanceof String) {
			System.out.println("[ Denomalizer ] received a string message");
			
			System.out.println("[ Denomalizer ] message = " + (String) message);
			
		} else {
			System.err.println("[ Denomalizer ] received unknown message "+ message.toString());
			unhandled(message);
		}

	}
}
