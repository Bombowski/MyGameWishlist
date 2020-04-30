package mygamewishlist.model.pojo.db;

public class ReviewList {

	private double averageRating;
	private String name;
	private int userRating;
	private int idGame;
	private String genres;

	public ReviewList() {
		super();
	}

	public ReviewList(double averageRating, String name, int userRating, int idGame, String genres) {
		super();
		this.averageRating = averageRating;
		this.name = name;
		this.userRating = userRating;
		this.idGame = idGame;
		this.genres = genres;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserRating() {
		return userRating;
	}

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

}
