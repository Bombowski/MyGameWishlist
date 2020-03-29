package mygamewishlist.model.ejb;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.dao.GameDAO;
import mygamewishlist.model.dao.ReviewDAO;
import mygamewishlist.model.dao.StoreDAO;
import mygamewishlist.model.dao.UserDAO;
import mygamewishlist.model.dao.WishListGameDAO;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

@Stateless
@LocalBean
public class CreateQuery {

	private static final UserDAO USR_DAO = new UserDAO();
	private static final GameDAO GAME_DAO = new GameDAO();
	private static final WishListGameDAO LIST_DAO = new WishListGameDAO();
	private static final StoreDAO STORE_DAO = new StoreDAO();
	private static final ReviewDAO REV_DAO = new ReviewDAO();
	
	public CreateQuery() {}
	
	public User getUserByEmail(String email) {
		return USR_DAO.getUserByEmail(email);
	}
	
	public void addUser(User usr) {
		USR_DAO.addUser(usr);
	}
	
	public ArrayList<ReviewList> getReviewList(int idUser) {
		return REV_DAO.getReviewList(idUser);
	}
	
	public ArrayList<ReviewList> getReviewListNotLogged() {
		return REV_DAO.getReviewListNotLogged();
	}
	
	public void updateReview(Review rev) {
		REV_DAO.updateReview(rev);
	}
	
	public void deleteReview(int idUser, int idGame) {
		REV_DAO.deleteReview(idUser, idGame);
	}
	
	public Game getGame(int idGame) {
		return GAME_DAO.getGame(idGame);
	}
	
	public ArrayList<Game> getGames() {
		return GAME_DAO.getGames();
	}
	
	public void addGame(Game game) {
		GAME_DAO.addGame(game);
	}
	
	public void updateGame(Game game) {
		GAME_DAO.updateGame(game);
	}
	
	public void deleteGame(int idGame) {
		GAME_DAO.deleteGame(idGame);
	}
	
	public ArrayList<Store> getStores() {
		return STORE_DAO.getStores();
	}
	
	public ArrayList<String> getStoreNames() {
		return STORE_DAO.getStoreNames();
	}
	
	public ArrayList<WishListGame> getListByIdUser(int idUser) {
		return LIST_DAO.getListByIdUser(idUser);
	}
	
	public WishListGame getGameFromListByIdUserUrl(int idUser, String url) {
		return LIST_DAO.getGameFromListByIdUserUrl(idUser, url);
	}
	
	public ArrayList<User> getUsersWithList() {
		return USR_DAO.getUsersWithList();
	}
	
	public Store getStoreByName(String name) {
		return STORE_DAO.getStoreByName(name);
	}
	
	public void addGame2Wishlist(ArrayList<WishListGame> games) {
		LIST_DAO.addGame2Wishlist(games);
	}
	
	public ArrayList<WishListGame2Scrap> getGamesFromListById(int idUser) {
		return LIST_DAO.getGamesFromListById(idUser);
	}
	
	public int getIdListByIdUser(int idUser) {
		return USR_DAO.getIdListByIdUser(idUser);
	}
	
	public void deleteGameWishlist(String url, int idList) {
		LIST_DAO.deleteGameWishlist(url, idList);
	}
	
	public void updatePrices(ScrapedGame sg) {
		LIST_DAO.updatePrices(sg);
	}
	
	public void updateMinMax(double min, double max, String url, int idList) {
		LIST_DAO.updateMinMax(min, max, url, idList);
	}
}
