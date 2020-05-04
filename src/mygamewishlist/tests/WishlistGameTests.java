package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGameSteam;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WishlistGameTests{

	private static WishListGame wlg;
	private static WishListGameSteam wlgs;
	private final int idUser = 1;
	
	@BeforeClass
	public static void init() {
		wlg = new WishListGame();
		wlg.setCurrentPrice(20);
		wlg.setDefaultPrice(20);
		wlg.setDiscount(0);
		wlg.setGameName("test game");
		wlg.setIdStore(1);
		wlg.setImg("img");
		wlg.setMaxPrice(2);
		wlg.setMinPrice(2);
		wlg.setUrlGame("urlGame");
		wlg.setUrlStore("https://store.steampowered.com");
		
		wlgs = new WishListGameSteam(wlg);
		wlgs.setAppid(10);
		wlgs.setUrlGame("urlGame2");
		wlgs.setGameName("test game2");
	}

	@Test
	public void addGame2Wishlist() {
		ArrayList<WishListGame> tmpArr = new ArrayList<WishListGame>();
		
		tmpArr.add(wlg);
		tmpArr.add(wlgs);
		Tests.cq_ejb.addGame2Wishlist(tmpArr, idUser);
		assertEquals(true, Tests.cq_ejb.getListByIdUser(idUser).contains(wlg));
	}
	
	@Test
	public void getListByIdUser() {
		assertEquals(true, Tests.cq_ejb.getListByIdUser(idUser).size() > 0);
	}

	@Test
	public void getGameFromListByIdUserUrl() {
		assertEquals(wlg, Tests.cq_ejb.getGameFromListByIdUserUrl(idUser, wlg.getUrlGame()));
	}

	@Test
	public void getGamesFromListById() {
		assertEquals(true, Tests.cq_ejb.getGamesFromListById(idUser).size() > 0);
	}

	@Test
	public void getSteamGamesFromListById() {
		assertEquals(true, Tests.cq_ejb.getSteamGamesFromListById(idUser).size() > 0);
	}

	@Test
	public void wUpdatePrices() {
		ArrayList<ScrapedGame> tmpArr = new ArrayList<ScrapedGame>();
		ScrapedGame sg = new ScrapedGame();
		sg.setDefaultPrice(10);
		sg.setCurrentPrice(10);
		sg.setUrlGame(wlg.getUrlGame());
		tmpArr.add(sg);
		
		Tests.cq_ejb.updatePrices(tmpArr);
		WishListGame tmpWlg = Tests.cq_ejb.getGameFromListByIdUserUrl(idUser, sg.getUrlGame());
		assertEquals(true, tmpWlg.getCurrentPrice() == sg.getCurrentPrice() && 
				tmpWlg.getDefaultPrice() == sg.getDefaultPrice());
	}

	@Test
	public void wUpdateMinMax() {
		Tests.cq_ejb.updateMinMax(10, 10, wlg.getUrlGame(), idUser);
		WishListGame tmpWlg = Tests.cq_ejb.getGameFromListByIdUserUrl(idUser, wlg.getUrlGame());
		assertEquals(true, tmpWlg.getMaxPrice() == 10 && tmpWlg.getMinPrice() == 10);
	}

	@Test
	public void zDeleteGameWishlist() {
		Tests.cq_ejb.deleteGameWishlist(wlg.getUrlGame(), idUser);
		Tests.cq_ejb.deleteGameWishlist(wlgs.getUrlGame(), idUser);
		assertEquals(true, Tests.cq_ejb.getGameFromListByIdUserUrl(idUser, wlg.getUrlGame()) == null &&
				Tests.cq_ejb.getGameFromListByIdUserUrl(idUser, wlgs.getUrlGame()) == null);
	}
}
