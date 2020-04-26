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
import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SteamApiLink;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.pojo.db.WishListGame2ScrapSteam;

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

		int i = 0;
		for (Integer id : appids) {
			if (i == 5) {
				break;
			}
				
			sal.setLink(this.getApp);
			sal.addParam("appids", id + "");
			WebTarget t = c.target(sal.print());

			try {
				JSONObject jo = new JSONObject(t.request().get(String.class));
				JSONObject chk = jo.getJSONObject(id + "");

				if (chk.getBoolean("success")) {
					JSONObject data = chk.getJSONObject("data");
					if (!data.getBoolean("is_free")) {
						games.add(getRow(data, id, g2s));
						i++;
					}
				}
			} catch (JSONException e) {
				LOG.logError(e.getMessage());
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}

	private ScrapedGame getRow(JSONObject data, int id, Game2Scrap g2s) throws JSONException {
		ScrapedGame ssg = new ScrapedGame();

		ssg.setStoreName(g2s.getStoreName());
		ssg.setFullName(data.getString("name"));
		ssg.setImg(data.getString("header_image"));
		ssg.setUrlStore(g2s.getStoreUrl());
		ssg.setUrlGame("/app/" + id);

		JSONObject prices = data.getJSONObject("price_overview");
		ssg.setCurrentDiscount(prices.getDouble("discount_percent"));
		ssg.setCurrentPrice(Double.parseDouble(
				ScrapingFunctions.replaceCommasEurosPercent(prices.getString("final_formatted"))));
		
		if (ssg.getCurrentDiscount() == 0) {
			ssg.setDefaultPrice(ssg.getCurrentPrice());
		} else {
			ssg.setDefaultPrice(Double.parseDouble(
					ScrapingFunctions.replaceCommasEurosPercent(prices.getString("initial_formatted"))));			
		}		

		return ssg;
	}

	public ArrayList<SteamGame> getSteamGameList() throws JSONException {
		SteamApiLink sal = new SteamApiLink();
		sal.setLink(getList);

		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(sal.print());
		JSONObject jo = new JSONObject(t.request().get(String.class));
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
		WishListGame2ScrapSteam wlgs = (WishListGame2ScrapSteam)wlg;
		ScrapedGame toReturn = new ScrapedGame();
		
		SteamApiLink sal = new SteamApiLink();
		sal.setLink(getApp);
		sal.addParam("appids", wlgs.getAppid() + "");
		
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(sal.print());
		try {
			JSONObject jo = new JSONObject(t.request().get(String.class));
			
			JSONObject chk = jo.getJSONObject(wlgs.getAppid() + "");
			if (chk.getBoolean("success")) {
				JSONObject data = chk.getJSONObject("data");
				JSONObject prices = data.getJSONObject("price_overview");
				
				toReturn.setCurrentDiscount(prices.getDouble("discount_percent"));
				toReturn.setCurrentPrice(Double.parseDouble(
						ScrapingFunctions.replaceCommasEurosPercent(prices.getString("final_formatted"))));
				
				if (toReturn.getCurrentDiscount() == 0) {
					toReturn.setDefaultPrice(toReturn.getCurrentPrice());
				} else {
					toReturn.setDefaultPrice(Double.parseDouble(
							ScrapingFunctions.replaceCommasEurosPercent(prices.getString("initial_formatted"))));			
				}
			} else {
				toReturn.setCurrentPrice(wlgs.getCurrentPrice());
				toReturn.setDefaultPrice(wlg.getDefaultPrice());
				toReturn.setCurrentDiscount(0);
			}
		} catch (JSONException e) {
			LOG.logError(e.getMessage());
		}
		
		return toReturn;
	}
}