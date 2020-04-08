package mygamewishlist.model.pojo.db;

public class WishListGame2ScrapSteam extends WishListGame2Scrap {

	private int appid;

	public WishListGame2ScrapSteam() {
		super();
	}

	public WishListGame2ScrapSteam(int appid) {
		super();
		this.appid = appid;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

}
