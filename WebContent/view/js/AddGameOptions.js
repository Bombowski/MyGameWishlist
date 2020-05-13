var buttons; 
var tables;

$(window).ready(function() {
	// getting list of tables and buttons
    tables = document.getElementById("tables").children;
    buttons = document.getElementsByClassName("buttons")[0].children;
    var i = 0;
    
    // adding listeners to buttons
    for (; i < buttons.length; i++) {
    	$(buttons[i]).click(function() {
    		// getting table id by apending tbl to button id
    		var idTbl = this.id + "tbl";
    		for (var j = 0; j < tables.length; j++) {
    			// hiding all of the tables, and showing the one with the right id,
    			// and changing colors of the buttons
				if (tables[j].id == idTbl) {
					$(tables[j]).show();
					$(this).addClass("btn-primary");
					$(this).removeClass("btn-dark");
				} else {
					$(tables[j]).hide();
					var button = document.getElementById($(tables[j]).attr("id").replace("tbl",""));
					$(button).addClass("btn-dark");
					$(button).removeClass("btn-primary");
				}
			}
        });
	}
    
    // removing all of the buttons that belong to empty tables
    for (i = 0; i < buttons.length; i++) {
    	if (!removeButton(tables, buttons[i])) {
    		$(buttons[i]).remove();
    		i--;
    	}
	}
    
    // setting the first button to selected
    var tmp = buttons[0];
    $(tmp).removeClass("btn-dark");
	$(tmp).addClass("btn-primary");
	
	// hiding all of the not selected tables
    for (i = tables.length - 2; i >= 0; i--) {
		$(tables[i]).hide();
	}
});

function removeButton(tables, button) {
	for (var i = 0; i < tables.length; i++) {
		var table = tables[i];
		if ($(table).attr("id").replace("tbl","") == $(button).attr("id")) {
			if ($(table).is(":empty")) {
	        	$(table).hide();
	        } 
			return true;
		}
	}
	return false;
} 

