package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingGOG {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final String searchURL = "https://www.gog.com/games/ajax/filtered?mediaType=game&search=";
	
	public ScrapingGOG() {}
	
	public Hashtable<String,ArrayList<ScrapedGame>> getGOGGames(Game2Scrap g2s) {
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(searchURL + g2s.getName());		
		Hashtable<String,ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		try {
			JSONObject obj = new JSONObject((String) t.request().get(String.class));
			JSONArray arr = obj.getJSONArray("products");
			
			for (int i = 0; i < arr.length(); i++) {
				ScrapedGame sg = getRow(arr.getJSONObject(i), g2s.getStoreName(), g2s.getUrl());
				
				if (sg != null) {
					games.add(sg);
				}
			}
		} catch (JSONException e) {
			LOG.logError(e.getMessage());
		}
		
		toReturn.put("GOG", games);
		
		return toReturn;
	}
	
	private ScrapedGame getRow(JSONObject json, String storeName, String url) throws JSONException {
		ScrapedGame sg = new ScrapedGame();
		
		sg.setStoreName(storeName);
		sg.setFullName(json.getString("title"));
		sg.setUrl(url + json.getString("url"));
		sg.setImg("https:" + json.getString("image") + ".jpg");
		
		JSONObject price = json.getJSONObject("price");
		sg.setCurrentDiscount(price.getDouble("discountPercentage"));
		sg.setCurrentPrice(Double.parseDouble(price.getString("amount")));
		sg.setDefaultPrice(Double.parseDouble(price.getString("baseAmount")));
		
		return sg;
	}
	
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		Document doc = null;
		try {
			doc = ScrapingFunctions.getDoc(wlg.getUrlStore() + wlg.getUrlGame(), "");
		} catch (IOException e) {
			 LOG.logError(e.getMessage());
		}
		
		if (doc == null) {
			return null;
		}
		
		ScrapedGame sc = new ScrapedGame();		
		
		sc.setDefaultPrice(Double.parseDouble(doc.select(".product-actions-price__base-amount").text()));		
		sc.setCurrentPrice(Double.parseDouble(
				doc.select(".product-actions-price__final-amount").text()));
		
		if (sc.getCurrentPrice() == sc.getDefaultPrice()) {
			sc.setCurrentDiscount(0);
		} else {
			sc.setCurrentDiscount(Math.round(
					(100 - sc.getCurrentPrice() * 100 / sc.getDefaultPrice()) * 100) / 100);			
		}
		
		return sc;
	}
}
