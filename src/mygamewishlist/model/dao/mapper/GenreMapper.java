package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Genre;

public interface GenreMapper {

	public ArrayList<Genre> getGenres();
	
	public Genre getGenreById(@Param("id") int id);
	
	public Genre getGenreByName(@Param("name") String name);
	
	public void deleteGameGenres(@Param("idGame") int idGame);
}
