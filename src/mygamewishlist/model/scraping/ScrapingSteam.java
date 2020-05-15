package mygamewishlist.model.scraping;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ServerErrorException;
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

/**
 * @author Patryk
 *
 * Class used for web scraping steam games
 */
public class ScrapingSteam {

	private static final MyLogger LOG = MyLogger.getLOG();

	private String getList;
	private String getApp;

	@EJB
	CreateQueryEJB cq_ejb = new CreateQueryEJB();

	/**
	 * Constructor initializes all of the links needed for
	 * scraping.
	 */
	public ScrapingSteam() {
		getList = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
		getApp = "https://store.steampowered.com/api/appdetails/";
	}

	/**
	 * Function that returns a list of games by the object provided.
	 * In case of steam games, first a search is made in the database,
	 * the only parameter used is name of the game, then with the returned
	 * list of appid's, a petition is made, until 5 games are found, search
	 * is capped because of performance issues. 
	 * 
	 * @param g2s Game2Scrap
	 * @return Hashtable<String, ArrayList<ScrapedGame>>
	 */
	public Hashtable<String, ArrayList<ScrapedGame>> getSteamGames(Game2Scrap g2s) {
		// get list of appid's
		ArrayList<Integer> appids = cq_ejb.getSteamGameIdsByName(g2s.getName());
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		SteamApiLink sal = new SteamApiLink();
		Client c = ClientBuilder.newClient();

		int i = 0;
		for (Integer id : appids) {
			// if 5 results were found and valid, the loop stops.
			if (i == 5) {
				break;
			}

			// setting the link and the appid parameter
			sal.setLink(this.getApp);
			sal.addParam("appids", id + "");
			WebTarget t = c.target(sal.print());

			try {
				// making a petition
				JSONObject jo = new JSONObject(t.request().get(String.class));
				// getting the json
				JSONObject chk = jo.getJSONObject(id + "");

				// checking if it's valid
				if (chk.getBoolean("success")) {
					JSONObject data = chk.getJSONObject("data");
					if (!data.getBoolean("is_free")) {
						// adding results to the list
						games.add(getSearchedGame(data, id, g2s));
						i++;
					}
				}
			} catch (JSONException | InternalServerErrorException e) {
				LOG.logError(e.getMessage());
			}
		}
		
		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
		toReturn.put(g2s.getStoreName(), games);
		
		return toReturn;
	}

	/**
	 * Returns all of the data in a form of ScrapedGame object,
	 * it gets all of the data from JSONObject that is provided.
	 * 
	 * @param data JSONObject, object returned by stema api
	 * @param id int, id is the appid of steam game, and it is used to build 
	 * the game link.
	 * @param g2s Game2Scrap, this object is used for getting store data and
	 * storing it int he ScrapedGame object.
	 * @return
	 * @throws JSONException
	 */
	private ScrapedGame getSearchedGame(JSONObject data, int id, Game2Scrap g2s) throws JSONException {
		ScrapedGame ssg = new ScrapedGame();

		// setting all of the parameters
		ssg.setStoreName(g2s.getStoreName());
		ssg.setFullName(data.getString("name"));
		ssg.setImg(data.getString("header_image"));
		ssg.setUrlStore(g2s.getStoreUrl());
		ssg.setUrlGame("/app/" + id);

		// getting the prices object and setting prices
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

	/**
	 * Returns a list of all of the games that are currently on steam.
	 * Parameters returned by this steam api functions are steam
	 * appid, and game title
	 * This function should get executed once a day, so that the games
	 * get updated.
	 * 
	 * @return ArrayList<SteamGame>
	 * @throws JSONException
	 */
	public ArrayList<SteamGame> getSteamGameList() throws JSONException {
		/*
		 * building the link, SteamApiLink automatically adds the 
		 * steam api key to the url
		 */
		SteamApiLink sal = new SteamApiLink();
		sal.setLink(getList);

		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(sal.print());
		// make the petition and get the objects we are interested in
		JSONObject jo = new JSONObject(t.request().get(String.class));
		JSONObject applist = (JSONObject) jo.get("applist");
		JSONArray jarr = applist.getJSONArray("apps");

		ArrayList<SteamGame> games = new ArrayList<SteamGame>();

		// for each result, create a SteamGame object
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject jobj = jarr.getJSONObject(i);
			SteamGame sg = new SteamGame();

			sg.setAppid(jobj.getInt("appid"));
			sg.setName(jobj.getString("name"));

			games.add(sg);
		}

		return games;
	}

	/**
	 * Returns ScrapedGame object with current prices from steam api.
	 * 
	 * @param wlg WishlistGame2Scrap, is cast to WishListGame2ScrapSteam, and then 
	 * is used to get all of the information necessary to create the petition. 
	 * @return ScrapedGame
	 */
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		WishListGame2ScrapSteam wlgs = (WishListGame2ScrapSteam)wlg;
		ScrapedGame toReturn = new ScrapedGame();
		toReturn.setCurrentPrice(-1);
		
		// create the link with appid
		SteamApiLink sal = new SteamApiLink();
		sal.setLink(getApp);
		sal.addParam("appids", wlgs.getAppid() + "");
		
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(sal.print());
		try {
			// make the petition
			JSONObject jo = new JSONObject(t.request().get(String.class));
			
			// get the object and check if the petition was successful
			JSONObject chk = jo.getJSONObject(wlgs.getAppid() + "");
			if (chk.getBoolean("success")) {
				JSONObject data = chk.getJSONObject("data");
				JSONObject prices = data.getJSONObject("price_overview");
				
				// set all of the prices
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
		} catch (JSONException | ServerErrorException e) {
			LOG.logError(e.getMessage());
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement ste : e.getStackTrace()) {
				sb.append(ste.toString())
					.append("\n");
			}
			LOG.logError(sb.toString());
		}
		
		return toReturn;
	}
}