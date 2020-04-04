package mygamewishlist.model.pojo;

import java.util.Hashtable;

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
	
	public void addParam(String name, String value) {
		params.put(name, value);
	}
	
	public void clearParams() {
		params.clear();
	}
	
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
