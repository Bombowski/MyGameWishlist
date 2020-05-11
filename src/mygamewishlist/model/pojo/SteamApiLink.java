package mygamewishlist.model.pojo;

import java.util.Hashtable;

/**
 * @author Patryk
 *
 * POJO class used for generating link with petition to steam api
 */
public class SteamApiLink {

	private SecretClass sc;
	private StringBuilder fullLink;
	private String link;
	private Hashtable<String, String> params;
	
	public SteamApiLink() {
		sc = SecretClass.getSC();
		params = new Hashtable<String, String>();
	}
	
	public void setLink(String link) {
		this.link = link; 
	}
	
	/**
	 * Adds a parameter to the link
	 * 
	 * @param name String
	 * @param value String
	 */
	public void addParam(String name, String value) {
		params.put(name, value);
	}
	
	/**
	 * Clears list of parameters
	 */
	public void clearParams() {
		params.clear();
	}
	
	/**
	 * Returns a link with all of the parameters, and automatically
	 * adds the steam api key to the url
	 * 
	 * @return String
	 */
	public String print() {
		fullLink = new StringBuilder();
		fullLink.append(link).append("?");
		
		for (String str : params.keySet()) {
			fullLink.append(str).append("=").append(params.get(str)).append("&");
		}
		
		fullLink.append("key=").append(sc.steamToken);
		
		return fullLink.toString();
	}
}
