package mygamewishlist.model.scraping;

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

/**
 * @author Patryk
 *
 * Class used for webscraping GOG
 */
public class ScrapingGOG {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final String searchURL = "https://www.gog.com/games/ajax/filtered?mediaType=game&search=";
	
	/**
	 * Empty constructor for class initialization
	 */
	public ScrapingGOG() {}
	
	/**
	 * Returns a list of ScrapedGame's using data provided in the
	 * Game2Scrap object.
	 * 
	 * @param g2s Game2Scrap, used for creating the petition, and is
	 * forwarded to further functions.
	 * @return Hashtable<String,ArrayList<ScrapedGame>>
	 */
	public Hashtable<String,ArrayList<ScrapedGame>> getGOGGames(Game2Scrap g2s) {
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(searchURL + g2s.getName().replace(" ", "+"));		
		Hashtable<String,ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		try {
			// making the petition to GOG api
			JSONObject obj = new JSONObject((String) t.request().get(String.class));
			// getting the list of products
			JSONArray arr = obj.getJSONArray("products");
			
			// getting data from each product and saving it in the list
			for (int i = 0; i < arr.length(); i++) {
				ScrapedGame sg = getSearchedGame(arr.getJSONObject(i), g2s);
				
				if (sg != null) {
					games.add(sg);					
				}				
			}
		} catch (JSONException e) {
			LOG.logError(e.getStackTrace());
		}
		
		// add results to the hashtable and return it
		toReturn.put("GOG", games);
		
		return toReturn;
	}
	
	/**
	 * Returns data of the game stored in the JSONObject.
	 * 
	 * @param json JSONObject
	 * @param g2s Game2Scrap
	 * @return
	 * @throws JSONException
	 */
	private ScrapedGame getSearchedGame(JSONObject json, Game2Scrap g2s) throws JSONException {
		JSONObject price = json.getJSONObject("price");
		
		// checking if the game is free
		if ((Boolean)price.get("isFree")) {
			return null;
		}
		
		ScrapedGame sg = new ScrapedGame();
		
		// setting all of the values
		sg.setStoreName(g2s.getStoreName());
		sg.setFullName(json.getString("title"));
		sg.setUrlStore(g2s.getStoreUrl());
		sg.setUrlGame(json.getString("url"));
		sg.setImg("https:" + json.getString("image") + ".jpg");
				
		sg.setCurrentDiscount(price.getDouble("discountPercentage"));
		sg.setCurrentPrice(Double.parseDouble(price.getString("amount")));
		sg.setDefaultPrice(Double.parseDouble(price.getString("baseAmount")));
		
		return sg;
	}
	
	/**
	 * Returns current prices and discount of the game.
	 * 
	 * @param wlg WishLisgGame2Scrap, used for creating the petition.
	 * @return
	 */
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		ScrapedGame toReturn = new ScrapedGame();
		toReturn.setCurrentPrice(-1);		
		
		Document doc = null;
		try {
			doc = ScrapingFunctions.getDocCookie(wlg.getUrlStore() + wlg.getUrlGame(), "", "gog_lc", "ES_EUR_en-US");
		} catch (Exception e) {
			LOG.logError(e.getStackTrace());
		}
		
		// checking if doc isn't null
		if (doc == null) {
			return toReturn;
		}
		
		// setting all of the values
		try {
			toReturn.setDefaultPrice(Double.parseDouble(doc.select(".product-actions-price__base-amount").text()));		
			toReturn.setCurrentPrice(Double.parseDouble(doc.select(".product-actions-price__final-amount").text()));
		} catch(NumberFormatException e) {
			LOG.logError(e.getStackTrace());
			toReturn.setDefaultPrice(-1);
			return toReturn;
		}
		
		
		if (toReturn.getCurrentPrice() == toReturn.getDefaultPrice()) {
			toReturn.setCurrentDiscount(0);
		} else {
			toReturn.setCurrentDiscount(Math.round(
					(100 - toReturn.getCurrentPrice() * 100 / toReturn.getDefaultPrice()) * 100) / 100);			
		}
		
		return toReturn;
	}
}
