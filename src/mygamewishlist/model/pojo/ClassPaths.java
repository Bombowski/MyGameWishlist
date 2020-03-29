package mygamewishlist.model.pojo;

public class ClassPaths {

	private static ClassPaths cp = new ClassPaths();

	private ClassPaths() {}
	
	public static ClassPaths getCP() {
		if (cp == null) {
			synchronized (ClassPaths.class) {
				if (cp == null) {
					cp = new ClassPaths();
				}
			}
		}
		return cp;
	}
	
	public final String PROJECT = "/MyGameWishlist";
	public final String JSP = ".jsp";
	public final String VIEW = "/view";
	public final String LOGGED = new StringBuilder().append(VIEW).append("/logged").toString();
	public final String ADMIN = new StringBuilder().append(LOGGED).append("/admin").toString();	
	
	public final String LOGIN = "/Login";
	public final String REVIEW_LIST = "/ReviewList";
	public final String GAME_INFO = "/GameInfo";
	public final String ADD_GAME_OPTIONS = "/AddGameOptions";
	public final String ADD_GAME_WISHLIST = "/AddGameWishlist";
	public final String UPDATE_GAME_WISHLIST = "/UpdateGameWishlist";
	public final String DELETE_GAME_WISHLIST = "/DeleteGameWishlist";
	public final String GAME_HISTORY = "/GameHistory";
	public final String LOGOUT = "/Logout";
	public final String MYLIST = "/MyList";
	public final String ADD_GAME = "/AddGame";
	public final String DELETE_GAME = "/DeleteGame";
	public final String UPDATE_GAME = "/UpdateGame";
	public final String GAME_LIST = "/GameList";
	
	public final String REDIRECT_LOGIN = new StringBuilder().append(PROJECT).append(LOGIN).toString();
	public final String REDIRECT_REVIEW_LIST = new StringBuilder().append(PROJECT).append(REVIEW_LIST).toString();
	public final String REDIRECT_GAME_INFO = new StringBuilder().append(PROJECT).append(GAME_INFO).toString();
	public final String REDIRECT_ADD_GAME_OPTIONS = new StringBuilder().append(PROJECT).append(ADD_GAME_OPTIONS).toString();
	public final String REDIRECT_ADD_GAME_WISHLIST = new StringBuilder().append(PROJECT).append(ADD_GAME_WISHLIST).toString();
	public final String REDIRECT_UPDATE_GAME_WISHLIST = new StringBuilder().append(PROJECT).append(UPDATE_GAME_WISHLIST).toString();
	public final String REDIRECT_DELETE_GAME_WISHLIST = new StringBuilder().append(PROJECT).append(DELETE_GAME_WISHLIST).toString();
	public final String REDIRECT_GAME_HISTORY = new StringBuilder().append(PROJECT).append(GAME_HISTORY).toString();
	public final String REDIRECT_LOGOUT = new StringBuilder().append(PROJECT).append(LOGOUT).toString();
	public final String REDIRECT_MYLIST = new StringBuilder().append(PROJECT).append(MYLIST).toString();
	public final String REDIRECT_ADD_GAME = new StringBuilder().append(PROJECT).append(ADD_GAME).toString();
	public final String REDIRECT_DELETE_GAME = new StringBuilder().append(PROJECT).append(DELETE_GAME).toString();
	public final String REDIRECT_UPDATE_GAME = new StringBuilder().append(PROJECT).append(UPDATE_GAME).toString();
	public final String REDIRECT_GAME_LIST = new StringBuilder().append(PROJECT).append(GAME_LIST).toString();
	
	public final String JSP_LOGIN = new StringBuilder().append(VIEW).append(LOGIN).append(JSP).toString();
	public final String JSP_REVIEW_LIST = new StringBuilder().append(VIEW).append(REVIEW_LIST).append(JSP).toString();
	public final String JSP_GAME_INFO = new StringBuilder().append(VIEW).append(GAME_INFO).append(JSP).toString();
	public final String JSP_ADD_GAME_OPTIONS = new StringBuilder().append(LOGGED).append(ADD_GAME_OPTIONS).append(JSP).toString();
	public final String JSP_ADD_GAME_WISHLIST = new StringBuilder().append(LOGGED).append(ADD_GAME_WISHLIST).append(JSP).toString();
	public final String JSP_UPDATE_GAME_WISHLIST = new StringBuilder().append(LOGGED).append(UPDATE_GAME_WISHLIST).append(JSP).toString();
	public final String JSP_GAME_HISTORY = new StringBuilder().append(LOGGED).append(GAME_HISTORY).append(JSP).toString();
	public final String JSP_MYLIST = new StringBuilder().append(LOGGED).append(MYLIST).append(JSP).toString();
	public final String JSP_ADD_GAME = new StringBuilder().append(ADMIN).append(ADD_GAME).append(JSP).toString();
	public final String JSP_UPDATE_GAME = new StringBuilder().append(ADMIN).append(UPDATE_GAME).append(JSP).toString();
	public final String JSP_GAME_LIST = new StringBuilder().append(ADMIN).append(GAME_LIST).append(JSP).toString();
}
