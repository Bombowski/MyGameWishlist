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
	});
	
}

$(".logout").click(function() {
	if (gapi.auth2 == undefined) {
	    gapi.load('auth2', init);
	}
	logout2();	
});

function init() {
	gapi.auth2.init();
}

function logout2() {
	gapi.auth2.getAuthInstance().disconnect();
    location.reload();
}