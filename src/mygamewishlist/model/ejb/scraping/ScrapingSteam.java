package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;

public class ScrapingSteam {

	private String url;
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final int STEAM_ID = 1; 
	
	protected ScrapingSteam() {
		url = "https://store.steampowered.com/search/?term=";
	}
	
	protected ArrayList<ScrapedGame> getSteamGames(String name){
		Document doc = null;
		try {
			doc = Jsoup.connect(new StringBuilder()
						.append(url)
						.append(name)
						.toString()
					)
					.get();
		} catch (IOException e) {
			LOG.logError(e.getMessage());
			return new ArrayList<ScrapedGame>();
		}
		
		if (doc.select("#search_result_container").text().equals("No results were returned for that query.")) {
			return new ArrayList<ScrapedGame>();
		}
		
		Elements ele = doc.select(".search_result_row");
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		
		for (int i = 0; i < ele.size(); i++) {
			ScrapedGame sc = getRow(ele.get(i), i);
			if (sc != null) {
				games.add(sc);
			}
		}
		
		return games;
	}
	
	private ScrapedGame getRow(Element ele, int i) {
		String img;
		String url;
		String fullName;
		double defaultP;
		double currentP;
		double discount;
		
		img = ele.select("img").attr("src");
		url = ele.attr("href");
		fullName = ele.select(".title").text();
		discount = 0;
		
		String price = ele.select(".search_price").text();
		
		if (price.equals("")) {
			return null;
		} else if (price.toUpperCase().equals("FREE TO PLAY") || price.equals("Free")) {
			currentP = defaultP = 0;
		} else if(price.equals("Free Demo")) {
			currentP = defaultP = -1;
		} else {
			String[] prices = ScrapingEJB.splitSpacesReplaceCommasEuros(price);
			
			if (prices.length == 2) {
				defaultP = Double.parseDouble(prices[0]);
				currentP = Double.parseDouble(prices[1]);
				discount = Double.parseDouble(ele
						.select(".search_discount")
						.text()
						.replace("%", ""));
			} else {
				currentP = defaultP = Double.parseDouble(prices[0]);
			}
		}
		
		return new ScrapedGame(url, fullName, img, STEAM_ID, defaultP, currentP, discount);
	}
}
