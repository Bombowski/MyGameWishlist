package mygamewishlist.model.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.Function;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.Cookie;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.scraping.ScrapingGOG;
import mygamewishlist.model.scraping.ScrapingInstantGaming;
import mygamewishlist.model.scraping.ScrapingSteam;

@Stateless
@LocalBean
public class ScrapingEJB {

	private static final MyLogger LOG = MyLogger.getLOG(); 
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	private Hashtable<String, Function<Game2Scrap, Hashtable<String,ArrayList<ScrapedGame>>>> scraping = 
			new Hashtable<String, Function<Game2Scrap, Hashtable<String,ArrayList<ScrapedGame>>>>();
	private Hashtable<String, Function<WishListGame2Scrap, ScrapedGame>> timerScr =
			new Hashtable<String, Function<WishListGame2Scrap, ScrapedGame>>();
	
	private static final String STEAM = "Steam";
	private static final String INSTANT = "Instant Gaming";
	private static final String GOG = "GOG";
	
	private ScrapingSteam ss;
	private ScrapingInstantGaming si;
	private ScrapingGOG gg;
	
	public ScrapingEJB() {
		ss = new ScrapingSteam();
		gg = new ScrapingGOG();
		si = new ScrapingInstantGaming();
		
		scraping.put(STEAM, ss::getSteamGames);
		scraping.put(INSTANT, si::getInstantGames);
		scraping.put(GOG, gg::getGOGGames);
		
		timerScr.put(STEAM, ss::getGame);
		timerScr.put(INSTANT, si::getGame);
		timerScr.put(GOG, gg::getGame);
	}
	
	public Hashtable<String,ArrayList<ScrapedGame>> getGamesByNameUrl(Game2Scrap g2s) {
		for (String key : scraping.keySet()) {
			if (key.equals(g2s.getStoreName())) {
				return scraping.get(key).apply(g2s);
			}
		}
		
		return new Hashtable<String,ArrayList<ScrapedGame>>();
	}
	
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		for (String key : timerScr.keySet()) {
			if(key.equals(wlg.getStoreName())) {
				return timerScr.get(key).apply(wlg);
			}
		}
		return new ScrapedGame();
	}
	
	public static String replaceSpaces(String str) {
		return str.replace(" ", "+");
	}
	
	public static String replaceCommasEurosPercent(String price) {
		return price.replace(",", ".").replace("€", "").replace("%", "");
	}

	public static Document getDoc(String url, String name, Cookie ck) throws IOException {
		Document doc = Jsoup
				.connect(new StringBuilder()
					.append(url)
					.append(name)
					.toString())
				.cookie(ck.getName(), ck.getValue())
				.get();
		
		return doc;
	}
	
	public static Document getDoc(String url, String name) throws IOException {
		Document doc = Jsoup.connect(new StringBuilder()
						.append(url)
						.append(name)
						.toString())
					.get();
		
		return doc;
	}
	
	public static String ifnull0(String str) {
		return str == null ? "0" : str;
	}
	
	public void loadGames() throws JSONException {
		ArrayList<SteamGame> sg = ss.getSteamGames();
		
		cq_ejb.addSteamGames(sg);
	}
}
