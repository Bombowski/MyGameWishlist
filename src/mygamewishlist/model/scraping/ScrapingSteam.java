package mygamewishlist.model.scraping;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.ejb.ScrapingEJB;
import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SteamApiLink;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingSteam {

	private static final MyLogger LOG = MyLogger.getLOG();

	private String getList;
	private String getApp;

	@EJB
	CreateQueryEJB cq_ejb = new CreateQueryEJB();

	public ScrapingSteam() {
		getList = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
		getApp = "https://store.steampowered.com/api/appdetails/";
	}

	public Hashtable<String, ArrayList<ScrapedGame>> getSteamGames(Game2Scrap g2s) {
		ArrayList<Integer> appids = cq_ejb.getSteamGameIdsByName(g2s.getName());
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		SteamApiLink sal = new SteamApiLink();
		Client c = ClientBuilder.newClient();

		for (Integer id : appids) {
			sal.setLink(getApp);
			sal.addParam("appids", id + "");
			WebTarget t = c.target(sal.print());

			try {
				double start1 = System.currentTimeMillis();
				JSONObject jo = new JSONObject((String) t.request().get(String.class));
				System.out.println("Conecting, getting json - " + (System.currentTimeMillis() - start1));
				JSONObject chk = jo.getJSONObject(id + "");

				double start2 = System.currentTimeMillis();
				if (chk.getBoolean("success")) {
					JSONObject data = chk.getJSONObject("data");
					if (!data.getBoolean("is_free")) {
						games.add(getRow(data, id, g2s.getUrl(), g2s.getStoreName()));
					}
				}
				System.out.println("Got data from json, adding to list - " + (System.currentTimeMillis() - start2));
			} catch (JSONException e) {
				LOG.logError(e.getMessage());
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}

	private ScrapedGame getRow(JSONObject data, int id, String url, String storeName) throws JSONException {
		ScrapedGame ssg = new ScrapedGame();

		ssg.setStoreName(storeName);
		ssg.setFullName(data.getString("name"));
		ssg.setImg(data.getString("header_image"));
		ssg.setUrl(url + id);

		JSONObject prices = data.getJSONObject("price_overview");
		ssg.setCurrentDiscount(prices.getDouble("discount_percent"));
		ssg.setCurrentPrice(
				Double.parseDouble(ScrapingEJB.replaceCommasEurosPercent(prices.getString("final_formatted"))));
		
		if (ssg.getCurrentDiscount() == 0) {
			ssg.setDefaultPrice(ssg.getCurrentPrice());
		} else {
			ssg.setDefaultPrice(
					Double.parseDouble(ScrapingEJB.replaceCommasEurosPercent(prices.getString("initial_formatted"))));			
		}		

		return ssg;
	}

	public ArrayList<SteamGame> getSteamGames() throws JSONException {
		SteamApiLink sal = new SteamApiLink();
		sal.setLink(getList);

		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(sal.print());
		JSONObject jo = new JSONObject((String) t.request().get(String.class));
		JSONObject applist = (JSONObject) jo.get("applist");
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

	public ScrapedGame getGame(WishListGame2Scrap wlg) {

		return null;
	}
}