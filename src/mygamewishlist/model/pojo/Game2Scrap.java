package mygamewishlist.model.pojo;

public class Game2Scrap {

	private String name;
	private String storeName;
	private String storeUrl;
	private String queryPart;

	public Game2Scrap() {
		super();
	}

	public Game2Scrap(String name, String storeName, String storeUrl, String queryPart) {
		super();
		this.name = name;
		this.storeName = storeName;
		this.storeUrl = storeUrl;
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

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String url) {
		this.storeUrl = url;
	}

	public String getQueryPart() {
		return queryPart;
	}

	public void setQueryPart(String queryPart) {
		this.queryPart = queryPart;
	}

}
