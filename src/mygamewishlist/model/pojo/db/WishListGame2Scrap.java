package mygamewishlist.model.pojo.db;

public class WishListGame2Scrap {

	private String storeUrl;
	private String gameUrl;
	private String storeName;
	private String gameName;
	private String img;
	private double defaultPrice;
	private double currentPrice;
	private double minPrice;
	private double maxPrice;

	public WishListGame2Scrap() {
		super();
	}

	public WishListGame2Scrap(String storeUrl, String gameUrl, String storeName, String gameName, String img,
			double defaultPrice, double currentPrice, double minPrice, double maxPrice) {
		super();
		this.storeUrl = storeUrl;
		this.gameUrl = gameUrl;
		this.storeName = storeName;
		this.gameName = gameName;
		this.img = img;
		this.defaultPrice = defaultPrice;
		this.currentPrice = currentPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public String getGameUrl() {
		return gameUrl;
	}

	public void setGameUrl(String gameUrl) {
		this.gameUrl = gameUrl;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

}
