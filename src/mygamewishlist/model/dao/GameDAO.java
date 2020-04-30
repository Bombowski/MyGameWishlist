package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.GameMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Developer;
import mygamewishlist.model.pojo.db.Game;

public class GameDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static GameMapper gameMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getGaMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		gameMapper = session.getMapper(GameMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
	
	public Game getGame(int idGame) {
		try {
			getGaMapper();
			return gameMapper.getGame(idGame);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new Game();
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
	
	public void addGame(Game game) {
		try {
			getGaMapper();
			gameMapper.addGame(game);
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
	
	public void updateGame(Game game) {
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
}
