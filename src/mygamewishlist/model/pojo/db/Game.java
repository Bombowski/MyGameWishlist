package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 *
 * POJO class that contains data of a game
 */
public class Game {

	private String name;
	private String description;
	private String genres;
	private String releaseDate;
	private int id;
	private String developer;

	public Game() {
		super();
	}

	public Game(String name, String description, String genres, String releaseDate, int id, String developer) {
		super();
		this.name = name;
		this.description = description;
		this.genres = genres;
		this.releaseDate = releaseDate;
		this.id = id;
		this.developer = developer;
	}

	public Game(String name, String description, String releaseDate, int id, String developer) {
		super();
		this.name = name;
		this.description = description;
		this.releaseDate = releaseDate;
		this.id = id;
		this.developer = developer;
	}

	public Game(String name, String description, String genres, String releaseDate, String developer) {
		super();
		this.name = name;
		this.description = description;
		this.genres = genres;
		this.releaseDate = releaseDate;
		this.developer = developer;
	}

	public Game(String name, String description, String releaseDate, String developer) {
		super();
		this.name = name;
		this.description = description;
		this.releaseDate = releaseDate;
		this.developer = developer;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Game) {
			Game ga = (Game)o;
			if (this.description.equals(ga.description) && this.developer.equals(ga.getDeveloper()) &&
					this.genres.equals(ga.genres) && this.name.equals(ga.getName()) && 
					this.releaseDate.equals(ga.releaseDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

}
