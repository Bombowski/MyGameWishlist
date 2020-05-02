$(window).ready(function() {
    var forms = document.getElementsByTagName("form");
    
    for (var i = 0; i < forms.length; i++) {
        forms[i].addEventListener("submit",initCheck,false);
    }
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
        }
    }
}