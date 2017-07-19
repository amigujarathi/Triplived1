
<%@ page isELIgnored="false"%>

<style>
.form-group.required .control-label:after {
	color: red;
	content: "*";
	position: absolute;
}
</style>

<script src="http://triplived.com/static/m/js/jquery.c.min.js?v=2.2" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="http://fezvrasta.github.io/bootstrap-material-design/dist/css/bootstrap-material-design.css"/>
<link rel="stylesheet" type="text/css" href="http://fezvrasta.github.io/bootstrap-material-design/dist/css/ripples.min.css"/>


<div class="container">

	<form id="mailer" method="POST">

		<div class="row">
			<div class="col-lg-8">
				<form class="form-horizontal">
					<fieldset>
						<legend>Travel Partner</legend>
						<div class="form-group">
							<label for="inputEmail" class="col-md-2 control-label">Email</label>

							<div class="col-md-10">
								<input type="email" class="form-control" id="inputEmail"
									placeholder="Email">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword" class="col-md-2 control-label">Password</label>

							<div class="col-md-10">
								<input type="password" class="form-control" id="inputPassword"
									placeholder="Password">

								<!--
        <div class="checkbox">
          <label>
            <input type="checkbox"> Checkbox
          </label>
          <label>
            <input type="checkbox" disabled> Disabled Checkbox
          </label>
        </div>
        <br>

        <div class="togglebutton">
          <label>
            <input type="checkbox" checked> Toggle button
          </label>
        </div>
        -->
							</div>
						</div>
						<div class="form-group" style="margin-top: 0;">
							<!-- inline style is just to demo custom css to put checkbox below input above -->
							<div class="col-md-offset-2 col-md-10">
								<div class="checkbox">
									<label> <input type="checkbox"> Checkbox
									</label> <label> <input type="checkbox" disabled="">
										Disabled Checkbox
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-10">
								<div class="togglebutton">
									<label> <input type="checkbox" checked="">
										Toggle button
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputFile" class="col-md-2 control-label">File</label>

							<div class="col-md-10">
								<input type="text" readonly="" class="form-control"
									placeholder="Browse..."> <input type="file"
									id="inputFile" multiple="">
							</div>
						</div>
						<div class="form-group">
							<label for="textArea" class="col-md-2 control-label">Textarea</label>

							<div class="col-md-10">
								<textarea class="form-control" rows="3" id="textArea"></textarea>
								<span class="help-block">A longer block of help text that
									breaks onto a new line and may extend beyond one line.</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">Radios</label>

							<div class="col-md-10">
								<div class="radio radio-primary">
									<label> <input type="radio" name="optionsRadios"
										id="optionsRadios1" value="option1" checked=""> Option
										one is this
									</label>
								</div>
								<div class="radio radio-primary">
									<label> <input type="radio" name="optionsRadios"
										id="optionsRadios2" value="option2"> Option two can be
										something else
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="select111" class="col-md-2 control-label">Select</label>

							<div class="col-md-10">
								<select id="select111" class="form-control">
									<option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
									<option>5</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="select222" class="col-md-2 control-label">Select
								Multiple</label>

							<div class="col-md-10">
								<select id="select222" multiple="" class="form-control">
									<option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
									<option>5</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-10 col-md-offset-2">
								<button type="button" class="btn btn-default">Cancel</button>
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</div>
					</fieldset>
				</form>
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

		jQuery("#send").on("click", function(e) {
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
				dataType : 'JSON',
				contentType : "application/json; charset=utf-8",
				url : DOMAIN + 'mailer',
				success : function(data) {
					alert("Sent Successfully! ");
					jQuery("#send").show();
				},
				error : function(data) {
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
