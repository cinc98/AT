package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import agents.Collector;
import agents.Master;
import agents.PredictAgent;
import ws.WSEndPoint;

@Startup
@Singleton
public class Data {
	@EJB
	private WSEndPoint ws;

	private static ArrayList<AgentType> types = new ArrayList<>();
	private static ArrayList<AgentskiCentar> agentskiCentri = new ArrayList<>();
	private static HashMap<String, Agent> agents = new HashMap<>();
	public static List<Agent> agenti = new ArrayList<>();

	static {

		String currentIp = "";
		BufferedReader br = null;
		java.nio.file.Path p = Paths.get(".").toAbsolutePath().normalize();
		String line = "";

		try {
			br = new BufferedReader(new FileReader(p.toString() + "\\config.txt"));

			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentIp = sb.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		currentIp = currentIp.substring(2, currentIp.length() - 2);
		AgentskiCentar host = new AgentskiCentar("8080", currentIp);

		AgentType a = new AgentType();
		a.setModule("abc");
		a.setName("Ping");
		types.add(a);

		AgentType a1 = new AgentType();
		a1.setModule("abc");
		a1.setName("Pong");
		types.add(a1);

		AgentType a2 = new AgentType();
		a2.setModule("abc");
		a2.setName("Collector");

		AgentType a3 = new AgentType();
		a3.setModule("abc");
		a3.setName("PredictAgent");

		AgentType a4 = new AgentType();
		a4.setModule("abc");
		a4.setName("MasterAgent");

		Collector col = new Collector();
		col.setId(new AID("collector", host, a2));
		agenti.add(col);
		agents.put(col.getId().getName(), col);

		PredictAgent pa = new PredictAgent();
		pa.setId(new AID("predictor", host, a3));
		agenti.add(pa);
		agents.put(pa.getId().getName(), pa);

		Master m = new Master();
		m.setId(new AID("master", host, a4));
		agenti.add(m);
		agents.put(m.getId().getName(), m);

	}

	public ArrayList<AgentskiCentar> getAgentskiCentri() {
		return agentskiCentri;
	}

	public void setAgentskiCentri(ArrayList<AgentskiCentar> agentskiCentri) {
		this.agentskiCentri = agentskiCentri;
	}

	public ArrayList<AgentType> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<AgentType> tipovi) {
		this.types = tipovi;
	}

	public HashMap<String, Agent> getAgents() {
		return agents;
	}

	public void setAgents(HashMap<String, Agent> agenti) {
		this.agents = agenti;
	}

}
