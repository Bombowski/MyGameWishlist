package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.WishListGame;

public interface WishListGameMapper {

	public ArrayList<WishListGame> getListByIdUser(@Param("idUser") int idUser);
	
	public WishListGame getGameFromListByIdUser(@Param("idUser") int idUser, @Param("url") String url);
	
	public void addGame2Wishlist(WishListGame wlg);
	
	public ArrayList<WishListGame> getGamesByStore(@Param("idStore") int idStore);
}
