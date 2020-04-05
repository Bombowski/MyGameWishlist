package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mygamewishlist.model.ejb.ScrapingEJB;
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
			doc = ScrapingEJB.getDoc(g2s.getUrl(), g2s.getName());
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
			ScrapedGame sg = getRow(element, g2s.getStoreName());
			
			if (sg != null) {
				games.add(sg);
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}
	
	private ScrapedGame getRow(Element e, String storeName) {
		String url = e.select(".cover").attr("href");
		String img = e.select(".picture").attr("src");
		String name = e.select(".name").text();
		String priceS = e.select(".price").text();
		String discountS = e.select(".discount").text();
		
		if (priceS.equals("N/A")) {
			return null;
		}
		
		double discountD;
		double defaultP;
		double currentP = Double.parseDouble(ScrapingEJB.replaceCommasEurosPercent(priceS));
		
		if (discountS.equals("")) {
			defaultP = currentP;
			discountD = 0;
		} else {
			discountD = Math.abs(Double.parseDouble(discountS.replace("%", "")));
			defaultP = currentP * 100 / (100 - discountD);
		}
		
		ScrapedGame sg = new ScrapedGame();
		sg.setUrl(url);
		sg.setImg(img);
		sg.setStoreName(storeName);
		sg.setFullName(name);
		sg.setDefaultPrice(defaultP);
		sg.setCurrentPrice(currentP);
		sg.setCurrentDiscount(discountD);
		
		return sg;
	}
	
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		
		
		return null;
	}
}
