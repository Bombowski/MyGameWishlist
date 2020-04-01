$(window).ready(function() {
	var imgs = document.getElementsByClassName("table")[0].getElementsByTagName("img");
	
	for (var i = 0; i < imgs.length; i++) {
		imgs[i].width = 125;
	}
});
