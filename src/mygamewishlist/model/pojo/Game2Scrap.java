package mygamewishlist.model.pojo;

public class Game2Scrap {

	private String name;
	private String storeName;
	private String url;

	public Game2Scrap() {
		super();
	}

	public Game2Scrap(String name, String storeName, String url) {
		super();
		this.name = name;
		this.storeName = storeName;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
