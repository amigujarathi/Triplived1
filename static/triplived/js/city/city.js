 var currentCity = "" ;
		 var cityId = "" ;
		 var attractionId = "";

		 var dataaa = [];
		 var transportType = "";
		 
		 getMsgTime = function(msgTimeStamp){
			 if(msgTimeStamp !== undefined && msgTimeStamp.toString().length == 10) {
			     var monthNames = [ "January", "February", "March", "April", "May", "June",
			    "July", "August", "September", "October", "November", "December" ];
				
			     var myVar = (msgTimeStamp*1000).toString();
			     var value = new Date(parseInt(myVar.replace(/(^.*\()|([+-].*$)/g, '')));
				 
				 var monthName = monthNames[value.getMonth()].substring(0,3);
				 
				 var msgDate = value.getDate() + " " + monthName + "," + value.getFullYear() + " | " + value.getHours() + ":" + value.getMinutes(); 
		 
				 return msgDate;
			} 
		}		 
		 	 		 
if(cityType == "Destination") {
	$("#transportType1").show();
	$("#showForDestinationCityId").show();
	$("#showForSourceCityId").hide(); 
}
if(cityType == "Source") {
	//$("#transportType").val("good");
	$("#transportType1").hide();
	$("#showForDestinationCityId").hide();
	$("#showForSourceCityId").show();
}

function loadAttractionScreen() {
	document.location.href = DOMAIN+"attraction/progress?tripId="+tripId+"&cityId="+cityId+"&subTripId="+subTripId;
}
function loadHotelScreen() {
	document.location.href = DOMAIN+"hotel/progress?tripId="+tripId+"&cityId="+cityId+"&subTripId="+subTripId;
}
function loadActivityScreen() {
	document.location.href = DOMAIN+"activity/progress?tripId="+tripId+"&cityId="+cityId+"&subTripId="+subTripId;
}
function loadCityScreen() {
	document.location.href = DOMAIN+"city/progress?tripId="+tripId;
}

function enableAddImageButton() {
	$("#uploadPhotoId").attr('disabled',false);	
}
		 
$(document).ready(function() {
	
	$("#addAttractionId").hide();
	$("#addHotelId").hide();
	$("#addActivityId").hide();
	$("#addDestinationCityId").hide();
	
	$("#cityType1").val(cityType);
	
	
	$('#cityType1').prop('disabled', true);
	
		
	//create structure of the ongoing trip
	var html = '<div class="container"><div class="row" style="background-color:yellow"><div class="col-lg-12">';
	
			if(currentTripDetails.subTrips != undefined) {
			jQuery.each(currentTripDetails.subTrips, function() {
				
				 $.each(this, function(name, value) {
					 
					 if(name == 'fromCityDTO') {
						 html += '<div class="row" style="border-bottom:2px;border-style:solid">';
						 var obj = value;
						 html += '<input type="hidden" value='+obj.subTripId+'/>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<label class="control-label">'+obj.cityName+'</label>';
						 html+='</div>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<label class="control-label">'+obj.cityType+'</label>';
						 html+='</div>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<a class="control-label">'+''+'</a>';
						 html+='</div>';
						 html+='</div>';
					}
					if(name == 'toCityDTO') {
						 html += '<div class="row" style="border-bottom:2px;border-style:solid">';
						 var obj = value;
						 html += '<input type="hidden" class="subTripClass" value='+obj.subTripId+'/>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<label class="control-label" class="cityClass">'+obj.cityName+'</label>';
						 html+='</div>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<label class="control-label">'+obj.cityType+'</label>';
						 html+='</div>';
						 html += '<div class="form-group required col-lg-4" >';
						 html+='</div>';
						 html+='</div>';
						 
						 if(obj.events != undefined) {
							 jQuery.each(obj.events, function() {
								 $.each(this, function(name1, value1) {
									 if(name1=='id') {
										 html += '<div class="row" style="border-bottom:2px;border-style:solid">';
										 html += '<input type="hidden" value='+value1+'/>';
									 }
									 if(name1=='cityId'){
									 html += '<input type="hidden" value='+value1+'/>';
									 }
									 
									 if(name1=='cityName'){
										 html += '<div class="form-group required col-lg-3" >';
										 html += '<label class="control-label">'+value1+'</label>';
										 html+='</div>';
									 }
									 if(name1=='eventName'){
										 html += '<div class="form-group required col-lg-3" >';
										 html += '<label class="control-label">'+value1+'</label>';
										 html+='</div>';
									 }
									 if(name1=='timestamp'){
										 html += '<div class="form-group required col-lg-3" >';
										 html += '<label class="control-label">'+getMsgTime(value1)+'</label>';
										 html+='</div>';
									 }
									 if(name1=='type'){
									 /*html += '<div class="form-group required col-lg-3" >';
									 html += '<label class="control-label">'+value1+'</label>';
									 html+='</div>';*/
									 html += '<div class="form-group required col-lg-3" >';
									 html += '<label class="control-label">'+value1+'</label>';
									 html+='</div>';
									 html+='</div>';
									 }
								 });
								 
							 });
						   }
					} 
					
				 });
				 
			});
		}
		html += '</div></div></div>';
		$( ".inner" ).append(html);
	
	
	jQuery('#datetimepicker').datetimepicker();
	
	$("#existing-images-div").hide();
	
		 var citiesDataSource = new Bloodhound({
			  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
			  queryTokenizer: Bloodhound.tokenizers.whitespace,
			  remote: '../citySuggest/%QUERY'
			});
			 
		 citiesDataSource.initialize();
		
		 $('#cityName').typeahead({
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
			   currentCity = item.cityname;
			   
			   $.ajax({
					type: "GET",
					contentType: "application/json; charset=utf-8",
					url: '../hotelSuggest'+"/"+cityId+"/*",
					success: function(data){
						dataaa = {};
						var selectList = $("#attractionName");
						$("#attractionName").empty();
						for (var i = 0; i < data.length; i++) {
						    var option = document.createElement("option");
						    option.value = data[i].id;
						    option.text = data[i].attractionName;
						    selectList[0].options.add(option);
						    
						    dataaa[data[i].id] = data[i];
						    
						}
						
					},
					dataType: "JSON"
				});
			   
			})
		 
			
		 	$('#fileupload').fileupload({
		 		dataType : 'json',
		 		acceptFileTypes: /(\.|\/)(jpg)$/i,
		 		url : DOMAIN+'city/ajax-post',
		 		singleFileUploads: false,
		 		formData : function(form) {

		 			$('<input />').attr({type : 'hidden',id : 'cityId'      ,name : 'cityId',      value : cityId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'attractionId',name : 'attractionId',value : attractionId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'cityType',name : 'cityType',value : cityType,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'transportType',name : 'transportType',value : $("#transportType1").val(),}).appendTo('form');

		 			return form.serializeArray();
		 		}

		 	}).on('fileuploadprocessalways', function (e, data) {
		 		var currentFile = data.files[data.index];
		 	    if (data.files.error && currentFile.error) {
		 	      // there was an error, do something about it
		 	     alert(currentFile.error);
		 	    }

		         
		     }).bind('fileuploadadd', function (e, data) {

		      
		     	var currentfiles = [];
		         $(this).fileupload('option').filesContainer.children().each(function(){
		             currentfiles.push($.trim($('.name', this).text()));
		         });

		         data.files = $.map(data.files, function(file,i){
		             if ($.inArray(file.name,currentfiles) >= 0) { 
		                 alert("File "+file.name+" is already present.");
		                 $('.fileupload-process').hide();
		                 return null; 
		             }else {
		            	 if($("#cityName").val() == ""){
		            	  	 $('.fileupload-process').hide();
		            	  	 alert("Please enter city name");
		            	  	 return null;
		            	  }
		            	 
		            	 if($("#datetimepicker").val() == "" || $("#datetimepicker").val() == undefined){
		            	  	 $('.fileupload-process').hide();
		            	  	 alert("Please enter time");
		            	  	 return null;
		            	   }
		             }
		             return file;
		         });
		         
		         $("#uploadPhotoId").attr('disabled',true);
		     	
		      }).bind('fileuploaddone', function (e, data) {
		    	  if(cityType == 'Source') {
						
						$("#addDestinationCityId").show();
						$('#cityName').prop('disabled', true);
						$('#message').prop('disabled', true);
						$('#submitBtnId').prop('disabled', true);
						
					}
					if(cityType == 'Destination') {
						$("#addAttractionId").show();
						$("#addHotelId").show();
						//$("#addActivityId").show();
						$('#cityName').prop('disabled', true);
						$('#message').prop('disabled', true);
						$('#submitBtnId').prop('disabled', true);
					}
			     });
		  
		 

		
		 
     });
 
function submitForm(){
	
	if($("#cityName").val() == ""){
	  	 $('.fileupload-process').hide();
	  	 alert("Please enter city name");
	  	 return null;
	 }
	
	if($("#datetimepicker").val() == "" || $("#datetimepicker").val() == undefined){
	  	 $('.fileupload-process').hide();
	  	 alert("Please enter time");
	  	 return null;
	   }

	  if( $(document).find('.files').find(".start") != undefined &&  $(document).find('.files').find(".start").length > 0){
	  
	  $(document).find('.files').find(".start").click();
	  
	  }else {
	  
	      var data = {};
	  data.tripId = tripId;    
	  data.cityDescription =  $("#message").val();
	  data.cityId = cityId;
	  data.cityType = cityType;
	  data.cityName = $("#cityName").val();
	  data.transportType = $("#transportType1").val();
	  data.timeStamp = $("#datetimepicker").val();
	  $.post(  DOMAIN+'city/ajax-post-wi', data)
	  .done(function( data ) {
    	  if(cityType == 'Source') {
				
				$("#addDestinationCityId").show();
			}
			if(cityType == 'Destination') {
				$("#addAttractionId").show();
				$("#addHotelId").show();
				//$("#addActivityId").show();
			}
			$('#cityName').prop('disabled', true);
			$('#message').prop('disabled', true);
			$('#submitBtnId').prop('disabled', true);
			$("#uploadPhotoId").attr('disabled',true);
	  });
	  }
} 