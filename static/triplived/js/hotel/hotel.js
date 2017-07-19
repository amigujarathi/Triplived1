 var currentCity = "" ;
		 var cityId = tripCityId ;
		 var hotelId = "";

		 var dataaa = []; 
		 var hotel_name = "";
		 
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
		 
		 function loadAttractionScreen() {
				document.location.href = DOMAIN+"attraction/progress?tripId="+tripId+"&cityId="+cityId+"&subTripId="+subTripId;
		}
		function loadHotelScreen() {
			document.location.href = DOMAIN+"hotel/progress?tripId="+tripId+"&cityId="+cityId+"&subTripId="+subTripId;
		}
		function loadNewCity() {
			document.location.href = DOMAIN+"city/progress?tripId="+tripId+"&status=new";
		}
		
		function enableAddImageButton() {
			$("#uploadPhotoId").attr('disabled',false);	
		}
		
		function enableAddImageButton() {
			$("#uploadPhotoId").attr('disabled',false);	
		}
						 
		 
$(document).ready(function() {
	
	$("#addAttractionId").hide();
	$("#addHotelId").hide();
	$("#addDestinationId").hide();
	
	//create structure of the ongoing trip
	var html = '<div class="container"><div class="row" style="background-color:yellow"><div class="col-lg-12">';
	
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
						 html += '<input type="hidden" value='+obj.subTripId+'/>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<label class="control-label">'+obj.cityName+'</label>';
						 html+='</div>';
						 html += '<div class="form-group required col-lg-4" >';
						 html += '<label class="control-label">'+obj.cityType+'</label>';
						 html+='</div>';
						 html += '<div class="form-group required col-lg-4" >';
						 //html += '<a class="control-label">'+'ADD ATTRACTIONS'+'</a>';
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
		
		
			   
			   $.ajax({
					type: "GET",
					contentType: "application/json; charset=utf-8",
					url: '../hotelSuggest'+"/"+cityId+"/*",
					success: function(data){
						dataaa = {};
						var selectList = $("#hotelName");
						$("#hotelName").empty();
						
						var option = document.createElement("option");
					    option.value = "Select";
					    option.text = "--Select--";
					    selectList[0].options.add(option);
					    
						for (var i = 0; i < data.length; i++) {
						    var option = document.createElement("option");
						    option.value = data[i].id;
						    option.text = data[i].name;
						    selectList[0].options.add(option);
						    
						    dataaa[data[i].id] = data[i];
						    
						}
						
					},
					dataType: "JSON"
				});
			   
			
		 
			
			
			$("#hotelName").on('change',function(){
				
				if(this.value == ""){
					return;
				}
				hotelId = this.value;
				hotel_name = $('#hotelName :selected').text();
				
				
				$("#message").val("");
				$("#existing-images-div").hide();
				
				$("#existing_images").text('')
			
			});
		 
		


		 	
		 	$('#fileupload').fileupload({
		 		dataType : 'json',
		 		acceptFileTypes: /(\.|\/)(jpg)$/i,
		 		url : DOMAIN+'hotel/ajax-post',
		 		singleFileUploads: false,
		 		formData : function(form) {

		 			$('<input />').attr({type : 'hidden',id : 'cityId' ,name : 'cityId',      value : cityId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'hotelId',name : 'hotelId',value : hotelId,}).appendTo('form');
		 			$('<input />').attr({type : 'hidden',id : 'hotel_name',name : 'hotel_name',value : hotel_name,}).appendTo('form');
		 			

		 			return form.serializeArray();
		 		}

		 	}).on('fileuploadprocessalways', function (e, data) {
		 		var index = data.index;
		 		var file = data.files[index];
		 	    if (data.files.error && file.error) {
		 	      // there was an error, do something about it
		 	     alert(file.error);
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
		             }else{
		             	 if(cityId =="") {
		             		 $('.fileupload-process').hide();
		         			 alert("Please select city");
		         			 return null;
		         	     }
		         	     if(hotelId == ""){
		         	    	 $('.fileupload-process').hide();
		         	    	 alert("Please select Hotel");
		         	    	 return null;
		         	     }
		         	    if($("#datetimepicker").val() == "" || $("#datetimepicker").val() == undefined){
		         	     	 $('.fileupload-process').hide();
		         	     	 alert("Please enter check in time before selecting photos");
		         	     	 return null;
		         	      }
		         	    
		               }
		             return file;
		         });
		        
		         $("#uploadPhotoId").attr('disabled',true);
		     	
		      }).bind('fileuploaddone',function (e, data){
		    	 	$('#cityname').prop('disabled', true);
		    		$('#hotelName').prop('disabled', true);
		    		$('#datetimepicker').prop('disabled', true);
		    		$('#hotelRating').prop('disabled', true);
		    		$('#message').prop('disabled', true);
		    		$('#submitBtnId').prop('disabled', true);
		    		$("#addAttractionId").show();
		    		$("#addHotelId").show();
		    		$("#addDestinationId").show();
		    		
		      });
     });

function submitForm(){
	
	if(hotelId == ""){
    	 $('.fileupload-process').hide();
    	 alert("Please select hotel");
    	 return null;
     }
	
	if($("#datetimepicker").val() == "" || $("#datetimepicker").val() == undefined){
   	 $('.fileupload-process').hide();
   	 alert("Please enter check in time");
   	 return null;
    }
	
	

	  if( $(document).find('.files').find(".start") != undefined &&  $(document).find('.files').find(".start").length > 0){
	  
	  $(document).find('.files').find(".start").click();
	  
	  }else {
	  
	      var data = {};
	  data.tripId = tripId;    
	  data.cityId= cityId;
	  data.hotelId = hotelId;
	  data.subTripId= subTripId;
	  data.timeStamp = $("#datetimepicker").val();
	  data.hotelDescription =  $("#message").val();
	  data.cityname = $("#cityname").val();
	  data.hotel_name = hotel_name;
	  data.hotelRating = $("#hotelRating").val();
	  $.post(  DOMAIN+'hotel/ajax-post-wi', data)
	  .done(function( data ) {
		  $('#cityname').prop('disabled', true);
  		$('#hotelName').prop('disabled', true);
  		$('#datetimepicker').prop('disabled', true);
  		$('#hotelRating').prop('disabled', true);
  		$('#message').prop('disabled', true);
  		$("#addAttractionId").show();
		$("#addHotelId").show();
		$("#addDestinationId").show();
		$('#submitBtnId').prop('disabled', true);
		$("#uploadPhotoId").attr('disabled',true);
	  });
	  }
	}

 
 