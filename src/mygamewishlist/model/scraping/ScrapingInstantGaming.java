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

/**
 * @author Patryk
 *
 * Function used for web scraping Instant Gaming
 */
public class ScrapingInstantGaming {

	private static final MyLogger LOG = MyLogger.getLOG();
	
	/**
	 * Empty constructor used just for initialization of the class
	 */
	public ScrapingInstantGaming() {}
	
	/**
	 * Function that returns a list of Instant gaming games, by
	 * data provided in the Game2Scrap object.
	 * 
	 * @param g2s Game2Scrap, used for creating a petition, and is
	 * forwarded to the next function
	 * @return Hashtable<String,ArrayList<ScrapedGame>>
	 */
	public Hashtable<String,ArrayList<ScrapedGame>> getInstantGames(Game2Scrap g2s) {
		Document doc = null;
		try {
			// get the document
			doc = ScrapingFunctions.getDoc(g2s.getStoreUrl() + g2s.getQueryPart(), g2s.getName());
		} catch (IOException e) {
			LOG.logError(e.getMessage());
			return new Hashtable<String,ArrayList<ScrapedGame>>();
		}		
		
		// check if document contains something
		if (doc == null) {
			return new Hashtable<String,ArrayList<ScrapedGame>>();
		} else if (doc.select(".noresult-title").text()
				.equals("Sorry, we have no games matching your query :(")) {
			return new Hashtable<String, ArrayList<ScrapedGame>>();
		}
		
		// select the elements
		Elements e = doc.selectFirst(".search").select(".item");
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		// get game from each element
		for (Element element : e) {
			ScrapedGame sg = getSearchedGame(element, g2s);
			
			if (sg != null) {
				games.add(sg);
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}
	
	/**
	 * Gets and returns all of the game data from Element.
	 * 
	 * @param e Element, contains all of the game data
	 * @param g2s Game2Scrap, used for getting store information
	 * @return ScrapedGame
	 */
	private ScrapedGame getSearchedGame(Element e, Game2Scrap g2s) {
		// set all of the data
		String url = e.select(".cover").attr("href");
		url = url.substring(0, url.length() - 1);
		String img = e.select(".picture").attr("src");
		String name = e.select(".name").text();
		String priceS = e.select(".price").text();
		String discountS = e.select(".discount").text();
		
		// check if price is valid
		if (priceS.equals("N/A")) {
			return null;
		}
		
		double discountD;
		double defaultP;
		double currentP = Double.parseDouble(ScrapingFunctions.replaceCommasEurosPercent(priceS));
		
		// if price is valid, check discount and set defualt price
		if (discountS.equals("")) {
			defaultP = currentP;
			discountD = 0;
		} else {
			discountD = Math.abs(Double.parseDouble(
					ScrapingFunctions.replaceCommasEurosPercent(discountS)));
			defaultP = currentP * 100 / (100 - discountD);
		}
		
		// set everything in the ScrapedGame object
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
	
	/**
	 * Returns current price and discount of the game.
	 * 
	 * @param wlg WishListGame2Scrap, used for creating the petition
	 * @return ScrapedGame
	 */
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		ScrapedGame toReturn = new ScrapedGame();
		// get the document
		Document doc = null;
		try {
			LOG.logDebug(wlg.toString());
			doc = ScrapingFunctions.getDoc(wlg.getUrlStore() + wlg.getUrlGame(), "");
		} catch (IOException e1) {
			LOG.logError(e1.getMessage().concat(" - IOException ScrapingInstantGaming"));
			
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement ste : e1.getStackTrace()) {
				sb.append(ste.toString());
			}
			LOG.logError(sb.toString());
		}
		
		// if document is null, reutrn ScrapedGame with negative price
		if (doc == null) {
			toReturn.setCurrentPrice(-1);
			return toReturn;
		}
		
		// select the element
		Element e = doc.selectFirst(".prices");
		
		// get prices and discount
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
		
		// set prices and discount into ScrapedGame
		ScrapedGame sc = new ScrapedGame();
		sc.setCurrentDiscount(discountD);
		sc.setCurrentPrice(priceD);
		sc.setDefaultPrice(defaultD);
		
		LOG.logDebug(sc.toString());
		
		return sc;
	}
}
