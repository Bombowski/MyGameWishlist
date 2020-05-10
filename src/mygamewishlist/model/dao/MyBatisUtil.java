package mygamewishlist.model.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mygamewishlist.model.pojo.MyLogger;

/**
 * @author Patryk
 * 
 * Utility class for getting MyBatis configuration
 */
public class MyBatisUtil {
	
	private static SqlSessionFactory factory;
	private static final MyLogger LOG = MyLogger.getLOG();
	
	private MyBatisUtil() {}

	/**
	 * Initializes MyBatis config
	 */
	static {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
		} catch (IOException e) {
			LOG.logError(e.getMessage());
		}
		factory = new SqlSessionFactoryBuilder().build(reader);
	}

	/**
	 * Returns a SqlSessionFactory
	 * 
	 * @return SqlSessionFactory
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		return factory;
	}
}