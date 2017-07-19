var categories = [];

function getAllSports() {

	var data = store.get("allActivityList");
	/*
	 The extra condition inside if is for refreshing the local storage when the sports list changes. This value is picked from 
	 environment.properties file
	 TO DO 
	 */
	if ((data == null) || (store.get("lastUpdatedLocalStorageValue") != '${lastUpdatedLocalStorage}')) {
		
		store.set("lastUpdatedLocalStorageValue", '${lastUpdatedLocalStorage}');
		var dfr = $.ajax({
			url : DOMAIN + 'searchActivity/getActivityList',
			dataType : 'json'
		});

		dfr = dfr.pipe(function(results) {
					store.set('allActivityList', results);
			//		createAutoComplete();
					//creatCategorySelect();
				});
	} else {
		//createAutoComplete();
		// creatCategorySelect();
	}
	
	 
}

function createAutoComplete() {
	
	$('#activity').typeahead('destroy');
	 
	var substringActivityMatcher = function(strs) {
		  return function findMatches(q, cb) {
		    var matches, substrRegex;
		    matches = [];
		    substrRegex = new RegExp(q, 'i');
		    $.each(strs, function(i, str) {
		    	if (substrRegex.test(str.activityName) /*&& str.categoryId == selectedVal*/ ) {
		    	  matches.push({ value: str.activityName, id: str.activityId , categoryName:  str.categoryName, sportName : str.activityName });
		      	}
		    });
		    cb(matches);
		  };
		};
		
	 $('#activity').typeahead({
		  hint: true,
		  highlight: true,
		  minLength: 1
		},
		{
		  name: 'activity',
		  displayKey: 'value',
		//  source: store.get("allActivityList")
		  source: substringActivityMatcher(store.get("allActivityList"))
		});
}

//TO DO to remove
/*function creatCategorySelect(){
	var seloption= "";
	$.each( store.get("allActivityList"), function(item, value) {
		if(categories.indexOf(value.categoryName) == -1) {
			categories.push(value.categoryName);	
			
			 seloption += '<option value="'+value.categoryId+'">'+value.categoryName+'</option>'; 
		}
	});
	
	$('#category').append(seloption);
}
*/
$(function() {
//Register click when edit link is clicked
$(document).on("click", '.edit-activity', function() {
	var sportsId = this.id.split("-")[1];

	var element = $(this);
	element.html("Save");
	element.removeClass("edit-activity");
	element.addClass("save-activity");
	
	//show cancel
	$("table tr td span#cancel-span-" + sportsId).removeClass("hidden");
	
	var previousSelectedLevel = $("table tr#" + sportsId + " td.level").html() ;
	var content = $("#editActivitycontent").tmpl(new Object());
	for( var i = 0 ; i < content[0].options.length; i++ ) {
		
		if(content[0].options[i].value == previousSelectedLevel){
			content[0].options[i].selected = true;
			break;
		}
	}

	$("table tr#" + sportsId + " td.desc textarea").removeAttr("disabled");
	$("table tr#" + sportsId + " td.level").html(content);
	$("table tr#" + sportsId + " td.level").append("<span id='level-"+sportsId+"' class='hidden'>"+previousSelectedLevel+"</span>");
});

$(document).on(
		"click",
		'.cancel-activity',
		function() {
			var sportsId = this.id.split("-")[1];
			//text area
			$("table tr#" + sportsId + " td.desc textarea").attr("disabled","disabled");
			
			//edit and save flip
			var element = $("table tr#" + sportsId + " td.action a#edit-"+ sportsId );
			element.html("Edit");
			element.addClass("edit-activity");
			element.removeClass("save-activity");
			
			//Hide cancel button
			$("table tr td span#cancel-span-" + sportsId).addClass("hidden");
			
			//show original level
			var originalLevelField = $("table tr#" + sportsId + " td.level") ;
			originalLevelField.html( originalLevelField.find("span#level-"+ sportsId ).html() );
			
		});

$(document).on("change",'#category',
		  function() {
	
			/*$('.typeahead').typeahead('destroy');
	
			var selectedVal = this.selectedOptions[0].value;
			if(selectedVal == "") {
				return ;
			}*/
			//var data = store.get("allActivityList");
			//var activities = [] ; 
			//var index = 0;
			/*$.each(data, function(i, value) {
				
				if(value.categoryId == selectedVal){
				
					var sport = new Object();
					
					sport.sportName = value.activityName;
					sport.value = value.activityName;
					sport.id = value.activityId;
					sport.categoryName = value.categoryName; 
					//sport.tokens = jQuery.makeArray(value.activityName);
					activities[index] = sport;
					index++;
				}
			});*/
			
			// $('#activity').typeahead('destroy');
    		/* var substringActivityMatcher = function(strs) {
    			  return function findMatches(q, cb) {
    			    var matches, substrRegex;
    			    matches = [];
    			    substrRegex = new RegExp(q, 'i');
    			    $.each(strs, function(i, str) {
    			    	if (substrRegex.test(str.activityName) && str.categoryId == selectedVal ) {
    			    	  matches.push({ value: str.activityName, id: str.activityId , categoryName:  str.categoryName, sportName : str.activityName });
    			      	}
    			    });
    			    cb(matches);
    			  };
    			};*/
    			 
    			/*$('#activity').typeahead({
    			  hint: true,
    			  highlight: true,
    			  minLength: 1
    			},
    			{
    			  name: 'activity',
    			  displayKey: 'value',
    			  source: store.get("allActivityList")
    			  //source: substringActivityMatcher(store.get("allActivityList"))
    			});*/
    			
			/*$('#activity')
			.typeahead(
					{
						local : activities,
						template : [ '<p class="repo-language">{{value}}</p>' ].join(''),
						engine : Hogan
					});*/
			
	}
);
//Register click when activity is saved  
$(document).on(
		"click",
		'.save-activity',
		function() {
			
			var sportsId = this.id.split("-")[1];
			
			//disable all links for a moment
			$("table tr#" + sportsId + " td a").bind('click', function(e){
		        e.preventDefault();
			}) ;
			
			
			var element = $(this);
			var selectedVal = $("table tr#" + sportsId + "  td.level select").val();
			var desc = $("table tr#" + sportsId + "  td.desc textarea").val();

			element.html("Edit");
			element.removeClass("save-activity");
			element.addClass("edit-activity");

			//hide cancel span
			$("table tr#" + sportsId + " td span#cancel-span-" + sportsId).addClass("hidden");
			
			$("table tr#" + sportsId + " td span#progress-save-" + sportsId).addClass("progress-save");
			
			
			var activity = new Object();
			activity.activityId = sportsId;
			activity.activitylevel = selectedVal;
			activity.activityDesc = desc;
			activity.activity = $("table tr#" + sportsId + " td.actname").html();

			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : DOMAIN + "userinterest/updateactivityinfo",
				data : JSON.stringify(activity),
				success : function(resp) {
					$("table tr#" + sportsId + " td.desc textarea").attr("disabled", "disabled");
					$("table tr#" + sportsId + " td.level").html(selectedVal);
					$("table tr#" + sportsId + " td span#progress-save-" + sportsId).removeClass("progress-save");
					
					//reregisr click
					$("table tr#" + sportsId + " td a").unbind('click');
				},
				dataType : "JSON"
			});
		});

//Register delete of an activity
$(document).on("click", '.delete-activity', function() {

	var sportsId = this.id.split("-")[1];
	$("#del-act-name").html($("table tr#" + sportsId + " td.actname").html());
	$("#del-act-id").val(sportsId);
	$('#deleteActivity').modal('show');

	//deleteactivityinfo
});

//When activity is not deleted
$("#noDeleteActivity").click(function() {
	$("#del-act-name").html("");
	$("#del-act-id").val("");
	$('#deleteActivity').modal('hide');
});

//When activity is deleted	
$("#yesDeleteActivity").click(function() {

	var sportsId = $("#del-act-id").val();

	var activity = new Object();
	activity.activityId = sportsId;
	activity.activity = $("table tr#" + sportsId + " td.actname").html();

	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : DOMAIN + "userinterest/deleteactivityinfo",
		data : JSON.stringify(activity),
		success : function(resp) {
			$("table tr#" + sportsId).remove();
			alreadySelectedSports[sportsId] = undefined;
			$('#deleteActivity').modal('hide');
		},
		dataType : "JSON"
	});
});

});

$(function() {

	
	//Register Validators 
	$.validator.addMethod("validActivityCheck", function(value, element) {
		if ($("#activity").val().trim() != "" && $("#activityId").val().trim() == "") {
			return false;
		}
		return true;
	}, 'Please select a valid activity!');
	$.validator.addMethod(
					"alreadyExistsActivityCheck",
					function(value, element) {
						if (alreadySelectedSports[$("#activityId").val()] != undefined) {
							return false;
						}
						return true;
					}, 'You have already added this activity!');

	//Form Validation 
	$('#useractivityform').validate(
					{
						onkeyup : false,
						rules : {
							//category :{	required : true	},
							activity : {
								required : true /*,
								validActivityCheck : true,
								alreadyExistsActivityCheck : true*/
							},
							activityDesc : {
								required : true,
								minlength : 5,
								maxlength: 150
							},
							activitylevel : {
								required : true
							}
						},
						messages : {
						//	category : {required : "Please select some category"	},
							activity : {
								required : "Please enter some activity"
							},
							activityDesc : {
								required : "Please enter some description about the activity",
								minlength : "At least {0} characters are expected",
								maxlength : "Please summarize in less then {0} characters"
							},
							activitylevel : {
								required : "Please select your proficiency level "
							}
						},
						invalidHandler : function(event, validator) {
							$.each(validator.errorList, function(index, value) {
								$("#label-error-" + value.element.name).val(
										value.message);
							});
						},
						submitHandler : function(form) {
							
							
							if ($("#activity").val().trim() != "" && !isValidActivity($("#activity").val())) {
								$("#label-error-activity").html("Please select a valid activity!");
								$("#label-error-activity").css("display", "");
								return false;
							}
							
							 
							
							if (alreadySelectedSports[$("#activityId").val()] != undefined) {
								$("#label-error-activity").html("You have already added this activity!");
								$("#label-error-activity").css("display", "");
								return false;
							}
							
							if (alreadySelectedSports[$("#activityId").val()] == undefined) {
								var data = {};
								$("#useractivityform").serializeArray().map(
										function(x) {
											data[x.name] = x.value;
										});
								
								 $("button#add-activity").attr("disabled", "disabled");
								$.ajax({
											type : "POST",
											contentType : "application/json; charset=utf-8",
											url : DOMAIN+ "userinterest/saveactivityinfo",
											beforeSend : function(event, request, settings )
												{
													$("span.saveactivity").addClass("progress-save");
												},
											complete   : function(event, request, settings ) {$("span.saveactivity").removeClass("progress-save");},
											data : JSON.stringify(data),
											success : function(resp) {
												
												if(resp.activityId != -1){
													$("#no-activity-registered").remove();
													var activity = new Object();
													activity.sportName = resp.activity;
													activity.id = resp.activityId;
													activity.level = resp.activitylevel;
													activity.description = resp.activityDesc;
													 
	
													alreadySelectedSports[activity.id] = activity.id;
	
													$("#activitycontent").tmpl(activity).appendTo("table tbody#user-intrested-activity");
												}else{
													alert(resp.activityDesc);
												}
												$('#addactivity').modal('hide');
												$("button#add-activity").removeAttr("disabled");
											},
											
											dataType : "JSON"
										});
							} else {
								$("#activitymsg").html("You have already added this activity!");
								$("#activitymsg").addClass("activity-msg");
							}
						}
					});

	
	//Validate form on add activity 
	$("#add-activity").click(function() {
		$('#useractivityform').valid();
	});

	//first time when user click on add activity
	$("#addactivity-href").click(function() {
		$("#useractivityform").data('validator').resetForm();
		createAutoComplete();
		$('#addactivity').modal('show');
	});

	//when the add activity form gets hidden either on cancel or addition of an activi then reset the form
	$('#addactivity').on(
			'hide.bs.modal',
			function(e) {
				$(':input', '#useractivityform').not(':button, :submit, :reset, :radio').val('').removeAttr('checked').removeAttr('selected');
				$(":input[type='radio']", "#useractivityform").prop("checked", false);
				$("#activitymsg").html("");
				$("#activitymsg").removeClass("activity-msg");
			});

	//Hide form whend cacel is clicked
	$("#cancel-add-activity").click(function() {
		$('#addactivity').modal('hide');
	});
 

	// when activity is selected
	$('input[name=activity]').on('typeahead:selected', function(evt, item) {
		$('#activityId').val(item.id);
		/* if(alreadySelectedSports[$("#activityId").val()] != undefined) {} */
	});
});

//Fetch user interests
$.ajax({
	type : "GET",
	contentType : "application/json",
	url : DOMAIN + "userinterest",
	success : function(e) {

		$("table tbody#user-intrested-activity").html("");
		if (e == "") {
			var data = {
				msg : "It seems that you have not registered for any activity. Please add some activities to your profile."
			};
			$("#noActivitycontent").tmpl(data).appendTo("table tbody#user-intrested-activity");
		} else {
			
			// get all caregories available for users
			// iterate on available activities
			$("#activitycontent").tmpl(e).appendTo("table tbody#user-intrested-activity");
		}
		
		//TODO to merge both
		$.each(e, function(index, value) {
			alreadySelectedSports[value.id] = value.id;
		});
		
/*		var allActivities = store.get("allActivityList");
		$.each(allActivities, function(index, activityValue) {
			$.each(e, function(index, value) {
				if(activityValue.activityName == value.sportName){
					if(alreadySelectedCategories.indexOf(activityValue.categoryName) == -1) {
						alreadySelectedCategories.push(activityValue.categoryName);	
					}else {
						
					}
				}
				
			});
		});
		
		//alreadySelectedCategories.push("Music");	
		$("#activityPreferecesContent").tmpl(alreadySelectedCategories).appendTo("div#activityPrefContent");
		$("#activityPreferecesBar").tmpl(alreadySelectedCategories).appendTo("div#activitypref ul");*/
		
		
		
		/*$('#activitypref li a').on("click", function (e) {
			  e.preventDefault();
			//  $(this).tab('show');
			 // alert("hi" + this.id);
			  //this.tab('show');
		});*/
			
		
		$("#activitypref ul.nav").tabs();
		//$("#activitycontent").tmpl(e).appendTo("table tbody#user-intrested-activity");
 
	},
	failure : function(data) {
				alert(data);
	},
	dataType : "JSON"
});


function isValidActivity(sportName) {
	
	var activityId =  $("#activityId").val();
	if(activityId == "") {
		return false;
	}
	var activities = store.get("allActivityList");
	
	for ( var i = 0; i < activities.length; i++) {
		var activity = activities[i];
		if(sportName == activity.activityName && activityId == activity.activityId  /* && $("#category")[0].selectedOptions[0].value == activity.categoryId*/) {
			return true;
		}
	}
	 
	
	return false;
	
}

/*
 * Fuel UX Radio
 * https://github.com/ExactTarget/fuelux
 *
 * Copyright (c) 2012 ExactTarget
 * Licensed under the MIT license.
 */

(function($)
{
	var old = $.fn.radio;

	// RADIO CONSTRUCTOR AND PROTOTYPE

	var Radio = function (element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn.radio.defaults, options);

		// cache elements
		this.$label = this.$element.parent();
		this.$icon = this.$label.find('i');
		this.$radio = this.$label.find('input[type=radio]');
		this.groupName = this.$radio.attr('name');

		// set default state
		this.setState(this.$radio);

		// handle events
		this.$radio.on('change', $.proxy(this.itemchecked, this));
	};

	Radio.prototype = {

		constructor: Radio,

		setState: function ($radio) {
			$radio = $radio || this.$radio;

			var checked = $radio.is(':checked');
			var disabled = !!$radio.prop('disabled');

			this.$icon.removeClass('checked disabled');
			this.$label.removeClass('checked');

			// set state of radio
			if (checked === true) {
				this.$icon.addClass('checked');
				this.$label.addClass('checked');
			}
			if (disabled === true) {
				this.$icon.addClass('disabled');
			}
		},

		resetGroup: function () {
			var group = $('input[name="' + this.groupName + '"]');

			// reset all radio buttons in group
			group.next().removeClass('checked');
			group.parent().removeClass('checked');
		},

		enable: function () {
			this.$radio.attr('disabled', false);
			this.$icon.removeClass('disabled');
		},

		disable: function () {
			this.$radio.attr('disabled', true);
			this.$icon.addClass('disabled');
		},

		itemchecked: function (e) {
			var radio = $(e.target);

			this.resetGroup();
			this.setState(radio);
		},

		check: function () {
			this.resetGroup();
			this.$radio.prop('checked', true);
			this.setState(this.$radio);
		},

		uncheck: function () {
			this.$radio.prop('checked', false);
			this.setState(this.$radio);
		},

		isChecked: function () {
			return this.$radio.is(':checked');
		}
	};


	// RADIO PLUGIN DEFINITION

	$.fn.radio = function (option) {
		var args = Array.prototype.slice.call( arguments, 1 );
		var methodReturn;

		var $set = this.each(function () {
			var $this   = $( this );
			var data    = $this.data( 'radio' );
			var options = typeof option === 'object' && option;

			if( !data ) $this.data('radio', (data = new Radio( this, options ) ) );
			if( typeof option === 'string' ) methodReturn = data[ option ].apply( data, args );
		});

		return ( methodReturn === undefined ) ? $set : methodReturn;
	};

	$.fn.radio.defaults = {};

	$.fn.radio.Constructor = Radio;

	$.fn.radio.noConflict = function () {
		$.fn.radio = old;
		return this;
	};


	// RADIO DATA-API

	$(function () {
		$(window).on('load', function () {
			//$('i.radio').each(function () {
			$('.radio-custom > input[type=radio]').each(function () {
				var $this = $(this);
				if ($this.data('radio')) return;
				$this.radio($this.data());
			});
		});
	});
	
	
	
	
})(jQuery);





var alreadySelectedSports = {};
var alreadySelectedCategories = [];
getAllSports();