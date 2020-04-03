package mygamewishlist.model.ejb.scraping;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.WishListGame;

/**
 * @author Patryk
 *
 * temporal steam REST testing
 */
public class Steam {

	public ArrayList<SteamGame> getSteamGames() throws JSONException {
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target("https://api.steampowered.com/ISteamApps/GetAppList/v2?key=AD1DE1B4CEB1383A64F09E85786275A8");
		JSONObject jo = new JSONObject((String)t.request().get(String.class));
		JSONObject applist = (JSONObject)jo.get("applist");
		JSONArray jarr = applist.getJSONArray("apps");
		
		ArrayList<SteamGame> games = new ArrayList<SteamGame>();
		
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject jobj = jarr.getJSONObject(i);
			SteamGame sg = new SteamGame();
			
			sg.setAppid(jobj.getInt("appid"));
			sg.setName(jobj.getString("name"));
			
			games.add(sg);
		}
		
		return games;
	}
	
	public WishListGame asd() {
		return null;
	}
}
