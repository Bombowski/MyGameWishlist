package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Patryk
 *
 * Class that contains functions used by all of the scraping classes
 */
public class ScrapingFunctions {
	
	/**
	 * Replaces all of the commas by dots, euros by empty string, and
	 * percante by empty string.
	 * 
	 * @param price String
	 * @return String
	 */
	protected static String replaceCommasEurosPercent(String price) {
		return price.replace(",", ".").replace("€", "").replace("%", "");
	}
	
	/**
	 * Concatenates url and name, and then returns the document
	 * 
	 * @param url String
	 * @param name String
	 * @return Document
	 * @throws IOException
	 */
	protected static Document getDoc(String url, String name) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.get();
	}
	
	/**
	 * Concatenates url and name, and then adds a coockie with a name of
	 * ckName, and value of ckValue.
	 * 
	 * @param url String
	 * @param name String
	 * @param ckName String
	 * @param ckValue String
	 * @return Document
	 * @throws IOException
	 */
	protected static Document getDocCookie(String url, String name, String ckName, String ckValue) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.cookie(ckName, ckValue)	
			.get();
	}
	
	/**
	 * Concatenates url and name, and then adds the map coockies as a
	 * list of coockies.
	 * 
	 * @param url String
	 * @param name String 
	 * @param cookies HashMap<String, String>
	 * @return Document
	 * @throws IOException
	 */
	protected static Document getDocCookie(String url, String name, HashMap<String, String> cookies) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.cookies(cookies)
			.get();
	}
	
	/**
	 * if the string provided is null, a 0 is returned, else str
	 * 
	 * @param str String
	 * @return String
	 */
	protected static String ifnull0(String str) {
		return str == null ? "0" : str;
	}
}
