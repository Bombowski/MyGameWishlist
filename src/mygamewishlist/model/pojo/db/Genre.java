package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 *
 * POJO class that contains data of a genre
 */
public class Genre {

	private int id;
	private String name;

	public Genre() {
		super();
	}

	public Genre(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Genre) {
			Genre ge = (Genre) o;
			if (this.id == ge.getId() && this.name.equals(ge.getName())) {
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
