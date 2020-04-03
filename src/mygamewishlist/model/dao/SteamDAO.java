package mygamewishlist.model.dao;

import java.util.ArrayList;
import java.util.List;

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
	
	public int getGameIdsByName(String name) {
		try {
			getUserMapper();
			return steamMapper.getGameIdsByName(name);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return -1;
	}
	
	public void addSteamGames(ArrayList<SteamGame> sg) {
		try {
			getUserMapper();
			List list = sg;
			
			steamMapper.addGame(list);
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
