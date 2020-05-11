package mygamewishlist.model.pojo.db;

/**
 * @author Patryk
 *
 * POJO class that contians all of the user data
 */
public class User {

	private int id;
	private String email;
	private String name;
	private int admin;

	public User() {
	}

	public User(int id, String email, String name, int admin) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.admin = admin;
	}

	/**
	 * Doesn't compare user id's
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			try {
				User usr = (User) o;
				if (usr.getAdmin() == this.admin && usr.getEmail().equals(this.email) &&
						usr.getName().equals(this.name)) {
					return true;
				}				
			} catch (NullPointerException e) {
				return false;
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
