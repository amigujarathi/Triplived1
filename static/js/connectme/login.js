$(document).ready(function()
{
	
	/*$('#login-dropdown, #sign-up-dropdown, #forgot-password-dropdown').on('hide.bs.dropdown', function () {
	    return false;
	});*/
	
		$('#login-dropdown').on('show.bs.dropdown', function () {
			$("div.form-group").find("label.error").text("");
			$("#login-msgs").removeClass("error-success-msgs");
			$("#login-msgs").text("");
	});

	$('#forgot-password-dropdown').on('show.bs.dropdown', function () {
		$("div.form-group").find("label.error").text("");
		$("#forgot-msgs").removeClass("error-success-msgs");
		$("#forgot-msgs").text("");
	});

	$('#sign-up-dropdown').on('show.bs.dropdown', function () {
		$("div.form-group").find("label.error").text("");
			$("#signup-msgs").removeClass("error-success-msgs");
			$("#signup-msgs").text("");
	});
 
	// LOGIN VALIDATIONS
    $('#login-form').validate({
        rules: {
            email:    {required: true},
            password: {required: true}
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('success').addClass('error');
        },
        messages: {
        	email: "Please enter your email",
        	password: "Please enter your password"
    	},
    	 submitHandler: function(form) {
    		 
    		 $(".loginsubmit").addClass("progress-ajax");
    		// $("#login-form").submit();
		  	 	var data = {};
		  	 	$("#login-form").serializeArray().map(function(x){data[x.name] = x.value;});
		  		
		  		
		  		$.ajax({
		  				type:"POST",
		  				contentType: "application/json",
		  				url: DOMAIN+"home/login",
		  				data: JSON.stringify(data),
		  	            beforeSend: function (xhr) {
		  	               xhr.setRequestHeader("X-Ajax-call", "true");
		  	            },
		  	          
		  				success: function(data) { 
		  					//alert(data);
		  					 $('body').addClass('loading-indicator');
		  					if(data.result == 'success'){
		  						window.location = DOMAIN+data.redirectUrl;
		  					} else if(data.result == 'failure'){
		  						$("#login-msgs").addClass("error-success-msgs");
		  						$("#login-msgs").text(data.messages[0]);
		  						$(".loginsubmit").removeClass("progress-ajax");
		  					}
		  				},
		  				failure : function (data) {
		  					alert(data);
		  				},
		  				dataType: "JSON"
		  		});
    	}
    });

	
	// LOGIN VALIDATIONS
    $('#forgot-password-form').validate({
        rules: {
            email:    {required: true},
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('success').addClass('error');
        },
        messages: {
        	email: "Please enter your email"
    	},
    	 submitHandler: function(form) {
    		 
    		 $(".forgotsubmit").addClass("progress-ajax");
		  		var data = {};
		  		$("#forgot-password-form").serializeArray().map(function(x){data[x.name] = x.value;});
		  		
		  		$.ajax({
		  				type:"POST",
		  				contentType: "application/json",
		  				url: DOMAIN+"forgotpassword",
		  				data: JSON.stringify(data),
		  				success: function(data) { 
		  					//alert(data);
		  					 $('body').addClass('loading-indicator');
 					 
		  					 if(data.result=="success") {
		  					     $("#forgot-msgs").addClass("success-msgs");
		  					 } else{
		  						$("#forgot-msgs").addClass("error-success-msgs");
		  					 }
		  					 $("#forgot-msgs").text(data.messages[0]);
		  					 $(".forgotsubmit").removeClass("progress-ajax");
		  					 
		  				},
		  				failure : function (data) {
		  					alert(data);
		  				},
		  				dataType: "JSON"
		  		});
    	}
    });

//FIREFOX BUG or bootstrap bug for firefox
$('#email, #userName, #fname, #lname, #dateofbirth').on('keydown',function(e){
    e.stopPropagation();
});


 //stoping menu to close on datepicker events
 $(document).on('click', 'span.month, th.next, th.prev, th.switch, span.year, td.day, th.datepicker-switch ', function (e) {
        e.stopPropagation();
    });

 $('#dateofbirth').datepicker({
	 autoclose: true,
	 orientation: "top auto",
	 startView: 2,
	 endDate: new Date(),
	 format: "dd M yyyy"
		});
	  
 
		 $('#signupuser-form').validate({
	        rules: {
	            fname:    		{required: true},
	            lname:    		{required: true},
	            userName:   	{required: true, email: true },
	            dateofbirth:    {required: true},
	            gender:    		{required: true},
	            password: 		{required: true, minlength: 5}
	        },
	        invalidHandler: function(event, validator) {
	        	
	        	 var errors = validator.numberOfInvalids();
	        	 
	        	    if (errors) {
	        	    	
	        	    	$("#signup-msgs").addClass("error-success-msgs");
	        	    	if(errors > 1) {
				            $("#signup-msgs").html("You must fill in all of the fields.");
	        	    	}else if(validator.invalid.password != null || validator.invalid.password != undefined ) {
				            $("#signup-msgs").html(validator.invalid.password);
		        	    } else if(validator.invalid.userName != null || validator.invalid.userName != undefined ) { 
				            $("#signup-msgs").html(validator.invalid.userName);
		        	    } else {
				            $("#signup-msgs").html("You must fill in all of the fields.");
		        	    }
	        	    } else {
			        	$("#signup-msgs").removeClass("error-success-msgs");
			            $("#signup-msgs").html("");
	        	    }
	        	    

	        },
	        highlight: function (element, errorClass) {
	            $(element).closest('.form-group').removeClass('success').addClass('error');
	        },
	          messages: {
	        	password: { required: "Please enter password" ,minlength :"Password should contain at least {0} characters"},
	        	userName:    {remote: jQuery.format("{0} is already registered.") },
	        	userName:    {required: jQuery.format("Please enter a valid email.") }
	    	},  
	    	
	    	showErrors: function(errorMap, errorList) {
	    		//$("#signup-msgs").addClass("error-success-msgs");
	           // $("#signup-msgs").html("You must fill in all of the fields.");
	            //$( "#signupuser-form input:blank" ).css( "border-color", "red" );
	        },
	    	submitHandler: function(form) {
	    		
		  		var isValidEmail = false;
		  		$.ajax({
		  			url: "/validate/email?userName="+$("#userName").val(),
					async: false,
        			success: function(data) {
        				isValidEmail = data;
        				if(data == 'false') {
        					$("#signup-msgs").addClass("error-success-msgs");
			            	$("#signup-msgs").html($("#userName").val()+ " is already registered. ");
        				} else {
        					$("#signup-msgs").removeClass("error-success-msgs");
				            $("#signup-msgs").html("");
				            
				            $(".signupsubmit").addClass("progress-ajax");
				    		var data = {};
					  		$("#signupuser-form").serializeArray().map(function(x){data[x.name] = x.value;});
					  		
					  		$.ajax({
					  				type:"POST",
					  				contentType: "application/json",
					  				url: DOMAIN+"home/signup-user",
					  				data: JSON.stringify(data),
					  				success: function(data) { 
					  					//alert(data);
					  					 $('body').addClass('loading-indicator');
					  					if(data.result == 'success'){
					  						window.location = DOMAIN+data.redirectUrl;
					  					} else if(data.result == 'failure'){
					  						
					  						$("#signup-msgs").addClass("error-success-msgs");
					  						$("#signup-msgs").text(data.messages);
					  					}
					  					
					  					$(".signupsubmit").removeClass("progress-ajax");
					  				},
					  				failure : function (data) {
					  					alert(data);
					  				},
					  				dataType: "JSON"
					  		});
					  		
        				}
    				}
        		});
	    	}
	 });
});