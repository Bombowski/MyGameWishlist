package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.json.JSONException;

import mygamewishlist.model.ejb.scraping.ScrapingEJB;
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
	CreateQuery cq_ejb;
	
	@EJB
	MailEJB mail_ejb;
	
	public TimersEJB() {}
	
	//@Schedule(second = "00", minute = "00", hour = "12")
	@Schedule(second = "00", minute = "*/3", hour = "*")
	public void loadGames() {
		System.out.println("loadGames");
		try {
			scr_ejb.loadGames();
		} catch (JSONException e) {
			LOG.logError(e.getMessage());
		}
	}
	
//	@Schedule(second = "00", minute = "*/10", hour = "*")
	public void chkPrices() {
		System.out.println("chkPrices");
		
		Hashtable<String, ScrapedGame> chkedGames = new Hashtable<String, ScrapedGame>();
		ArrayList<User> users = cq_ejb.getUsersWithList();
		
		for (User us : users) {
			ArrayList<WishListGame2Scrap> games = cq_ejb.getGamesFromListById(us.getId());
			Hashtable<Integer, ScrapedGame> toSend = new Hashtable<Integer, ScrapedGame>();
			int i = 0;
			
			for (WishListGame2Scrap wlg : games) {
				ScrapedGame scGame = scr_ejb.getGame(wlg);
				
//				if (wlg.getCurrentPrice() > scGame.getCurrentPrice()) {
					scGame.setFullName(wlg.getGameName());
					scGame.setImg(wlg.getImg());
					scGame.setStoreName(wlg.getStoreName());
					chkedGames.put(wlg.getGameUrl(), scGame);
					toSend.put(i, scGame);
					i++;
//				}
			}
			
			if (!toSend.isEmpty()) {
				mail_ejb.sendMailItemsOnSale(us, toSend);
			}
		}
	}
	
	
}
