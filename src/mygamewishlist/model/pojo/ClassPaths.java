package mygamewishlist.model.pojo;

/**
 * @author Patryk
 *
 * Singleton class that contains paths to all of the servlets
 * and jsp's.
 * There are 3 versions of each path:
 * 1.- variables with just the name of the class contain a slash and name of
 * class, and are meant to be used for request dispatchers, example:
 * RequestDispacher rd = getServletContext.getRequestDispatcher(ClassPaths.getCP().LOGIN);
 * 2.- variables with "REDIRECT_" and name of the class, these are used for send redirects,
 * example: response.sendRedirect(ClassPaths.getCP().REDIRECT_LOGIN);
 * 3.- variables with "JSP_" and name of the class, these are used for redirecting to jsp's,
 * example: RequestDispacher rd = getServletContext.getRequestDispatcher(ClassPaths.getCP().JSP_LOGIN);
 * 
 * Not all of the classes have all of the options as some of them don't need a servlet, etc.
 */
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
	
	private final String PROJECT = "/MyGameWishlist";
	private final String JSP = ".jsp";
	private final String VIEW = "/view";
	private final String LOGGED = new StringBuilder().append(VIEW).append("/logged").toString();
	private final String ADMIN = new StringBuilder().append(LOGGED).append("/admin").toString();	
	
	public final String LOGIN = "/Login";
	public final String REVIEW_LIST = "/ReviewList";
	public final String GAME_INFO = "/GameInfo";
	public final String USER_REVIEWS = "/UserReviews";
	public final String ADD_GAME_OPTIONS = "/AddGameOptions";
	public final String ADD_GAME_WISHLIST = "/AddGameWishlist";
	public final String UPDATE_GAME_WISHLIST = "/UpdateGameWishlist";
	public final String DELETE_GAME_WISHLIST = "/DeleteGameWishlist";
	public final String LOGOUT = "/Logout";
	public final String MYLIST = "/MyList";
	public final String PRICE_TIMELINE = "/PriceTimeline";
	public final String ADD_GAME = "/AddGame";
	public final String DELETE_GAME = "/DeleteGame";
	public final String UPDATE_GAME = "/UpdateGame";
	public final String GAME_LIST = "/GameList";
	
	public final String REDIRECT_LOGIN = redirect(LOGIN);
	public final String REDIRECT_REVIEW_LIST = redirect(REVIEW_LIST);
	public final String REDIRECT_GAME_INFO = redirect(GAME_INFO);
	public final String REDIRECT_USER_REVIEWS = redirect(USER_REVIEWS);
	public final String REDIRECT_ADD_GAME_OPTIONS = redirect(ADD_GAME_OPTIONS);
	public final String REDIRECT_ADD_GAME_WISHLIST = redirect(ADD_GAME_WISHLIST);
	public final String REDIRECT_UPDATE_GAME_WISHLIST = redirect(UPDATE_GAME_WISHLIST);
	public final String REDIRECT_DELETE_GAME_WISHLIST = redirect(DELETE_GAME_WISHLIST);
	public final String REDIRECT_LOGOUT = redirect(LOGOUT);
	public final String REDIRECT_MYLIST = redirect(MYLIST);
	public final String REDIRECT_PRICE_TIMELINE = redirect(PRICE_TIMELINE);
	public final String REDIRECT_ADD_GAME = redirect(ADD_GAME);
	public final String REDIRECT_DELETE_GAME = redirect(DELETE_GAME);
	public final String REDIRECT_UPDATE_GAME = redirect(UPDATE_GAME);
	public final String REDIRECT_GAME_LIST = redirect(GAME_LIST);
	
	public final String JSP_LOGIN = jsp(VIEW,LOGIN);
	public final String JSP_REVIEW_LIST = jsp(VIEW,REVIEW_LIST);
	public final String JSP_GAME_INFO = jsp(VIEW,GAME_INFO);
	public final String JSP_USER_REVIEWS = jsp(LOGGED,USER_REVIEWS);
	public final String JSP_ADD_GAME_OPTIONS = jsp(LOGGED,ADD_GAME_OPTIONS);
	public final String JSP_ADD_GAME_WISHLIST = jsp(LOGGED,ADD_GAME_WISHLIST);
	public final String JSP_UPDATE_GAME_WISHLIST = jsp(LOGGED,UPDATE_GAME_WISHLIST);
	public final String JSP_MYLIST = jsp(LOGGED,MYLIST);
	public final String JSP_PRICE_TIMELINE = jsp(LOGGED,PRICE_TIMELINE);
	public final String JSP_ADD_GAME = jsp(ADMIN,ADD_GAME);
	public final String JSP_UPDATE_GAME = jsp(ADMIN,UPDATE_GAME);
	public final String JSP_GAME_LIST = jsp(ADMIN,GAME_LIST);
	
	/**
	 * Appends project path to the provided string
	 * 
	 * @param name String
	 * @return String
	 */
	private String redirect(String name) {
		return PROJECT + name;
	}
	
	/**
	 * Appends path of the folder, to the name, and then to the JSP string.
	 * 
	 * @param where String, path of the folder
	 * @param name String name of the file
	 * @return String
	 */
	private String jsp(String where, String name) {
		return new StringBuilder().append(where).append(name).append(JSP).toString();
	}
}
