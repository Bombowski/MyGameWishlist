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
    	var idTbl = tables[i];
    	if (!$(tables[i]).length) {
        	$(tables[i]).remove();
        	$(buttons[i]).remove();
        	i--;
        } else if ($(tables[i]).is(":empty")) {
        	$(tables[i]).hide();
        	$(idTbl.substring(0, idTbl.indexOf("tbl"))).hide();
        } 
	}
    
    for (var i = 1; i < tables.length; i++) {
		$(tables[i]).hide();
	}
});
