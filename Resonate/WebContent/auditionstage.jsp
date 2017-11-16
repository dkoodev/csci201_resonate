<%@ include file="includes/global_header.jsp" %>
<%
/*if (u == null) {
	response.sendRedirect("/Resonate/login.jsp");
} 
if (p == null) {
	response.sendRedirect("/Resonate/myprojects.jsp");
}*/
%>

<table style="width:100%; height:100%; overflow-x: scroll;">
	<tr style="width:100%; height:625px;">
		<td style="width: 320px; padding: 0px 7px 0px 7px;">
		<div style="width:306px; height: 30px;">Tracks</div>
		<div style="width:306px; height: 595px;">
			<div id="track_0" class="snapTrack" style="margin-bottom: 12px; width: 100%; height:60px; border: #000000 2px solid; -webkit-transition: transform 300ms, width 300ms; transition: transform 300ms, width 300ms; -moz-user-select: -moz-none; -khtml-user-select: none; -webkit-user-select: none; -ms-user-select: none; user-select: none;">
				<div style="float:left">
					Track Title<br />
					Creator: isaac<br />
				</div>
				<div style="float:right;">
					00:00:00<br />
					Votes
				</div>
			</div>
			<div id="track_1" class="snapTrack" style="width: 100%; height:60px; border: #000000 2px solid; -webkit-transition: transform 300ms, width 300ms; transition: transform 300ms, width 300ms; -moz-user-select: -moz-none; -khtml-user-select: none; -webkit-user-select: none; -ms-user-select: none; user-select: none;">
				<div style="float:left">
					Track Title 2<br />
					Creator: git god<br />
				</div>
				<div style="float:right;">
					00:00:00<br />
					Votes
				</div>
			</div>
		</div>
		</td>
		<td style="width:75%; background: #f0f0f0; border-left:#979797 2px solid;">
		<div style="width: 100%; height: 30px;" >Stage</div>
		<div style="width: 10px; height: 595px; float:left;">
				<br />1
				<br /><br /><br />2
				<br /><br /><br />3
				<br /><br /><br />4
				<br /><br /><br />5
				<br /><br /><br />6
			</div>
			<div id="scroller" style="width: 940px; height: 595px; overflow-x:scroll; float:left;">
				<div id="stage" style="width:4000px; height: 575px;">
					
				</div>		
			</div>
		</td>
	</tr>
</table>
<div id="bug"></div>
<script type="text/javascript">
var tracksin = 0;
//var tracksout = 2;
$('.snapTrack').each(function(index) {
	var x = 0, y = 0;
	var element = document.getElementById('track_' + index.toString());
	var dragobj = interact(element);
	  	  dragobj.on('doubletap', function (event) {
		  if (x == 0 && y == 0) {
			  x = 333; y = 22 + 72*(tracksin-(index));
			  element.style.webkitTransform =
				    element.style.transform =
				        'translate(333px, ' + y + 'px)';
			  $(element).css('width', 1200);
			  tracksin++;
			  dragobj.draggable({
				    snap: {
				      targets: [
				        interact.createSnapGrid({ x: 20, y: 64 })
				      ],
				      range: Infinity,
				      relativePoints: [ { x: 0, y: 0 } ]
				    },
				    inertia: true,
				    restrict: {
				      restriction: document.getElementById('stage'),
				      elementRect: { top: 0, left: 0, bottom: 1, right: 1 },
				      endOnly: true
				    }
				  })
				  .on('dragmove', function (event) {
				    x += event.dx;
				    //y += event.dy; TODO

				    event.target.style.webkitTransform =
				    event.target.style.transform =
				        'translate(' + x + 'px, ' + y + 'px)';
				  });
		  } else {
		  	element.style.webkitTransform =
			    element.style.transform =
			        'translate(0px, 0px)';
		  			x=y=0;
		  			dragobj.draggable(false);
		  			$(element).css('width', 306);
		  			tracksin--;
		  }
	  });
});

$(function() {
	$('#scroller').scrollLeft(0);

	$('#scroller').scroll(function() {
		//$('#track_1').css('transform-x', $('#scroller').scrollLeft());
		$('#bug').html("<p>"+ $('#scroller').scrollLeft() +"</p>");
	});
});
</script>
<%@ include file="includes/global_footer.jsp" %>