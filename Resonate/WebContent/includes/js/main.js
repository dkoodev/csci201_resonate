
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
});