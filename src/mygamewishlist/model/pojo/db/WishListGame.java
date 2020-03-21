package mygamewishlist.model.pojo.db;

public class WishListGame {

	private int idUser;
	private String url;
	private int idShop;
	private String name;
	private double defaultPrice;
	private double lastNotifiedPrice;
	private double minPrice;
	private double maxPrice;

	public WishListGame(int idUser, String url, int idShop, String name, double defaultPrice, double lastNotifiedPrice,
			double minPrice, double maxPrice) {
		super();
		this.idUser = idUser;
		this.url = url;
		this.idShop = idShop;
		this.name = name;
		this.defaultPrice = defaultPrice;
		this.lastNotifiedPrice = lastNotifiedPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public WishListGame() {
		super();
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
