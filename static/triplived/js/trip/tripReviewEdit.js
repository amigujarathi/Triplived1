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
function getAttractionReviews() {
	var tripId = $("#tripId").val();
	if (tripId == "") {
		alert("please enter trip ID");
	} else {
		$('#responsive').modal('show');
		setAttractionDataInTable(tripId);
		$('#responsive').modal('hide');
	}
}

function getHotelReviews() {
	var tripId = $("#tripId").val();
	if (tripId == "") {
		alert("please enter trip ID");
	} else {
		$('#responsive').modal('show');
		setHotelDataInTable(tripId);
		$('#responsive').modal('hide');
	}
}

function setAttractionDataInTable(tripId) {
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
												+ 'tripReviewEdit/attractionReviews'
												+ "/" + tripId,
										dataType : "json"
									},
									modify : {
										create : function(items, success, error) {
											$('#responsive').modal('show');
											var newItem = items[0];
											$
													.ajax({
														type : "GET",
														url : DOMAIN
																+ 'tripReviewEdit/attractionReviews'
																+ "/" + tripId,
														dataType : "json",
														data : newItem.data,
														complete : function(xhr) {
															if (xhr.readyState == 4) {
																if (xhr.status == 201) {
																	// update
																	// the id of
																	// the
																	// newly-created
																	// item with
																	// the
																	// one
																	// returned
																	// from the
																	// server in
																	// the
																	// Location
																	// hader url
																	var location = xhr
																			.getResponseHeader("Location");
																	newItem.data.Id = +location
																			.replace(
																					/^.*?\/([\d]+)$/,
																					"$1");
																	success();
																	return;
																}
															}
															error(xhr);
														}
													});
											$('#responsive').modal('hide');
										},
										update : function(items, success, error) {
											$('#responsive').modal('show');
											items[0].data.tripId = tripId;
											$
													.ajax(
															{
																type : "POST",
																url : DOMAIN
																		+ 'tripReviewEdit/saveAttractionReviews',
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
										attractionDetailsId : {
											path : "attractionDetailsId",
											type : String
										},
										attractionId : {
											path : "attractionId",
											type : String
										},
										attractionName : {
											path : "attractionName",
											type : String
										},
										review : {
											path : "review",
											type : String
										},
										suggestion : {
											path : "suggestion",
											type : String
										},
										timestamp : {
											path : "timestamp",
											type : String
										},
										curate : {
											path : "curate",
											type : String
										}
									}
								}
							},
							sorting : true,
							rowHover : false,
							columns : [
									{
										field : "attractionId",
										title : "Attraction ID",
										width : "50px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "attractionName",
										title : "Attraction Name",
										width : "120px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "review",
										title : "Review",
										width : "200px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "suggestion",
										title : "Suggestion",
										width : "200px"
									},
									{
										field : "timestamp",
										title : "TimeStamp",
										width : "100px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "curate",
										title : "Curated",
										width : "60px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									}, {
										width : 180,
										title : " ",
										buttons : [ {
											commandName : "edit",
											caption : "Edit"
										} ]
									} ],
							editing : {
								enabled : true,
								type : "row"
							}
						});
	});
}

function setHotelDataInTable(tripId) {
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
												+ 'tripReviewEdit/hotelReviews'
												+ "/" + tripId,
										dataType : "json"
									},
									modify : {
										create : function(items, success, error) {
											var newItem = items[0];
											$
													.ajax({
														type : "GET",
														url : DOMAIN
																+ 'tripReviewEdit/hotelReviews'
																+ "/" + tripId,
														dataType : "json",
														data : newItem.data,
														complete : function(xhr) {
															if (xhr.readyState == 4) {
																if (xhr.status == 201) {
																	// update
																	// the id of
																	// the
																	// newly-created
																	// item with
																	// the
																	// one
																	// returned
																	// from the
																	// server in
																	// the
																	// Location
																	// hader url
																	var location = xhr
																			.getResponseHeader("Location");
																	newItem.data.Id = +location
																			.replace(
																					/^.*?\/([\d]+)$/,
																					"$1");
																	success();
																	return;
																}
															}
															error(xhr);
														}
													});
										},
										update : function(items, success, error) {
											$('#responsive').modal('show');
											items[0].data.tripId = tripId;
											$
													.ajax(
															{
																type : "POST",
																url : DOMAIN
																		+ 'tripReviewEdit/saveHotelReviews',
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
										hotelDetailsId : {
											path : "hotelDetailsId",
											type : String
										},
										hotelId : {
											path : "hotelId",
											type : String
										},
										hotelName : {
											path : "hotelName",
											type : String
										},
										review : {
											path : "review",
											type : String
										},
										suggestion : {
											path : "suggestion",
											type : String
										},
										timestamp : {
											path : "timestamp",
											type : String
										},
										curate : {
											path : "curate",
											type : String
										}
									}
								}
							},
							sorting : true,
							rowHover : false,
							columns : [
									{
										field : "hotelId",
										title : "Hotel ID",
										width : "50px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "hotelName",
										title : "Hotel Name",
										width : "120px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "review",
										title : "Review",
										width : "200px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}	
									},
									{
										field : "suggestion",
										title : "Suggestion",
										width : "200px"
									},
									{
										field : "timestamp",
										title : "TimeStamp",
										width : "100px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "curate",
										title : "Curated",
										width : "60px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									}, {
										width : 180,
										title : " ",
										buttons : [ {
											commandName : "edit",
											caption : "Edit"
										} ]
									} ],
							editing : {
								enabled : true,
								type : "row"
							}
						});
	});
}