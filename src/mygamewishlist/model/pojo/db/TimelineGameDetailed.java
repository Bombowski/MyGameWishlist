package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 * 
 * POJO class that extends TimelineGame and additionally contains
 * name of the game and url of the store
 */
public class TimelineGameDetailed extends TimelineGame {

	private String name;
	private String urlStore;

	public TimelineGameDetailed() {
		super();
	}

	public TimelineGameDetailed(String name, String urlStore) {
		super();
		this.name = name;
		this.urlStore = urlStore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlStore() {
		return urlStore;
	}

	public void setUrlStore(String urlStore) {
		this.urlStore = urlStore;
	}

}
