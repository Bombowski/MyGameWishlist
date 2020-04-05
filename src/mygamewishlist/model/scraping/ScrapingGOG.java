package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import mygamewishlist.model.ejb.ScrapingEJB;
import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingGOG {

	private static final MyLogger LOG = MyLogger.getLOG();
	
	public ScrapingGOG() {}
	
	public Hashtable<String,ArrayList<ScrapedGame>> getGOGGames(Game2Scrap g2s) {
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
	
	public ScrapedGame getGame(WishListGame2Scrap wlg) {
		Document doc = null;
		try {
			doc = ScrapingEJB.getDoc(wlg.getUrlStore() + wlg.getUrlGame(), wlg.getGameName());
		} catch (IOException e) {
			LOG.logError(e.getMessage());
		}
		
		if (doc == null) {
			return new ScrapedGame();
		}
		
		return null;
	}
}
