var currentCity = "";
var cityId = "";
var attractionId = "";
var currentCityDescription = "";

var attractionData = [];
var container = "";
var editor = "";

$(document)
		.ready(
				function() {

					/* $("#cityDescArea").hide(); */

					tinymce
							.init({
								selector : "textarea.textselect",
								forced_root_block : '',
								plugins : [
										"advlist autolink lists link image charmap print preview hr anchor pagebreak",
										"searchreplace wordcount visualblocks visualchars code fullscreen",
										"insertdatetime media nonbreaking save table contextmenu directionality",
										"template paste textcolor colorpicker textpattern" ],
								toolbar1 : "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
								toolbar2 : "preview media | forecolor backcolor"
							});
					/*
					 * tinymce.activeEditor.getContent(); // Get the raw
					 * contents of the currently active editor
					 * tinymce.activeEditor.getContent({format : 'raw'});
					 */
					// Get content of a specific editor:
				});

// this function is used to get the data corresponding to the trip ID
function getTripStages() {
	var tripId = $("#tripId").val();
	if (tripId == "") {
		alert("please enter trip ID");
	} else {
		$('#responsive').modal('show');
		$
				.ajax({
					type : "GET",
					url : DOMAIN + 'tripVideoStatus/getTripVideoStages' + "/"
							+ tripId,
					success : function(data) {
						uncheckStages();
						if (data == "" || data == null) {
							alert("No Stages Found!");
						} else {
							for (i = 0; i < data.length; i++) {
								switch (data[i]['tripVideoStatus']) {
								case 'TRIP_PUBLISH':
									document.getElementById("trip_pub").checked = true;
									break;
								case 'VIDEO_GENERATED':
									document.getElementById("vid_gen").checked = true;
									break;
								case 'VIDEO_REQUEST_RECEIVED':
									document.getElementById("vid_re").checked = true;
									break;
								case 'YOUTUBE_UPLOADED':
									document.getElementById("you_up").checked = true;
									break;
								case 'YOUTUBE_LIVE':
									document.getElementById("you_live").checked = true;
									break;
								case 'MAIL_SENT':
									document.getElementById("mail_sent").checked = true;
									break;
								case 'NOTIFICATION_REQUEST_RECEIVED':
									document.getElementById("not_req").checked = true;
									break;
								case 'NOTIFICATION_SENT':
									document
											.getElementById("Notification_sent").checked = true;
									break;
								}
							}
						}
						$('#responsive').modal('hide');
					},
					error : function(xhr, status, error) {
						$('#responsive').modal('hide');
						alert("Please enter valid Trip ID as no valid stages found!");
					},
					dataType : "JSON"
				});
	}
}

// this function is used to get the data corresponding to the trip ID
function getTripPath() {
	var tripId = $("#tripId").val();
	if (tripId == "") {
		alert("please enter trip ID");
	} else {
		$('#responsive').modal('show');
		$.ajax({
			type : "GET",
			url : DOMAIN + 'tripVideoStatus/getTripVideoPath' + "/" + tripId,
			success : function(data) {
				$("#server_path").val(data['serverPath']);
				$("#youtube_path").val(data['youTubePath']);
				if (data['youTubeStatus'] == "A")
					$("#video_status").val("A");
				else
					$("#video_status").val("I");
				$('#responsive').modal('hide');
			},
			error : function(xhr, status, error) {
				$('#responsive').modal('hide');
				alert("Please enter valid Trip ID as no valid paths found!");
			},
			dataType : "JSON"
		});
	}
}
function validate() {
	var serverPath = $("#server_path").val();
	var youTubePath = $("#youtube_path").val();
	var videoStatus = $("#video_status").val();
	if (serverPath == "" || youTubePath == "" || videoStatus == "") {
		return true;
	} else
		return false;
}
// function is called to update video path
function updateVideoPaths() {
	var serverPath = $("#server_path").val();
	var youTubePath = $("#youtube_path").val();
	var videoStatus = $("#video_status").val();
	var tripId = $("#tripId").val();
	if (validate()) {
		alert("please enter path properly");
	} else {
		var data = {};
		data.serverPath = serverPath;
		data.youTubePath = youTubePath;
		data.videoStatus = videoStatus;
		data.tripId = tripId;
		$('#responsive').modal('show');
		$.ajax({
			type : "POST",
			url : DOMAIN + 'tripVideoStatus/updateTripVideoPath',
			data : data,
			success : function(data) {
				alert("Path succesfully updated");
				$("#server_path").val("");
				$("#youtube_path").val("");
				$("#video_status").val("");
				$('#responsive').modal('hide');
			},
			error : function(xhr, status, error) {
				$('#responsive').modal('hide');
				alert("Please enter valid Trip ID as no valid paths found!");
			}
		});
	}
}

// function is called to update video stages
function updateTripVideoStages() {
	if ($('#trip_pub').is(":checked")) {
		var TripPub = $('#trip_pub').val();
	} else
		var TripPub = null;
	if ($('#vid_gen').is(":checked")) {
		var VidGen = $('#vid_gen').val();
	} else
		var VidGen = null;
	if ($('#vid_re').is(":checked")) {
		var VidReq = $('#vid_re').val();
	} else
		var VidReq = null;
	if ($('#you_up').is(":checked")) {
		var YouUp = $('#you_up').val();
	} else
		var YouUp = null;
	if ($('#you_live').is(":checked")) {
		var YouLive = $('#you_live').val();
	} else
		var YouLive = null;
	if ($('#mail_sent').is(":checked")) {
		var mailSent = $('#mail_sent').val();
	} else
		var mailSent = null;
	if ($('#not_req').is(":checked")) {
		var NotReq = $('#not_req').val();
	} else
		var NotReq = null;
	if ($('#Notification_sent').is(":checked")) {
		var NotSent = $('#Notification_sent').val();
	} else
		var NotSent = null;
	var data = {};
	var tripId = $("#tripId").val();
	data.tripId = tripId;
	data.TripPub = TripPub;
	data.VidGen = VidGen;
	data.VidReq = VidReq;
	data.YouUp = YouUp;
	data.YouLive = YouLive;
	data.mailSent = mailSent;
	data.NotReq = NotReq;
	data.NotSent = NotSent;
	$('#responsive').modal('show');
	$.ajax({
		type : "POST",
		url : DOMAIN + 'tripVideoStatus/updateTripVideoStages',
		data : data,
		success : function(data) {
			alert("Stages succesfully updated");
			uncheckStages();
			$('#tripId').val("");
			$('#responsive').modal('hide');
		},
		error : function(xhr, status, error) {
			$('#responsive').modal('hide');
			alert("Please enter valid Trip ID as no valid stages were found!");
			uncheckStages();
			$('#tripId').val("");
		}
	});
}

function uncheckStages() {
	document.getElementById("trip_pub").checked = false;
	document.getElementById("vid_gen").checked = false;
	document.getElementById("vid_re").checked = false;
	document.getElementById("you_up").checked = false;
	document.getElementById("you_live").checked = false;
	document.getElementById("mail_sent").checked = false;
	document.getElementById("not_req").checked = false;
	document.getElementById("Notification_sent").checked = false;

}
