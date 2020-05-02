package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Developer;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.GameFull;

public interface GameMapper {

	public GameFull getGameById(@Param("id") int idGame);
	
	public Integer getGameIdByTitle(@Param("title") String title);
	
	public ArrayList<Game> getGames();
	
	public void addGame(Game game);
	
	public void updateGame(GameFull game);
	
	public void deleteGame(@Param("id") int idGame);

	public Developer getDeveloperById(@Param("id") int id);

	public ArrayList<Developer> getDevelopers();
	
	public void addGameGenre(@Param("idGame") int idGame, @Param("idGenre") int idGenre);
}
