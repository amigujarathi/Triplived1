
<%@ page isELIgnored="false"%>

<style>
.form-group.required .control-label:after {
	color: red;
	content: "*";
	position: absolute;
}
</style>
<style>
.tripStages {
	width: 30px;
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
<head>
<script language="javascript">
	// put this in the <head></head> section 
	window.addEventListener("load", function() {
		document.getElementById('tripId').disabled = false;
	}, false);
</script>
</head>
<div class="container">

	<form id="fileupload" method="POST" enctype="multipart/form-data">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Trip Entities</h1>
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
							style="margin-top: -21px; position: absolute">Trip ID</label> <input
							placeholder="Trip ID" type="text" id="tripId" name="tripId"
							required="required" style="width: 376px" disabled=true/>
					</div>
				</div>
			</div>


		</div>
	</form>
	<div class=row>
		<button id="getTripEntities" type="button"
			onclick="javascript:getCityReviews()" class="btn btn-primary start">
			<span>Get City Reviews</span>
		</button>

		<button id="getTripEntities" type="button"
			onclick="javascript:getAttractionReviews()"
			class="btn btn-primary start">
			<span>Get Attraction Reviews</span>
		</button>

		<button id="getTripEntities" type="button"
			onclick="javascript:getHotelReviews()" class="btn btn-primary start">
			<span>Get Hotel Reviews</span>
		</button>
	</div>


	<!-- Editable Grid - START -->
	<div id="grid" style="margin-top: 30px"></div>
	<!-- Editable Grid - END -->
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