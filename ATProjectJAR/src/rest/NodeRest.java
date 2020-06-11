package rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.AID;
import model.Agent;
import model.AgentType;
import model.AgentskiCentar;
import model.Data;
import model.IAgent;


@Singleton
@Startup
@Path("/node")
public class NodeRest {
	
	@EJB
	Data database;
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public void notifyMaster(AgentskiCentar a) {
		System.out.println("Ja sam master i registrujem novi agentski centar");
		database.getAgentskiCentri().add(a);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget rtarget = client.target("http://" + a.getAddress() + ":8080/ATProjectWAR/rest/node/host/nodes");
		Response response = rtarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(database.getAgentskiCentri(),MediaType.APPLICATION_JSON));
		
		for(AgentskiCentar at : database.getAgentskiCentri()) {
			if(at.getAddress().equals(a.getAddress()))
				continue;			
			ResteasyClient client2 = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget2 = client2.target("http://" + at.getAddress() + ":8080/ATProjectWAR/rest/node/host/node");
			Response response2 = rtarget2.request(MediaType.APPLICATION_JSON).post(Entity.entity(a,MediaType.APPLICATION_JSON));
		}
		ResteasyClient client3 = new ResteasyClientBuilder().build();
		ResteasyWebTarget rtarget3 = client3.target("http://" + a.getAddress() + ":8080/ATProjectWAR/rest/node/agents/classes");
		Response response3 = rtarget3.request(MediaType.APPLICATION_JSON).get();		
		List<AgentType> list =  response3.readEntity(ArrayList.class);
		System.out.println("Primio sam nove tipove" + list);
		
		for(AgentskiCentar at : database.getAgentskiCentri()) {
			if(at.getAddress().equals(a.getAddress()))
				continue;			
			ResteasyClient client4 = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget4 = client4.target("http://" + at.getAddress() + ":8080/ATProjectWAR/rest/node/agents/classes");
			Response response4 = rtarget4.request(MediaType.APPLICATION_JSON).post(Entity.entity(list,MediaType.APPLICATION_JSON));
		}
		int num = 1;
		while(num !=3) {
		ResteasyClient client5 = new ResteasyClientBuilder().build();
		ResteasyWebTarget rtarget5 = client5.target("http://" + a.getAddress() + ":8080/ATProjectWAR/rest/node/agents/running");
		Response response5 = rtarget5.request(MediaType.APPLICATION_JSON).post(Entity.entity(database.getAgentskiCentri(),MediaType.APPLICATION_JSON));
			if(Response.Status.OK.ordinal()==response3.getStatus()) {
				break;
			}
		}
		
		if(num == 3) {
			for(AgentskiCentar at : database.getAgentskiCentri()) {
				if(at.getAddress().equals(a.getAddress()))
					continue;			
				ResteasyClient client6 = new ResteasyClientBuilder().build();
				ResteasyWebTarget rtarget6 = client6.target("http://" + at.getAddress() + ":8080/ATProjectWAR/rest/node/host/node"+a.getAddress());
				Response response6 = rtarget6.request(MediaType.APPLICATION_JSON).delete();
			}
		}
		
		
		
	}
	
	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public void notifyAll(AgentskiCentar a) {
		System.out.println("Ja sam ne-master cvor i primam informaciju da je novi registrovan");
		database.getAgentskiCentri().add(a);
		return;
	}
	@POST
	@Path("/nodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void getNoMaster(ArrayList<AgentskiCentar> agents){
		System.out.println("Trenutno sam u novom ne-masteru i primam nove cvorove");
		for(AgentskiCentar a : agents)
			database.getAgentskiCentri().add(a);
		return ;		
	}
	@GET
	@Path("/node")
	public ResponseBuilder turnOffNode() {
		return Response.ok();
	}
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AgentType> getAgents() {
		System.out.println("Trenutno sam u ne-masteru i vracam sve tipove");
		return database.getTypes();
	}
	@POST
	@Path("/agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void receiveAgents(ArrayList<AgentType> agents){
		System.out.println("Trenutno sam u novom ne-masteru i primam nove tipove koje mi je master poslao");
		for(AgentType a : agents)
			database.getTypes().add(a);
		return;		
	}
	@POST
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void loggedIn(HashMap<String, Agent> runningAgents) {
		System.out.println("Sada sam u novom ne-masteru i dobijam spisak pokrenutih agenata");
		database.setAgents(runningAgents);
		return;
	}
	
	@DELETE
	@Path("/node/{alias}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteNode(@PathParam("alias") String alias) {
		System.out.println("Sada sam u ne-master cvoru i primio sam poruku koji je cvor neaktivan");
		for(AgentskiCentar a :database.getAgentskiCentri()) {
			if(a.getAddress() == alias) {
				database.getAgentskiCentri().remove(a);
				break;
			}	
		}
	}
	    
		

}
