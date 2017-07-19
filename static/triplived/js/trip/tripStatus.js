function loadAttractionScreen() {
	document.location.href = DOMAIN + attractionUrl;
}
function loadHotelScreen() {
	document.location.href = DOMAIN + hotelUrl;
}
function loadActivityScreen() {
	document.location.href = "";
}
function loadCityScreen() {
	document.location.href = DOMAIN + newDestinationUrl;
}

function enableAddImageButton() {
	$("#uploadPhotoId").attr('disabled',false);	
}

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
		 
$(document).ready(function() {
	
		
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
	
     });
 
