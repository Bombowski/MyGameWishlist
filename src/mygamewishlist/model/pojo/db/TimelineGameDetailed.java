package mygamewishlist.model.pojo.db;

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
