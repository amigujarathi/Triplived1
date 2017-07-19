 
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
      
      <form id="fileupload"   method="POST" enctype="multipart/form-data">
      
      <div class="row">
        <div class="col-lg-12">
          <h1 class="page-header">Add Attraction Description</h1>
        </div>
      </div>
      
      
      <div class="row">

        <div class="col-lg-6">
            <div class="row" id="smsgs"><!--   FF/CC --></div>
	            <div class="row">
	              <div class="form-group required col-lg-6" >
	                <label class="control-label" for="name">City</label>
	                <input placeholder="City Name" type="text" id="cityname" name="cityname" required="required" class="typeahead form-control" id="cityname" />
	              </div>
	              <div class="form-group required col-lg-6">
	                <label class="control-label"  for="email">Attraction Name</label>
	                <select required="required" class="typeahead form-control " id="attractionName" name="attractionName">
	                	<option value="">Select</option>
	                </select>
	              </div>
		        </div>
		         <div class="row">
	              <div class="form-group required col-lg-6" id = "latitudeDiv"   >
	                <label class="control-label" for="latitude">Attraction Latitude</label>
	                <input placeholder="Logitude" type="number" name="latitude" class="form-control" id="latitude" />
	              </div>
	              <div class="form-group required col-lg-6" id = "longitudeDiv" >
	                <label  class="control-label"for="longitude">Attraction Longitude</label>
	                <input placeholder="Latitude" type="number" name="longitude" class="form-control " id="longitude" />
	              </div>
		        </div>
		        <div class="row">
	              <div class="form-group   col-lg-6" id = "placeidDiv" >
	                <label  class="control-label"for="googleplaceid1">Google Place Id</label>
	                <input placeholder="Google Place Id" type="text" name="googleplaceid1" class="form-control " id="googleplaceid1" />
	              </div>
	              <div class="form-group  col-lg-6" id = "placenameDiv" >
	                <label  class="control-label"for="googleplacename1">Google Place Name</label>
	                <input placeholder="Google Place Name" type="text" name="googleplacename1" class="form-control " id="googleplacename1" />
	              </div>
		        </div>
		        
		        <div class="row">
	              <div class="form-group   col-lg-6" id = "placeTimeDiv" >
	                <label  class="control-label"for="placeTimeid1">Timings</label>
	                <input placeholder="Timings" type="text" name="placeTimeid1" class="form-control " id="placeTimeid1" />
	              </div>
	              <div class="form-group  col-lg-6" id = "placeAddressDiv" >
	                <label  class="control-label"for="placeAddressid1">Address</label>
	                <input placeholder="Address" type="text" name="placeAddressid1" class="form-control " id="placeAddressid1" />
	              </div>
		        </div>  
		        
		        <div class="row">
	              <div class="form-group   col-lg-6" id = "bestTimeDiv" >
	                <label  class="control-label"for="bestTimeid1">Best time to visit</label>
	                <input placeholder="Best time to visit" type="text" name="bestTimeid1" class="form-control " id="bestTimeid1" />
	              </div>
	              <div class="form-group  col-lg-6" id = "reqTimeDiv" >
	                <label  class="control-label"for="reqTimeid1">Required Time</label>
	                <input placeholder="Required Time" type="text" name="reqTimeid1" class="form-control " id="reqTimeid1" />
	              </div>
		        </div> 
		        
		        <div class="row">
	              <div class="form-group   col-lg-4" id = "phoneDiv" >
	                <label  class="control-label"for="phoneId">Phone</label>
	                <input placeholder="Phone" type="text" name="phoneId" class="form-control " id="phoneId" />
	              </div>
	              <div class="form-group  col-lg-4" id = "webSiteDiv" >
	                <label  class="control-label"for="webSiteId">Website</label>
	                <input placeholder="Website" type="text" name="webSiteId" class="form-control " id="webSiteId" />
	              </div>
	              <div class="form-group  col-lg-4" id = "ticketDiv" >
	                <label  class="control-label"for="ticketId">Ticket</label>
	                <input placeholder="Ticket" type="text" name="ticketId" class="form-control " id="ticketId" />
	              </div>
		        </div> 
		        
		        <div class="row" id = "cityDescArea">
	             <div class="form-group required col-lg-12">
	               <label class="control-label"  for="message">City Description</label>
	               <textarea placeholder="Description about the city" name="cdesc" class="form-control textselect" rows="9" id="cityDescription"></textarea>
	             </div>
             </div>    
        </div>
         <div class="col-lg-6" id="attractionDescArea">
         
         	 <div class="row">
         	 	   
	               <div class="form-group required col-lg-12">
	                <label class="control-label"  id="lastUpdateDetail">Attraction Category</label>
	              </div>
         	 </div>
         	 <div class="row">
         	 	   
	               <div class="form-group required col-lg-12">
	                <label class="control-label"  for="email">Attraction Category</label>
	               <div id = "cat_div" style="border: 1px solid #9e9e9e;padding: 10px;margin: 2px;"></div>
	               <button type="button" id ="inactivateAttraction" onclick="inactivateAttraction()" class="btn btn-danger delete ">
				             <i class="glyphicon glyphicon-trash"></i>
				                    <span>Inactivate Attraction</span>
				   </button>
	              </div>
         	 </div>
             <div class="row">
	             <div class="form-group required col-lg-12">
	               <label class="control-label"  for="message">Attraction Description</label>
	               <textarea placeholder="Description about the attraction" name="adesc" class="form-control textselect" rows="7" id="attractionDescription"></textarea>
	             </div>
             	</div>   
       		  <div class="row">
	             <div class="form-group required col-lg-12">
	               <label class="control-label"  for="message">Attraction PunchLine</label>
	               <textarea placeholder="Few Lines about the attraction" name="attractionPunchline" class="form-control" rows="2" id="attractionPunchline"></textarea>
	             </div>
             </div>
         </div>
 
        <div class="clearfix"><!-- FF/CC --></div>
        <div class="col-lg-12" style="padding-top:3px">
        	<div class="form-group col-lg-12">
					<div class="row fileupload-buttonbar">
						<div class="col-lg-7">
							
							 <!-- The fileinput-button span is used to style the file input field as button -->
			                <span class="btn btn-success fileinput-button">
			                    <i class="glyphicon glyphicon-plus"></i>
			                    <span>Add files...</span>
			                    <input type="file" name="fileToUpload" required="false" multiple=   >
			                </span>
			               <!--  <button type="reset" class="btn btn-warning cancel">
			                    <i class="glyphicon glyphicon-ban-circle"></i>
			                    <span>Cancel upload</span>
			                </button>
			                <button type="button" class="btn btn-danger delete">
			                    <i class="glyphicon glyphicon-trash"></i>
			                    <span>Delete</span>
			                </button> -->
			                <span style="border-right: 2px solid red; margin:12px"></span>
							<button type="button" onclick="submitForm()" class="btn btn-primary start">
			                    <i class="glyphicon glyphicon-upload"></i>
			                    <span>Submit and Upload</span>
			                </button>
			                
			                <input type="checkbox" class="toggle">
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
						<table role="presentation" class="table table-striped"><tbody id = "filesToUpload" class="files"></tbody></table>
					</div>
        	</div>
      	</div>
      	</form>	
      
      <div class="row" id="existing-images-div">
	        <div class="col-lg-12">
	          <h1 class="page-header">Existing Images  <span id ='totalimages'> </span></h1>
	        </div>
	      	<div>
	 
	      			<div id ="existing_images" class="col-lg-12">
	      			FF/CC
	      			</div>
	      		
	      	</div>
      	</div>
      	
      	
    </div>
    
<div id="responsive" class="modal"  tabindex="-1" data-backdrop="static" style="display: none;" data-keyboard="false">
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
