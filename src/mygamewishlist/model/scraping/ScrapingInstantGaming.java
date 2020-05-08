package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingInstantGaming {

	private static final MyLogger LOG = MyLogger.getLOG();
	
	public ScrapingInstantGaming() {}
	
	public Hashtable<String,ArrayList<ScrapedGame>> getInstantGames(Game2Scrap g2s) {
		Document doc = null;
		try {
			doc = ScrapingFunctions.getDoc(g2s.getStoreUrl() + g2s.getQueryPart(), g2s.getName());
		} catch (IOException e) {
			LOG.logError(e.getMessage());
			return new Hashtable<String,ArrayList<ScrapedGame>>();
		}		
		
		if (doc == null) {
			return new Hashtable<String,ArrayList<ScrapedGame>>();
		}		
		if (doc.select(".noresult-title").text()
				.equals("Sorry, we have no games matching your query :(")) {
			return new Hashtable<String, ArrayList<ScrapedGame>>();
		}
		
		Elements e = doc.selectFirst(".search").select(".item");
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		for (Element element : e) {
			ScrapedGame sg = getRow(element, g2s);
			
			if (sg != null) {
				games.add(sg);
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}
	
	private ScrapedGame getRow(Element e, Game2Scrap g2s) {
		String url = e.select(".cover").attr("href");
		url = url.substring(0, url.length() - 1);
		String img = e.select(".picture").attr("src");
		String name = e.select(".name").text();
		String priceS = e.select(".price").text();
		String discountS = e.select(".discount").text();
		
		if (priceS.equals("N/A")) {
			return null;
		}
		
		double discountD;
		double defaultP;
		double currentP = Double.parseDouble(ScrapingFunctions.replaceCommasEurosPercent(priceS));
		
		if (discountS.equals("")) {
			defaultP = currentP;
			discountD = 0;
		} else {
			discountD = Math.abs(Double.parseDouble(
					ScrapingFunctions.replaceCommasEurosPercent(discountS)));
			defaultP = currentP * 100 / (100 - discountD);
		}
		
		ScrapedGame sg = new ScrapedGame();
		sg.setUrlStore(g2s.getStoreUrl());
		sg.setUrlGame(url.substring(url.lastIndexOf("/")));
		sg.setImg(img);
		sg.setStoreName(g2s.getStoreName());
		sg.setFullName(name);
		sg.setDefaultPrice(defaultP);
		sg.setCurrentPrice(currentP);
		sg.setCurrentDiscount(discountD);
		
		return sg;
	}
	
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		Document doc = null;
		try {
			doc = ScrapingFunctions.getDoc(wlg.getUrlStore() + wlg.getUrlGame(), "");
		} catch (IOException e1) {
			LOG.logError(e1.getMessage().concat(" - IOException ScrapingInstantGaming"));
		}
		
		if (doc == null) {
			return new ScrapedGame();
		}
		
		Element e = doc.selectFirst(".prices");
		
		String discountS = e.select(".discount").text();
		
		double defaultD;
		double discountD;
		double priceD = Double.parseDouble(ScrapingFunctions.
				replaceCommasEurosPercent(e.select(".price").text()));
		
		if (discountS.equals("N/A")) {
			discountD = 0;
			defaultD = priceD;
		} else {
			discountD = Math.abs(Double.parseDouble(
					ScrapingFunctions.replaceCommasEurosPercent(discountS).replace("%", "")));
			defaultD = Double.parseDouble(e.select(".retail").attr("data-retail"));
		}
		
		ScrapedGame sc = new ScrapedGame();
		sc.setCurrentDiscount(discountD);
		sc.setCurrentPrice(priceD);
		sc.setDefaultPrice(defaultD);
		
		LOG.logDebug(sc.toString());
		
		return sc;
	}
}
