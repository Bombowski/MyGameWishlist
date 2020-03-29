package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.WishListGameMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class WishListGameDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static WishListGameMapper listMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getWlgMapper() {
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
			getWlgMapper();
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
	
	public WishListGame getGameFromListByIdUserUrl(int idUser, String url) {
		try {
			getWlgMapper();
			return listMapper.getGameFromListByIdUserUrl(idUser, url);
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
			getWlgMapper();
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
	
	public ArrayList<WishListGame2Scrap> getGamesFromListById(int idUser) {
		try {
			getWlgMapper();
			return listMapper.getGamesFromListById(idUser);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<WishListGame2Scrap>();
	}
	
	public void deleteGameWishlist(String url, int idList) {
		try {
			getWlgMapper();
			listMapper.deleteGameWishlist(url, idList);;
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
	
	public void updatePrices(ScrapedGame sg) {
		try {
			getWlgMapper();
			listMapper.updatePrices(sg);
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
	
	public void updateMinMax(double min, double max, String url, int idList) {
		try {
			getWlgMapper();
			listMapper.updateMinMax(min, max, url, idList);
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
