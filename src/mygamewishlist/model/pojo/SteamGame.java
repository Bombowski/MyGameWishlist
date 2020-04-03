package mygamewishlist.model.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SteamGame {

	private String name;
	private int appid;

	public SteamGame(String name, int appid) {
		super();
		this.name = name;
		this.appid = appid;
	}

	@Override
	public String toString() {
		return "SteamGame [name=" + name + ", appid=" + appid + "]";
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
