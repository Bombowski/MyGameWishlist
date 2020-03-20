package mygamewishlist.model.pojo.db;

public class Game {

	private String name;
	private String description;
	private int id;

	public Game(String name, String description, int id) {
		super();
		this.name = name;
		this.description = description;
		this.id = id;
	}
	
	public Game(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Game() {
		super();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
