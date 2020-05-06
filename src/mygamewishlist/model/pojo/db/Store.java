package mygamewishlist.model.pojo.db;

public class Store {

	private int id;
	private String name;
	private String url;
	private String queryPart;
	private String img;

	public Store() {
		super();
	}

	public Store(int id, String name, String url, String queryPart, String img) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.queryPart = queryPart;
		this.img = img;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQueryPart() {
		return queryPart;
	}

	public void setQueryPart(String queryPart) {
		this.queryPart = queryPart;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
