function getCities() {
    	
    	var data = store.get("user#cities");
    	var dfr = $.ajax({
             url: DOMAIN+'region/getAllCities',
             dataType:'json'
         });
    	 
    	 dfr = dfr.pipe(function(results) {
    		 //console.log(results);
    		 
    		 var cities =[];
    		 $.each( results, function (index, value) {
    			 var myObject = new Object();
    			 myObject.city = value.city; 
    			 myObject.value = value.city; 
    			 myObject.id = value.id;
    			 myObject.tokens = jQuery.makeArray(value.city);
    			 cities[index]= myObject;
    		 });
    		 
    		 //store cities in localstorage
    		 store.set('cities', cities);
    		 
    		 $('#city').typeahead('destroy');
    		 var substringMatcher = function(strs) {
    			  return function findMatches(q, cb) {
    			    var matches, substrRegex;
    			    matches = [];
    			    substrRegex = new RegExp(q, 'i');
    			    $.each(strs, function(i, str) {
    			    	if (substrRegex.test(str.value)) {
    			    	  matches.push({ value: str.value, id: str.id });
    			      	}
    			    });
    			    cb(matches);
    			  };
    			};
    			 
    			 
    			 
    			$('#city').typeahead({
    			  hint: true,
    			  highlight: true,
    			  minLength: 2
    			},
    			{
    			  name: 'states',
    			  displayKey: 'value',
    			  source: substringMatcher(store.get('cities'))
    			});
    		 
    		 $('input[name=city]').on('typeahead:selected', function(evt, item) {
	    		 $('#street').val("");

    			 $('#cityId').val(item.id);
    			 var areas = [];
    			 var dfr = $.ajax({
    				 	beforeSend : function(event, request, settings )
						{
							$("input#city").addClass("progress-save-input");
							$("input#street").attr("disabled", "disabled");
						},
						complete   : function(event, request, settings ) {
							$("input#city").removeClass("progress-save-input");
							$("input#street").removeAttr("disabled");
						},
						url: DOMAIN+'region/getAllRegionsForCity?cityId='+$('#cityId').val(),
						dataType:'json'
    	         });
    	    	 
    			 $('#street').typeahead('destroy');
    	    	 dfr = dfr.pipe(function(results) {
    	    		//console.log(results);
    	    		 $.each( results, function (index, value) {
    	    			 var myObject = new Object();
    	    			 myObject.area = value.street; 
    	    			 myObject.value = value.street; 
    	    			 myObject.id = value.id;
    	    			 myObject.tokens = jQuery.makeArray(value.street);
    	    			 areas[index]= myObject;
    	    		 });
    	    		 
    	    		 
    	    		 $('#street').typeahead({
    	    			  hint: true,
    	    			  highlight: true,
    	    			  minLength: 1
    	    			},
    	    			{
    	    			  name: 'states',
    	    			  displayKey: 'value',
    	    			  source: substringMatcher(areas)
    	    			});
        	    	 
    	    		 
        	    	 //when street is selected where should we put the street id
        	    	 $('input[name=street]').on('typeahead:selected', function(evt, item) {
        	    		 $('#streetId').val(item.id);
        	    	 });
    			});
    	 });
    });
 }

$(document).ready(function()
{
	$('#addressinformation').validate({
	    rules: {
	    	country:    {required: true},
	        city: {required: true},
	        street: {required: true}
	    },
	     messages: {
	    	 country: "Please select country",
	    	 city: "Please select  city",
	    	 street: "Please select  street"
		},
		invalidHandler : function(event, validator) {
			$.each(validator.errorList, function(index, value) {
				$("#label-error-" + value.element.name).val(value.message);
			});
		},
		 submitHandler: function(form) {
			 
		  		var data = {};
				$("#addressinformation").serializeArray().map(function(x){data[x.name] = x.value;});
				
				$.ajax({
					type: "POST",
					contentType: "application/json; charset=utf-8",
					url: DOMAIN+"addOrUpdate/updateLocationOfPerson",
					beforeSend : function(event, request, settings ) {$("span.profilesaveaddress").addClass("progress-save");},
					complete   : function(event, request, settings ) { 
								$("span.profilesaveaddress").removeClass("progress-save");
								$("span.saveaddressNotification").addClass("saving-details").fadeOut("slow",function(){
									$("span.saveaddressNotification").removeClass("saving-details");
									$("span.saveaddressNotification").attr('style', '');
								});
								//$("span.saveaddressNotification").removeClass("saving-details");
								},
					data: JSON.stringify(data),
					success: function(e) {/*alert(e) */ },
					dataType: "JSON"
				});
		}
	});
});


//As data is being loaded using ajax, hence showing loader in the filed, 
$("input#city").addClass("progress-save-input");
$("input#street").addClass("progress-save-input");

$.ajax({
	type: "GET",
	contentType: "application/json",
	url: DOMAIN+"addOrUpdate/getAddressInfo",
	success: function(e) {
		
		//remove loader classes
		$("input#city").removeClass("progress-save-input");
		$("input#street").removeClass("progress-save-input");
		
		$("input.typeahead"). css("background","");
		
		if( e != null && e !=undefined) {
			$("#cityId").val(e.cityid);
			$("#streetId").val(e.streetId);
			$("#city").val(e.city);
			$("#street").val(e.street);
		}
		
	},
	failure : function (data) {
		$("input#city").removeClass("progress-save-input");
		$("input#street").removeClass("progress-save-input");
	},
	dataType: "JSON"
});

getCities();