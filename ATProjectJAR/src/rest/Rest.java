package rest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.AID;
import model.Agent;
import model.AgentType;
import model.AgentskiCentar;
import model.Data;
import model.IAgent;
import model.Performative;
import model.Spyder;
import ws.WSEndPoint;

@LocalBean
@Path("")
public class Rest {
	@EJB
	Data database;

	@EJB
	WSEndPoint ws;
	
	@GET
	@Path("/search/{brand}/{priceFrom}/{priceTo}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> search(@PathParam(value = "brand") String brand, @PathParam(value = "priceFrom") String p1,
			@PathParam(value = "priceTo") String p2) {
		Spyder spider = new Spyder();
		String url = "https://www.polovniautomobili.com/auto-oglasi/pretraga?brand="+brand+"&price_from="+p1+"&price_to="+p2;
		System.out.println(url);
		List<String> prices = new ArrayList<String>();
		prices = spider.search(url, "audi");
		return (ArrayList<String>) prices;
	}

	// GET/messages � dobavi listu performativa
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

	// GET /agents/classes � dobavi listu svih tipova agenata na sistemu
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getTipovi() {
		System.out.println("TIPOVI---" + database.getTypes());
		return database.getTypes();
	}

	// GET /agents/running � dobavi sve pokrenute agente sa sistema
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<IAgent> getAgents() {

		return new ArrayList<>(database.getAgents().values());
	}

	// DELETE /agents/running/{aid} � zaustavi odredjenog agenta
	@DELETE
	@Path("/agents/running/{aid}")
	public void stopAgent(@PathParam("aid") String aid) {

		HashMap<String, Agent> agenti = database.getAgents();
		for (String a : agenti.keySet()) {
			if (a.equals(aid)) {
				database.getAgents().remove(a);
				break;
			}
		}

	}

	// PUT /agents/running/{type}/{name} � pokreni agenta odredenog tipa sa zadatim
	// imenom;
	@PUT
	@Path("/agents/running/{type}/{name}")
	public void startAgent(@PathParam("type") String type, @PathParam("name") String name) {
		AgentskiCentar host = new AgentskiCentar("localhost", "8080");

		AID aid = new AID(name, host, new AgentType(type, null));
		Agent agent = new Agent(aid);
		database.getAgents().put(aid.getName(), agent);
		ObjectMapper mapper = new ObjectMapper();
		String msg=null;
		try {
			msg = mapper.writeValueAsString(agent);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ws.echoTextMessage(msg);
		String currentIp;
		InetAddress ip = null ;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        currentIp = ip.toString();
	    String[] split = currentIp.split("/");
	    currentIp=split[1];
	    String[] split2 = currentIp.split("/n");
	    currentIp=split2[0];
	  //Ulogovao se novi , treba da se javi svim hostovima
	    for(AgentskiCentar at : database.getAgentskiCentri()) {
			if(at.getAddress().equals(currentIp))
				continue;			
			ResteasyClient client2 = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget2 = client2.target("http://7cbf1630576a.ngrok.io/ATProjectWAR/rest/node/host/node");
			Response response2 = rtarget2.request(MediaType.APPLICATION_JSON).post(Entity.entity(host,MediaType.APPLICATION_JSON));
		}
	}

}
