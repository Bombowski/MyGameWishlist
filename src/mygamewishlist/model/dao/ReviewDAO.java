package mygamewishlist.model.dao;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.ReviewMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.User;

public class ReviewDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static ReviewMapper reviewMapper;
	
	/**
	 * Crea la conexion con la base de datos y consigue
	 * la interfaz
	 */
	private static void getViaMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		reviewMapper = session.getMapper(ReviewMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
	
	public User getNotVisited(String email) {
		try {
			getViaMapper();
//			return userMapper.userExists(email);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new User();
	}
}
