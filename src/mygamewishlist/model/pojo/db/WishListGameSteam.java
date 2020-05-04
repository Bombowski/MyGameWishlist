package mygamewishlist.model.pojo.db;

public class WishListGameSteam extends WishListGame {

	private int appid;

	public WishListGameSteam() {
		super();
	}

	public WishListGameSteam(WishListGame wlg) {
		super();
		this.setUrlGame(wlg.getUrlGame());
		this.setUrlStore(wlg.getUrlStore());
		this.setIdStore(wlg.getIdStore());
		this.setGameName(wlg.getGameName());
		this.setImg(wlg.getImg());
		this.setDefaultPrice(wlg.getDefaultPrice());
		this.setCurrentPrice(wlg.getCurrentPrice());
		this.setDiscount(wlg.getDiscount());
		this.setMinPrice(wlg.getMinPrice());
		this.setMaxPrice(wlg.getMaxPrice());
	}
	
	public WishListGameSteam(WishListGameSteam wlg) {
		super();
		this.setUrlGame(wlg.getUrlGame());
		this.setUrlStore(wlg.getUrlStore());
		this.setIdStore(wlg.getIdStore());
		this.setGameName(wlg.getGameName());
		this.setImg(wlg.getImg());
		this.setDefaultPrice(wlg.getDefaultPrice());
		this.setCurrentPrice(wlg.getCurrentPrice());
		this.setDiscount(wlg.getDiscount());
		this.setMinPrice(wlg.getMinPrice());
		this.setMaxPrice(wlg.getMaxPrice());
		this.appid = wlg.getAppid();
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
