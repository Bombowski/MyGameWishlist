package mygamewishlist.model.pojo.db;

public class Developer {

	private int id;
	private String name;

	public Developer() {
		super();
	}

	public Developer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
