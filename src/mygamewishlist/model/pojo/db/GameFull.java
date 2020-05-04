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
	
	public GameFull(GameFull gf) {
		this.idDeveloper = gf.getIdDeveloper();
		this.idGenres = gf.getIdGenres();
		this.setDescription(gf.getDescription());
		this.setDeveloper(gf.getDeveloper());
		this.setGenres(gf.getGenres());
		this.setId(gf.getId());
		this.setName(gf.getName());
		this.setReleaseDate(gf.getReleaseDate());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof GameFull) {
			GameFull gf = (GameFull) o;
			if (this.idDeveloper == gf.idDeveloper && this.idGenres.equals(gf.idGenres) &&
					gf.getDescription().equals(gf.getDescription()) && 
					this.getDeveloper().equals(gf.getDeveloper()) &&
					this.getGenres().equals(gf.getGenres()) && this.getName().equals(gf.getName()) && 
					this.getReleaseDate().equals(gf.getReleaseDate())) {
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
