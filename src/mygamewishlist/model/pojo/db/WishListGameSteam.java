package mygamewishlist.model.pojo.db;

public class WishListGameSteam extends WishListGame {

	private int appid;

	public WishListGameSteam() {
		super();
	}

	public WishListGameSteam(int appid) {
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
