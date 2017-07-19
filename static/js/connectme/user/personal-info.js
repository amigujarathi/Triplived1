$(function() {
	/* $( "#dateofbirth" ).datepicker({
	changeMonth: true,
	changeYear: true,
	yearRange: "1947:1995",
	dateFormat : "yyyy-mm-dd",
	defaultDate: new Date(1990, 1 - 1, 1) 
	});*/
	
	$('#dateofbirth').datepicker({
	 autoclose: true,
	 startView: 2,
	 endDate: new Date(),
	 format: "dd M yyyy",
	 orientation: "bottom right"
});
	
 
$('#basicprofileinformation').validate({
	    rules: {
	    	fname:    {required: true},
	        lname: {required: true},
	        email: {required: true, email: true},
	        telephone: {required: true, number: true},
	        dateofbirth: {required: true}
	    },
	     messages: {
	    	 fname: "Please enter first name",
	    	 lname: "Please enter last name",
	    	 email: "Please enter email",
	    	 telephone:  { required: "Please enter a valid mobile number" ,  number: "Please enter a valid mobile number"},
	    	 dataeofbirth: "Please select date of birth",
		},
/*		invalidHandler : function(event, validator) {
			$.each(validator.errorList, function(index, value) {
				$("#label-error-" + value.element.name).val(value.message);
			});
		},*/
		 submitHandler: function(form) {
			  
				var data = {};
				$("#basicprofileinformation").serializeArray().map(function(x){data[x.name] = x.value;});
				$.ajax({
					type: "POST",
					contentType: "application/json; charset=utf-8",
					url: DOMAIN+"addOrUpdate/profile",
					data: JSON.stringify(data),
					beforeSend : function(event, request, settings ) {$("span.profilesave").addClass("progress-save");},
					complete   : function(event, request, settings ) {$("span.profilesave").removeClass("progress-save");},
					success: function(data){
						if(data.result != 'success') {
							alert("Problem occured. Please save again or save after some time if problem persist.");
						}
					},
					dataType: "JSON"
				});
		}
	});
 
	


});
function validatepersonalInfo() {
	return true;
}