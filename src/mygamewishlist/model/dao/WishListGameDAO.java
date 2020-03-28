package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.WishListGameMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.WishListGame;

public class WishListGameDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static WishListGameMapper listMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getViaMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		listMapper = session.getMapper(WishListGameMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
	
	public ArrayList<WishListGame> getListByIdUser(int id) {
		try {
			getViaMapper();
			return listMapper.getListByIdUser(id);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<WishListGame>();
	}
	
	public WishListGame getGameFromListByIdUser(int idUser, String url) {
		try {
			getViaMapper();
			return listMapper.getGameFromListByIdUser(idUser, url);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new WishListGame();
	}
	
	public void addGame2Wishlist(ArrayList<WishListGame> games) {
		try {
			getViaMapper();
			for (WishListGame wlg : games) {
				listMapper.addGame2Wishlist(wlg);
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
	
	public ArrayList<WishListGame> getGamesByStore(int idStore) {
		try {
			getViaMapper();
			return listMapper.getGamesByStore(idStore);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<WishListGame>();
	}
}
