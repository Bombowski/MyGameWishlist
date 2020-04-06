package mygamewishlist.model.ejb;

import java.util.ArrayList;
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
	ScrapingEJB scr_ejb = new ScrapingEJB();

	@EJB
	CreateQueryEJB cq_ejb = new CreateQueryEJB();

	@EJB
	MailEJB mail_ejb = new MailEJB();

	public TimersEJB() {
	}

//	@Schedule(second = "00", minute = "00", hour = "12")
//	@Schedule(second = "00", minute = "*/3", hour = "*")
	public void loadGames() {
		System.out.println("loadGames");
		try {
			scr_ejb.loadGames();
		} catch (JSONException e) {
			LOG.logError(e.getMessage());
		}
	}

//	@Schedule(second = "00", minute = "*/3", hour = "*")
	public void chkPrices() {
		System.out.println("chkPrices");

		Hashtable<String, ScrapedGame> chkedGames = new Hashtable<String, ScrapedGame>();
		ArrayList<User> users = cq_ejb.getUsersWithList();

		for (User us : users) {
			ArrayList<WishListGame2Scrap> games = cq_ejb.getGamesFromListById(us.getId());
			ArrayList<ScrapedGame> toSend = new ArrayList<ScrapedGame>();

			for (WishListGame2Scrap wlg : games) {
				ScrapedGame scGame;
				boolean add = false;
				if (chkedGames.keySet().contains(wlg.getUrlGame())) {
					scGame = chkedGames.get(wlg.getUrlGame());

					if (lowerPrice(wlg, scGame)) {
						add = true;
					}
				} else {
					scGame = scr_ejb.getGame(wlg);

					if (lowerPrice(wlg, scGame)) {
						add = true;
						scGame.setFullName(wlg.getGameName());
						scGame.setImg(wlg.getImg());
						scGame.setStoreName(wlg.getStoreName());
						scGame.setUrl(wlg.getUrlStore() + wlg.getUrlGame());
						chkedGames.put(wlg.getUrlGame(), scGame);
					}
				}

				if (add) {
					toSend.add(scGame);
				}
			}

			if (!toSend.isEmpty()) {
				mail_ejb.sendMailItemsOnSale(us, toSend);
				cq_ejb.updatePrices(toSend, cq_ejb.getIdListByIdUser(us.getId()));
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
}