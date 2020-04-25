package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapingFunctions {
	
	protected static String replaceCommasEurosPercent(String price) {
		return price.replace(",", ".").replace("€", "").replace("%", "");
	}
	
	protected static Document getDoc(String url, String name) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.get();
	}
	
	protected static Document getDocCookie(String url, String name, String ckName, String ckValue) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.cookie(ckName, ckValue)	
			.get();
	}
	
	protected static Document getDocCookie(String url, String name, HashMap<String, String> cookies) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.cookies(cookies)
			.get();
	}
	
	protected static String ifnull0(String str) {
		return str == null ? "0" : str;
	}
}
