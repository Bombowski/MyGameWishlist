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

function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	
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

$(".logout").click(function() {
	if (gapi.auth2 == undefined) {
		onLoad();
	}
	gapi.auth2.getAuthInstance().disconnect();
	window.location.href = "/MyGameWishlist/Logout";
});

function onLoad() {
	gapi.load('auth2', function() {
		gapi.auth2.init();
	});
}

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

function cookiesChked() {
	document.cookie="chked=t";
	$("#cookies").remove();
}