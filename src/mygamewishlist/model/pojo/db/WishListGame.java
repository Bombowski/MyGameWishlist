package mygamewishlist.model.pojo.db;

public class WishListGame {

	private String urlShop;
	private String urlGame;
	private int idList;
	private int idShop;
	private String name;
	private double defaultPrice;
	private double currentPrice;
	private double discount;
	private double lastNotifiedPrice;
	private double minPrice;
	private double maxPrice;

	public WishListGame() {
		super();
	}

	public WishListGame(String urlShop, String urlGame, int idList, int idShop, String name, double defaultPrice,
			double currentPrice, double discount, double lastNotifiedPrice, double minPrice, double maxPrice) {
		super();
		this.urlShop = urlShop;
		this.urlGame = urlGame;
		this.idList = idList;
		this.idShop = idShop;
		this.name = name;
		this.defaultPrice = defaultPrice;
		this.currentPrice = currentPrice;
		this.discount = discount;
		this.lastNotifiedPrice = lastNotifiedPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public String getUrlShop() {
		return urlShop;
	}

	public void setUrlShop(String urlShop) {
		this.urlShop = urlShop;
	}

	public String getUrlGame() {
		return urlGame;
	}

	public void setUrlGame(String urlGame) {
		this.urlGame = urlGame;
	}

	public int getIdList() {
		return idList;
	}

	public void setIdList(int idList) {
		this.idList = idList;
	}

	public int getIdShop() {
		return idShop;
	}

	public void setIdShop(int idShop) {
		this.idShop = idShop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getLastNotifiedPrice() {
		return lastNotifiedPrice;
	}

	public void setLastNotifiedPrice(double lastNotifiedPrice) {
		this.lastNotifiedPrice = lastNotifiedPrice;
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
