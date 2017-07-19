$(function() {
	
	$('#generalfileupload').fileupload({
		dataType : 'json',
		url : DOMAIN+'home/ajax-post',
		done : function(e, data) {
			//console.log("done");
			//console.log("done");
			var profileid = data.result.split(".")[0];
			var d = (new Date().getTime()) ;
			var imgPath = USER_IMAGES+"/"+profileid+"/"+data.result+"?"+d ;
			$("img#user-profile-image")[0].src = imgPath;
			$("img#uploaded-img")[0].src= imgPath; 
			
			var userImg = $("#uploaded-img")[0] ;
			userImg.src = imgPath;
			
			$("span.image-upload-save").removeClass("progress-save");
		},
		stop : function() {
			console.log("stop");
		},
		start : function() {
			console.log("start");
			$("span.image-upload-save").addClass("progress-save");
		},
		fail : function(e, data) {
			console.log("failed");
			$("span.image-upload-save").removeClass("progress-save");
		},

		formData : function(form) {

			$('<input />').attr({
				type : 'hidden',
				id : 'sequence',
				name : 'sequence',
				value : $("#sequence").html(),
			}).appendTo('form');

			return form.serializeArray();
		}
	});
	$( "#submitbasicprofiledescription" ).click(function( event ) {
		 
		 // Stop form from submitting normally
		event.preventDefault();
		
		if(validateaboutme()) {
			
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: DOMAIN+"addOrUpdate/updateAboutme",
				data: $("#about-me-description").val(),
				success: function(data){
					if(data.result != 'success') {
						alert("Problem occured. Please save again or save after some time if problem persist.");
					}
				},
				beforeSend : function(event, request, settings ) {$("span.basicprofiledescsave").addClass("progress-save");},
				complete   : function(event, request, settings ) {$("span.basicprofiledescsave").removeClass("progress-save");},
				failure : function (data) {
					//alert(data);
				},
				dataType: "JSON"
			});
		}
	});
});

function validateaboutme() {
	return true;
}