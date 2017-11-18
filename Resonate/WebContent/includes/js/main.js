var $socket;
// Unless we have time, the login button just redirects to login.jsp
/* When the login button is clicked, display login form */
/*function loginPopup() {
	if ($('#loginPopup').css('display') == 'none') {
		$('#loginPopup').fadeIn(200);
	} else {
		$('#loginPopup').fadeOut(100);
	}
}
	*/
/* When anything other than the login form is clicked, hide the form */
/*
$(document).click(function(e) {
    if (e.target.id != 'loginPopup' && !$('#loginPopup').find(e.target).length
    	&& e.target.id != 'loginBtn' && !$('#loginBtn').find(e.target).length	) {
        $("#loginPopup").fadeOut(100);
    }
});
*/
/* Any functions that should be called at the beginning of each page call */
$(function() {
	var windowHeight = ("innerHeight" in window) ? window.innerHeight : document.documentElement.offsetHeight; 
	
	if($('#mainBody').height() < 625) {
		$('#footer').addClass('down');
	}
	
	$("#piano").height(windowHeight-125);
	
	
	$('.errorNotifier').popover().popover('show'); 
	$('.inputs').on('focus', function () {
		$('.errorNotifier').popover('hide');
		$('.errorNotifier').popover('disable');
	});
	
	$socket = new WebSocket("ws://localhost:8080/Resonate/resonating");
	$socket.onopen = function(event) {
		// TODO: do nothing, right?
	}
	$socket.onmessage = function(event) {
		var fullstring = event.data;
		var stringComponents = fullstring.split(" ");
		var type = stringComponents[0];
		var id = stringComponents[1].toString();
		var elem;
		if (type == "p") {
			elem = document.getElementById("project_vote_" + id);
		} else if (type == "t") {
			elem = document.getElementById("track_vote_" + id);
		}
		var votes = $(elem).text();
		console.log(votes);
		var vInt = parseInt(votes);
		console.log(vInt);
		
		$(elem).text(vInt+1);
		
	}
	$socket.onclose = function(event) {
		// TODO: throw error? Do nothing?
	}
});

function addProjectLike(projectid, userid) {
	if (userid != -1){ 
		$socket.send("p " + projectid + " " + userid);
	}
}

function addTrackVote(projectid, trackid) {
	if (projectid != -1){ 
		$socket.send("t " + trackid +  " " +projectid );
	}
}