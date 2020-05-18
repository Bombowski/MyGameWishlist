package mygamewishlist.model.ejb;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.dao.GameDAO;
import mygamewishlist.model.dao.GenreDAO;
import mygamewishlist.model.dao.ReviewDAO;
import mygamewishlist.model.dao.SteamDAO;
import mygamewishlist.model.dao.StoreDAO;
import mygamewishlist.model.dao.TimelineDAO;
import mygamewishlist.model.dao.UserDAO;
import mygamewishlist.model.dao.VariablesDAO;
import mygamewishlist.model.dao.WishListGameDAO;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.Developer;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.GameFull;
import mygamewishlist.model.pojo.db.Genre;
import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;
import mygamewishlist.model.pojo.db.ReviewOfGame;
import mygamewishlist.model.pojo.db.ReviewUser;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.TimelineGame;
import mygamewishlist.model.pojo.db.TimelineGameDetailed;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;
import mygamewishlist.model.pojo.db.WishListGame2ScrapSteam;

/**
 * @author Patryk
 *
 * EJB class that is used for communicating with DAO classes,
 * and making queries.
 */
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
	private static final GenreDAO GEN_DAO = new GenreDAO();
	
	/**
	 * Empty contructor for EJB
	 */
	public CreateQueryEJB() {}
	
	/**
	 * Gets user data by his e-mail
	 * 
	 * @param email String
	 * @return User
	 */
	public User getUserByEmail(String email) {
		return USR_DAO.getUserByEmail(email);
	}
	
	/**
	 * Adds a user to the database
	 * 
	 * @param usr User
	 */
	public void addUser(User usr) {
		USR_DAO.addUser(usr);
	}
	
	/**
	 * Gets a list of games and their average reviews score
	 * 
	 * @return ArrayList<ReviewList>
	 */
	public ArrayList<ReviewList> getReviewList() {
		return REV_DAO.getReviewList();
	}
	
	/**
	 * Adds or a review, or incase it already exists, it gets
	 * updated
	 * 
	 * @param rev Review
	 */
	public void addOrUpdateReview(Review rev) {
		REV_DAO.addOrUpdateReview(rev);
	}
	
	/**
	 * Deletes a review by id of the user, and id of the game
	 * 
	 * @param idUser int
	 * @param idGame int
	 */
	public void deleteReview(int idUser, int idGame) {
		REV_DAO.deleteReview(idUser, idGame);
	}
	
	/**
	 * Returns game data by it's id
	 * 
	 * @param idGame int
	 * @return GameFull
	 */
	public GameFull getGameById(int idGame) {
		return GAME_DAO.getGameById(idGame);
	}
	
	/**
	 * Returns a list of games
	 * 
	 * @return ArrayList<Game>
	 */
	public ArrayList<Game> getGames() {
		return GAME_DAO.getGames();
	}
	
	/**
	 * Adds a game
	 * 
	 * @param game GAmeFull
	 */
	public void addGame(GameFull game) {
		GAME_DAO.addGame(game);
	}
	
	/**
	 * Updates a game, and it's genres
	 * 
	 * @param game GameFull
	 */
	public void updateGame(GameFull game) {
		// update game data
		GAME_DAO.updateGame(game);
		// delete current genres
		GEN_DAO.deleteGameGenres(game.getId());
		// add selected genres
		GAME_DAO.addGameGenre(game.getId(), game.getIdGenres().split(","));
	}
	
	/**
	 * Deletes a game
	 * 
	 * @param idGame int
	 */
	public void deleteGame(int idGame) {
		GAME_DAO.deleteGame(idGame);
	}
	/**
	 * Returns a list of stores
	 * 
	 * @return ArrayList<Store>
	 */
	public ArrayList<Store> getStores() {
		return STORE_DAO.getStores();
	}
	
	/**
	 * Returns a list of store names
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getStoreNames() {
		return STORE_DAO.getStoreNames();
	}
	
	/**
	 * Returns a wishlist by id user
	 * 
	 * @param idUser int
	 * @return ArrayList<WishListGame>
	 */
	public ArrayList<WishListGame> getListByIdUser(int idUser) {
		return LIST_DAO.getListByIdUser(idUser);
	}
	
	/**
	 * Returns a game from wishlist by it's url and id of the user
	 * 
	 * @param idUser int
	 * @param url String
	 * @return WishListGame
	 */
	public WishListGame getGameFromListByIdUserUrl(int idUser, String url) {
		return LIST_DAO.getGameFromListByIdUserUrl(idUser, url);
	}
	
	/**
	 * Returns a list of users who have at least one
	 * game in thei wishlist
	 * 
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getUsersWithList() {
		return USR_DAO.getUsersWithList();
	}
	
	/**
	 * Returns store data by it's name
	 * 
	 * @param name String
	 * @return Store
	 */
	public Store getStoreByName(String name) {
		return STORE_DAO.getStoreByName(name);
	}
	
	/**
	 * Adds a list of games to users wishlist
	 * 
	 * @param games ArrayList<WishListGame>
	 * @param idUser int
	 */
	public void addGame2Wishlist(ArrayList<WishListGame> games, int idUser) {
		LIST_DAO.addGame2Wishlist(games, idUser);
	}
	
	/**
	 * Returns a list of games by id user
	 * 
	 * @param idUser int
	 * @return ArrayList<WishlistGame2Scrap>
	 */
	public ArrayList<WishListGame2Scrap> getGamesFromListById(int idUser) {
		return LIST_DAO.getGamesFromListById(idUser);
	}
	
	/**
	 * returns a list of steam games from wishlist by id user
	 * 
	 * @param idUser int
	 * @return ArrayList<WishListGame2ScrapSteam>
	 */
	public ArrayList<WishListGame2ScrapSteam> getSteamGamesFromListById(int idUser) {
		return LIST_DAO.getSteamGamesFromListById(idUser);
	}
	
	/**
	 * Deletes a game from users wishlist
	 * 
	 * @param url String
	 * @param idUser int
	 */
	public void deleteGameWishlist(String url, int idUser) {
		LIST_DAO.deleteGameWishlist(url, idUser);
	}
	
	/**
	 * Updates prices of provided games
	 * 
	 * @param sg ArrayList<ScrapedGame>
	 */
	public void updatePrices(ArrayList<ScrapedGame> sg) {
		LIST_DAO.updatePrices(sg);
	}
	
	/**
	 * Updates price of provided game
	 * 
	 * @param sg ScrapedGame
	 */
	public void updatePrices(ScrapedGame sg) {
		LIST_DAO.updatePrices(sg);
	}
	
	/**
	 * Updates min and max price by id user and game url
	 * 
	 * @param min double
	 * @param max double
	 * @param url String
	 * @param idUser int
	 */
	public void updateMinMax(double min, double max, String url, int idUser) {
		LIST_DAO.updateMinMax(min, max, url, idUser);
	}
	
	/**
	 * Returns a list of steam game id's by a String search
	 * param
	 * 
	 * @param name String
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getSteamGameIdsByName(String name) {
		return STEAM_DAO.getSteamGameIdsByName(name);
	}
	
	/**
	 * Adds a list of steam games
	 * 
	 * @param sg ArrayList<SteamGame>
	 */
	public void addSteamGames(ArrayList<SteamGame> sg) {
		STEAM_DAO.addSteamGames(sg);
	}
	
	/**
	 * Returns a variable
	 * 
	 * @param name String
	 * @return String
	 */
	public String getVariable(String name) {
		return VAR_DAO.getVariable(name);
	}
	
	/**
	 * Adds an entry in the timeline table
	 * 
	 * @param sg ScrapedGame
	 * @param time String (current date)
	 */
	public void add2Timeline(ScrapedGame sg, String time) {
		TIM_DAO.add2Timeline(sg, time, this.getIdUrlByUrl(sg.getUrlGame()));
	}
	
	/**
	 * Returns the id of an url
	 * 
	 * @param url String
	 * @return int
	 */
	private int getIdUrlByUrl(String url) {
		return LIST_DAO.getIdUrlByUrl(url);
	}
	
	/**
	 * Returns a list of timeline entries by game url
	 * 
	 * @param url String
	 * @return ArrayList<TimelineGame>
	 */
	public ArrayList<TimelineGame> getTimelineByUrl(String url) {
		return TIM_DAO.getTimelineByUrl(url);
	}
	
	/**
	 * Returns a list of timeline entries with some more details 
	 * (game title, and store url)
	 * 
	 * @param url String
	 * @return ArrayList<TimelineGameDetailed>
	 */
	public ArrayList<TimelineGameDetailed> getTimelineByUrlDetailed(String url) {
		return TIM_DAO.getTimelineByUrlDetailed(url);
	}
	
	/**
	 * Returns a list of genres
	 * 
	 * @return ArrayList<Genres>
	 */
	public ArrayList<Genre> getGenres() {
		return GEN_DAO.getGenres();
	}
	
	/**
	 * Returns genre data by it's id
	 * 
	 * @param id int
	 * @return Genre
	 */
	public Genre getGenreById(int id) {
		return GEN_DAO.getGenreById(id);
	}
	
	/**
	 * Returns genre data by name
	 * 
	 * @param name String
	 * @return Genre
	 */
	public Genre getGenreByName(String name) {
		return GEN_DAO.getGenreByName(name);
	}
	
	/**
	 * Returns a list of developers
	 * 
	 * @return ArrayList<Developer>
	 */
	public ArrayList<Developer> getDevelopers() {
		return GAME_DAO.getDevelopers();
	}
	
	/**
	 * Returns developer data by it's id
	 * 
	 * @param id int
	 * @return Developer
	 */
	public Developer getDeveloperById(int id) {
		return GAME_DAO.getDeveloperById(id);
	}
	
	/**
	 * Returns game id by it's title
	 * 
	 * @param title String
	 * @return int
	 */
	public int getGameIdByTitle(String title) {
		return GAME_DAO.getGameIdByTitle(title);
	}
	
	/**
	 * Deletes a steam game by id
	 * 
	 * @param id int
	 */
	public void deleteSteamGameById(int id) {
		STEAM_DAO.deleteSteamGameById(id);
	}
	
	/**
	 * Deletes a user by email
	 * 
	 * @param email String
	 */
	public void deleteUser(String email) {
		USR_DAO.deleteUser(email);
	}
	
	/**
	 * REturns a list of reviews of one game.
	 * 
	 * @param idUser -1 if not logged
	 * @param idGame int
	 * @return ArrayList<ReviewOfGame>
	 */
	public ArrayList<ReviewOfGame> getGameReviews(int idUser, int idGame) {
		return REV_DAO.getGameReviews(idUser, idGame);
	}
	
	/**
	 * Returns users game review
	 * 
	 * @param idUser int
	 * @param idGame int
	 * @return ReviewOfGame
	 */
	public ReviewOfGame getGameReview(int idUser, int idGame) {
		return REV_DAO.getGameReview(idUser, idGame);
	}
	
	/**
	 * Returns a list of all of the reviews of one user
	 * 
	 * @param idUser int user id
	 * @return ArrayList<ReviewUser>
	 */
	public ArrayList<ReviewUser> getUserReviews(int idUser) {
		return REV_DAO.getUserReviews(idUser);
	}
}
