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
 * Clase de utilidad para obtener una configuraciónn de MyBatis
 */
public class MyBatisUtil {
	
	private static SqlSessionFactory factory;
	private static final MyLogger LOG = MyLogger.getLOG();
	
	private MyBatisUtil() {}

	/**
	 * Estático para que sólo se configure MyBatis una vez
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
	 * Obtiene una SqlSessionFactory
	 * 
	 * @return SqlSessionFactory
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		return factory;
	}
}