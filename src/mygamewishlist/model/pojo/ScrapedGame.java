package mygamewishlist.model.pojo;

public class ScrapedGame {

	private String url;
	private String fullName;
	private String img;
	private int storeId;
	private double defaultPrice;
	private double currentPrice;
	private double currentDiscount;

	public ScrapedGame() {
		super();
	}

	public ScrapedGame(String url, String fullName, String img, int storeId, double defaultPrice,
			double currentPrice, double currentDiscount) {
		super();
		this.url = url;
		this.fullName = fullName;
		this.img = img;
		this.storeId = storeId;
		this.defaultPrice = defaultPrice;
		this.currentPrice = currentPrice;
		this.currentDiscount = currentDiscount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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
