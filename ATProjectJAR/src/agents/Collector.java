package agents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import jms.JMSQueue;
import model.ACLPoruka;
import model.AID;
import model.Agent;
import model.Car;
import model.Performative;
import model.SpyderLeg;

public class Collector extends Agent {
	private List<Car> prices = new ArrayList<Car>();

	public ArrayList<Car> search(String year_from, String year_to, String p1, String p2) {
		prices.clear();
		int i = 1;
		while (i < 3) {
			String currentUrl = "https://www.polovniautomobili.com/auto-oglasi/pretraga??page=" + i + "&year_from="
					+ year_from + "&year_to=" + year_to + "&price_from=" + p1 + "&price_to=" + p2;
			SpyderLeg leg = new SpyderLeg();
			List<Car> ret = leg.crawl(currentUrl);
			if (prices.size() == 0) {
				this.prices = ret;
			} else {
				for (Car s : ret) {
					this.prices.add(s);
				}
			}
			i++;
		}
		return (ArrayList<Car>) prices;
	}
	
	@Override
	public void handleMessage(ACLPoruka poruka){
		System.out.println("Ko lektor has rcived message, well see what it does. " + poruka);
		if (poruka.getPerformative().equals(Performative.SEARCH)) {
			List<Car> prices = new ArrayList<Car>();
			prices = search(poruka.getContent().split("-")[0], poruka.getContent().split("-")[1], poruka.getContent().split("-")[2], poruka.getContent().split("-")[3]);
			System.out.println("Ende" + prices);

			ObjectMapper mapper = new ObjectMapper();
			String msg = null;
			try {
				msg = mapper.writeValueAsString(prices);
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
			
			try (PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\data.json")) {
				out.println(msg);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
