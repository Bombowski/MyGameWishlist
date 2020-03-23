package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.ReviewMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;

public class ReviewDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static ReviewMapper reviewMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getRevMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		reviewMapper = session.getMapper(ReviewMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
		
	public ArrayList<ReviewList> getReviewList(int idUser) {
		try {
			getRevMapper();
			return reviewMapper.getReviewList(idUser);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<ReviewList>();
	}
	
	public ArrayList<ReviewList> getReviewListNotLogged() {
		try {
			getRevMapper();
			return reviewMapper.getReviewListNotLogged();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<ReviewList>();
	}
	
	public void updateReview(Review rev) {
		try {
			getRevMapper();
			reviewMapper.updateReview(rev);
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
	
	public void deleteReview(int idUser, int idGame) {
		try {
			getRevMapper();
			reviewMapper.deleteReview(idUser, idGame);;
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
