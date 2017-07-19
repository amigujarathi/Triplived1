 
<%@ page isELIgnored="false"%>
 
<style>
.form-group.required .control-label:after{
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
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
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
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery>
						<img src="{%=file.thumbnailUrl%}" width="300px" height="200px">
					</a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>

 
<div class="container">
      
      <form id="fileupload"   method="POST" enctype="multipart/form-data">
      
      <div class="row page-header">
        <div class="col-lg-6">
          <h1>Start your trip</h1>
        </div>
         <div class="col-lg-6">
          <div><h3 class="pull-right"><a href="${context}trip/trip-end" role="button">End Trip</a></h3></div>
        </div>
      </div>
      
      <div class="row" style="padding-top:10px">

        <div class="col-lg-6">
            <div class="row" id="smsgs"><!--   FF/CC --></div>
	            
		         <div class="row">
	              <div class="form-group required col-lg-6" id = "tripDiv"   >
	                <label class="control-label" for="tripName">TripName</label>
	                <input placeholder="TripName" name="tripName" class="form-control" id="tripName" />
	              </div>
	              <div class="form-group required col-lg-6" id = "timeStampDiv" >
	                <label  class="control-label">Started at:</label><br/>
	                <input id="datetimepicker" class="form-control" type="text" name="timeStamp">
	              </div>
	              
		        </div>            
        </div>
         <div class="col-lg-6">
         	<div class="row">
	              <div class="form-group required col-lg-12">
	                <label class="control-label"  for="message">Trip Description</label>
	                <textarea placeholder="One or Two liner about the Trip" name="tripDescription" class="form-control" rows="2" id="message"></textarea>
	              </div>
	        </div>
	        
         </div>
        <div class="clearfix"><!-- FF/CC --></div>
        <div class="col-lg-12" style="padding-top:3px">
        	<div class="form-group col-lg-12">
					<div class="row fileupload-buttonbar">
						<div class="col-lg-7">
							
							 <!-- The fileinput-button span is used to style the file input field as button -->
			                
			                
			                <button type="submit" class="btn btn-primary start" onclick="submitForm()" id="submitBtnId">
			                      <span>Record</span>
			                </button>
			                <div id="showLoaderId" style="top: -39px; vertical-align: middle; margin-left: 37%; height: 49px;">
									<img src="${images_url}/../ajax-loader.gif"></img>
							</div>
			                
			                <!-- The global file processing state -->
			                <span class="fileupload-process"></span>
						</div>
						<!-- The global progress state -->
						<div class="col-lg-5 fileupload-progress fade">
						    <!-- The global progress bar -->
			                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
			                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
			                </div>
			                <!-- The extended global progress state -->
			                <div class="progress-extended">&nbsp;</div>
						</div>
					</div>
						<!-- The table listing the files available for upload/download -->
						<table id="presentationId" role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
						<div id="continueTripId">
						<label>Your trip has been initiated !!!</label>
						<button type="button" class="btn btn-default" onclick="loadCity()">Continue</button>
						</div>				
					</div>
        	</div>
      	</div>
      						
      	</form>	
      
      <div id="tripResponse"></div>
    </div>
 
