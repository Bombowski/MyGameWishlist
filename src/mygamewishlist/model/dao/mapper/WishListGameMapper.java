package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.pojo.db.WishListGame2ScrapSteam;

public interface WishListGameMapper {

	public ArrayList<WishListGame> getListByIdUser(@Param("idUser") int idUser);
	
	public WishListGame getGameFromListByIdUserUrl(@Param("idUser") int idUser, @Param("url") String url);
	
	public void addGame2Wishlist(@Param("idUser") int idUser, @Param("idUrl") int idUrl,
			@Param("min") double min, @Param("max") double max);
	
	public void addUrlWLPT(@Param("wlg") WishListGame wlg, @Param("steamAppid") String steamAppid);
	
	public ArrayList<WishListGame2Scrap> getGamesFromListById(@Param("idUser") int idUser);
	
	public ArrayList<WishListGame2ScrapSteam> getSteamGamesFromListById(@Param("idUser") int idUser);
	
	public void deleteGameWishlist(@Param("idUrl") int idUrl, @Param("idUser") int idUser);
	
	public void updatePrices(ScrapedGame sg);
	
	public void updateMinMax(@Param("min") double min, @Param("max") double max, 
			@Param("idUrl") int idUrl, @Param("idUser") int idUser);
	
	public Integer getIdUrlByUrl(@Param("url") String url);
}
