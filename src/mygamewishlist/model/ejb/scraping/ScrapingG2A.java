package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;

public class ScrapingG2A {

	private String url;
	private static final MyLogger LOG = MyLogger.getLOG();
	
	protected ScrapingG2A() {
		url = "https://www.g2a.com/search?query=";
	}
	
	protected ArrayList<ScrapedGame> getG2AGames(String name) {
		Document doc = null;
		try {
			doc = Jsoup.connect(new StringBuilder()
						.append(url)
						.append(name)
						.toString()
					)
					.get();
		} catch (IOException e) {
			LOG.logError(e.getMessage());
			return new ArrayList<ScrapedGame>();
		}
		
		
		return new ArrayList<ScrapedGame>();
	}
}
