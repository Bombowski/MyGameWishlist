package mygamewishlist.model.pojo;

/**
 * @author Patryk
 *
 * Class that contains data from scraped game
 */
public class ScrapedGame {

	private String urlStore;
	private String urlGame;
	private String fullName;
	private String img;
	private String storeName;
	private double defaultPrice;
	private double currentPrice;
	private double currentDiscount;

	public ScrapedGame() {
		super();
	}

	public ScrapedGame(String urlStore, String urlGame, String fullName, String img, String storeName,
			double defaultPrice, double currentPrice, double currentDiscount) {
		super();
		this.urlStore = urlStore;
		this.urlGame = urlGame;
		this.fullName = fullName;
		this.img = img;
		this.storeName = storeName;
		this.defaultPrice = defaultPrice;
		this.currentPrice = currentPrice;
		this.currentDiscount = currentDiscount;
	}

	public String getUrlStore() {
		return urlStore;
	}
	
	@Override
	public String toString() {
		return "ScrapedGame [urlStore=" + urlStore + ", urlGame=" + urlGame + ", fullName=" + fullName + ", img=" + img
				+ ", storeName=" + storeName + ", defaultPrice=" + defaultPrice + ", currentPrice=" + currentPrice
				+ ", currentDiscount=" + currentDiscount + "]";
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public double getCurrentDiscount() {
		return currentDiscount;
	}

	public void setCurrentDiscount(double currentDiscount) {
		this.currentDiscount = currentDiscount;
	}

}
