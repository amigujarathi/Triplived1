
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


<div class="container">

	<form id="fileupload" method="POST" enctype="multipart/form-data">

		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Edit Trip Video</h1>
			</div>
		</div>


		<div class="row" style="margin-top: -35px;">

			<div class="col-lg-12">
				<div class="row" id="smsgs">
					<!--   FF/CC -->
				</div>
				<div class="row">
					<div class="form-group required col-lg-4" style="margin-top: 24px">
						<label class="control-label" for="name"
							style="margin-top: -21px; position: absolute">Trip ID</label> <input
							placeholder="Trip ID" type="text" id="tripId" name="tripId"
							required="required" style="width: 376px" />

						<button id="getTripDetails" type="button"
							onclick="getTripStages()" class="btn btn-primary start"
							style="float: right; margin-right: -201px; margin-top: -30px;">
							<span>Get Stages</span>
						</button>
					</div>
				</div>
			</div>

			<div class="clearfix">
				<!-- FF/CC -->
			</div>
			<div id="trip_stages" style="height: 200px;">
				<p>Trip Stages</p>
				<div class="checkkboxes_states" style="height: 80px">
					<div style="height: 35px;">
						<input type="checkbox" name="trip_pub" id="trip_pub"
							class="tripStages" value="TRIP_PUBLISH">Trip_Publish<input
							class="tripStages" type="checkbox" name="vid_gen" id="vid_gen"
							value="VIDEO_GENERATED">Video_Generated <input
							type="checkbox" class="tripStages" name="vid_re" id="vid_re"
							value="VIDEO_REQUEST_RECEIVED">Video_request_received <input
							type="checkbox" class="tripStages" name="you_up" id="you_up"
							value="YOUTUBE_UPLOADED">Youtube_Uploaded<br>
					</div>
					<div>
						<input class="tripStages" type="checkbox" name="you_live"
							id="you_live" value="YOUTUBE_LIVE">Youtube_Live <input
							class="tripStages" type="checkbox" name="mail_sent"
							id="mail_sent" value="MAIL_SENT">Mail_Sent<input
							type="checkbox" class="tripStages" name="not_req" id="not_req"
							value="NOTIFICATION_REQUEST_RECEIVED">Notification_Request_Received
						<input class="tripStages" type="checkbox" name="Notification_sent"
							id="Notification_sent" value="NOTIFICATION_SENT">Notification_sent<br>
					</div>

				</div>
				<button id="UpdateTripStages" type="button"
					onclick="updateTripVideoStages()" class="btn btn-primary start">
					<span>Update Stages</span>
				</button>
			</div>

			<div id="video_upload_paths">
				<p style="height: 50px; font-size:20px;">Video Upload Paths</p>
				<button id="getTripPathDetails" type="button"
					onclick="getTripPath()" class="btn btn-primary start"
					style="margin-left: 483px; margin-top: -59px; position:absolute;">
					<span>Get Video Path</span>
				</button>
				<div class="checkkboxes_states" style="height: 45px;">
					<label class="control-label" for="name"
						style="margin-top: -21px; position: absolute">Server Path</label>
					<input placeholder="Server Path" type="text" id="server_path"
						name="serverPath" required="required" style="width: 376px" /> <label
						class="control-label" for="name"
						style="margin-top: -21px; position: absolute">Youtube Path</label>
					<input placeholder="Youtube Path" type="text" id="youtube_path"
						name="youTubePath" required="required" style="width: 376px" /> <label
						class="control-label" for="name"
						style="margin-top: -21px; position: absolute">Server Path</label>
					<select id="video_status">
						<option value="">Select Status</option>
						<option value="A">Active</option>
						<option value="I">InActive</option>
					</select>
				</div>
				<button id="UpdateVideoPaths" type="button"
					onclick="updateVideoPaths()" class="btn btn-primary start">
					<span>Update Paths</span>
				</button>
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
