$(window).ready(function() {
    $("#chkAll").click(function() {
        var boxes = $("input[type=checkbox]");
        var chk = true;
        
        for (var i = 0; i < boxes.length; i++) {
            if (boxes[i].id != "chkAll" && boxes[i].checked) {
                chk = false;
                break;
            }
        }
        
        for (var i = 0; i < boxes.length; i++) {
            if (boxes[i].id != "#chkAll") {
                $(boxes[i]).prop("checked",chk);
            }
        }
    });
});