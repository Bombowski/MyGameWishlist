package mygamewishlist.model.pojo;

public class User {

	private String email;
	private String name;
	private int admin;

	public User() {
	}

	public User(String email, String name, int admin) {
		super();
		this.email = email;
		this.name = name;
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

}
