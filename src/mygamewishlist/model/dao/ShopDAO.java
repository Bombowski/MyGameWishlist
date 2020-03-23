package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.ShopMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Store;

public class ShopDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static ShopMapper sotreMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getStMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		sotreMapper = session.getMapper(ShopMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
	
	public ArrayList<Store> getStores() {
		try {
			getStMapper();
			return sotreMapper.getStores();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<Store>();
	}
	
	public String getStoreUrlById(int id) {
		try {
			getStMapper();
			return sotreMapper.getStoreUrlById(id);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return "";
	}
}
