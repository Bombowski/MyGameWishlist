package mygamewishlist.model.pojo.db;

public class GameFull extends Game {

	private String idGenres;
	private int idDeveloper;

	public GameFull() {
		super();
	}

	public GameFull(String idGenres, int idDeveloper) {
		super();
		this.idGenres = idGenres;
		this.idDeveloper = idDeveloper;
	}

	public String getIdGenres() {
		return idGenres;
	}

	public void setIdGenres(String idGenres) {
		this.idGenres = idGenres;
	}

	public int getIdDeveloper() {
		return idDeveloper;
	}

	public void setIdDeveloper(int idDeveloper) {
		this.idDeveloper = idDeveloper;
	}

}
