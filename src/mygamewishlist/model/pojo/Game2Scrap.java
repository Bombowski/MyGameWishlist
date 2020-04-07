package mygamewishlist.model.pojo;

public class Game2Scrap {

	private String name;
	private String storeName;
	private String url;
	private String queryPart;

	public Game2Scrap() {
		super();
	}

	public Game2Scrap(String name, String storeName, String url, String queryPart) {
		super();
		this.name = name;
		this.storeName = storeName;
		this.url = url;
		this.queryPart = queryPart;
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

	public String getQueryPart() {
		return queryPart;
	}

	public void setQueryPart(String queryPart) {
		this.queryPart = queryPart;
	}

}
