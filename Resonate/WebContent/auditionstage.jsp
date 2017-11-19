<%@ include file="includes/global_header.jsp" %>
<%@ page import="java.util.Vector" %>
<%
String pidStr = request.getParameter("project");
if (pidStr == null || pidStr.equals("")) {
	response.sendRedirect("/Resonate/browseProjects.jsp");
	return;
}
int projid = Integer.parseInt(pidStr);
Project p = JDBCDriver.getProject(projid);

if (p == null) {
	response.sendRedirect("/Resonate/browseProjects.jsp");
	return;
}
session.setAttribute("project", p);

Vector<Track> tracks = p.getTracks();
%>
<div id="playhead">
	<div id="topArrow"></div>
	<div id="line"></div>
</div>
<div id="hideDiv"></div>
<% 
for (int i=0; i<tracks.size(); i++ ) {
%>
<div id="track_<%= i %>" class="snapTrack trackable">
	<div style="float:left">
		<span class="trackTitle"><%= tracks.elementAt(i).getName() %></span><br />
		<span class="creator">Creator: <%= tracks.elementAt(i).getCreator().getName() %></span><br />
	</div>
	<div style="float:right; text-align: right;">
		<span id="duration_0">00:00:00</span><br />
		<a href="#" onClick="addTrackVote(<%=p.getId() %>, <%=tracks.elementAt(i).getId() %>);"><img src="images/vote_orange.png" class="voteArrow" /></a>
		<span id="track_vote_<%=tracks.elementAt(i).getId() %>" class="voteNums"><%= tracks.elementAt(i).getVotes() %></span>
	</div>
</div>
<audio id="audio_<%=i %>" src="<%= tracks.elementAt(i).getFileLocation() %>" preload="auto"></audio>
<%
}
%>
<!-- 
<div id="track_0" class="snapTrack trackable">
	<div style="float:left">
		<span class="trackTitle">Track Title</span><br />
		<span class="creator">Creator: isaac</span><br />
	</div>
	<div style="float:right; text-align: right;">
		<span id="duration_0">00:00:00</span><br />
		<img src="images/vote_orange.png" class="voteArrow" />
		<span id="vote_track_0" class="voteNums">12</span>
	</div>
</div>
<div id="track_1" class="snapTrack trackable">
	<div style="float:left">
		<span class="trackTitle">Track Title 2</span><br />
		<span class="creator">Creator: git god</span><br />
	</div>
	<div style="float:right; text-align: right;">
		<span id="duration_1">00:00:00</span><br />
		<img src="images/vote_orange.png" class="voteArrow" />
		<span id="vote_track_0" class="voteNums">14</span>
	</div>
</div>
<div id="track_2" class="snapTrack trackable">
	<div style="float:left">
		<span class="trackTitle">Solo</span><br />
		<span class="creator">Creator: git god</span><br />
	</div>
	<div style="float:right; text-align: right;">
		<span id="duration_2">00:00:00</span><br />
		<img src="images/vote_orange.png" class="voteArrow" />
		<span id="vote_track_0" class="voteNums">8</span>
	</div>
</div> -->
<!-- 
<audio id="audio_0" src="uploads/tracks/project1/project1_audio1_guitar1.mp3" preload="auto"></audio>
<audio id="audio_1" src="uploads/tracks/project1/project1_audio2_bass1.mp3" preload="auto"></audio>
<audio id="audio_2" src="uploads/tracks/project1/project1_audio3_guitar2.mp3" preload="auto"></audio>-->
<table style="width:100%; height:100%; overflow-x: scroll;">
	<tr style="width:100%; height:625px;">
		<td style="width: 320px; padding: 0px 7px 0px 7px;">
		<div class="title" style="width:306px;">Tracks</div>
		<div style="width:306px; height: 590px;">
			&nbsp;
		</div>
		</td>
		<td style="width:75%; background: #f0f0f0; border-left:#979797 2px solid;">
		<div class="title" style="width: 100%; margin-left: 5px; padding-left:2px;">Stage</div>
		<div style="width: 10px; height: 495px; float:left;">
				<br /><br />1
				<br /><br /><br />2
				<br /><br /><br />3
				<br /><br /><br />4
				<br /><br /><br />5
				<br /><br /><br />6
			</div>
			<div id="scroller">
				<div id="stage" style="width:8000px; height: 495px;">
					<div id="noInserts">Double click a track to add it to your mix!</div>
				</div>		
			</div>
			<div id="controls">
				<div id="projInfo">Project: <span class="bold"><%= p.getName() %></span><br />
								Project Owner: <span class="bold">You
								<% for (User editor : p.getEditors() ) {
									
								}
								%>
								</span></div>
				<div id="stopBtn"><div id="stopSquare"></div></div>
				<div id="playBtn"><div id="playTriangle"></div></div>
			</div>
		</td>
	</tr>
</table>

<form id="style="opacity:0;" method="POST" action="SaveProject">
	<input type="hidden" name="projectid" value="<%=p.getId() %>" />
</form>

<script type="text/javascript">

// TODO: Potentially waveforms, if there's time.
// https://www.npmjs.com/package/waveform-data

var tracksin = 0;
var scrollOffset = 0;
var playhead = 0;
var playing = false;

function audioLoad(audio, element, index) {
	$(element).css('opacity', 1);
	
	var aWidth = 40*(audio.duration);
	
	var min = Math.floor((audio.duration)/60);
	var sec = Math.floor(audio.duration);
	var ms = Math.floor(((audio.duration%1)*1000)%60);
	var timeString;
	if (min < 10) {
		timeString = "0" + min.toString() + ":"; 
	} else {
		timeString = min.toString() + ":";
	}
	if (sec < 10) {
		timeString = timeString + "0" + sec.toString() + ":"; 
	} else {
		timeString = timeString + sec.toString() + ":";
	}
	if (ms < 10) {
		timeString = timeString + "0" + ms.toString(); 
	} else {
		timeString = timeString + ms.toString();
	}
	$("#duration_" + index.toString()).text(timeString);
	
	return aWidth;
}

$(function() {
	$('.trackable').each(function(index) {
		var element = document.getElementById('track_' + index.toString());
		
		/* Initializing audio */
		var audio = document.getElementById("audio_" + index.toString());
		var aWidth = 60;
		$(audio).data('tOffset', 0);
		audio.loop = false;
		if (audio.readyState > 3) {
			aWidth = audioLoad(audio, element, index);
		} else {
			$(audio).on('canplaythrough', function(event) {
				aWidth = audioLoad(audio, element, index);
			});
		}
		/* Audio initialized */
		
		var x = 0, y = 0;
		var box = (130 + 72*index).toString() + "px";

		$(element).css('top', box);
		$(element).data('inserted', false);
		$(element).data('xVal', null);
		$(element).data('yVal', null);
		
		var dragobj = interact(element);
		dragobj.draggable({
			snap: {
				targets: [interact.createSnapGrid({ x: 10, y: 64 })],
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
			
			x = $(element).data('xVal');
			x += event.dx;
			$(element).data('xVal', x);
			$(element).data('yVal', y);
			//console.log($(element).data('xVal'));
			
			event.target.style.webkitTransform =
			event.target.style.transform =
			    'translate(' + x + 'px, ' + y + 'px)';
			
	
			var ofs = $(audio).data('tOffset');
			$(audio).data('tOffset', ofs + (0.022*event.dx));
			console.log($(audio).data('tOffset'));
			if (playhead == 0) audio.currentTime = 0;
			else audio.currentTime = (playhead-$(audio).data('tOffset'));
		}).draggable(false);
	  	
		dragobj.on('doubletap', function (event) {
			if (playhead == 0) audio.currentTime = 0;
			else audio.currentTime = (playhead-$(audio).data('tOffset'));
			
			if (x == 0 && y == 0) {
				x = 333; y = 22 + 72*(tracksin-(index));
				$(element).data('inserted', true);
				$(element).data('xVal', x);
				$(element).data('yVal', y);
				  element.style.webkitTransform =
					    element.style.transform =
					        'translate(' + (333 - scrollOffset) + 'px, ' + y + 'px)';
				  $(element).css('width', aWidth);
				  $(element).css('z-index', 5);
				  $(element).css('background', '#ffffff url(images/waveform' + index%3 + '.PNG) repeat-x left bottom');
				  tracksin++;
				  $(element).addClass('snapTrackInserted');
				  $(element).removeClass('snapTrack');
				  
				  $('#noInserts').css('opacity', 0);
				  
				  dragobj.draggable(true);
		
			  } else {
				if (!audio.paused) audio.pause();
			  	element.style.webkitTransform =
				    element.style.transform =
				        'translate(0px, 0px)';
			  	x=y=0;
			  	$(element).data('inserted', false);
			  	$(element).data('xVal', null);
				$(element).data('yVal', null);
				$(audio).data('tOffset', 0);
			  	dragobj.draggable(false);
			  	$(element).css('width', 306);
			  	$(element).css('z-index', 15);
			  	$(element).addClass('snapTrack');
			  	$(element).removeClass('snapTrackInserted');
			  
			  	tracksin--;
			  }
	  	});
	});
	
	$('#scroller').scrollLeft(0);
	var prevScroll = 0;
	var scrollAmt = 0;
	
	$('#scroller').scroll(function() {
		scrollOffset = $('#scroller').scrollLeft();
		scrollAmt = scrollOffset - prevScroll;
		$('.trackable').each(function(index) {
			var element = document.getElementById('track_' + index.toString());
			var x = $(element).data('xVal') - scrollAmt;
			console.log(x);
			$(element).data('xVal', x);
			var y = $(element).data('yVal');
			//y += event.dy; TODO
			
			element.style.webkitTransform =
			element.style.transform =
			    'translate(' + x + 'px, ' + y + 'px)';
		});
		prevScroll = $('#scroller').scrollLeft();
	});
	
	$("#playBtn").click(function() {
		$('.trackable').each(function(index) {
			var element = document.getElementById("track_" + index.toString());
			var audio = document.getElementById("audio_" + index.toString());
			
			if (!playing) {
				if (!$(element).data('inserted')) {
					if (audio.ended) {
						audio.currentTime = 0;
					}
				}
			} else {
				audio.pause();
			}
		});
		if (!playing) {
			playing = true;
			$('#playBtn').html('<div id="pauseSq"><div class="pauseRect"></div><div class="pauseRect"></div></div>');
		} else {
			playing = false;
			$('#playBtn').html('<div id="playTriangle"></div>');
		}
	});
	
	$("#stopBtn").click(function() {
		$('.trackable').each(function(index) {
			//var element = document.getElementById("track_" + index.toString());
			var audio = document.getElementById("audio_" + index.toString());
			
			audio.pause();
			audio.currentTime = 0;
		});
		playing = false;
		$('#playBtn').html('<div id="playTriangle"></div>');
		playhead = 0;
		var playheadDiv = document.getElementById("playhead");
		
		playheadDiv.style.webkitTransform =
			playheadDiv.style.transform =
			    'translate(0px, 0px)';
	});
	
	$("#saveBtn").click(function() {
		$('.trackable').each(function(index) {
			
		});
	});
});
var d = new Date();
var prevTime = d.getTime();
var currTime;
setInterval(function () {
	d = new Date();
	currTime = d.getTime();
	
	var addTime = currTime-prevTime;
	if (playing) {
		playhead += addTime/1000;
		console.log(playhead);
		var playheadDiv = document.getElementById("playhead");
			
		var playheadPos = (playhead*(1000/25)-scrollOffset);
		
		if (playheadPos < 0) {
			$(playheadDiv).hide();
		} else {
			$(playheadDiv).show();
			playheadDiv.style.webkitTransform =
				playheadDiv.style.transform =
				    'translate(' + playheadPos + 'px, 0px)';
		}
		
		var anyplays = false;

		$('.trackable').each(function(index) {
			var element = document.getElementById("track_" + index.toString());
			var audio = document.getElementById("audio_" + index.toString());
			if (!audio.playing && $(element).data('inserted')) {
				anyplays = true;
				var cOffset = $(audio).data('tOffset');
				if (playhead >= cOffset && playhead < (cOffset + audio.duration)) {
					audio.play();
				}
			}
		});
		
		if (!anyplays) {
			playhead = 0;
			playing = false;
			$('#playBtn').html('<div id="playTriangle"></div>');
		} else anyplays = false;
	}
	
	d = new Date();
	prevTime = d.getTime();
}, 300);

</script>
<%@ include file="includes/global_footer.jsp" %>