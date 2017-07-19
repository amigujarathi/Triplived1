var currentCity = "";
var cityId = "";
var attractionId = "";
var currentCityDescription = "";

var attractionData = [];
var container = "";
var editor = "";

$(document)
		.ready(
				function() {

					/* $("#cityDescArea").hide(); */

					tinymce
							.init({
								selector : "textarea.textselect",
								forced_root_block : '',
								plugins : [
										"advlist autolink lists link image charmap print preview hr anchor pagebreak",
										"searchreplace wordcount visualblocks visualchars code fullscreen",
										"insertdatetime media nonbreaking save table contextmenu directionality",
										"template paste textcolor colorpicker textpattern" ],
								toolbar1 : "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
								toolbar2 : "preview media | forecolor backcolor"
							});
					/*
					 * tinymce.activeEditor.getContent(); // Get the raw
					 * contents of the currently active editor
					 * tinymce.activeEditor.getContent({format : 'raw'});
					 */
					// Get content of a specific editor:
					// editable grid code
				});

// this function is used to get the reviews of the trip corresponding to the
// trip ID
function getAdminReviews() {
	var tripId = $("#tripId").val();
	if (tripId == "") {
		alert("please enter trip ID");
	} else {
		$('#responsive').modal('show');
		setReviewDataInTable(tripId);
		$('#responsive').modal('hide');
	}
}

function setReviewDataInTable(tripId) {
	$("#grid").empty();
	$(function() {
		$("#grid")
				.shieldGrid(
						{
							dataSource : {
								events : {
									error : function(event) {
										if (event.errorType == "transport") {
											// transport error is an ajax error;
											// event holds the xhr object
											if (event.error.statusText == "OK") {
												alert("Data updated!");
											} else {
												alert("Please login or enter correct ID!");
											}
											// reload the data source if the
											// operation that failed was save
											if (event.operation == "save") {
												this.read();
											}
										} else {
											// other data source error -
											// validation, etc
											alert("please login or enter correct ID");
										}
									}
								},
								remote : {
									read : {
										type : "GET",
										url : DOMAIN
												+ 'tripReviewComments/tripReviews'
												+ "/" + tripId,
										dataType : "json"
									},
									modify : {
										create : function(items, success, error) {
											$('#responsive').modal('show');
											items[0].data.tripId = tripId;
											$
													.ajax(
															{
																type : "POST",
																url : DOMAIN
																		+ 'tripReviewComments/saveTripReviews',
																dataType : "json",
																contentType : "application/json",
																data : JSON
																		.stringify(items[0].data)
															}).then(success,
															error);
											$('#responsive').modal('hide');
										}
									}
								},
								schema : {
									fields : {
										id : {
											path : "id",
											type : Number
										},
										tripId : {
											path : "tripId",
											type : String
										},
										reviewer_id : {
											path : "reviewer_id",
											type : String
										},
										entityName : {
											path : "entityName",
											type : String
										},
										review : {
											path : "review",
											type : String
										},
										status : {
											path : "status",
											type : String
										},
										remark : {
											path : "remark",
											type : String
										},
										updatedDate : {
											path : "updatedDate",
											type : String
										}
									}
								}
							},
							sorting : true,
							rowHover : false,
							columns : [
									{
										field : "reviewer_id",
										title : "Reviewer Name",
										width : "80px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "entityName",
										title : "Entity Name",
										width : "80px",
										attributes : {
											style : "text-align: center;"
										}
									},
									{
										field : "review",
										title : "Reviews",
										width : "120px",
										attributes : {
											style : "text-align: center;"
										}
									},
									{
										field : "remark",
										title : "Remark",
										width : "200px"
									},
									{
										field : "updatedDate",
										title : "Date",
										width : "100px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									} ],
							toolbar : [ {
								buttons : [ {
									commandName : "insert",
									caption : "Insert"
								}, {
									commandName : "save",
									caption : "Save"
								}, {
									commandName : "cancel",
									caption : "Cancel"
								} ],
								position : "top"
							} ],
							editing : {
								enabled : true,
								type : "row"
							}
						});
	});
}