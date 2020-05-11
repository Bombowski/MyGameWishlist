package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 *
 * POJO class that contians all of the wishlist game data
 */
public class WishListGame {

	private String urlGame;
	private String urlStore;
	private int idStore;
	private String gameName;
	private String img;
	private double defaultPrice;
	private double currentPrice;
	private double discount;
	private double minPrice;
	private double maxPrice;

	public WishListGame() {
		super();
	}

	public WishListGame(String urlGame, String urlStore, int idStore, String gameName, String img,
			double defaultPrice, double currentPrice, double discount, double minPrice, double maxPrice) {
		super();
		this.urlGame = urlGame;
		this.urlStore = urlStore;
		this.idStore = idStore;
		this.gameName = gameName;
		this.img = img;
		this.defaultPrice = defaultPrice;
		this.currentPrice = currentPrice;
		this.discount = discount;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}
	
	public WishListGame(WishListGame wlg) {
		super();
		this.urlGame = wlg.getUrlGame();
		this.urlStore = wlg.getUrlStore();
		this.idStore = wlg.getIdStore();
		this.gameName = wlg.getGameName();
		this.img = wlg.getImg();
		this.defaultPrice = wlg.getDefaultPrice();
		this.currentPrice = wlg.getCurrentPrice();
		this.discount = wlg.getDiscount();
		this.minPrice = wlg.getMinPrice();
		this.maxPrice = wlg.getMaxPrice();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof WishListGame) {
			WishListGame wlg = (WishListGame) o;
			if (this.urlGame.equals(wlg.getUrlGame()) && this.urlStore.equals(wlg.getUrlStore()) &&
					this.idStore == wlg.getIdStore() && this.gameName.equals(wlg.getGameName()) &&
					this.img.equals(wlg.getImg()) && this.defaultPrice == wlg.getDefaultPrice() &&
					this.currentPrice == wlg.getCurrentPrice() && this.discount == wlg.getDiscount() &&
					this.minPrice == wlg.getMinPrice() && this.maxPrice == wlg.getMaxPrice()) {
				return true;
			}
		}
		return false;
	}

	public String getUrlGame() {
		return urlGame;
	}

	public void setUrlGame(String urlGame) {
		this.urlGame = urlGame;
	}

	public String getUrlStore() {
		return urlStore;
	}

	public void setUrlStore(String urlStore) {
		this.urlStore = urlStore;
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
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
