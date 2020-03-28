package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingG2A {

	private static final MyLogger LOG = MyLogger.getLOG();
	
	protected ScrapingG2A() {}
	
	protected Hashtable<String,ArrayList<ScrapedGame>> getG2AGames(Game2Scrap g2s) {
		Document doc = null;
		try {
			doc = Jsoup.connect(new StringBuilder()
						.append(g2s.getUrl())
						.append(g2s.getName())
						.toString()
					)
					.get();
		} catch (IOException e) {
			LOG.logError(e.getMessage());
			return new Hashtable<String,ArrayList<ScrapedGame>>();
		}
		
		
		return new Hashtable<String,ArrayList<ScrapedGame>>();
	}
	
	protected ScrapedGame getGame(WishListGame2Scrap wlg) {
		Document doc = ScrapingEJB.getDoc(wlg.getStoreUrl() + wlg.getGameUrl(), wlg.getGameName());
		
		
		
		return null;
	}
}
