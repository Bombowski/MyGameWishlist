package mygamewishlist.model.pojo.db;

public class ReviewUser {

	private String review;
	private double rating;
	private String gameName;
	private int idGame;

	public ReviewUser(String review, double rating, String gameName, int idGame) {
		super();
		this.review = review;
		this.rating = rating;
		this.gameName = gameName;
		this.idGame = idGame;
	}

	public ReviewUser() {
		super();
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

}
