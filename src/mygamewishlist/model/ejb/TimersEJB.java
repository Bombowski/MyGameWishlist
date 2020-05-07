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
	
	public TimersEJB() {
	}

	private void initAll() {
		scr_ejb = new ScrapingEJB();
		cq_ejb = new CreateQueryEJB();
		mail_ejb = new MailEJB();
	}
	
	@Schedule(second = "00", minute = "00", hour = "12")
	public void loadGames() {
		initAll();
		try {
			scr_ejb.loadGames();
		} catch (JSONException e) {
			LOG.logError(e.getMessage());
		}
	}

	@Schedule(second = "00", minute = "00", hour = "*/1")
	public void chkPrices() {
		initAll();

		Hashtable<String, ScrapedGame> chkedGames = new Hashtable<String, ScrapedGame>();
		ArrayList<User> users = cq_ejb.getUsersWithList();

		for (User us : users) {
			ArrayList<WishListGame2Scrap> games = cq_ejb.getGamesFromListById(us.getId());
			games.addAll(cq_ejb.getSteamGamesFromListById(us.getId()));
			
			ArrayList<ScrapedGame> toSend = new ArrayList<ScrapedGame>();

			for (WishListGame2Scrap wlg : games) {
				ScrapedGame scGame;
				
				if (chkedGames.keySet().contains(wlg.getUrlGame())) {
					scGame = chkedGames.get(wlg.getUrlGame());
				} else {
					scGame = scr_ejb.getGame(wlg);
					
					scGame.setFullName(wlg.getGameName());
					scGame.setImg(wlg.getImg());
					scGame.setStoreName(wlg.getStoreName());
					scGame.setUrlGame(wlg.getUrlGame());
					scGame.setUrlStore(wlg.getUrlStore());
					chkedGames.put(wlg.getUrlGame(), scGame);					
					
					add2Timeline(scGame);					
				}

				if (lowerPrice(wlg, scGame)) {
					toSend.add(scGame);
				}
			}

			if (!toSend.isEmpty()) {
				mail_ejb.sendMailItemsOnSale(us, toSend);
				cq_ejb.updatePrices(toSend);
			}
		}
	}

	private boolean lowerPrice(WishListGame2Scrap wlg, ScrapedGame sg) {
		double current = sg.getCurrentPrice();
		double min = wlg.getMinPrice();
		double max = wlg.getMaxPrice();

		if (min != -1 || max != -1 ) {
			if (min != -1) {
				if (min >= current) {
					return true;
				}
			} else {
				if (max >= current) {
					return true;
				}
			}
		} else {
			if (wlg.getCurrentPrice() > current) {
				return true;
			}
		}

		return false;
	}

	private void add2Timeline(ScrapedGame sg) {
		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		
		sb.append(c.get(Calendar.YEAR))
			.append("-")
			.append(c.get(Calendar.MONTH) + 1)
			.append("-")
			.append(c.get(Calendar.DAY_OF_MONTH));
		
		cq_ejb.add2Timeline(sg, sb.toString());
	}
}