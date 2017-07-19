 var currentCity = "" ;
 var cityId = "" ;
 var attractionId = "";
 var currentCityDescription="";
	 
 var attractionData = []; 
		 
$(document).ready(function() {
	
	 $("#cityDescArea").hide();
	 $("#attractionDescArea").hide();
	 
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
	
	$("#existing-images-div").hide();
	
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
			   
			   $("#attractionDescArea").hide();
			   $("#cityDescArea").show();
			  /* if(currentCityDescription == undefined || currentCityDescription == "") {
				   
				   $("#cityDescArea").show();
				   
		 	   }else{
		 		   
		 		  $("#cityDescArea").hide();
		 	   }*/
		 
			   //$("#cityDescription").val(currentCityDescription);
			   if(currentCityDescription == undefined) {
				   
				   currentCityDescription = "";
			   }
			   window.parent.tinymce.get("cityDescription").setContent(currentCityDescription);
			   
			   
			   $('#responsive').modal('show');
			   $.ajax({
					type: "GET",
					contentType: "application/json; charset=utf-8",
					url: '../attractionSuggest'+"/"+cityId+"/*",
					success: function(data){
						attractionData = {};
						var selectList = $("#attractionName");
						$("#attractionName").empty();
						
						for (var i = 0; i < data.length; i++) {
						    var option = document.createElement("option");
						    option.value = data[i].id;
						    option.text = data[i].attractionName;
						    selectList[0].options.add(option);
						    
						    
						    attractionData[data[i].id] = data[i];
						    
						    
						    
						}
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
							url: DOMAIN+'attraction/upload/delete/'+name+"/"+attractionId,
							success: function(data){
								
								var div = document.getElementById(name+"-div");
								$(div).remove();
								
								$('#responsive').modal('hide');
								
							},
							dataType: "JSON"
						});
		 		}  
				 
			});
			
			$(document).on("click","#inactivateAttraction",function(e){

				var r = confirm("Are You Sure your want to inactivate this attrction! ");
		 		if (r == true) {
		 			var name = this.id;
					
		 			$('#responsive').modal('show');
					 $.ajax({
							type: "DELETE",
							contentType: "application/json; charset=utf-8",
							url: DOMAIN+'attraction/upload/inactivateAttraction/'+attractionId,
							success: function(data){
								
								/*var div = document.getElementById(name+"-div");
								$(div).remove();*/
								
								$('#responsive').modal('hide');
								if(data == 1){
									$("select#attractionName option[value='"+attractionId+"']").remove();
								}
								
							},
							dataType: "JSON"
						});
		 		}  
				 
			});
			
			
			$("#attractionName").on('change',function(){
				
				if(this.value == ""){
					return;
				}
				attractionId = this.value;
				var c = attractionData[attractionId] 
				var descr = c.description;
				
				showHide();
				$("#attractionDescArea").show();
				
				if(c.description == undefined){
					descr = "" ;
				}
				
				window.parent.tinymce.get("attractionDescription").setContent(descr) ;
				
				
				if(c.latitude == 0.0){
					$("#latitude").val(c.latitude);
				}else{
					$("#latitude").val(c.latitude);
					//$('#latitude').prop('readonly', true);
				}
				if(c.longitude == 0.0) {
					$("#longitude").val(c.longitude);
				}else{
					$("#longitude").val(c.longitude);
				//	$('#longitude').prop('readonly', true);
				}
				
				$("#attractionDescription").val(c.description);
				$("#attractionPunchline").val(c.punchLine);
				
				$("#googleplaceid1").val(c.googlePlaceId);
				$("#googleplacename1").val(c.googleplaceName);
				
				$("#placeTimeid1").val(c.timings);
				$("#placeAddressid1").val(c.address);
				
				$("#bestTimeid1").val(c.bestTime);
				$("#reqTimeid1").val(c.reqTime);
				
				$("#phoneId").val(c.phoneNumber);
				$("#webSiteId").val(c.webSite);
				$("#ticketId").val(c.ticket);
				$("#lastUpdateDetail").text("Last update details: On - " + c.updatedDate + "  By - "+c.updatedBy);
				
				
 
				if(c.categories != "" ) {
					
					$.each(c.categories, function (index, category) {
						 $('#cat_div').append(category.name +'&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" checked="checked"  name="cat_check_box" value= "'+category.categorySeq+'" />&nbsp;&nbsp;&nbsp;&nbsp;');
					});
					
				}
				
				
				
				if(c.images != "" ) {
					
					var rows = $();
			        $.each(c.images, function (index, img) {
			        	
			        	var name = img.split("___")[2];
			        	var imgSrc = img.split("___")[0];
			        	
			            var row = drawImages(imgSrc, name) ;
			            rows = rows.add(row);
			            
			        });
			        
			        $("#existing_images").append(rows);
			        
			        $("#totalimages").val(c.length);
			        $("#existing-images-div").show();
			        $("#existing_images").append(rows);
				}else{
					
					$("#existing-images-div").hide();
					$("#existing_images").text('');
				}
			//	alert(c.latitude + "-"+c.longitude);
				
			});
		 
		 	
		 	$('#fileupload').fileupload({
		 		dataType : 'json',
		 		acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		 		url : DOMAIN+'attraction/upload/ajax-post',
		 		singleFileUploads: false,
		 		disableValidation : true,
		 		maxFileSize: 1024 * 1024*3,
		 	    messages: {
		 	        maxFileSize: 'File exceeds maximum allowed size of 3MB',
		 	    },
		 		formData : function(form) {

		 			$("input[type='hidden']").remove();
		 			
		 			//$('<input />').attr({type : 'hidden',id : 'cityId'      ,name : 'cityId',      value : cityId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'attractionId',name : 'attractionId',value : attractionId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'cityId',name : 'cityId',value : cityId,}).appendTo('form');
		 			
		 			$('<input />').attr({type : 'hidden',id : 'googlePlaceid',name : 'googlePlaceid',value : $("#googlePlaceid1").val(),}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'googlePlaceName',name : 'googlePlaceName',value : $("#googlePlaceName1").val(),}).appendTo('form');
		 			
		 			$('<input />').attr({type : 'hidden',id : 'placeTime',name : 'placeTime',value : $("#placeTimeid1").val(),}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'placeAddress',name : 'placeAddress',value : $("#placeAddressid1").val(),}).appendTo('form');
		 			
		 			$('<input />').attr({type : 'hidden',id : 'bestTime',name : 'bestTime',value : $("#bestTimeid1").val(),}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'reqTime',name : 'reqTime',value : $("#reqTimeid1").val(),}).appendTo('form');
		 			

		 			$('<input />').attr({type : 'hidden',id : 'cityDescription_1',name : 'cityDescription',value : window.parent.tinymce.get("cityDescription").getContent()}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'attractionDescription_1',name : 'attractionDescription',value : window.parent.tinymce.get("attractionDescription").getContent()}).appendTo('form');
		 			
		 			$('<input />').attr({type : 'hidden',id : 'phone',name : 'phone',value : $("#phoneId").val(),}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'webSite',name : 'webSite',value : $("#webSiteId").val(),}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'ticket',name : 'ticket',value : $("#ticketId").val(),}).appendTo('form');
		 			
		 			
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
		    	 
		    	 if(alert('Attraction updated !!')){}
		    	  else    window.location.reload(); 
		    	 
		    	 var rows = $();
			        $.each(data._response.result.files, function (index, img) {
			        	
			        	var name = img.name;
			        	var imgSrc = img.thumbnailUrl;
			        	
			            var row = drawImages(imgSrc, name);
			            rows = rows.add(row);
			            
			        });
			        
			        $("#existing-images-div").show();
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
			 				if (attractionId == "") {
			 					$('.fileupload-process').hide();
			 					alert("Please select attraction");
			 					return null;
			 				}
			               }
			             return file;
			         });
		         }
		         
		         
		     	
		      });
		  
		 	function drawImages( imgSrc, name) {
		 		
		 		var row = $('<div class="template-download col-lg-3" id = "'+name+'-div"><span class="name"></span><br/><span class="delete"></span></div>');
		     	var link = "<a target ='blank' href='http://119.81.58.12"+imgSrc+"'>" ;
		     	link = link + "<img class='img-responsive' style= 'border-radius: 2px;border: 1px solid black;' height='140px' width ='140px' src ='http://119.81.58.12"+imgSrc+"' /> </a> <br><span>Name:"+ name +"</span>" ;
		        
		     	row.find('.name').append(link);
		     	row.find('.delete').append("<input type ='button' class='deleteImage' name='delteimage' value='Delete Image'  id = '"+name+"' />");
         
		        return row ;
		 	}
     });

function showHide() {
	
	$("#cat_div").text("");
	$("#attractionDescription").val("");
	$("#attractionPunchline").val("");
	$("#cityDescription").val("");

	$("#latitude").val("");
	$("#longitude").val("");
	$("#message").val("");
	$('#longitude').prop('readonly', false);
	$('#latitude').prop('readonly', false);

	$("#existing-images-div").hide();
	$("#existing_images").text('')
	
	 $("#googleplacename1").val("");
	
	 $("#googleplaceid1").val("");
	 
	 $("#placeAddressid1").val("");
	 $("#placeTimeid1").val("");
	 
	 $("#bestTimeid1").val("");
	 $("#reqTimeid1").val("");
	 
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
			   data.attractionId = attractionId;
			   data.latitude= $("#latitude").val();
			   data.longitude = $("#longitude").val();
			   data.googlePlaceName = $("#googleplacename1").val();
			   data.googlePlaceid = $("#googleplaceid1").val();
			   data.attractionDescription =  window.parent.tinymce.get("attractionDescription").getContent() ;
			   data.cityDescription = window.parent.tinymce.get("cityDescription").getContent();
			   data.attractionPunchline = $("#attractionPunchline").val();
			   data.placeAddress = $("#placeAddressid1").val();
			   data.placeTiming = $("#placeTimeid1").val();
			   data.bestTime = $("#bestTimeid1").val();
			   data.reqTime = $("#reqTimeid1").val();
			   
			   data.phone = $("#phoneId").val();
			   data.webSite = $("#webSiteId").val();
			   data.ticket = $("#ticketId").val();
							   
			   $('#responsive').modal('show');
			   $.post(  DOMAIN+'attraction/upload/ajax-post-data', data)
			   .done(function( data ) {
				   $('#responsive').modal('hide');
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
	if (attractionId == "") {
		$('.fileupload-process').hide();
		alert("Please select attraction");
		return null;
	}
	
	if (window.parent.tinymce.get("attractionDescription").getContent()  == "") {
		$('.fileupload-process').hide();
		alert("Please select attraction description");
		return null;
	}
	
	if (window.parent.tinymce.get("cityDescription").getContent() == "") {
		$('.fileupload-process').hide();
		alert("Please select city description");
		return null;
	}
 
	return true;
}	

 		

 