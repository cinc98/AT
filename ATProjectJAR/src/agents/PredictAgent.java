package agents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ejb.EJB;

import jms.JMSQueue;
import model.ACLPoruka;
import model.AID;
import model.Agent;
import model.Data;
import model.Performative;

public class PredictAgent extends Agent {
	
	@EJB
	Data database;
	
	@Override
	public void handleMessage(ACLPoruka poruka) {
		System.out.println("Ko lektor has rcived message, well see what it does. " + poruka);
		if (poruka.getPerformative().equals(Performative.PREDICT)) {
			String s = null;
			String predict = "";
			try {

				String command = "python C:\\Users\\HP\\Desktop\\Fakultet\\AT\\AT\\predict.py " + poruka.getContent().split("-")[0] + " " + poruka.getContent().split("-")[1] + " "
						+ poruka.getContent().split("-")[2];
				Process p = Runtime.getRuntime().exec(command);

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				// read the output from the command
				// System.out.println("Here is the standard output of the command:\n");

				while ((s = stdInput.readLine()) != null) {
					// System.out.println(s);
					predict = s;
				}
				System.out.println("Vasa cena je : " + predict);

				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}

			} catch (IOException e) {
				System.out.println("exception happened - here's what I know: ");
				e.printStackTrace();
				System.exit(-1);
			}
			
			ACLPoruka aclPoruka = new ACLPoruka();
			aclPoruka.setReceivers(new AID[] { database.agenti.get(2).getId() });
			aclPoruka.setConversationId(poruka.getConversationId());
			aclPoruka.setPerformative(Performative.INFORM);
			aclPoruka.setContent(predict);
			new JMSQueue(aclPoruka);
		}
	}
}
