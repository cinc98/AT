package rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.AID;
import model.AgentType;
import model.AgentskiCentar;
import model.Data;
import model.IAgent;
import model.Performative;

@LocalBean
@Path("")
public class Rest {
	@EJB
	Data database;
	
	// GET/messages – dobavi listu performativa
	@GET
	@Path("/messages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Performative> getPerformative() {
		ArrayList<Performative> temp = new ArrayList<Performative>();
		for (Performative p : Performative.values()) {
			temp.add(p);
		}
		return temp;
	}
	// GET /agents/classes – dobavi listu svih tipova agenata na sistemu
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getTipovi() {
		System.out.println("TIPOVI---"+database.getTypes());
		return database.getTypes();
	}
	// GET /agents/running – dobavi sve pokrenute agente sa sistema
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<IAgent> getAgents() {
		return new ArrayList<>(database.getAgents().values());
	}
	//DELETE /agents/running/{aid} – zaustavi odredjenog agenta
	@DELETE
	@Path("/agents/running/{aid}")
	public void stopAgent(@PathParam("aid") String aid) {
			
		HashMap<AID, IAgent> agenti =database.getAgents(); 
		for(AID a : agenti.keySet()) {
			if(a.getName().equals(aid)) {
				database.getAgents().remove(a);
				break;
			}
		}		
			 
	}

	
	
	
}
