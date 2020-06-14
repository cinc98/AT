package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spyder {
	private static final int MAX_PAGES_TO_SEARCH = 10;
    private List<Car> prices = new ArrayList<Car>();
    
    
    public ArrayList<Car> search(String year_from,String year_to,String p1,String p2)
    {
    	int i = 1;
        while(i <3)
        { 
            String currentUrl= "https://www.polovniautomobili.com/auto-oglasi/pretraga??page="+i+"&year_from=" + year_from + "&year_to="
    				+ year_to + "&price_from=" + p1 + "&price_to=" + p2;
            SpyderLeg leg = new SpyderLeg();
            List<Car> ret = leg.crawl(currentUrl);
            if(prices.size()==0) {
            	this.prices =ret; 
            }else {
            	for(Car s : ret) {
            		this.prices.add(s);
            	}
            }           
            i++;
        }
        return (ArrayList<Car>) prices;
    }
   
}

