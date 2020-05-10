package mygamewishlist.model.dao;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.VariablesMapper;
import mygamewishlist.model.pojo.MyLogger;

/**
 * @author Patryk
 *
 * Class that gets the Variables interface, and gets
 * the database conection
 */
public class VariablesDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static VariablesMapper variablesMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getUserMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		variablesMapper = session.getMapper(VariablesMapper.class);
	}
	
	/**
	 * Closes the conection
	 */
	private static void closeAll() {
		session.close();
	}
	
	public String getVariable(String name) {
		try {
			getUserMapper();
			return variablesMapper.getVariableByName(name);
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
}
