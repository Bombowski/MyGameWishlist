package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import mygamewishlist.model.ejb.scraping.ScrapingEJB;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;

@LocalBean
@Stateless
public class TimersEJB {

	@EJB
	ScrapingEJB scr_ejb;
	
	@EJB
	CreateQuery cq_ejb;
	
	@EJB
	MailEJB mail_ejb;
	
	public TimersEJB() {}
	
	@Schedule(second = "00", minute = "*/1", hour = "*")
	public void chkPrices() {
		System.out.println("Hello");
		
		Hashtable<String, ScrapedGame> chkedGames = new Hashtable<String, ScrapedGame>();
		ArrayList<User> users = cq_ejb.getUsers();
		ArrayList<Store> stores = cq_ejb.getStores();
		
		for (Store st : stores) {
			ArrayList<WishListGame> games = cq_ejb.getGamesByStore(st.getId());
			
			for (WishListGame wlg : games) {
				ScrapedGame scGame = scr_ejb.getGame(wlg, st.getName());
				
				if (wlg.getCurrentPrice() > scGame.getCurrentPrice()) {
					mail_ejb.sendMailItemsOnSale(destination, games, store);
				}
			}
		}
	}
}
