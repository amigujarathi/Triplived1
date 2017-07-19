 var currentCity = "" ;
 var cityId = "" ;
 var attractionId = "";
 var currentCityDescription="";
	 
 var attractionData = []; 
 var container = "";
 var editor = "";
		 
$(document).ready(function() {
	
	 /* $("#cityDescArea").hide(); */
	 
	tinymce.init({
	    selector: "textarea.textselect",
	    forced_root_block : '',
	    plugins: [
					"advlist autolink lists link image charmap print preview hr anchor pagebreak",
					"searchreplace wordcount visualblocks visualchars code fullscreen",
					"insertdatetime media nonbreaking save table contextmenu directionality",
					"template paste textcolor colorpicker textpattern"
	    ],
	    toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
	    toolbar2: "preview media | forecolor backcolor"
	});
	/*
	 * tinymce.activeEditor.getContent(); // Get the raw contents of the
	 * currently active editor tinymce.activeEditor.getContent({format :
	 * 'raw'});
	 */
	// Get content of a specific editor:
     });

// function to fetch json

function fetchJson(){
	var tripId = $("#tripId").val();
	if(( $('#WebDialog_jsonEditor').children().length > 0)){
                $('#WebDialog_jsonEditor').empty();
            }
	if(tripId == ""){
		alert("please enter trip ID");
	}
	else{
		var compactjson;
		$.ajax({
			type: "GET",
			url: DOMAIN+'tripJson/ajax-fetch-json'+"/"+tripId,
			success: function(data){
				console.log(data);
				var json = data;
				var data = {};
				data.compactjson = json['tripDataEdited'];
				  $.post(DOMAIN+'tripJson/ajax-beautify-json', data)
				   .done(function( data ) {
					   var json = data;
						$('#jsonEditor').modal('show');
						container = document.getElementById("WebDialog_jsonEditor");
						var options = {
							      editable: function (node) {
							        switch (node.field) {
							          case 'tripId':
							            return false;
								  case (node.path,"id"):
							            return false;
								  case (node.path,"subTripId"):
							            return false;
							          default:
							            return true;
							        }
							      }
							    };
					    editor = new JSONEditor(container,options,json);
					    editor.set(json);
					    editor.expandAll();
				   });
			},
			error:function(xhr, status, error){
				alert("Please enter valid Trip ID!");
			},
			dataType: "JSON"
		});
	}
	
}


// function to submit json

function submitJson(){
    var json = editor.get();
    var tripId = json['tripId'];
    var compactJson = JSON.stringify(json, null, 2);
    var FinalJson = JSON.parse(compactJson);
    var data = FinalJson;
        $.ajax({
        headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
        },
        type: "POST",
        url: DOMAIN+'tripJson/ajax-submit-json',
        data:JSON.stringify(json),
        success: function(data){
	     alert("trip successfuly submitted");
            if(( $('#WebDialog_jsonEditor').children().length > 0)){
                $('#WebDialog_jsonEditor').empty();
            }
	   $('#jsonEditor').modal('hide');
        }
    });
}
// close json editor
function closeJsonEditor(){
	if(( $('#WebDialog_jsonEditor').children().length > 0)){
                $('#WebDialog_jsonEditor').empty();
            }
	$('#jsonEditor').modal('hide');
}