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

	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof GameFull) {
			GameFull gf = (GameFull) o;
			if (this.idDeveloper == gf.idDeveloper && this.idGenres.equals(gf.idGenres) &&
					((Game) gf).equals((Game)this)) {
				return true;
			}
		}		
		return false;
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
