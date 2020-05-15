package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Patryk
 *
 * Class that contains functions used by all of the scraping classes
 */
public class ScrapingFunctions {
	
	private static final String CURRENT_USR_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
	private static final String REFERRER = "http://www.google.com";
	
	private ScrapingFunctions() {}
	
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
		return getConection(url, name)
				.userAgent(CURRENT_USR_AGENT)
				.referrer(REFERRER)
				.execute().bufferUp().parse();
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
		return getConection(url, ckName)
				.userAgent(CURRENT_USR_AGENT)
				.referrer(REFERRER)
				.cookie(ckName, ckValue)	
				.execute().bufferUp().parse();
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
		return getConection(url, name)
				.userAgent(CURRENT_USR_AGENT)
				.referrer(REFERRER)
				.cookies(cookies)
				.execute().bufferUp().parse();
	}
	
	/**
	 * Returns a conection of url + name
	 * 
	 * @param url String
	 * @param name String
	 * @return Conection
	 */
	private static Connection getConection(String url, String name) {
		return Jsoup.connect(url + name);
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
