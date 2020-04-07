var buttons; 
var tables;

window.onbeforeunload = function() {
	$.ajax({
	    url: '/MyGameWishlist/AddGameOptions',
	    type: 'DELETE',
	    success: function(result) {}
	});
}

$(window).ready(function() {    
    tables = document.getElementById("tables").children;
    buttons = document.getElementsByClassName("buttons")[0].children;
    var i = 0;
    
    for (i = 0; i < buttons.length; i++) {
    	$(buttons[i]).click(function() {
    		var idTbl = this.id + "tbl";
    		for (var j = 0; j < tables.length; j++) {
				if (tables[j].id == idTbl) {
					$(tables[j]).show();
				} else {
					$(tables[j]).hide();
				}
			}
        });
	}
    
    for (i = 0; i < buttons.length; i++) {
    	var bool = removeButton(tables, buttons[i]);    	
    	
    	if (!bool) {
    		$(buttons[i]).remove();
    		i--;
    	}
	}
    
    for (var i = tables.length - 2; i >= 0; i--) {
		$(tables[i]).hide();
	}
});

function removeButton(tables, button) {
	for (var i = 0; i < tables.length; i++) {
		var table = tables[i];
		if ($(table).attr("id").replace("tbl","") == $(button).attr("id")) {
			if ($(table).is(":empty")) {
	        	$(table).hide();
	        	$(table).hide();
	        } 
			return true;
		}
	}
	return false;
} 

