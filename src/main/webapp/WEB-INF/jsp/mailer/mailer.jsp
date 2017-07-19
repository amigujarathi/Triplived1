
<%@ page isELIgnored="false"%>

<style>
.form-group.required .control-label:after {
	color: red;
	content: "*";
	position: absolute;
}
</style>

<script src="http://triplived.com/static/m/js/jquery.c.min.js?v=2.2" type="text/javascript"></script>

<div class="container">

	<form id="mailer" method="POST">

		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Send Bulk Mailer</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-8">
				<div class="row" id="smsgs">
					<!--   FF/CC -->
				</div>
				<div class="row">
					<div class="form-group required col-lg-4">
						<label class="control-label" for="name">Name</label> <input
							placeholder="Name" type="text" id="name" name="name"
							required="required" class="typeahead form-control" id="name" />
					</div>
					<div class="form-group required col-lg-3">
						<label class="control-label" for="email">Designation</label> <input
							placeholder="Designation" type="text" id="designation" name="designation"
							required="required" class="typeahead form-control" id="designation" />
					</div>
					<div class="form-group required col-lg-3">
						<label class="control-label" for="email">Email</label> <input
							placeholder="Email" type="text" id="email" name="email"
							required="required" class="typeahead form-control" id="email" />
					</div>
					<div class="form-group required col-lg-2">
						<label class="control-label" for="email">Password</label> <input
							placeholder="Password" type="text" id="password" name="password"
							required="required" class="typeahead form-control" id="password" />
					</div>
				</div>
				<div class="row">
					<div class="form-group required col-lg-6">
						<label class="control-label" for="name">Subject</label> <input
							placeholder="Subject" type="text" id="subject" name="subject"
							required="required" class="typeahead form-control" id="subject" />
					</div>
					<div class="form-group required col-lg-6" id="latitudeDiv">
						<label class="control-label" for="latitude">Template Type</label>
						<select required="required" class="typeahead form-control " id="templateName" name="templateName">
							<option value="">Select</option>
							<option value="User ReConnect">User ReConnect</option>
							<option value="FeebBack">FeebBack</option>
							<option value="Trip Improvement">Trip Improvement</option>
							<option value="Only Source Destination">Only Source Destination</option>
							<option value="Good Trip">Good Trip</option>
							<option value="Travel Agent">Travel Agent</option>
						</select>
					</div>
				</div>
				<div class="row" id="users-row">
					<div class="form-group required col-lg-12">
						<label class="control-label" for="message">Users ( name,
							email)</label>
						<textarea placeholder="users in name, email format" name="cdesc"
							class="form-control textselect" rows="9" id="users"></textarea>
					</div>
				</div>
				
				<div class="row" id="userss">
					<input type="button" name="Send" value="Send" id = "send" />
				</div>
			</div>
		</div>
	</form>



</div>

<div id="responsive" class="modal" tabindex="-1" data-backdrop="static"
	style="display: none;" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<h4 class="modal-title">Please Wait!</h4>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
				//var DOMAIN = "${context}" ;
				jQuery(document).ready(function() {

					 
					
					
					jQuery("#send").on("click",function(e) {
						jQuery("#send").hide();
						submitForm()
					 });
					
				});

				
				function submitForm() {

						var data = new Object();

						if (validate() == true) {
							data.name = $("#name").val();
							data.designation = $("#designation").val();
							data.email = $("#email").val();
							data.password = $("#password").val();
							data.templateName = $("#templateName").val();
							data.subject = $("#subject").val();
							data.users = $("#users").val();
							 
							/* $.post(DOMAIN + 'mailer', data).done(
									function(data) {
										// $('#responsive').modal('hide');
										alert("Sent Successfully! ");
									}).fail(function() {
								alert("Failed!");
							}); */
							
							$.ajax({
								type : "POST",
								data : JSON.stringify(data),
								dataType: 'JSON',
								contentType : "application/json; charset=utf-8",
								url : DOMAIN + 'mailer',
								success : function(data) {
									alert("Sent Successfully! ");
									jQuery("#send").show();
								},
								error: function(data) {
									alert("Failed!");
									jQuery("#send").show();
								},
								dataType : "JSON"
							});
							
						}
/* 
					} */
				}
					
					function validate() {

						 

						if ($("#name").val() == "") {
							alert("Please enter  name");
							return null;
						}
						if ($("#email").val() == "") {
							alert("Please enter email");
							return null;
						}
						if ($("#subject").val() == "") {
							alert("Please enter subject");
							return null;
						}
						if ($("#users").val() == "") {
							alert("Please enter users");
							return null;
						}
						if ($("#password").val() == "") {
							alert("Please enter password");
							return null;
						}
						if ($("#templateName").val() == "") {
							alert("Please select template Name");
							return null;
						}
						return true;
					}
				
				/* function loadSlSearch() {
				 window.location += "slSearch";
				}  */
			</script>
