package mygamewishlist.model.pojo.db;

public class ReviewList {

	private double averageRating;
	private String name;
	private double userRating;
	private int idGame;
	private String genres;

	public ReviewList() {
		super();
	}

	public ReviewList(double averageRating, String name, double userRating, int idGame, String genres) {
		super();
		this.averageRating = averageRating;
		this.name = name;
		this.userRating = userRating;
		this.idGame = idGame;
		this.genres = genres;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ReviewList) {
			ReviewList tmp = (ReviewList)o;
			if (this.averageRating == tmp.getAverageRating() && this.name.equals(tmp.getName()) &&
					this.userRating == tmp.getUserRating() && this.idGame == tmp.getIdGame() &&
					this.genres.equals(tmp.genres)) {
				return true;
			}
		}
		return false;
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

	public double getUserRating() {
		return userRating;
	}

	public void setUserRating(double userRating) {
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
