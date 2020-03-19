package mygamewishlist.model.pojo;

public class ClassPaths {

	private static final String PROJECT = "/MyGameWishlist";
	private static final String JSP = ".jsp";
	private static final String VIEW = "/view";
	private static final String LOGGED = new StringBuilder().append(VIEW).append("/logged").toString();
	private static final String ADMIN = new StringBuilder().append(LOGGED).append("/admin").toString();	
	
	public static final String LOGIN = "/Login";
	public static final String REVIEW_LIST = "/ReviewList";
	public static final String ADD_GAME_OPTIONS = "/AddGameOptions";
	public static final String ADD_GAME_WISHLIST = "/AddGameWishlist";
	public static final String DELETE_GAME_WISHLIST = "/Delete/GameWishList";
	public static final String GAME_HISTORY = "/GameHistory";
	public static final String LOGOUT = "/Logout";
	public static final String MYLIST = "/MyList";
	public static final String ADD_GAME = "/AddGame";
	public static final String DELETE_GAME = "/DeleteGame";
	public static final String GAME_LIST = "/GameList";
	
	public static final String REDIRECT_LOGIN = new StringBuilder().append(PROJECT).append(LOGIN).toString();
	public static final String REDIRECT_REVIEW_LIST = new StringBuilder().append(PROJECT).append(REVIEW_LIST).toString();
	public static final String REDIRECT_ADD_GAME_OPTIONS = new StringBuilder().append(PROJECT).append(ADD_GAME_OPTIONS).toString();
	public static final String REDIRECT_ADD_GAME_WISHLIST = new StringBuilder().append(PROJECT).append(ADD_GAME_WISHLIST).toString();
	public static final String REDIRECT_DELETE_GAME_WISHLIST = new StringBuilder().append(PROJECT).append(DELETE_GAME_WISHLIST).toString();
	public static final String REDIRECT_GAME_HISTORY = new StringBuilder().append(PROJECT).append(GAME_HISTORY).toString();
	public static final String REDIRECT_LOGOUT = new StringBuilder().append(PROJECT).append(LOGOUT).toString();
	public static final String REDIRECT_MYLIST = new StringBuilder().append(PROJECT).append(MYLIST).toString();
	public static final String REDIRECT_ADD_GAME = new StringBuilder().append(PROJECT).append(ADD_GAME).toString();
	public static final String REDIRECT_DELETE_GAME = new StringBuilder().append(PROJECT).append(DELETE_GAME).toString();
	public static final String REDIRECT_GAME_LIST = new StringBuilder().append(PROJECT).append(GAME_LIST).toString();
	
	public static final String JSP_LOGIN = new StringBuilder().append(VIEW).append(LOGIN).append(JSP).toString();
	public static final String JSP_REVIEW_LIST = new StringBuilder().append(VIEW).append(REVIEW_LIST).append(JSP).toString();
	public static final String JSP_ADD_GAME_OPTIONS = new StringBuilder().append(LOGGED).append(ADD_GAME_OPTIONS).append(JSP).toString();
	public static final String JSP_ADD_GAME_WISHLIST = new StringBuilder().append(LOGGED).append(ADD_GAME_WISHLIST).append(JSP).toString();
	public static final String JSP_DELETE_GAME_WISHLIST = new StringBuilder().append(LOGGED).append(DELETE_GAME_WISHLIST).append(JSP).toString();
	public static final String JSP_GAME_HISTORY = new StringBuilder().append(LOGGED).append(GAME_HISTORY).append(JSP).toString();
	public static final String JSP_LOGOUT = new StringBuilder().append(LOGGED).append(LOGOUT).append(JSP).toString();
	public static final String JSP_MYLIST = new StringBuilder().append(LOGGED).append(MYLIST).append(JSP).toString();
	public static final String JSP_ADD_GAME = new StringBuilder().append(ADMIN).append(ADD_GAME).append(JSP).toString();
	public static final String JSP_DELETE_GAME = new StringBuilder().append(ADMIN).append(DELETE_GAME).append(JSP).toString();
	public static final String JSP_GAME_LIST = new StringBuilder().append(ADMIN).append(GAME_LIST).append(JSP).toString();
}
