package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;

public class WishlistGameTests{

	private static WishListGame wlg;
	
	@BeforeClass
	public static void init() {
		wlg = new WishListGame();
		wlg.setCurrentPrice(20);
		wlg.setDefaultPrice(20);
		wlg.setDiscount(0);
		wlg.setGameName("test game");
		wlg.setIdStore(1);
		wlg.setImg("img");
		wlg.setMaxPrice(-1);
		wlg.setMinPrice(-1);
		wlg.setUrlGame("urlGame");
		wlg.setUrlStore("/app/20920");
	}

	@Test
	public void addGame2Wishlist() {
		ArrayList<WishListGame> tmpArr = new ArrayList<WishListGame>();
		tmpArr.add(wlg);
		Tests.cq_ejb.addGame2Wishlist(tmpArr, 1);
		assertEquals(true, Tests.cq_ejb.getListByIdUser(1).contains(wlg));
	}
	
	@Test
	public void getListByIdUser() {
		assertEquals(true, Tests.cq_ejb.getListByIdUser(1).size() > 0);
	}

	@Test
	public void getGameFromListByIdUserUrl() {
		Tests.cq_ejb.getGameFromListByIdUserUrl(1, wlg.getUrlGame());
	}

	@Test
	public void getGamesFromListById() {
		assertEquals(true, Tests.cq_ejb.getGamesFromListById(1).size() > 1);
	}

	@Test
	public void getSteamGamesFromListById() {
		assertEquals(true, Tests.cq_ejb.getSteamGamesFromListById(1));
	}

	@Test
	public void updatePrices() {
		ArrayList<ScrapedGame> tmpArr = new ArrayList<ScrapedGame>();
		ScrapedGame sg = new ScrapedGame();
		sg.setDefaultPrice(10);
		sg.setCurrentPrice(10);
		sg.setUrlGame(wlg.getUrlGame());
		tmpArr.add(sg);
		
		Tests.cq_ejb.updatePrices(tmpArr);
		WishListGame tmpWlg = Tests.cq_ejb.getGameFromListByIdUserUrl(1, sg.getUrlGame());
		assertEquals(true, tmpWlg.getCurrentPrice() == sg.getCurrentPrice() && 
				tmpWlg.getDefaultPrice() == sg.getDefaultPrice());
	}

	@Test
	public void updateMinMax() {
		Tests.cq_ejb.updateMinMax(10, 10, wlg.getUrlGame(), 1);
		WishListGame tmpWlg = Tests.cq_ejb.getGameFromListByIdUserUrl(1, wlg.getUrlGame());
		assertEquals(true, tmpWlg.getMaxPrice() == 10 && tmpWlg.getMinPrice() == 10);
	}

	@Test
	public void deleteGameWishlist() {
		Tests.cq_ejb.deleteGameWishlist(wlg.getUrlGame(), 1);
		assertEquals(0, Tests.cq_ejb.getGameFromListByIdUserUrl(1, wlg.getUrlGame()).getIdStore());
	}
}
