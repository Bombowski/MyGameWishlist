package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.SteamMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.SteamGame;

/**
 * @author Patryk
 *
 * Class that gets the Steam interface, and gets
 * the database conection
 */
public class SteamDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static SteamMapper steamMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getUserMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		steamMapper = session.getMapper(SteamMapper.class);
	}
	
	/**
	 * Closes the conection
	 */
	private static void closeAll() {
		session.close();
	}
	
	public ArrayList<Integer> getSteamGameIdsByName(String name) {
		try {
			getUserMapper();
			return steamMapper.getSteamGameIdsByName(
					new StringBuilder().append("%").append(name).append("%").toString());
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<Integer>();
	}
	
	public void addSteamGames(ArrayList<SteamGame> sg) {
		try {
			getUserMapper();
			for (int i = 0; i < sg.size(); i++) {
				steamMapper.addGame(sg.get(i));
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
	
	public void deleteSteamGameById(int id) {
		try {
			getUserMapper();
			steamMapper.deleteSteamGameById(id);
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
