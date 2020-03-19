window.onload = function() {
	if (gapi.auth2 == undefined) {
		onLoad();
	}
}

function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
//	var imagurl = profile.getImageUrl();
//	var name = profile.getName();
//	var email = profile.getEmail();
	
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