package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.GameMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Developer;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.GameFull;

/**
 * @author Patryk
 *
 * Class that gets the WishListGame interface, and gets
 * the database conection
 */
public class GameDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static GameMapper gameMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getGaMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		gameMapper = session.getMapper(GameMapper.class);
	}
	
	/**
	 * Closes the conection
	 */
	private static void closeAll() {
		session.close();
	}
	
	public GameFull getGameById(int idGame) {
		try {
			getGaMapper();
			return gameMapper.getGameById(idGame);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new GameFull();
	}
	
	public ArrayList<Game> getGames() {
		try {
			getGaMapper();
			return gameMapper.getGames();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<Game>();
	}
	
	public void addGame(GameFull game) {
		try {
			getGaMapper();
			// Adds the game
			gameMapper.addGame(game);
			// Gets the id of this game
			int gameId = gameMapper.getGameIdByTitle(game.getName());
			
			/*
			 * Splits the string that contains the id's of genres, and adds
			 * all of the genres.
			 */
			for (String str : game.getIdGenres().split(",")) {
				gameMapper.addGameGenre(gameId, Integer.parseInt(str));
			}
			
			session.commit();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
	}
	
	public int getGameIdByTitle(String title) {
		try {
			getGaMapper();
			return gameMapper.getGameIdByTitle(title);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return 0;
	}
	
	public void updateGame(GameFull game) {
		try {
			getGaMapper();
			gameMapper.updateGame(game);
			session.commit();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
	}
	
	public void deleteGame(int idGame) {
		try {
			getGaMapper();
			gameMapper.deleteGame(idGame);
			session.commit();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
	}

	public ArrayList<Developer> getDevelopers() {
		try {
			getGaMapper();
			return gameMapper.getDevelopers();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<Developer>();
	}

	public Developer getDeveloperById(int id) {
		try {
			getGaMapper();
			return gameMapper.getDeveloperById(id);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new Developer();
	}
	
	public void addGameGenre(int idGame, String[] idGenres) {
		try {
			getGaMapper();
			/*
			 * for each genre id in the array, there is an insert 
			 */
			for (String str : idGenres) {
				gameMapper.addGameGenre(idGame, Integer.parseInt(str));
			}
			session.commit();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
	}
}
