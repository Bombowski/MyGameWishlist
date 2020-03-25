package mygamewishlist.model.pojo.db;

public class Store {

	private int id;
	private String name;
	private String url;
	private String queryPart;

	public Store() {
		super();
	}

	public Store(int id, String name, String url, String queryPart) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.queryPart = queryPart;
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

}
