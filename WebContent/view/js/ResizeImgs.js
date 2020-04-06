$(window).ready(function() {
	var tables = document.getElementsByClassName("table");
	
	for (var i = 0; i < tables.length; i++) {
		var imgs = tables[i].getElementsByTagName("img");
		
		for (var j = 0; j < imgs.length; j++) {
			imgs[j].width = 125;
		}
	}
	
});
