$(window).ready(function() {
    var forms = document.getElementsByTagName("form");
    
    for (var i = 0; i < forms.length; i++) {
        forms[i].addEventListener("submit",initCheck,false);
    }
    
    $(".checkbox-block").click(function(e) {
		var input = $(this).find("input[type=checkbox]");
		if (e.target.nodeName == 'INPUT') {
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
		$(input).addClass("d-block")
		$(input).removeClass("d-none")
	}, function() {
		var input = $(this).find("input[type=checkbox]");
		$(input).addClass("d-none")
		$(input).removeClass("d-block")
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
}