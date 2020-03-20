package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Game;

public interface GameMapper {

	public Game getGame(@Param("id") int idGame);
	
	public ArrayList<Game> getGames();
	
	public void addGame(Game game);
	
	public void updateGame(Game game);
	
	public void deleteGame(@Param("id") int idGame);
}
