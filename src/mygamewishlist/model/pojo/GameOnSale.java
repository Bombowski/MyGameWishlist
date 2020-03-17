package mygamewishlist.model.pojo;

public class GameOnSale {

	private String name;
	private String url;
	private double originalPrice;
	private double currentPrice;
	private String discout;

	public GameOnSale(String name, String url, double originalPrice, double currentPrice, String discout) {
		super();
		this.name = name;
		this.url = url;
		this.originalPrice = originalPrice;
		this.currentPrice = currentPrice;
		this.discout = discout;
	}

	public GameOnSale() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getDiscout() {
		return discout;
	}

	public void setDiscout(String discout) {
		this.discout = discout;
	}

}
