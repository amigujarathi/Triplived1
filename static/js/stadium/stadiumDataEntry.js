var sportsSelected = []; 
var regionSelected = [];

$(function() {
	
	populateCities();
	//var autoCompleteDataSourceForRegions =[];
	//var regionList = store.
	var allActivityList = store.get('allActivityListInStore'); 
	
	if(allActivityList == null || allActivityList == undefined ){
		
		alert("please visit some other page before accessing this page as activities are not initilized");
	}
	
	
	
	$("#city").change(function () {
		 $('#street').val("");

		 if($("#city").val() == ""){
			 return "";
		 }
		 var cityParam = $("#city").val().split("-")[1];
		 //$('#cityId').val(item.id);
		 var streets = [];
		 var dfr = $.ajax({
			 	url: DOMAIN+'region/getAllRegionsForCity?cityId='+cityParam,
				dataType:'json'
         });
    	 
    	 dfr = dfr.pipe(function(results) {
    		//console.log(results);
    		 $.each( results, function (index, value) {
    			 var myObject = new Object();
    			 myObject.area = value.street; 
    			 myObject.value = value.street; 
    			 myObject.id = value.id;
    			// myObject.tokens = jQuery.makeArray(value.street);
    			 streets[index]= myObject;
    		 });
    		 
    		 //stree typeahead
	    	/* $('#street').typeahead({
	      	      local: streets,
	      	      limit:10,
	      	  	  template: ['<p class="repo-language">{{area}}</p>',].join(''),                                                                 
	      	      engine: Hogan 
	      	 });*/
	    	 
	    	 $( "#street" ).autocomplete({
	    	        minLength: 0,
	    	        source: streets,
	    	        select: function( event, ui ) {
	    	           //alert(ui.item.id);
	    	           $('#streetId').val(ui.item.id);
	    	           this.value = ui.item.area;
	    	           return false;
	    	          }
	    	      })
	    	      .data( "ui-autocomplete" )._renderItem = function( ul, item ) {
	    	        return $( "<li>" )
	    	          .append( "<a>" + item.area + "</a>" )
	    	          .appendTo( ul );
	    	      };
	    	      
    		 
	    	 //when street is selected where should we put the street id
	    	/* $('input[name=street]').on('typeahead:selected', function(evt, item) {
	    		 $('#streetId').val(item.id);
	    	 });*/
		});
	});	 
	 
	
	var autoCompleteDataSourceForSports = [];
	for(var i = 0; i < allActivityList.length; i++) {
	     obj = {};
		 obj.label = allActivityList[i].activityName;
		 obj.value = allActivityList[i].activityName;
		 obj.id = allActivityList[i].activityId;
		 autoCompleteDataSourceForSports.push(obj);
	}
	
    
	$( "#sportsPlayed1" ).focus(function(){ 
		$("label#sportsPlayed1-label").hide();
	});
    
    $( "#sportsPlayed1" ).autocomplete({
        minLength: 0,
        source: autoCompleteDataSourceForSports,
        select: function( event, ui ) {
           sportsSelected.push(ui.item);
           
           var list = "";
           for(i=0; i<sportsSelected.length; i++){
               list +="<li>"+sportsSelected[i].value+"<a onClick='removeThisEl(this)' href='#' id=" + sportsSelected[i].id + "-" + sportsSelected[i].value.replace(/ /g,"_") + " > Remove" + "</a>" + "</li>";
           }
           $("#sportsSelectedP").empty();
           $("#sportsSelectedP").append(list);
           this.value = "";
           return false;
          }
      })
      .data( "ui-autocomplete" )._renderItem = function( ul, item ) {
        return $( "<li>" )
          .append( "<a>" + item.label + "</a>" )
          .appendTo( ul );
      };
      
   
      
  });
 
 function removeThisEl(value) {
	 	 
	     var id = value.id;
	     
         for(var i=0; i<sportsSelected.length; i++){
             if(sportsSelected[i].id == id) {
            	 sportsSelected.splice(i,1); 
             }
         }
         
         var list = "";
         for(var i=0; i<sportsSelected.length; i++){
             list +="<li>"+sportsSelected[i].value+"<a onClick='removeThisEl(this)' href='#' id=" + sportsSelected[i].id + " > Remove" + "</a>" + "</li>";
         }
         
         $("#sportsSelectedP").empty();
         $("#sportsSelectedP").append(list);
    
 };
 
 function populateCities() {
	 
	 var sel = $('<select class="form-control" id="city" placeholder="City" name="city">').appendTo('div#citiesdiv');
	 sel.append($("<option>").attr('value',"").text("Select City"));
	 $(store.get("cities")).each(function() {
		 sel.append($("<option>").attr('value',this.city+"-"+this.id).text(this.city));
	 });
 }
 
 $(function() {
	 $('#stadiumContentForm').validate({
	     rules: {
	         name:  {required: true},
	         sportsSelectedP:  {required: true},
	         city:  {required: true},
	         zipCode:  {required: true},
	         type:  {required: true},
	         address:  {required: true},
	         sportsPlayed:  {required: true}
	         
	        /*  email: {required: true, email: true},
	         mobile: {required: true, number: true},
	         message: {required: true, minlength : 50} */
	     },
	     highlight: function (element) {
	         $(element).closest('.form-group').removeClass('success').addClass('error');
	     },
	     messages: {
	     	name: "Please enter stadium name",
	     	sportsSelectedP: "Please select some sports",
	     	city: "Please select city",
	     	zipCode: "Please enter Pin Code",
	     	sportsPlayed:"Please select some sports",
	     	type:"Please select stadium type"
	 	},
	 	 submitHandler: function(form) {
	 		 
	 		 var sportsSelected = $("p#sportsSelectedP").find("li") ;
	 		 if(sportsSelected.length  == 0 ) {
	 			$("label#sportsPlayed1-label").html("Please Select Some Sports");
	 			$("label#sportsPlayed1-label").show();
	 			return false ;
	 		 }
	 		
	 		var sportsPlayed = [];
	 		$($("p#sportsSelectedP").find("li a")).each(function(){
	 			sportsPlayed.push($(this).attr('id'));
	 		});
	 		
	 		$("#sportsPlayed").val(sportsPlayed);
	 		
	  		var data = {};
	  		$("#stadiumContentForm").serializeArray().map(function(x){data[x.name] = x.value;});
	  		
	  		$(".stadiumContentSubmit").addClass("progress-ajax");
	  		$.ajax({
	  				type:"POST",
	  				contentType: "application/json",
	  				url: DOMAIN+"content/add",
	  				data: JSON.stringify(data),
	  				success: function(data) { 
	  					//alert(data);
	  					$('body').removeClass('loading-indicator');
	  					$(".contactussubmit").removeClass("progress-ajax");
	  					$("div#smsgs").removeClass("error-success-msgs");
	  					$("div#smsgs").removeClass("text-success");
	  					if(data.result == 'success'){
	  						$("div#smsgs").addClass("text-success");
	  					//	$("div#smsgs").text(data.messages[0]);
	  						form.reset();
	  						alert("data saved successfully");
	  					} else if(data.result == 'failure'){
	  						$("div#smsgs").addClass("error-success-msgs");
	  						//$("div#smsgs").text(data.messages[0]);
	  						alert("Error occured while saving data. Please save data again or contact admin if problem persists");
	  					}
	  				},
	  				failure : function (data) {
	  					alert(data);
	  				},
	  				dataType: "JSON"
	  		});
	 	}
	 });
	 
	 $('.multi-field-wrapper').each(function() {
		    var $wrapper = $('.multi-fields .multi-field', this);
		    $(".add-field", $(this)).click(function(e) {
		        $('.multi-field', $wrapper).clone(true).appendTo($wrapper).find('input').val('').focus();
		    });
		    
		    $('.multi-field .remove-field', $wrapper).click(function() {
		        if ($('.multi-field', $wrapper).length > 1) {
		            $(this).parent().parent('.multi-field').remove();
		        }
		    });
		});
	 
	 $('#establishedOn').datepicker({
		 autoclose: true,
		 orientation: "top auto",
		 startView: 2,
		 endDate: new Date(),
		 format: "dd M yyyy"
			});
 });

/*[  C:\Users\santoshjo\git\connectme-git\static\js\stadium\stadiumDataEntry.js  ]*/

