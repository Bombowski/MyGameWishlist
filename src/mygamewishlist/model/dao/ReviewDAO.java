package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.ReviewMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;
import mygamewishlist.model.pojo.db.ReviewOfGame;

/**
 * @author Patryk
 *
 * Class that gets the Reviewinterface, and gets
 * the database conection
 */
public class ReviewDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static ReviewMapper reviewMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getRevMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		reviewMapper = session.getMapper(ReviewMapper.class);
	}
	
	/**
	 * Closes the conection
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
	
	public void addOrUpdateReview(Review rev) {
		try {
			getRevMapper();			
			reviewMapper.addOrUpdateReview(rev);
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
			reviewMapper.deleteReview(idUser, idGame);
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
	
	public ArrayList<ReviewOfGame> getGameReviews(int idUser, int idGame) {
		try {
			getRevMapper();
			return reviewMapper.getGameReviews(idUser, idGame);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<ReviewOfGame>();
	} 
	
	public ReviewOfGame getGameReview(int idUser, int idGame) {
		try {
			getRevMapper();
			return reviewMapper.getGameReview(idUser, idGame);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ReviewOfGame();
	} 
}
