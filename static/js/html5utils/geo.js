var geocoder;

//Get the latitude and the longitude;
function latlongSuccessHandler(position) {
  var lat = position.coords.latitude;
  var lng = position.coords.longitude;
  codeLatLng(lat, lng);
}

function errorHandler(){
 // $("#error").html("Geocoder failed");
}

function initialize() {
  
	//Check whether browser support geolocation
  if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(latlongSuccessHandler, errorHandler);
	  geocoder = new google.maps.Geocoder();
  } 
}

function codeLatLng(lat, lng) {
  var latlng = new google.maps.LatLng(lat, lng);
  // $("#latlng").html(lat+" : "+lng);
  //get area information from 
  
  geocoder.geocode({'latLng': latlng}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
   // console.log(results)
      if (results[1]) {
       //formatted address
       // $("#address").html(results[6].formatted_address) ;
       // $("#completeAddress").html(results[0].formatted_address) ;
      //find country name
      
	      var city = null;
	      var state= null ;
	      var country = null;
	      var pincode = null;
      
          for (var i=0; i<results[0].address_components.length; i++) {
          for (var b=0;b<results[0].address_components[i].types.length;b++) {

          //there are different types that might hold a city admin_area_lvl_1 usually 
          //does in come cases looking for sublocality type will be more appropriate
             if (results[0].address_components[i].types[b] == "administrative_area_level_2") {
                  //this is the object you are looking for
                  if(city == null){
                  	city= results[0].address_components[i];
                  }
              }
          
              if (results[0].address_components[i].types[b] == "administrative_area_level_1") {
                 //this is the object you are looking for
                 if(state == null){
                 	state = results[0].address_components[i];
                 }
             }
              
              if (results[0].address_components[i].types[b] == "country") {
                  //this is the object you are looking for
                  if(country == null){
                  	country = results[0].address_components[i];
                  }
              }
              if (results[0].address_components[i].types[b] == "postal_code") {
                  //this is the object you are looking for
                  if(pincode == null){
                  	pincode = results[0].address_components[i];
                  }
              }
          }
      }
      //city data
    //  $("#city").html(city.short_name + " " + city.long_name);
     // $("#state").html(state.short_name + " " + state.long_name);
     // $("#country").html(country.short_name + " " + country.long_name);
     // $("#pincode").html(pincode.short_name+ " "+ pincode.long_name);


      var data = {};
      data["city"] = city.long_name;
      data["state"] = state.long_name;
      data["country"] = country.long_name;
      data["pincode"] = pincode.long_name;
      data["latitude"] = lat+"" ;
      data["longitude"] = lng +"";
      data["address"] = results[0].formatted_address;
      
      store.set("geoCity", data);
      $.ajax({
			type:"POST",
			contentType: "application/json",
			url: DOMAIN+"geo/userlocation",
			data: JSON.stringify(data),
            beforeSend: function (xhr) {
               xhr.setRequestHeader("X-Ajax-call", "true");
            },
          
			success: function(data) { 
				//alert(data);
				if(data.result == 'success'){
					//window.location = DOMAIN+data.redirectUrl;
					console.log("saved "+ data);
					//lastSearchedCity
					
					store.set("geoCityInterestify","Found##"+data.city+"##"+data.geoCityid);
					
				} else if (data.result == 'warning'){
					store.set("geoCityInterestify","NotFound");
					//alert("We are comming soon to your city.");
				}
			},
			failure : function (data) {
				console.log("error: "+data);
				//alert(data);
			},
			dataType: "JSON"
	});
      
      } else {
       // $("#error").html("No results found");
      }
    } else {
     // $("#error").html("Geocoder failed due to: " + status);
    }
    
  });
}


var geoCity = store.get("geoCity");
var geoCityInterestify = store.get("geoCityInterestify");

if(geoCityInterestify == undefined && geoCityInterestify == null ){
	initialize();	
}