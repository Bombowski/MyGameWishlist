package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.Cookie;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingSteam {

	private static final MyLogger LOG = MyLogger.getLOG(); 
	
	protected ScrapingSteam() {}
	
	protected Hashtable<String, ArrayList<ScrapedGame>> getSteamGames(Game2Scrap g2s) {
		Document doc = null;
		try {
			doc = ScrapingEJB.getDoc(g2s.getUrl(), g2s.getName());
		} catch (IOException e) {
			LOG.logError(e.getMessage());
		}
		
		if (doc == null) {
			return new Hashtable<String, ArrayList<ScrapedGame>>();
		}		
		if (doc.select("#search_result_container").text().equals("No results were returned for that query.")) {
			return new Hashtable<String, ArrayList<ScrapedGame>>();
		}
		
		Elements ele = doc.select(".search_result_row");
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		
		for (int i = 0; i < ele.size(); i++) {
			ScrapedGame sc = getRow(ele.get(i), g2s.getStoreName());
			
			if (sc != null) {
				games.add(sc);
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}
	
	protected ScrapedGame getGame(WishListGame2Scrap wlg) {
		Document doc = null;
		try {
			doc = ScrapingEJB.getDoc(wlg.getStoreUrl() + wlg.getGameUrl(), wlg.getGameName(), 
					new Cookie("birthtime", "568022401"));
		} catch (IOException e) {
			LOG.logError(e.getMessage());
		}
		
		if (doc == null) {
			return new ScrapedGame();
		}
		
		Element ele = doc.selectFirst(".game_purchase_action_bg");
		ScrapedGame tmp = new ScrapedGame();		

		String notSale = ele.select(".game_purchase_price").text();
		
		if (notSale.equals("")) {		
			String current = ScrapingEJB.splitSpacesReplaceCommasEuros(
					ele.select(".discount_final_price").text())[0];		
			String original = ScrapingEJB.splitSpacesReplaceCommasEuros(
					ele.select(".discount_original_price").text())[0];
			String discount = ele.select(".discount_pct").text().replace("%", "");
			
			tmp.setCurrentDiscount(
					Math.abs(Double.parseDouble(ScrapingEJB.ifnull0(discount))));
			tmp.setDefaultPrice(
					Double.parseDouble(ScrapingEJB.ifnull0(original)));
			tmp.setCurrentPrice(
					Double.parseDouble(ScrapingEJB.ifnull0(current)));
		} else {
			tmp.setCurrentPrice(
					Double.parseDouble(ScrapingEJB.splitSpacesReplaceCommasEuros(notSale)[0]));
			tmp.setDefaultPrice(tmp.getCurrentPrice());
			tmp.setCurrentDiscount(0);
		}
		
		return tmp;
	}
	
	private ScrapedGame getRow(Element ele, String storeName) {
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
		
		return new ScrapedGame(url, fullName, img, storeName, defaultP, currentP, discount);
	}

}