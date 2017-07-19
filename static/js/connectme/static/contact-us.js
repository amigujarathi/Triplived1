 $(function() {
	 $('#contactusform').validate({
	     rules: {
	         name:  {required: true},
	         email: {required: true, email: true},
	         mobile: {required: true, number: true},
	         message: {required: true, minlength : 50}
	     },
	     highlight: function (element) {
	         $(element).closest('.form-group').removeClass('success').addClass('error');
	     },
	     messages: {
	     	name: "Please enter your name",
	     	email: "Please enter your email",
	     	mobile:  { required: "Please enter a valid mobile number" ,  number: "Please enter a valid mobile number"},
	     	message:  { required: "Please enter your message",
	     				minlength : "At least {0} characters are expected"
	     	          }
	 	},
	 	 submitHandler: function(form) {
	 		 
	 		 $(".contactussubmit").addClass("progress-ajax");
			  		var data = {};
			  		$("#contactusform").serializeArray().map(function(x){data[x.name] = x.value;});
			  		
			  		
			  		$.ajax({
			  				type:"POST",
			  				contentType: "application/json",
			  				url: DOMAIN+"pages/contactus",
			  				data: JSON.stringify(data),
			  				success: function(data) { 
			  					//alert(data);
			  					 $('body').removeClass('loading-indicator');
			  					$(".contactussubmit").removeClass("progress-ajax");
			  					$("div#smsgs").removeClass("error-success-msgs");
			  					$("div#smsgs").removeClass("text-success");
			  					if(data.result == 'success'){
			  						$("div#smsgs").addClass("text-success");
			  						$("div#smsgs").text(data.messages[0]);
			  						form.reset();
			  					} else if(data.result == 'failure'){
			  						$("div#smsgs").addClass("error-success-msgs");
			  						$("div#smsgs").text(data.messages[0]);
			  					}
			  				},
			  				failure : function (data) {
			  					alert(data);
			  				},
			  				dataType: "JSON"
			  		});
	 	}
	 });
 });