
<%@ page isELIgnored="false"%>

<style>
.form-group.required .control-label:after {
	color: red;
	content: "*";
	position: absolute;
}
</style>


<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
			<p class="name">{%=file.name%}</p>
        </td>
        <td>
 			<input placeholder="File Original Source" type="text" id="fileFrom"  name="fileFrom" required="required" class="typeahead form-control" />
			<input placeholder="File Author " type="text" id="fileAuthor" name="fileAuthor" required="required" class="typeahead form-control" style ="margin-top:3px"/>
			<input placeholder="File Title " type="text" id="fileTitle" name="fileTitle" required="required" class="typeahead form-control" style ="margin-top:3px"/>	
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Done</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
	{% if (file.error) { %}
    	<tr class="template-download fade">
        	 <td>
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
	        </td>
	    </tr>
	{% } %}
{% } %}
</script>


<div class="container">

	<form id="fileupload" method="POST" enctype="multipart/form-data">

		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Add New Attraction</h1>
			</div>
		</div>


		<div class="row">

			<div class="col-lg-12">
				<div class="row" id="smsgs">
					<!--   FF/CC -->
				</div>
				<div class="row">
					<div class="form-group required col-lg-4" style="margin-top: 24px">
						<label class="control-label" for="name"
							style="margin-top: -21px; position: absolute">City</label> <input
							placeholder="City Name" type="text" id="cityname" name="cityname"
							required="required" class="typeahead form-control" id="cityname"
							style="width: 376px" />
					</div>
					<div class="form-group required col-lg-4">
						<label class="control-label" for="email">Existing
							Attraction Name</label> <select required="required"
							class="typeahead form-control " id="attractionName"
							name="attractionName">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-group required col-lg-4">
						<label class="control-label" for="email">Select Category
							Name</label> <select required="required" class="typeahead form-control "
							id="categoryName" name="categoryName">
							<option value="">Select</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="form-group required  col-lg-4" id="placenameDiv">
						<label class="control-label" for="newAttractionName">Add
							Attraction Name</label> <input placeholder="Add Attraction Name"
							type="text" name="newAttractionName" class="form-control "
							id="newAttractionName" />
					</div>
					<div class="form-group required col-lg-4" id="latitudeDiv">
						<label class="control-label" for="latitude">Attraction
							Latitude</label> <input placeholder="Latitude" type="number"
							name="latitude" class="form-control" id="latitude" />
					</div>
					<div class="form-group required col-lg-4" id="longitudeDiv">
						<label class="control-label" for="longitude">Attraction
							Longitude</label> <input placeholder="Longitude" type="number"
							name="longitude" class="form-control " id="longitude" />
					</div>
				</div>
				<div class="row">
					<div class="form-group  required col-lg-4" id="placeidDiv">
						<label class="control-label" for="googleplaceid1">Google
							Place Id</label> <input placeholder="Google Place Id" type="text"
							name="googleplaceid1" class="form-control " id="googleplaceid1" />
					</div>
					<div class="form-group required col-lg-4" id="Statediv">
						<label class="control-label" for="State">Enter State</label> <input
							placeholder="Enter state" type="text" name="State"
							class="form-control" id="State" />
					</div>
					<div class="form-group required col-lg-4" id="Addressdiv">
						<label class="control-label" for="Address">Enter Address</label> <input
							placeholder="Enter Address" type="text" name="Address"
							class="form-control " id="Address" />
					</div>
				</div>
				<!--   <div class="row">
	             
	               
		        </div>  -->
				<!--   <div class="row" id = "cityDescArea">
	             <div class="form-group required col-lg-12">
	               <label class="control-label"  for="message">City Description</label>
	               <textarea placeholder="Description about the city" name="cdesc" class="form-control textselect" rows="9" id="cityDescription"></textarea>
	             </div>
             </div>  -->
			</div>

			<div class="clearfix">
				<!-- FF/CC -->
			</div>
			<div class="col-lg-12" style="padding-top: 5px">
				<div class="form-group col-lg-12">
					<div class="row fileupload-buttonbar">
						<div class="col-lg-7">

							<!-- The fileinput-button span is used to style the file input field as button -->

							<button id="submitAttractionBtnId" type="button"
								onclick="submitForm()" class="btn btn-primary start"
								style="margin-left: 430px">
								<span>Submit Attraction</span>
							</button>
						</div>
						<!-- The global progress state -->
						<div class="col-lg-5 fileupload-progress fade">
							<!-- The global progress bar -->
							<div class="progress progress-striped active" role="progressbar"
								aria-valuemin="0" aria-valuemax="100">
								<div class="progress-bar progress-bar-success"
									style="width: 0%;"></div>
							</div>
							<!-- The extended global progress state -->
							<div class="progress-extended">&nbsp;</div>
						</div>
					</div>
					<!-- The table listing the files available for upload/download -->
					<table role="presentation" class="table table-striped">
						<tbody id="filesToUpload" class="files"></tbody>
					</table>
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
