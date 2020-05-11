package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;
import mygamewishlist.model.pojo.db.ReviewOfGame;

public interface ReviewMapper {
	
	public ArrayList<ReviewList> getReviewList();
	
	public void addOrUpdateReview(Review rev);
	
	public void deleteReview(@Param("idUser") int idUser, @Param("idGame") int idGame);
	
	public ArrayList<ReviewOfGame> getGameReviews(@Param("idUser") int idUser, @Param("idGame") int idGame);

	public ReviewOfGame getGameReview(@Param("idUser") int idUser, @Param("idGame") int idGame);
}
