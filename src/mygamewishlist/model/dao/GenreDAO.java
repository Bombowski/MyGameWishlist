package mygamewishlist.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import mygamewishlist.model.dao.mapper.GenreMapper;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Genre;

/**
 * @author Patryk
 *
 * Class that gets the Genre interface, and gets
 * the database conection
 */
public class GenreDAO {

	private static final MyLogger LOG = MyLogger.getLOG();
	private static SqlSession session;
	private static GenreMapper genreMapper;
	
	/**
	 * Creates conection with the database, and gets the interface
	 */
	private static void getGeMapper() {
		session = MyBatisUtil.getSqlSessionFactory().openSession();
		genreMapper = session.getMapper(GenreMapper.class);
	}
	
	/**
	 * Closes the conection
	 */
	private static void closeAll() {
		session.close();
	}
	
	public ArrayList<Genre> getGenres() {
		try {
			getGeMapper();
			return genreMapper.getGenres();
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new ArrayList<Genre>();
	}
	
	public Genre getGenreById(int id) {
		try {
			getGeMapper();
			return genreMapper.getGenreById(id);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new Genre();
	}
	
	public Genre getGenreByName(String name) {
		try {
			getGeMapper();
			return genreMapper.getGenreByName(name);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		} finally {
			try {
				closeAll();
			} catch (Exception e) {
				LOG.logError(e.getMessage());
			}
		}
		return new Genre();
	}
	
	public void deleteGameGenres(int idGame) {
		try {
			getGeMapper();
			genreMapper.deleteGameGenres(idGame);
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
