package mygamewishlist.model.scraping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapingFunctions {
	
	protected static String replaceCommasEurosPercent(String price) {
		return price.replace(",", ".").replace("€", "").replace("%", "");
	}
	
	protected static Document getDoc(String url, String name) throws IOException {
		Document doc = Jsoup.connect(new StringBuilder()
						.append(url)
						.append(name)
						.toString())
					.get();
		
		return doc;
	}
	
	protected static String ifnull0(String str) {
		return str == null ? "0" : str;
	}
}
