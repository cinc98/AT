package agents;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import jms.JMSQueue;
import model.ACLPoruka;
import model.AID;
import model.Agent;
import model.Performative;

public class Master extends Agent {

	@Override
	public void handleMessage(ACLPoruka poruka) {
		System.out.println("master has rcived message, well see what it does. " + poruka);
		System.out.println("vasa cena je " + poruka.getContent());
		
		try (PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\predict.txt")) {
			System.out.println("Upisujem");
			out.println(poruka.getContent());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
