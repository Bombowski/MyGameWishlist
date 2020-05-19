/**
 * File included in every jsp, it is used for managing oauth2,
 * creating and closing sessions, and cookies.
 */

/**
 * Initializing oauth2, checking if cookies banner should be shown
 */
window.onload = function() {
	if (gapi.auth2 == undefined) {
		onLoad();
	}
	
	var c = document.getElementById("cookies");
	if (chkCookies()) {
		$(c).remove();
	} else {
		c.addEventListener("click",cookiesChked);
	}	
}

/**
 * Executes after finishing google sign in form.
 * 
 * @param googleUser
 */
function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	
	// sending petition to login to create a user
	$.post("/MyGameWishlist/Login",
		{
			email: profile.getEmail(),
			name: profile.getName()
		},
		function() {
			window.location.href = "/MyGameWishlist/MyList";
		}
	);
}

/**
 * Executes after clicking on the logout button, it sends a
 * petition to Logout to close the session.
 */
$(".logout").click(function() {
	if (gapi.auth2 == undefined) {
		onLoad();
	}
	gapi.auth2.getAuthInstance().disconnect();
	window.location.href = "/MyGameWishlist/Logout";
});

/**
 * Executes on window load, and it loads oauth2 api.
 */
function onLoad() {
	gapi.load('auth2', function() {
		gapi.auth2.init();
	});
}

/**
 * Checks if chked cookie was already checked before.
 * 
 * @return boolean, true if it was, false if not
 */
function chkCookies() {
	var cookie = getCookie("chked");
	if (cookies == "") {
		document.cookie="chked=f";
		return false;
	} else if (cookie == "t") {
		return true;
	}
	return false;
}

/**
 * Returns cookie value by the name provided.
 * 
 * @param cname cookie name
 * @returns String cookie value
 */
function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(";");
	
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
	
		while (c.charAt(0) == " ") {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

/**
 * Sets the parameter of cookie 'chked' to 't', and removes the 
 * cookie banner notification.
 */
function cookiesChked() {
	document.cookie="chked=t";
	$("#cookies").remove();
}