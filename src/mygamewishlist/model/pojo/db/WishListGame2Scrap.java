package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 *
 * POJO class that contains data of the wishlist game,
 * and all of the data necessary for scraping
 */
public class WishListGame2Scrap {

	private String urlStore;
	private String urlGame;
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

	public WishListGame2Scrap(String urlStore, String urlGame, String storeName, String gameName, String img,
			double defaultPrice, double currentPrice, double minPrice, double maxPrice) {
		super();
		this.urlStore = urlStore;
		this.urlGame = urlGame;
		this.storeName = storeName;
		this.gameName = gameName;
		this.img = img;
		this.defaultPrice = defaultPrice;
		this.currentPrice = currentPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}
	
	@Override
	public String toString() {
		return "WishListGame2Scrap [urlStore=" + urlStore + ", urlGame=" + urlGame + ", storeName=" + storeName
				+ ", gameName=" + gameName + ", img=" + img + ", defaultPrice=" + defaultPrice + ", currentPrice="
				+ currentPrice + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + "]";
	}

	public String getUrlStore() {
		return urlStore;
	}

	public void setUrlStore(String urlStore) {
		this.urlStore = urlStore;
	}

	public String getUrlGame() {
		return urlGame;
	}

	public void setUrlGame(String urlGame) {
		this.urlGame = urlGame;
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
