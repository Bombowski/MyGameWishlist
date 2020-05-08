package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.Function;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;

import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.scraping.ScrapingGOG;
import mygamewishlist.model.scraping.ScrapingInstantGaming;
import mygamewishlist.model.scraping.ScrapingSteam;

@Stateless
@LocalBean
public class ScrapingEJB {
	
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
	
	public ArrayList<SteamGame> loadGames() throws JSONException {
		return ss.getSteamGameList();
	}
}
