package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpyderLeg
{
    private static final String USER_AGENT =
    		"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new ArrayList<String>();
    private List<Car> cars = new ArrayList<Car>();
    @SuppressWarnings("unused")
	private Document htmlDocument;

    public ArrayList<Car> crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200)                                                          
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return null;
            }
            Elements pricesOnPage = htmlDocument.select("span");
            Elements divsOnPage = htmlDocument.select("div");
           
            int numOfEl = 1;
            for(Element e : divsOnPage) {
            	if(e.className().equals("uk-width-medium-7-10 uk-width-7-10")) {
            		Elements divs = e.children();
            		int j = 0;
            		
            		while(j<divs.size()) {
            			if(divs.get(j).className().equals("inline-block")) {
            				try {
            					cars.add(new Car(Integer.parseInt(divs.get(j).text().split(" ")[0].replace('.', '\0').trim()),
            							Integer.parseInt(divs.get(j+1).text().split(" ")[0].replace(".", "").trim()),
            							Integer.parseInt(divs.get(j+3).text().split(" ")[0]),0));
            					numOfEl++;
            				}catch (Exception err) {
								break;
							}
            			}
            				
            			j+=6;
            		}
            	}
            }
            //System.out.println("AUTA IMA :"+ cars.size());
            int indOfCar =0 ;
            for(Element link : pricesOnPage)
            {            	
            	if(link.className().equals("price")) {
            		//System.out.println(link.text().split(" ")[0]);
            		this.links.add(link.text().split(" ")[0]);
            		
            		if(indOfCar == cars.size())
            			break;
            		this.cars.get(indOfCar).setPrice(Integer.parseInt(link.text().split(" ")[0].replace(".", "").trim()));
            		indOfCar++;
            	}
            		
            }
            
            return (ArrayList<Car>) cars;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return null;
        }
    }


  

}