$(window).ready(function() {
    var forms = document.getElementsByTagName("form");
    
    for (var i = 0; i < forms.length; i++) {
        forms[i].addEventListener("submit",initCheck,false);
    }
    
    $(".checkbox-block").click(function(e) {
		var input = $(this).find("input[type=checkbox]");
		
		var node = e.target.nodeName; 
		if (node == 'INPUT' || node == 'A') {
			return;
		}
		
		if (input != undefined) {
			if ($(input).prop("checked") == true) {
				$(input).prop("checked",false);
			} else {
				$(input).prop("checked",true);
			}
		}
	});
	
	$(".hide-chkbox").hover(function() {
		var input = $(this).find("input[type=checkbox]");
		$(input).addClass("d-block").removeClass("d-none")
	}, function() {
		var input = $(this).find("input[type=checkbox]");
		$(input).addClass("d-none").removeClass("d-block")
	});
});

function initCheck(e) {
    var inputs = this.querySelectorAll("input[type=checkbox]");
    if (inputs.length == 0) {
    	return;
    }
    
    e.preventDefault();

    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].checked) {
            this.submit();
            return;
        }
    }
    
    var errors = $(".error");
    for (var i = 0; i < errors.length; i++) {
		$(errors).text("")
	}
    
    
    ele = this;
    var att = $("html").attr("url");
    var ref;
    if (att != undefined) {
    	ref = att;
    } else {
    	ref = window.location.href;
    }
    
    setTimeout(function() {
    	setError(ele, getError(ref.substring(ref.lastIndexOf("/") + 1)));
    }, 200);
}

function getError(page) {
	if (/^(.*MyList.*)$/.test(page) || /^(.*AddGameOptions.*)$/.test(page)) {
		return "No items were selected";
	} else if (/^(.*AddGameWishlist.*)$/.test(page)) {
		return "No stores were selected";
	} else if (/^(.*UpdateGame.*)$/.test(page) || /^(.*AddGame.*)$/.test(page)) {
		return "No game genres were selected";
	}
	return "Unexpected error";
}

function setError(element, message) {
	var error = element.getElementsByClassName("error");
	
	for (var i = 0; i < error.length; i++) {
		error[i].innerText = message;
		$(error[i]).removeClass("error").addClass("error");
	}
}