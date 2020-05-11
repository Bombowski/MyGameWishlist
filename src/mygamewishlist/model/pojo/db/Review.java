package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 *
 * POJO Class that contains data of a review
 */
public class Review {

	private int idUser;
	private int idGame;
	private double rating;
	private String review;

	public Review() {
		super();
	}

	public Review(int idUser, int idGame, double rating, String review) {
		super();
		this.idUser = idUser;
		this.idGame = idGame;
		this.rating = rating;
		this.review = review;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Review) {
			Review tmp = (Review)o;
			if (this.idUser == tmp.getIdUser() && this.idGame == tmp.getIdGame() &&
					this.rating == tmp.getRating() && this.review == tmp.getReview()) {
				return true;
			}
		}
		return false;
	}
	
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
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
