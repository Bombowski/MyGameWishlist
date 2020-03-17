package mygamewishlist.model.pojo;

public class User {

	private String email;
	private String passwd;
	private int admin;

	public User() {}
	
	public User(String email, String passwd, int admin) {
		super();
		this.email = email;
		this.passwd = passwd;
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

}
