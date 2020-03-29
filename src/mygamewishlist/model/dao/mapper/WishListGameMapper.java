package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public interface WishListGameMapper {

	public ArrayList<WishListGame> getListByIdUser(@Param("idUser") int idUser);
	
	public WishListGame getGameFromListByIdUserUrl(@Param("idUser") int idUser, @Param("url") String url);
	
	public void addGame2Wishlist(WishListGame wlg);
	
	public ArrayList<WishListGame2Scrap> getGamesFromListById(@Param("idUser") int idUser);
	
	public void deleteGameWishlist(@Param("url") String url, @Param("idList") int idList);
	
	public void updatePrices(ScrapedGame sg);
	
	public void updateMinMax(@Param("min") double min, @Param("max") double max
			, @Param("url") String url, @Param("idList") int idList);
}
