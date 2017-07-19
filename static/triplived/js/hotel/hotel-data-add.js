 var currentCity = "" ;
 var cityId = "" ;
 var attractionId = "";
 var hotelId = "";
 var currentCityDescription="";
	 
 var hotels = []; 
 var container = "";
 var editor = "";
 var type = "new";
		 
$(document).ready(function() {
	
	 /*$("#cityDescArea").hide();*/
	 
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
	tinymce.activeEditor.getContent();
	// Get the raw contents of the currently active editor
	tinymce.activeEditor.getContent({format : 'raw'});*/
	// Get content of a specific editor:
	
	
	
	
	
		 var citiesDataSource = new Bloodhound({
			  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
			  queryTokenizer: Bloodhound.tokenizers.whitespace,
			  remote: '../citySuggest/%QUERY'
			});
			 
		 citiesDataSource.initialize();
		
		
		  
		 
		 $('#cityname').typeahead({
			  hint: true,
			  highlight: true,
			  minLength: 1
			},
			{
			  name: 'cities',
			  displayKey: 'cityName',
			  source: citiesDataSource.ttAdapter()
			  
			}).on('typeahead:selected', function(evt, item) {
			   cityId = item.id;
			   currentCity = item.cityName;
			   currentCityDescription = item.cityDescription;
			   
			   $("#cityDescArea").show();
		
			   if(currentCityDescription == undefined) {
				   
				   currentCityDescription = "";
			   }
			   
     			$("#newHotelName").val("");
				$("#latitude").val("");
				$("#longitude").val("");
				$("#starRatingid").val("");
				$("#Address").val("");
				$("#descriptionId").val("");
				$("#webSiteId").val("");
				
			    type = "new";
			    hotelId = null;
			   
			   $('#responsive').modal('show');
			   $.ajax({
					type: "GET",
					contentType: "application/json; charset=utf-8",
					url: '../hotelSuggest/portal'+"/"+cityId+"/*",
					success: function(data){
						hotels = {};
						var selectList = $("#hotelId");
						$("#hotelId").empty();
						
						for (var i = 0; i < data.length; i++) {
						    var option = document.createElement("option");
						    option.value = data[i].id;
						    option.text = data[i].name;
						    selectList[0].options.add(option);
						    
						    
						    hotels[data[i].id] = data[i];
						    
						}
						$('#responsive').modal('hide');
					},
					error: function(data) {
						$('#responsive').modal('hide');
					},
					dataType: "JSON"
				});
			   
			});
		 
		 
		 
		 $('#cityname').each(function() {
			   var elem = $(this);

			   // Save current value of element
			   elem.data('oldVal', elem.val());

			   // Look for changes in the value
			   elem.bind("propertychange change click keyup input paste", function(event){
			      // If value has changed...
			      if (elem.data('oldVal') != elem.val()) {
			       // Updated stored value
			    	 // alert(elem.val() + "=="+ elem.data('oldVal'));
			    	  if(currentCity != elem.val()){
			    		  //clear all text boxes
			    		  $("#attractionName").empty();
			    		  showHide();
			    	  }
			       elem.data('oldVal', currentCity);
			     }
			   });
			 });
			
			$(document).on("click",".deleteImage",function(e){

				var r = confirm("Are You Sure your want to delete this image! ");
		 		if (r == true) {
		 			var name = this.id;
					
		 			$('#responsive').modal('show');
					 $.ajax({
							type: "DELETE",
							contentType: "application/json; charset=utf-8",
							url: DOMAIN+'hotel/upload/delete/'+name+"/"+hotelId,
							success: function(data){
								
								var div = document.getElementById(name+"-div");
								$(div).remove();
								
								$('#responsive').modal('hide');
								
							},
							dataType: "JSON"
						});
		 		}  
				 
			});
		
			$("#hotelId").on('change',function(){
				showHide();
				if(this.value == ""){
					return;
				}
				hotelId = this.value;
				$("#newHotelName").val(hotels[hotelId].name);
				$("#latitude").val(hotels[hotelId].latitude);
				$("#longitude").val(hotels[hotelId].longitude);
				$("#starRatingId").val(hotels[hotelId].rating);
				$("#Address").val(hotels[hotelId].address);
				$("#descriptionId").val(hotels[hotelId].description);
				$("#webSiteId").val(hotels[hotelId].webSite);
				$("#newHotelName").attr("disabled", "disabled");
				type = "update";
				
				if(hotels[hotelId].hotelImages != undefined) {
					if(hotels[hotelId].hotelImages != "" ) {
						
						var rows = $();
				        $.each(hotels[hotelId].hotelImages, function (index, img) {
				        	
				        	var name = img.split("___")[0];
				        	var imgSrc = img.split("___")[0];
				        	
				            var row = drawImages(imgSrc, name) ;
				            rows = rows.add(row);
				            
				        });
				        
				        $("#existing_images").append(rows);
				        
				        $("#totalimages").val(hotels[hotelId].hotelImages.length);
				        $("#existing-images-div").show();
				        $("#existing_images").append(rows);
					}else{
						
						$("#existing-images-div").hide();
						$("#existing_images").text('');
					}
				}
				
				
			});
		 
		 	
		 	$('#fileupload').fileupload({
		 		dataType : 'json',
		 		acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		 		url : DOMAIN+'hotel/add/ajax-post-data',
		 		singleFileUploads: false,
		 		disableValidation : true,
		 		maxFileSize: 1024 * 1024*3,
		 	    messages: {
		 	        maxFileSize: 'File exceeds maximum allowed size of 3MB',
		 	    },
		 		formData : function(form) {

		 			$("input[type='hidden']").remove();
		 			
		 			/*//$('<input />').attr({type : 'hidden',id : 'cityId' ,name : 'cityId',      value : cityId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'attractionId',name : 'attractionId',value : attractionId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'cityId',name : 'cityId',value : cityId,}).appendTo('form');
		 			
		 			$('<input />').attr({type : 'hidden',id : 'googlePlaceid',name : 'googlePlaceid',value : $("#googlePlaceid1").val(),}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'googlePlaceName',name : 'googlePlaceName',value : $("#googlePlaceName1").val(),}).appendTo('form');
		 			

		 			*/
		 			
		 			$('<input />').attr({type : 'hidden',id : 'cityId',name : 'cityId',value : cityId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'type',name : 'type',value : type,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'hotelName',name : 'hotelName',value : $("#newHotelName").val(),}).appendTo('form');
		 			
		 			//currentCityDescription =  tinyMCE.get('cityDescription').getContent();
		 			return form.serializeArray();
		 		}

		 	}).bind('fileuploadprocessalways', function (e, data) {
		 		var currentFile = data.files[data.index];
		 	    if (data.files.error && currentFile.error) {
		 	      // there was an error, do something about it
		 	     alert(currentFile.error);
		 	    }
		     }).on('fileuploaddone', function (e, data) {
		    	 
		    	  if(alert('Hotel updated !!')){}
		    	  else    window.location.reload(); 
		    	  
		    	  $("#newHotelName").val("");
					$("#latitude").val("");
					$("#longitude").val("");
					$("#starRatingId").val("");
					$("#Address").val("");
					$("#descriptionId").val("");
					$("#webSiteId").val("");
					
				   alert("Hotel updated");
				   
		    	 var rows = $();
			        $.each(data._response.result.files, function (index, img) {
			        	
			        	var name = img.name;
			        	var imgSrc = img.thumbnailUrl;
			        	
			            var row = drawImages(imgSrc, name);
			            rows = rows.add(row);
			            
			        });
			        
			        $("#existing_images").append(rows);
			        
		     
			}).bind('fileuploadadd', function (e, data) {
		      
		     	var currentfiles = [];
		         $(this).fileupload('option').filesContainer.children().each(function(){
		             currentfiles.push($.trim($('.name', this).text()));
		         });

		         if(currentfiles.length > 0 ) {
						
						alert("Please upload the select files first. You can either select multiple files in one shot and upload ");
						data.files = null;
				}
		         else{
		        	 
		        	 data.files = $.map(data.files, function(file,i){
			             if ($.inArray(file.name,currentfiles) >= 0) { 
			                 alert("File "+file.name+" is already present.");
			                 $('.fileupload-process').hide();
			                 return null; 
			             }else{
			            	 if (cityId == "") {
			 					$('.fileupload-process').hide();
			 					alert("Please select city");
			 					return null;
			 				}
			 				if ($("#newHotelName").val() == "") {
			 					$('.fileupload-process').hide();
			 					alert("Please select hotel ");
			 					return null;
			 				}
			               }
			             return file;
			         });
		        	 
		        	 $("#addPhotosId").attr('disabled',true);
		         }
		         
		         
		     	
		      });
		  
		 	function drawImages( imgSrc, name) {
		 		
		 		var row = $('<div class="template-download col-lg-3" id = "'+name+'-div"><span class="name"></span><br/><span class="delete"></span></div>');
		     	var link = "<a target ='blank' href='http://119.81.58.12"+imgSrc+"'>" ;
		     	link = link + "<img class='img-responsive' style= 'border-radius: 2px;border: 1px solid black;' height='140px' width ='140px' src ='http://119.81.58.12"+imgSrc+"' /> </a> <br><span>Name:"+ name +"</span>" ;
		        
		     	row.find('.name').append(link);
		     	//row.find('.delete').append("<input type ='button' class='deleteImage' name='delteimage' value='Delete Image'  id = '"+name+"' />");
         
		        return row ;
		 	}
     });

function showHide() {
	
	$("#attractionDescription").val("");
	$("#attractionPunchline").val("");
	$("#cityDescription").val("");

	$("#latitude").val("");
	$("#longitude").val("");
	$("#message").val("");
	$('#longitude').prop('readonly', false);
	$('#latitude').prop('readonly', false);

	$("#existing_images").text('')
	
	 $("#googleplacename1").val("");
	
	 $("#googleplaceid1").val("");
}

function inactivateAttraction () {
	
	alert("hi")
}
function submitForm(){
	
	   if( $(document).find('.files').find(".start") != undefined &&  $(document).find('.files').find(".start").length > 0){
		   
		   $(document).find('.files').find(".start").click();
		   
	   }else {
	       var data = {};
	       
	       if(validate() == true) {
	    	   data.cityId= cityId;
			   data.hotelId = hotelId;
			   data.latitude= $("#latitude").val();
			   data.longitude = $("#longitude").val();
			   data.hotelName = $("#newHotelName").val();
			   data.descriptionId = $("#descriptionId").val();
			   data.starRatingId = $("#starRatingId").val();
			   data.Address = $("#Address").val();
			   data.type = type;
			   if(type == "new") {
				   data.hotelId = null;   
			   }
			   $("#submitAttractionBtnId").prop("disabled", true);
			   //$('#responsive').modal('show');
			   $.post(DOMAIN+'hotel/add/ajax-post-data', data)
			   .done(function( data ) {
				   $("#newHotelName").val("");
					$("#latitude").val("");
					$("#longitude").val("");
					$("#starRatingid").val("");
					$("#Address").val("");
					$("#descriptionId").val("");
					$("#webSiteId").val("");
					
				   alert("Hotel updated");
			   });
	       }
		  
	   }
}

function validate() {
	
	if (cityId == "") {
		$('.fileupload-process').hide();
		alert("Please select city");
		return null;
	}
	
	if($("#newHotelName").val() == ""){
		alert("Please enter Hotel name");
		return null;
	}
	
	if($("#starRatingid").val() == ""){
		alert("Please enter Star Rating");
		return null;
	}
	if($("#Address").val() == ""){
		alert("Please enter Address");
		return null;
	}
	
	
 
	return true;
}	


function enableUpdate() {
	$("#newHotelName").attr("disabled", "disabled");
	$("#latitude").val("");
	$("#longitude").val("");
	$("#starRatingid").val("");
	$("#Address").val("");
	$("#descriptionId").val("");
	$("#webSiteId").val("");
	type = "update";
	
};

function addNew() {
	$("#newHotelName").removeAttr("disabled");
	$("#newHotelName").val("");
	$("#latitude").val("");
	$("#longitude").val("");
	$("#starRatingid").val("");
	$("#Address").val("");
	$("#descriptionId").val("");
	$("#webSiteId").val("");
	type = "new";
};
