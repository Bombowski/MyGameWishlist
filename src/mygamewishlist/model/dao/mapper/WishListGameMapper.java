package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.pojo.db.WishListGame2ScrapSteam;
import mygamewishlist.model.pojo.db.WishListGameSteam;

public interface WishListGameMapper {

	public ArrayList<WishListGame> getListByIdUser(@Param("idUser") int idUser);
	
	public WishListGame getGameFromListByIdUserUrl(@Param("idUser") int idUser, @Param("url") String url);
	
	public void addGame2Wishlist(@Param("wlg") WishListGame wlg, @Param("idUser") int idUser);
	
	public void addSteamGame2Wishlist(@Param("wlg") WishListGameSteam wlg, @Param("idUser") int idUser);
	
	public ArrayList<WishListGame2Scrap> getGamesFromListById(@Param("idUser") int idUser);
	
	public ArrayList<WishListGame2ScrapSteam> getSteamGamesFromListById(@Param("idUser") int idUser);
	
	public void deleteGameWishlist(@Param("url") String url, @Param("idUser") int idUser);
	
	public void updatePrices(@Param("sg") ScrapedGame sg, @Param("idUser") int idUser);
	
	public void updateMinMax(@Param("min") double min, @Param("max") double max
			, @Param("url") String url, @Param("idUser") int idUser);
}
