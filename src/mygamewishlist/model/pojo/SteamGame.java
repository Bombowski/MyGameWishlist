package mygamewishlist.model.pojo;

/**
 * @author Patryk
 *
 * POJO class form steam games, it contains steam game title and appid
 */
public class SteamGame {

	private String name;
	private int appid;

	public SteamGame(String name, int appid) {
		super();
		this.name = name;
		this.appid = appid;
	}

	public SteamGame() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

}
