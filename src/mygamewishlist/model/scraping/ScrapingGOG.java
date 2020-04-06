package mygamewishlist.model.scraping;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		JSONObject obj = t.request().get(JSONObject.class);
		Hashtable<String,ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		try {
			JSONArray arr = obj.getJSONArray("products");
			
			for (int i = 0; i < arr.length(); i++) {
				ScrapedGame sg = getRow(arr.getJSONObject(i), g2s.getStoreName());
				
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
	
	private ScrapedGame getRow(JSONObject json, String storeName) throws JSONException {
		ScrapedGame sg = new ScrapedGame();
		
		sg.setStoreName("");
		sg.setFullName(json.getString("title"));
		sg.setUrl(json.getString("url"));
		sg.setImg(json.getString("image"));
		
		JSONObject price = json.getJSONObject("price");
		sg.setCurrentDiscount(price.getDouble("discountPercentage"));
		sg.setCurrentPrice(Double.parseDouble(json.getString("amount")));
		sg.setDefaultPrice(Double.parseDouble(json.getString("baseAmount")));
		
		return sg;
	}
	
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		
		
		return null;
	}
}
