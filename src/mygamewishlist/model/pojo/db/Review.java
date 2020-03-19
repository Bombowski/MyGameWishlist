package mygamewishlist.model.pojo.db;

public class Review {

	private int idUser;
	private int idGame;
	private int rating;

	public Review(int idUser, int idGame, int rating) {
		super();
		this.idUser = idUser;
		this.idGame = idGame;
		this.rating = rating;
	}

	public Review() {
		super();
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
