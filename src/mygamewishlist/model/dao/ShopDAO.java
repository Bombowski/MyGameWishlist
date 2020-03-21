package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.ShopMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Shop;

public class ShopDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static ShopMapper shopMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getShMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		shopMapper = session.getMapper(ShopMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
	
	public ArrayList<Shop> getShops() {
		try {
			getShMapper();
			return shopMapper.getShops();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<Shop>();
	}
	
	public String getShopUrlById(int id) {
		try {
			getShMapper();
			return shopMapper.getShopUrlById(id);
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
