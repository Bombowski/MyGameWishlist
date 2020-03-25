window.onbeforeunload = function() {
	$.ajax({
	    url: '/MyGameWishlist/AddGameOptions',
	    type: 'DELETE',
	    success: function(result) {}
	});
}

$(window).ready(function() {
    var tbl1 = document.getElementById("store1");
    var tbl2 = document.getElementById("store2");
    var tbl3 = document.getElementById("store3");
    
    $("#st1").click(function() {
        $(tbl1).show();
        $(tbl2).hide();
        $(tbl3).hide();
    });
    
    $("#st2").click(function() {
        $(tbl2).show();
        $(tbl1).hide();
        $(tbl3).hide();
    });
    
    $("#st3").click(function() {
        $(tbl3).show();
        $(tbl1).hide();
        $(tbl2).hide();
    });
    
    if ($(tbl1).is(":empty")) {
    	$(tbl1).hide();
    	$("#st1").hide();
    }
    
    if ($(tbl2).is(":empty")) {
    	$(tbl2).hide();
    	$("#st2").hide();
    }
    
    if ($(tbl3).is(":empty")) {
    	$(tbl3).hide();
    	$("#st3").hide();
    }
});