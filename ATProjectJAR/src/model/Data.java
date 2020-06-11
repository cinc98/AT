package model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import ws.WSEndPoint;

@Startup
@Singleton
public class Data {
	@EJB
	private WSEndPoint ws;
	
	private static ArrayList<AgentType> types = new ArrayList<>();
	private static ArrayList<AgentskiCentar> agentskiCentri = new ArrayList<>();
	private static HashMap<String, Agent> agents = new HashMap<>();
	
	static{
		
		
		AgentType a = new AgentType();
		a.setModule("abc");
		a.setName("test");
		types.add(a);
		
//		AID a1 = new AID();
//		a1.setName("prvi");
//		agents.put(a1,new Agent(new AID("A",null,null)));
//		agents.put(new AID(), new Agent(new AID("B",null,null)));
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
