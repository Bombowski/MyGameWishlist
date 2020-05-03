package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.WishListGameMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.pojo.db.WishListGame2ScrapSteam;
import mygamewishlist.model.pojo.db.WishListGameSteam;

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
	
	public void addGame2Wishlist(ArrayList<WishListGame> games, int idUser) {
		try {
			getWlgMapper();
			for (WishListGame wlg : games) {
				if (wlg instanceof WishListGameSteam) {
					listMapper.addUrlWLPT(wlg.getUrlGame(), ((WishListGameSteam) wlg).getAppid() + "");
				} else {
					listMapper.addUrlWLPT(wlg.getUrlGame(), null);
				}	
				
				listMapper.addGame2Wishlist(wlg, idUser, 
						listMapper.getIdUrlByUrl(wlg.getUrlGame()));
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
	
	public ArrayList<WishListGame2ScrapSteam> getSteamGamesFromListById(int idUser) {
		try {
			getWlgMapper();
			return listMapper.getSteamGamesFromListById(idUser);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<WishListGame2ScrapSteam>();
	}
	
	public void deleteGameWishlist(String url, int idUser) {
		try {
			getWlgMapper();
			listMapper.deleteGameWishlist(listMapper.getIdUrlByUrl(url), idUser);
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
	
	public void updatePrices(ArrayList<ScrapedGame> games) {
		try {
			getWlgMapper();
			for (ScrapedGame sg : games) {
				listMapper.updatePrices(sg, listMapper.getIdUrlByUrl(sg.getUrlGame()));
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
	
	public void updateMinMax(double min, double max, String url, int idUser) {
		try {
			getWlgMapper();
			listMapper.updateMinMax(min, max, listMapper.getIdUrlByUrl(url), idUser);
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
	
	public int getIdUrlByUrl(String url) {
		try {
			getWlgMapper();
			return listMapper.getIdUrlByUrl(url);
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
}
