package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
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

/**
 * @author Patryk
 *
 * Class that is used for calling scraping functions
 */
@Stateless
@LocalBean
public class ScrapingEJB {
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	/*
	 * list used by scraping called by user, like when user is
	 * searching for a game in a store. this object will sotre:
	 * Store name that identifies a scraping function, scraping
	 * function recieves a Game2Scrap parameter, and returns a HashTable
	 * of String (store name) as key, and a list of scraped game in a
	 * ArrayList.
	 */
	private Hashtable<String, Function<Game2Scrap, Hashtable<String,ArrayList<ScrapedGame>>>> scraping = 
			new Hashtable<String, Function<Game2Scrap, Hashtable<String,ArrayList<ScrapedGame>>>>();
	/*
	 * list used by TimersEJB class, it is used when the timer is
	 * checking for price changes in games, this object stores:
	 * String store name as key function as value, function recieves a
	 * WishlistGame2Scrap object, and returns Scraped game.
	 */
	private Hashtable<String, Function<WishListGame2Scrap, ScrapedGame>> timerScr =
			new Hashtable<String, Function<WishListGame2Scrap, ScrapedGame>>();
	
	// names of stores
	private static final String STEAM = "Steam";
	private static final String INSTANT = "Instant Gaming";
	private static final String GOG = "GOG";
	
	// store scraping classes
	private ScrapingSteam ss;
	private ScrapingInstantGaming si;
	private ScrapingGOG gg;
	
	public ScrapingEJB() {
		// initializing store classes
		ss = new ScrapingSteam();
		gg = new ScrapingGOG();
		si = new ScrapingInstantGaming();
		
		// adding functions and their keys to scaping Table
		scraping.put(STEAM, ss::getSteamGames);
		scraping.put(INSTANT, si::getInstantGames);
		scraping.put(GOG, gg::getGOGGames);
		
		// adding functions and their keys to timer Table
		timerScr.put(STEAM, ss::getGame);
		timerScr.put(INSTANT, si::getGame);
		timerScr.put(GOG, gg::getGame);
	}
	
	/**
	 * Returns a list of scraped games by their url.
	 * This function will iterate through Scraping Hashtable, until
	 * it finds a store with a name equal to g2s name, then it
	 * will call function stored as value, and apply the g2s object.
	 * 
	 * @param g2s Game2Scrap
	 * @return Hashtable<String,ArrayList<ScrapedGame>>
	 */
	public Hashtable<String,ArrayList<ScrapedGame>> getGamesByNameUrl(Game2Scrap g2s) {
		for (Entry<String, Function<Game2Scrap, Hashtable<String, ArrayList<ScrapedGame>>>> entry : scraping.entrySet()) {
			if (entry.getKey().equals(g2s.getStoreName())) {
				return entry.getValue().apply(g2s);
			}
		}
		
		return new Hashtable<String,ArrayList<ScrapedGame>>();
	}
	
	/**
	 * Returns new scraped data of a game.
	 * This function will iterate through timerScr Hashtable until
	 * it finds a key equal to wlg store name, then it will call
	 * this key's function and apply wlg to it.
	 * 
	 * @param wlg WishlistGame2Scrap
	 * @return ScrapedGame
	 */
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		for (Entry<String, Function<WishListGame2Scrap, ScrapedGame>> entry : timerScr.entrySet()) {
			if(entry.getKey().equals(wlg.getStoreName())) {
				return entry.getValue().apply(wlg);
			}
		}
		return new ScrapedGame();
	}
	
	/**
	 * Returns a list of steam games.
	 * 
	 * @return ArrayList<SteamGame>
	 * @throws JSONException
	 */
	public ArrayList<SteamGame> loadGames() throws JSONException {
		return ss.getSteamGameList();
	}
}
