package mygamewishlist.model.pojo.db;

public class ReviewOfGame {

	private String usrName;
	private double rating;
	private String review;

	public ReviewOfGame() {
		super();
	}

	public ReviewOfGame(String usrName, double rating, String review) {
		super();
		this.usrName = usrName;
		this.rating = rating;
		this.review = review;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

}
