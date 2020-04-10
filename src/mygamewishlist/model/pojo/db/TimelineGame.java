package mygamewishlist.model.pojo.db;

public class TimelineGame {

	private String urlGame;
	private String time;
	private double price;
	private double discount;

	public TimelineGame() {
		super();
	}

	public TimelineGame(String urlGame, String time, double price, double discount) {
		super();
		this.urlGame = urlGame;
		this.time = time;
		this.price = price;
		this.discount = discount;
	}

	public String getUrlGame() {
		return urlGame;
	}

	public void setUrlGame(String urlGame) {
		this.urlGame = urlGame;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

}
