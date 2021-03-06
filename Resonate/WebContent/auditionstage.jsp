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
		<span id="duration_<%=i%>">00:00:00</span><br />
		<a href="#" onClick="addTrackVote(<%=p.getId() %>, <%=tracks.elementAt(i).getId() %>);"><img src="images/vote_orange.png" class="voteArrow" /></a>
		<span id="track_vote_<%=tracks.elementAt(i).getId() %>" class="voteNums"><%= tracks.elementAt(i).getVotes() %></span>
	</div>
</div>
<script type="text/javascript">
var element = document.getElementById("track_<%=i%>");
$(element).data("trackId", <%= tracks.elementAt(i).getId() %>);
<% float del = tracks.elementAt(i).getDelay(); %>
$(element).data("savedOffset", <%= del %>);
</script>
<audio id="audio_<%=i %>" src="<%= tracks.elementAt(i).getFileLocation() %>" preload="auto"></audio>
<%
}
%>
<div id="addTrack"><a href="addTrack.jsp">+ Add A Track!</a></div>

<table style="width:100%; height:100%; overflow-x: scroll;">
	<tr style="width:100%; height:625px;">
		<td style="width: 320px; padding: 0px 7px 0px 7px;">
		<div class="title" style="width:306px;">Tracks</div>
		<div style="width:306px; height: 590px;">
			&nbsp;
		</div>
		</td>
		<td style="width:75%; background: #f0f0f0; border-left:#979797 2px solid;">
		<div class="title" style="width: 100%; margin-left: 5px; padding-left:2px;">
			<div style="float:left;">Stage</div>
			<div style="float:right; padding: 4px 20px 0 0; font-size: 18px;">
			<% if (p.getId() == 13) { %>
			<a href="uploads/projects/resources/13_CityOfStars_Resources.zip">View Project Resouces</a></div>
			<% } else { %>
			<a href="#">View Project Resouces</a></div>
			<% } %>
		</div>
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
					<div id="noInserts">Double click a track to add it to your mix!
					<br />Or, click 
					<span style="color: #ffffff; background: #345678; border-radius: 6px; padding: 4px; font-weight: 700;">Load</span>
					 to hear the Editors' mix!
					</div>
				</div>		
			</div>
			<div id="controls">
				<div id="projInfo">Project: <span class="bold"><%= p.getName() %></span><br />
								Editors: <span class="bold">
								<% boolean canSave = false;
								for (User editor : p.getEditors() ) { 
									if (u != null && editor.getName().equals(u.getName())) {
										canSave = true;
								%>
									<%="You" %>
									<% } else { %>
										<%=editor.getName()%>
									<% } %>
									<%=(!editor.equals(p.getEditors().lastElement())) ? ", " : "" %> 
								<% } %>
								</span></div>
				
				<div id="stopBtn"><div id="stopSquare"></div></div>
				<div id="playBtn"><div id="playTriangle"></div></div>
				
				<div id="downloading"><img src="images/loading-crop.gif" /></div>
				<div id="downloadBtn">Download!</div>
				<% if (canSave) { %>
				<div id="saveBtn">Save</div>
				<% } %>
				<div id="loadBtn">Load</div>
			</div>
		</td>
	</tr>
</table>

<form id="saveForm" style="opacity:0;" method="POST" action="SaveProject">
	<input type="hidden" name="projectid" value="<%=p.getId() %>" />
</form>
<form id="downloadForm" style="opacity:0;" method="POST" action="DownloadServlet">
	<input type="hidden" name="projectid" value="<%=p.getId() %>" />
</form>
<script type="text/javascript">

var trackStack = new Array();


var tracksin = 0;
var scrollOffset = 0;
var playhead = 0;
var playing = false;
var finalIndex = 0;

var cEvent = jQuery.Event("logged");

function audioLoad(audio, element, index) {
	$(element).css('opacity', 1);
	
	var aWidth = 40*(audio.duration);
	$(element).data('aWidth', aWidth);
	var min = Math.floor((audio.duration)/60);
	var sec = Math.floor(audio.duration)-min*60;
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

function insertAudio(x, y, element, audio, aWidth, dragobj, index) {
	var getId = $(element).attr('id');
	var idParts = getId.split("_");
	var index = parseInt(idParts[1]);
	
	trackStack.push(index);
	
	//console.log(index);
	
	x = 333-scrollOffset; y = 22 + 72*(tracksin-(index));
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
	  
	  $(audio).data('tOffset', 0);
	  
	  dragobj.draggable(true);
}

function removeAudio(x, y, element, audio, dragobj, index) {
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
  
  	$(audio).data('tOffset', -1);
  	
  	tracksin--;
  	
  	var removing = trackStack.lastIndexOf(index);
  	if (removing == trackStack.length-1) {
  		trackStack.pop();
  	} else {
  		var temp = trackStack.pop();
  		trackStack[removing] = temp;
  		
  		// Its shifting time!
  		var newElement = document.getElementById("track_" + temp.toString());
  		var xv = $(newElement).data('xVal');
  		var yv = $(newElement).data('yVal');
  		var howMany = trackStack.length-removing;
  		yv -= howMany*72;
  		
  		newElement.style.webkitTransform =
			newElement.transform =
			    'translate(' + xv + 'px, ' + yv + 'px)';
  	}
}

$(function() {
	$('.trackable').each(function(index) {
		var element = document.getElementById('track_' + index.toString());
		
		/* Initializing audio */
		var audio = document.getElementById("audio_" + index.toString());
		var aWidth = 60;
		$(audio).data('tOffset', -1);
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
			console.log("double tapped");
			
			if (!$(element).data('inserted')) {
				insertAudio(x, y, element, audio, aWidth, dragobj, index);
				x = $(element).data('xVal');
				y = $(element).data('yVal');
			} else {
				removeAudio(x, y, element, audio, dragobj, index);
				x = $(element).data('xVal');
				y = $(element).data('yVal');
			}
			
			if (playhead == 0) audio.currentTime = 0;
			else {
				var mvOff = (playhead-$(audio).data('tOffset'));
				if (mvOff < 0) mvOff = 0
				audio.currentTime = mvOff;
				
				if (mvOff > playhead) audio.pause();
			}

	  	});
		
		$(element).on("logged", function(event) {
		
			console.log("logged");
			
			if (!$(element).data('inserted')) {
				insertAudio(x, y, element, audio, aWidth, dragobj, index);
				x = $(element).data('xVal');
				y = $(element).data('yVal');
			} else {
				removeAudio(x, y, element, audio, dragobj, index);
				x = $(element).data('xVal');
				y = $(element).data('yVal');
			}
			
			if (playhead == 0) audio.currentTime = 0;
			else {
				var mvOff = (playhead-$(audio).data('tOffset'));
				if (mvOff < 0) mvOff = 0
				audio.currentTime = mvOff;
				
				if (mvOff > playhead) audio.pause();
			}
		});
		
		$(element).data('dragobj', dragobj);
		$(dragobj).trigger("doubletap");
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
		var numberoftracks = 0;
		$('.trackable').each(function(index) {
			var element = document.getElementById("track_" + index.toString());
			var audio = document.getElementById("audio_" + index.toString());
			var delay = $(audio).data('tOffset');
			if (delay == null) delay = -1;
			var tid = $(element).data('trackId');
			$("#saveForm").append("<input type=\"hidden\" name=\"track_" + index + "\" value=\""+ tid + " " + delay + "\" />");
			numberoftracks++;
		});
		$("#saveForm").append("<input type=\"hidden\" name=\"numTracks\" value=\"" + numberoftracks + "\" />");
		$("#saveForm").submit();
	});
	
	$("#loadBtn").click(function() {
		$('.trackable').each(function(index, element) {
			//var element = document.getElementById("track_" + index.toString());
			var audio = document.getElementById("audio_" + index.toString());
			var delay = $(element).data('savedOffset');
			var x = $(element).data('xVal');
			var y = $(element).data('yVal');
			var pOffset = delay*(1000/25);
			console.log("poffset " + pOffset);
			
			var isIn = $(element).data('inserted');
			
			//if its not inserted and supposed to be
			if(!$(element).data('inserted') && delay != -1) {
				$(element).trigger("logged");
				
				//x = $(element).data('xVal');
				//x += pOffset;
				x = 333 - scrollOffset + pOffset;
				$(element).data('xVal', x);
				$(audio).data('tOffset', delay).delay(1000);
				/*
				element.style.webkitTransform =
					element.style.transform =
					    'translate(' + x + 'px, ' + y + 'px)';
				*/
				setTimeout(function() {
					console.log(x);
					element.style.webkitTransform =
						element.style.transform =
						    'translate(' + x + 'px, ' + y + 'px)';
				}, 500);
			}
			// if its inserted and not supposed to be
			else if ($(element).data('inserted') && delay == -1) {
				$(element).trigger("logged");
			} 
			// if it inserted and is supposed to be: delay
			else if ($(element).data('inserted') && delay != -1) {
				//x = $(element).data('xVal');
				x = 333 - scrollOffset + pOffset;
				$(element).data('xVal', x);
				$(audio).data('tOffset', delay);
				
				var mvOff = (playhead-$(audio).data('tOffset'));
				if (mvOff < 0) {
					mvOff = 0;
				} 
				audio.currentTime = mvOff;
				if (mvOff > playhead) {
					audio.pause();
					audio.currentTime = 0;
				}
				
				
				/*element.style.webkitTransform =
					element.style.transform =
					    'translate(' + x + 'px, ' + y + 'px)';
				*/
				$(element).css({
					  '-webkit-transform' : 'translate(' + x + 'px, ' + y + 'px)',
					  '-moz-transform'    : 'translate(' + x + 'px, ' + y + 'px)',
					  '-ms-transform'     : 'translate(' + x + 'px, ' + y + 'px)',
					  '-o-transform'      : 'translate(' + x + 'px, ' + y + 'px)',
					  'transform'         : 'translate(' + x + 'px, ' + y + 'px)'
					});
			}
		});
	});
	
	$("#downloadBtn").click(function() {
		$("#downloading").css('opacity', 1);
		var numberoftracks = 0;
		$('.snapTrackInserted').each(function(index, element) {
			//var element = document.getElementById("track_" + index.toString());
			var tempId = $(element).attr('id');
			var idParts = tempId.split("_");
			var idNum = idParts[1];
			console.log(idNum);
			
			var audio = document.getElementById("audio_" + idNum);
			var delay = $(audio).data('tOffset');
			if (delay == null) delay = -1;
			var tid = $(element).data('trackId');
			$("#downloadForm").append("<input type=\"hidden\" name=\"track_" + index + "\" value=\""+ tid + "\" />");
			numberoftracks++;
		});
		$("#downloadForm").append("<input type=\"hidden\" name=\"numTracks\" value=\"" + numberoftracks + "\" />");
		$("#downloadForm").submit();
	});
	
});
var d = new Date();
var prevTime = d.getTime();
var currTime;
var cookieValue;
setInterval(function () {
	
	var cookieValue = Cookies.get('fileDownloadToken');
	console.log(cookieValue);
	if (cookieValue == "true") {
		$("#downloading").css('opacity', 0);
		Cookies.set('fileDownloadToken', 'false');
	}
	
	d = new Date();
	currTime = d.getTime();
	
	var addTime = currTime-prevTime;
	if (playing) {
		playhead += addTime/1000;
		//console.log(playhead);
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