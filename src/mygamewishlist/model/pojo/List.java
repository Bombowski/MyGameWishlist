package mygamewishlist.model.pojo;

public class List {

	private int id_User;
	private String url;
	private int id_shop;
	private String name;
	private double default_Price;

	public List(int id_User, String url, int id_shop, String name, double default_Price) {
		super();
		this.id_User = id_User;
		this.url = url;
		this.id_shop = id_shop;
		this.name = name;
		this.default_Price = default_Price;
	}

	public List() {
		super();
	}

	public int getId_User() {
		return id_User;
	}

	public void setId_User(int id_User) {
		this.id_User = id_User;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId_shop() {
		return id_shop;
	}

	public void setId_shop(int id_shop) {
		this.id_shop = id_shop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDefault_Price() {
		return default_Price;
	}

	public void setDefault_Price(double default_Price) {
		this.default_Price = default_Price;
	}

}
