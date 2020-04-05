package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.servlet.http.Cookie;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mygamewishlist.model.ejb.CreateQuery;
import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SecretClass;
import mygamewishlist.model.pojo.SteamApiLink;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingSteam {

	private static final MyLogger LOG = MyLogger.getLOG(); 
	
	private String getList;
	private String getApp;
	
	@EJB
	CreateQuery cq_ejb;
	
	protected ScrapingSteam() {
		getList = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
		getApp = "http://store.steampowered.com/api/appdetails/";
	}
	
	protected Hashtable<String, ArrayList<ScrapedGame>> getSteamGames(Game2Scrap g2s) {
		ArrayList<Integer> appids = cq_ejb.getSteamGameIdsByName(g2s.getName());
		SteamApiLink sal = new SteamApiLink();
		Client c = ClientBuilder.newClient();
		
		for (Integer id : appids) {
			sal.setLink(getApp);
			sal.addParam("appids", id + "");
			WebTarget t = c.target(sal.print());
			
			
			try {
				JSONObject jo = new JSONObject((String)t.request().get(String.class));
				JSONObject data = jo.getJSONObject(id + "").getJSONObject("data");
				
				int i = 0;
			} catch (JSONException e) {
				LOG.logError(e.getMessage());
			}
		}
		
		
//		Document doc = null;
//		try {
//			doc = ScrapingEJB.getDoc(g2s.getUrl(), g2s.getName());
//		} catch (IOException e) {
//			LOG.logError(e.getMessage());
//		}
//		
//		if (doc == null) {
//			return new Hashtable<String, ArrayList<ScrapedGame>>();
//		}		
//		if (doc.select("#search_result_container").text().equals("No results were returned for that query.")) {
//			return new Hashtable<String, ArrayList<ScrapedGame>>();
//		}
//		
//		Elements ele = doc.select(".search_result_row");
//		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
//		
//		
//		for (int i = 0; i < ele.size(); i++) {
//			ScrapedGame sc = getRow(ele.get(i), g2s.getStoreName());
//			
//			if (sc != null) {
//				games.add(sc);
//			}
//		}
//		
//		Hashtable<String, ArrayList<ScrapedGame>> toReturn = new Hashtable<String, ArrayList<ScrapedGame>>();
//		toReturn.put(g2s.getStoreName(), games);
//		
//		return toReturn;
		return new Hashtable<String, ArrayList<ScrapedGame>>();
	}
	
	protected ScrapedGame getGame(WishListGame2Scrap wlg) {
		Document doc = null;
		try {
			doc = ScrapingEJB.getDoc(wlg.getStoreUrl() + wlg.getGameUrl(), 
					wlg.getGameName(), new Cookie("birthtime", "568022401"));			
		} catch (IOException e) {
			LOG.logError(e.getMessage());
		}
		
		if (doc == null) {
			return new ScrapedGame();
		}
		
		Elements ele = doc.select(".game_purchase_action_bg");
		
		for (Element e : ele) {
			ScrapedGame tmp = getRowPriceChecking(e);
			if (tmp == null) {
				continue;
			}
			
			return tmp;
		}		
		
		return null;
	}
	
	private ScrapedGame getRowPriceChecking(Element ele) {
		if (ele.select(".btnv6_green_white_innerfade").text().equals("Download")) {
			return null;
		}
		
		ScrapedGame tmp = new ScrapedGame();
		
		String notSale = ele.select(".game_purchase_price").text();
		
		if (notSale.toUpperCase().equals("FREE TO PLAY")) {
			tmp.setCurrentPrice(0);
			tmp.setDefaultPrice(0);
			tmp.setCurrentDiscount(0);
		} else if (notSale.equals("")) {		
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
			return null;
		} else {
			String[] prices = ScrapingEJB.splitSpacesReplaceCommasEuros(price);
			
			if (prices.length == 2) {
				defaultP = Double.parseDouble(prices[0]);
				currentP = Double.parseDouble(prices[1]);
				discount = Math.abs(Double.parseDouble(ele
						.select(".search_discount")
						.text()
						.replace("%", "")));
			} else {
				currentP = defaultP = Double.parseDouble(prices[0]);
			}
		}
		
		return new ScrapedGame(url, fullName, img, storeName, defaultP, currentP, discount);
	}

	public ArrayList<SteamGame> getSteamGames() throws JSONException {
		SteamApiLink sal = new SteamApiLink();
		sal.setLink(getList);
		
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(sal.print());
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
}