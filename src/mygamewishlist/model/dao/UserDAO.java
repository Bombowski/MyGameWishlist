package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.UserMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 *
 * Class that gets the User interface, and gets
 * the database conection
 */
public class UserDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static UserMapper userMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getUserMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		userMapper = session.getMapper(UserMapper.class);
	}
	
	/**
	 * Cierra la conexion
	 */
	private static void closeAll() {
		session.close();
	}
	
	public User getUserByEmail(String email) {
		try {
			getUserMapper();
			return userMapper.getUserByEmail(email);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return null;
	}
	
	public void addUser(User usr) {
		try {
			getUserMapper();
			userMapper.addUser(usr);
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
	
	public ArrayList<User> getUsersWithList() {
		try {
			getUserMapper();
			return userMapper.getUsersWithList();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<User>();
	}
	
	public void deleteUser(String email) {
		try {
			getUserMapper();
			userMapper.deleteUser(email);
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
