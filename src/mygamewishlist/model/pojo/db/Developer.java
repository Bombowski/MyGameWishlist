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

	@Override
	public boolean equals(Object o) {
		if (o instanceof Developer) {
			Developer dev = (Developer) o;
			if (dev.getId() == this.getId() && dev.getName().equals(this.getName())) {
				return true;
			}
		}
		return false;
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
