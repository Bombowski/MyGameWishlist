package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.Function;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;

@Stateless
@LocalBean
public class ScrapingEJB {
	
	private MyLogger LOG = MyLogger.getLOG();
	private Hashtable<String, Function<String, ArrayList<ScrapedGame>>> scraping = 
			new Hashtable<String, Function<String, ArrayList<ScrapedGame>>>();
	
	public ScrapingEJB() throws IOException {
		scraping.put("https://store.steampowered.com/search/?term=", new ScrapingSteam()::getSteamGames);
		scraping.put("https://www.instant-gaming.com/en/", new ScrapingInstantGaming()::getInstantGames);
		scraping.put("https://www.g2a.com/", new ScrapingG2A()::getG2AGames);
	}
	
	public ArrayList<ScrapedGame> getGamesByNameUrl(String name, String storeUrl) {
		for (String key : scraping.keySet()) {
			if (key.equals(storeUrl)) {
				return scraping.get(key).apply(name);
			}
		}
		
		return new ArrayList<ScrapedGame>();
	}
	
	protected static String replaceSpaces(String str) {
		return str.replace(" ", "+");
	}
	
	protected static String[] splitSpacesReplaceCommasEuros(String price) {
		String prices[] = price.split(" ");

		for (int j = 0; j < prices.length; j++) {						
			prices[j] = prices[j].replace(",", ".");
			prices[j] = prices[j].replace("€", "");
		}
		
		return prices;
	}
}
