package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spyder {
	private static final int MAX_PAGES_TO_SEARCH = 10;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new ArrayList<String>();
    
    
    public ArrayList<String> search(String url, String searchWord)
    {
        while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpyderLeg leg = new SpyderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl = this.nextUrl();
            }
            this.pagesToVisit =leg.crawl(currentUrl); 
            boolean success = leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            //this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
		return (ArrayList<String>) pagesToVisit;
    }
    private String nextUrl(){
        String nextUrl;
        do
        {
            nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));
        
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}

