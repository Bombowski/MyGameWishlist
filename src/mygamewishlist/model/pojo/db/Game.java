package mygamewishlist.model.pojo.db;

public class Game {

	private String name;

	public Game(String name) {
		super();
		this.name = name;
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

}
