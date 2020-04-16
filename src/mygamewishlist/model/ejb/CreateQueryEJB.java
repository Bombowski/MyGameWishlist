package mygamewishlist.model.ejb;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.dao.GameDAO;
import mygamewishlist.model.dao.ReviewDAO;
import mygamewishlist.model.dao.SteamDAO;
import mygamewishlist.model.dao.StoreDAO;
import mygamewishlist.model.dao.TimelineDAO;
import mygamewishlist.model.dao.UserDAO;
import mygamewishlist.model.dao.VariablesDAO;
import mygamewishlist.model.dao.WishListGameDAO;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.TimelineGame;
import mygamewishlist.model.pojo.db.TimelineGameDetailed;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.pojo.db.WishListGame2ScrapSteam;

@Stateless
@LocalBean
public class CreateQueryEJB {

	private static final UserDAO USR_DAO = new UserDAO();
	private static final GameDAO GAME_DAO = new GameDAO();
	private static final WishListGameDAO LIST_DAO = new WishListGameDAO();
	private static final StoreDAO STORE_DAO = new StoreDAO();
	private static final ReviewDAO REV_DAO = new ReviewDAO();
	private static final SteamDAO STEAM_DAO = new SteamDAO();
	private static final VariablesDAO VAR_DAO = new VariablesDAO();
	private static final TimelineDAO TIM_DAO = new TimelineDAO();
	
	public CreateQueryEJB() {}
	
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
	
	public void addGame2Wishlist(ArrayList<WishListGame> games, int idUser) {
		LIST_DAO.addGame2Wishlist(games, idUser);
	}
	
	public ArrayList<WishListGame2Scrap> getGamesFromListById(int idUser) {
		return LIST_DAO.getGamesFromListById(idUser);
	}
	
	public ArrayList<WishListGame2ScrapSteam> getSteamGamesFromListById(int idUser) {
		return LIST_DAO.getSteamGamesFromListById(idUser);
	}
	
	public void deleteGameWishlist(String url, int idUser) {
		LIST_DAO.deleteGameWishlist(url, idUser);
	}
	
	public void updatePrices(ArrayList<ScrapedGame> sg, int idUser) {
		LIST_DAO.updatePrices(sg, idUser);
	}
	
	public void updateMinMax(double min, double max, String url, int idUser) {
		LIST_DAO.updateMinMax(min, max, url, idUser);
	}
	
	public ArrayList<Integer> getSteamGameIdsByName(String name) {
		return STEAM_DAO.getSteamGameIdsByName(name);
	}
	
	public void addSteamGames(ArrayList<SteamGame> sg) {
		STEAM_DAO.addSteamGames(sg);
	}
	
	public String getVariable(String name) {
		return VAR_DAO.getVariable(name);
	}
	
	public void add2Timeline(ScrapedGame sg, String time) {
		TIM_DAO.add2Timeline(sg, time);
	}
	
	public ArrayList<TimelineGame> getTimelineByUrl(String url) {
		return TIM_DAO.getTimelineByUrl(url);
	}
	
	public ArrayList<TimelineGameDetailed> getTimelineByUrlDetailed(String url) {
		return TIM_DAO.getTimelineByUrlDetailed(url);
	}
}
