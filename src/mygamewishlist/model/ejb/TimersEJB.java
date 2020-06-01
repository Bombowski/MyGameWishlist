package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.json.JSONException;

import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

/**
 * @author Patryk
 * 
 * EJB class with timers
 */
@LocalBean
@Stateless
public class TimersEJB {

	private static final MyLogger LOG = MyLogger.getLOG();

	@EJB
	ScrapingEJB scr_ejb;

	@EJB
	CreateQueryEJB cq_ejb;

	@EJB
	MailEJB mail_ejb;
	
	/*
	 * this list will contain games that will be sent to users to 
	 * notify them about price changes
	 */
	ArrayList<ScrapedGame> toSend;
	
	/**
	 * Empty constructor for ejb
	 */
	public TimersEJB() {}

	/**
	 * Used for initializing EJB, as they don't seem to initialize
	 * in a timer class
	 */ 
	private void initAll() {
		scr_ejb = new ScrapingEJB();
		cq_ejb = new CreateQueryEJB();
		mail_ejb = new MailEJB();
	}
	
	/**
	 * Once a day this function is used to load new steam games into the database.
	 */ 
	@Schedule(second = "00", minute = "00", hour = "12")
	public void loadGames() {
		initAll();
		try {
			cq_ejb.addSteamGames(scr_ejb.loadGames());
		} catch (JSONException e) {
			LOG.logError(e.getStackTrace());
		}
	}

	/**
	 * Every hour this function runs and updates prices, if they need to be
	 * updated (if they are different), and also inserts current price into
	 * the timeline table.
	 */ 
	@Schedule(second = "00", minute = "00", hour = "*/1")
	public void chkPrices() {
		// initialize EJB's
		initAll();
		/*
		 * this variable will store todays date, and it will be used for
		 * inserting into price timeline.
		 */	
		String time = getDate();

		/*
		 * this hashtable will store all of the checked games
		 */
		Hashtable<String, ScrapedGame> chkedGames = new Hashtable<String, ScrapedGame>();
		// get users that have at least one game in their wishlist
		ArrayList<User> users = cq_ejb.getUsersWithList();

		// initialize the list of games notifications to send
		toSend = new ArrayList<ScrapedGame>();
		
		for (User us : users) {
			// get current user's wishlist
			ArrayList<WishListGame2Scrap> games = cq_ejb.getGamesFromListById(us.getId());
			// get steam games from user's wishlist
			games.addAll(cq_ejb.getSteamGamesFromListById(us.getId()));

			for (WishListGame2Scrap wlg : games) {
				ScrapedGame scGame;
				
				// if the game is in the chkedGames HashTable, it was already cheched
				if (chkedGames.keySet().contains(wlg.getUrlGame())) {
					scGame = chkedGames.get(wlg.getUrlGame());
					
					// decide what to do with the new game data
					interpretPriceCodeOld(wlg,scGame);
				} else {
					// if the game isn't in the HashTable, scrape it
					scGame = scr_ejb.getGame(wlg);
					
					/*
					 * if games current price is -1 it means that something went wrong 
					 * in the scraping. Currently the only scraping that returns a -1 price
					 * is Instant Gaming.
					 */
					if (scGame.getCurrentPrice() == -1) {
						continue;
					}
					
					// set all of the scraped parameters to ScrapedGame object
					scGame.setFullName(wlg.getGameName());
					scGame.setImg(wlg.getImg());
					scGame.setStoreName(wlg.getStoreName());
					scGame.setUrlGame(wlg.getUrlGame());
					scGame.setUrlStore(wlg.getUrlStore());
					chkedGames.put(wlg.getUrlGame(), scGame);					
					
					// add new entry or modify old entry of price timeline
					cq_ejb.add2Timeline(scGame, time);
					// decide what to do with the new game data
					interpretPriceCodeNew(wlg,scGame);
				}
			}

			// if the list of games to notify is not empty, sen an email.
			if (!toSend.isEmpty()) {
				mail_ejb.sendMailItemsOnSale(us, toSend);
			}
			
			toSend.clear();
		}
	}

	/**
	 * This function is used when the game isn't in the list of games yet.
	 * Calls the lowerPrice function, then interprets the code returned, and
	 * decides whether or not add the game to toSend, update prices, or do nothing
	 * 
	 * @param wlg WishlistGame2Scrap game from database
	 * @param scGame ScrapedGame game from scraping
	 */
	private void interpretPriceCodeNew(WishListGame2Scrap wlg, ScrapedGame scGame) {
		switch(lowerPrice(wlg, scGame)) {
		case 1:
			cq_ejb.updatePrices(scGame);
			toSend.add(scGame);
			break;
		case 2:
			cq_ejb.updatePrices(scGame);
			break;
		case 0:
		default:
			break;
		}
	}

	/**
	 * This function is used when the game is in the list of checked games,
	 * calls the lowerPrice function, then interprets the code reutrned, and
	 * desides wether or not add the game to toSend, update pirces, or do nothing
	 * 
	 * @param wlg WishlistGame2Scrap game from database
	 * @param scGame ScrapedGame game from scraping
	 */
	private void interpretPriceCodeOld(WishListGame2Scrap wlg, ScrapedGame scGame) {
		switch(lowerPrice(wlg, scGame)) {
		case 1:
			toSend.add(scGame);
			break;
		case 2:
		case 0:
		default:
			break;
		}
	}
	
	/**
	 * This function will check current prices of both database game (old) and
	 * scraped game (new), it will also check default prices.
	 * 
	 * code 0 = no changes - don't update price nor add it to notification list
	 * code 1 = different price - update price and send notification to suer
	 * code 2 = different price but not beetween min and max - update price 
	 * 
	 * @param wlg WishlistGame2Scrap from database
	 * @param sg ScrapedGame from scraping
	 * @return int
	 */
	private int lowerPrice(WishListGame2Scrap wlg, ScrapedGame sg) {
		/*
		 * save object parameters in local variables so they will
		 * be easier to manage
		 */
		double newCurrent = sg.getCurrentPrice();
		double oldCurrent = wlg.getCurrentPrice();
		double min = wlg.getMinPrice();
		double max = wlg.getMaxPrice();
		
		/*
		 * check if current price is lower, then if min price is set,
		 * then if current price is lower or equal to min price.
		 */
		if (newCurrent < oldCurrent) {
			if (min != -1) {
				if (min >= newCurrent) {				
					return 1;
				}
				return 2;
			}
			return 1;
		/*
		 * check if current price is higher, then if max price is set,
		 * then if current price is higher or equal to max price.
		 */
		} else if (newCurrent > oldCurrent) {
			if (max != -1) {
				if (max <= newCurrent) {
					return 1;
				}			
				return 2;
			}
			return 1;
		} 
		
		// check if default prices are different
		if (wlg.getDefaultPrice() != sg.getDefaultPrice()) {
			return 2;
		}
		
		return 0;
	}

	/**
	 * Generates todays date in the sql format
	 * 
	 * @return String today's date
	 */
	private String getDate() {
		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		
		sb.append(c.get(Calendar.YEAR))
			.append("-")
			.append(c.get(Calendar.MONTH) + 1)
			.append("-")
			.append(c.get(Calendar.DAY_OF_MONTH));
		
		return sb.toString();
	}
}