function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
//	var imagurl = profile.getImageUrl();
//	var name = profile.getName();
//	var email = profile.getEmail();
	
	$.post("/MyGameWishlist/Login",
			{
			email: profile.getEmail(),
			},
			function() {
		alert("posted (?)");
	});
	
}

function logout() {	  
    gapi.auth2.getAuthInstance().disconnect();
    location.reload();
}