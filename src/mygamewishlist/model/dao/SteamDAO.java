package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.SteamMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.SteamGame;

public class SteamDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static SteamMapper steamMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getUserMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		steamMapper = session.getMapper(SteamMapper.class);
	}
	
	/**
	 * Cierra la conexion
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
			for (SteamGame game : sg) {
				steamMapper.addGame(game);
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
