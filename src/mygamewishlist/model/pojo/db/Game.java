package mygamewishlist.model.pojo.db;

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
