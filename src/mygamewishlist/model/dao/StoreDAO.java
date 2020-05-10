package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.StoreMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Store;

/**
 * @author Patryk
 *
 * Class that gets the Store interface, and gets
 * the database conection
 */
public class StoreDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static StoreMapper storeMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getStMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		storeMapper = session.getMapper(StoreMapper.class);
	}
	
	/**
	 * Closes the conection
	 */
	private static void closeAll() {
		session.close();
	}
	
	public ArrayList<Store> getStores() {
		try {
			getStMapper();
			return storeMapper.getStores();
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
	
	public ArrayList<String> getStoreNames() {
		try {
			getStMapper();
			return storeMapper.getStoreNames();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<String>();
	}
	
	public Store getStoreByName(String name) {
		try {
			getStMapper();
			return storeMapper.getStoreByName(name);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new Store();
	}
}
