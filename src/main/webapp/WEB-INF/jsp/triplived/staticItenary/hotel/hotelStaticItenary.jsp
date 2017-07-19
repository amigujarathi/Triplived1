 
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
		<td class="">
				<textarea placeholder="One liner about the photo" name="desc[]" rows="2" class="form-control"></textarea>
		</td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel" onclick="enableAddImageButton()">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    
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
                    
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>


<div class="container">
      
      <form id="fileupload"   method="POST" enctype="multipart/form-data">
      <input type="hidden" id="subTripId" name="subTripId" value='${param.subTripId}'/>
      <input type="hidden" id="tripId" name="tripId" value='${param.tripId}'/>
      
      
       <div class="row page-header">
        <div class="col-lg-12">
          <div class="col-lg-6">
          	<h1>Your place of stay in ${cityName}</h1>
          </div>
          	<div class="col-lg-6">
          		<div><h3 class="pull-right"><a href="${context}trip/trip-end" role="button">End Trip</a></h3></div>
        	</div>
          </div>
        </div>
      
      <div class="row" style="padding-top:10px;padding-bottom: 10px">
        <div class="col-lg-12">
          <div class="col-lg-12" style="padding-top:30px;" id="optionsId">
          	<div class="row">
          		<div class="col-lg-4">
          			<button type="button" id="addHotelId" class="btn btn-warning" onclick="loadHotelScreen()">Add another hotel</button>
          		</div>
          		<div class="col-lg-4">
          			<button type="button" id="addAttractionId" class="btn btn-warning" onclick="loadAttractionScreen()">Add Attraction</button>
          		</div>		
          		<div class="col-lg-4">
          			<button type="button" id="addDestinationId" class="btn btn-warning" onclick="loadNewCity()">New Destination</button>
          		</div>		
          		
          	</div>
          </div>
        </div>
      </div>
      
      <div class="row" style="padding-top:10px;padding-bottom: 10px">

        <div class="col-lg-6">
            <div class="row" id="smsgs"><!--   FF/CC --></div>
	            <div class="row">
	              <div class="form-group required col-lg-6" >
	                <label class="control-label" for="name">City</label><br/>
	                <input class="control-label form-control"  name="cityname1" id="cityname1" value="${cityName}" disabled/><br/>
	                <input class="control-label form-control"  name="cityname" id="cityname" value="${cityName}" type="hidden"/>
	              </div>
	              <div class="form-group required col-lg-6">
	                <label class="control-label"  for="email">Hotel Name</label>
	                <select required="required" class="typeahead form-control " id="hotelName" name="hotelName">
	                	<option value="">Select</option>
	                </select>
	              </div>
		        </div>
		         <div class="row">
	               <div class="form-group required col-lg-6" id = "timeStampDiv" >
	                <label  class="control-label"for="longitude">Time(preferably check-in)</label><br/>
	                <input id="datetimepicker" type="text " name="timeStamp" class="form-control">
	              </div>
	               <div class="form-group required col-lg-6" id="ratingParentId">
	                <label class="control-label"  for="email">Hotel Rating</label>
	                <select required="required" class="typeahead form-control " id="hotelRating" name="hotelRating">
	                	 <option>--Select--</option>
	                     <option>1</option>
					     <option>2</option>
					     <option>3</option>
					     <option>4</option>
					     <option>5</option>
					 </select>
	              </div>
	        </div>          
        </div>
         <div class="col-lg-6">
         	<div class="row">
	              <div class="form-group required col-lg-12">
	                <label class="control-label"  for="message">A few words about the hotel</label>
	                <textarea placeholder="One of Two liner about the hotel" name="hotelDescription" class="form-control" rows="2" id="message"></textarea>
	              </div>
	        </div>
	        
	        <!-- <div class="row">
	               <div class="form-group required col-lg-6" id = "timeStampDiv" >
	                <label  class="control-label"for="longitude">Time</label>
	                <input id="datetimepicker" type="text" name="timeStamp">
	              </div>
	               <div class="form-group required col-lg-6">
	                <label class="control-label"  for="email">Hotel Rating</label>
	                <select required="required" class="typeahead form-control " id="hotelName" name="hotelName">
	                	<option value="">Select</option>
	                </select>
	              </div>
	        </div> -->
	        
         </div>
        <div class="clearfix"><!-- FF/CC --></div>
        <div class="col-lg-12" style="padding-top:3px">
        	<div class="form-group col-lg-12">
					<div class="row fileupload-buttonbar">
						<div class="col-lg-7">
							
							 <!-- The fileinput-button span is used to style the file input field as button -->
			                <div class="btn btn-success fileinput-button" id="uploadPhotoId">
			                    <i class="glyphicon glyphicon-plus"></i>
			                    <span id="addPhotosId">Add Photos taken at hotel...</span>
			                    <input type="file" name="fileToUpload" multiple=>
			                </div>
			                
			                <span style="border-right: 2px solid red; margin:12px"></span>
			                <button type="submit" class="btn btn-primary start" onclick="submitForm()" id="submitBtnId">
			                    <i class="glyphicon glyphicon-upload"></i>
			                    <span>Submit</span>
			                </button>
			                
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
					<label>Please select all photos of this hotel for upload in one go. No more file uploads for this hotel would be allowed after that.</label>
						<!-- The table listing the files available for upload/download -->
						<table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
					</div>
        	</div>
      	</div>
      	</form>	
      
      <!-- <div class="row" id="existing-images-div">
	        <div class="col-lg-12">
	          <h1 class="page-header">Existing Images  <span id ='totalimages'> </span></h1>
	        </div>
	      	<div>
	      		<table style="width:500px; border:1">
	      			<thead>
	      				<th>Image</th>
	      				<th>Link to Big image</th>
	      				<th>Name</th>
	      			</thead>
	      			<tbody id ="existing_images">
	      			FF/CC
	      			</tbody>
	      		</table>
	      		
	      	</div>
      	</div> -->
    </div>
 
