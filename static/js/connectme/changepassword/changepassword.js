$(document).ready(function()
		{
 
	$('#changePasswordForm').validate({
						onkeyup : false,
						rules : {
							oldPassword : {
							required : true,
								minlength : 5,
								maxlength: 10
							},
							newPassword : {
								required : true,
								minlength : 5,
								maxlength: 20
							}
						},
						messages : {
							oldPassword : {
								required : "Please enter old Password"
							},
							newPassword : {
								required : "Please enter new Password",
								minlength : "At least {0} characters are expected",
								maxlength : "Can not enter more then {0} characters",
							}
						},
						invalidHandler : function(event, validator) {
							$.each(validator.errorList, function(index, value) {
								$("#label-error-" + value.element.name).val(value.message);
							});
						},
						submitHandler : function(form) {
						
							$(".chanePasswordsubmit").addClass("progress-ajax");
							
							var data = new Object();
							var op = $('#oldPassword').val();
							var np = $('#newPassword').val();
							data.oldPassword = op;
							data.newPassword = np;
	
								$.ajax({
									type: "POST",
									contentType : "application/json; charset=utf-8",
									data: JSON.stringify(data),
									url: DOMAIN+"forgotpassword/changePassword",
										success: function(response){
										 
											$("#changepassword-msgs").addClass("error-success-msgs");
					  						$("#changepassword-msgs").text(response.messages[0]);
					  						$(".chanePasswordsubmit").removeClass("progress-ajax");
					  						
					  						form.reset();
										
									},
									error: function(){						
										alert('Error while request..');
									}
								});
						 }
					});
			});		