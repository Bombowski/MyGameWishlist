var buttons; 
var tables;

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
    
    for (i = 0; i < buttons.length; i++) {
    	var bool = removeButton(tables, buttons[i]);    	
    	
    	if (!bool) {
    		$(buttons[i]).remove();
    		i--;
    	}
	}
    
    var tmp = buttons[0];
    $(tmp).removeClass("btn-dark");
	$(tmp).addClass("btn-primary");
	
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
	        } 
			return true;
		}
	}
	return false;
} 

