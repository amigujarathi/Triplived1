var currentCity = "";
var cityId = "";
var attractionId = "";
var hotelId = "";
var currentCityDescription = "";

var attractionData = [];
var hotelData = [];
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
					var citiesDataSource = new Bloodhound({
						datumTokenizer : Bloodhound.tokenizers.obj
								.whitespace('value'),
						queryTokenizer : Bloodhound.tokenizers.whitespace,
						remote : 'citySuggest/%QUERY'
					});

					citiesDataSource.initialize();

					$('#cityname')
							.typeahead({
								hint : true,
								highlight : true,
								minLength : 1
							}, {
								name : 'cities',
								displayKey : 'cityName',
								source : citiesDataSource.ttAdapter()

							})
							.on(
									'typeahead:selected',
									function(evt, item) {
										cityId = item.id;
										currentCity = item.cityName;
										currentCityDescription = item.cityDescription;

										$('#responsive').modal('show');
										$
												.ajax({
													type : "GET",
													contentType : "application/json; charset=utf-8",
													url : 'attractionSuggest'
															+ "/"
															+ cityId
															+ "/*",
													success : function(data) {
														attractionData = {};
														var selectList = $("#attractionName");
														$("#attractionName")
																.empty();

														for (var i = 0; i < data.length; i++) {
															var option = document
																	.createElement("option");
															option.value = data[i].id;
															option.text = data[i].attractionName;
															selectList[0].options
																	.add(option);

															attractionData[data[i].id] = data[i];

														}
														$('#responsive').modal(
																'hide');
													},
													dataType : "JSON"
												});

										// get hotel within city
										$('#responsive').modal('show');
										$
												.ajax({
													type : "GET",
													contentType : "application/json; charset=utf-8",
													url : 'hotelSuggest'
															+ "/" + cityId
															+ "/*",
													success : function(data) {
														hotelData = {};
														var selectList = $("#hotelName");
														$("#hotelName").empty();

														for (var i = 0; i < data.length; i++) {
															var option = document
																	.createElement("option");
															option.value = data[i].id;
															option.text = data[i].name;
															selectList[0].options
																	.add(option);

															hotelData[data[i].id] = data[i];

														}
														$('#responsive').modal(
																'hide');
													},
													dataType : "JSON"
												});

									});
					$("#attractionName").on('change', function() {

						if (this.value == "") {
							return;
						}
						attractionId = this.value;
						
					});
					$("#hotelName").on('change', function() {

						if (this.value == "") {
							return;
						}
						hotelId = this.value;
						
					});

				});

// this function is used to get the reviews of the trip corresponding to the
// trip ID
function getAttractionReviews() {
	if (attractionId == "") {
		alert("please select attraction");
	} else {
		$('#responsive').modal('show');
		setAttractionDataInTable(attractionId);
		$('#responsive').modal('hide');
	}
}

function getHotelReviews() {
	if (hotelId == "") {
		alert("please select hotel");
	} else {
		$('#responsive').modal('show');
		setHotelDataInTable(hotelId);
		$('#responsive').modal('hide');
	}
}

function setAttractionDataInTable(attractionId) {
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
												+ 'tripEntityCuration/attractionReviews'
												+ "/" + attractionId,
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
																+ 'tripEntityCuration/attractionReviews'
																+ "/" + attractionId,
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
											$
													.ajax(
															{
																type : "POST",
																url : DOMAIN
																		+ 'tripEntityCuration/saveAttractionReviews',
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
										attractionId : {
											path : "attractionId",
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
										status : {
											path : "status",
											type : Array
										},
										source : {
											path : "source",
											type : String
										},
										updateDate : {
											path : "updateDate",
											type : String
										},
										curateBy : {
											path : "curateBy",
											type : String
										}
									}
								}
							},
							sorting : true,
							rowHover : false,
							columns : [
									{
										field : "tripId",
										title : "Trip ID",
										width : "50px",
										attributes : {
											style : "text-align: center;pointer-events: none;"
										}
									},
									{
										field : "review",
										title : "Review",
										width : "200px",
										attributes : {
											style : "text-align: center;"
										}
									},
									{
										field : "suggestion",
										title : "Suggestion",
										width : "200px"
									},
									{
										field : "status",
										title : "Status",
										width : "140px",
										attributes : {
											style : "text-align: center;"
										},
										 columnTemplate: function (cell, item) {
						                        $('<select />')
						                            .appendTo(cell)
						                            .shieldDropDown({
						                                dataSource: {
						                                    data: ["Active","InActive"]
						                                },
						                                valueTemplate: function(value) {
						                                	if(value == "Active"){
						                          
						                                    return "A";
						                                    }
						                                	else if(value == "InActive"){
						                                		
						                                		return "I";
						                                		}
						                                },
						                                value: (item["rating"])
						                            });
						                    }
									},
									{
										field : "source",
										title : "Source",
										width : "100px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "curateBy",
										title : "Curated By",
										width : "60px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									}, 
									{
										field : "updateDate",
										title : "Updated Date",
										width : "60px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									}, 
									{
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

function setHotelDataInTable(hotelId) {
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
												+ 'tripEntityCuration/hotelReviews'
												+ "/" + hotelId,
										dataType : "json"
									},
									modify : {
										create : function(items, success, error) {
											var newItem = items[0];
											$
													.ajax({
														type : "GET",
														url : DOMAIN
																+ 'tripEntityCuration/hotelReviews'
																+ "/" + hotelId,
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
											$
													.ajax(
															{
																type : "POST",
																url : DOMAIN
																		+ 'tripEntityCuration/saveHotelReviews',
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
										hotelId : {
											path : "hotelId",
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
										status : {
											path : "status",
											type : Array
										},
										source : {
											path : "source",
											type : String
										},
										updateDate : {
											path : "updateDate",
											type : String
										},
										curateBy : {
											path : "curateBy",
											type : String
										}
									}
								}
							},
							sorting : true,
							rowHover : false,
							columns : [
									{
										field : "tripId",
										title : "Trip ID",
										width : "50px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "review",
										title : "Review",
										width : "200px",
										attributes : {
											style : "text-align: center;"
										}
									},
									{
										field : "suggestion",
										title : "Suggestion",
										width : "200px"
									},
									{
										field : "status",
										title : "Status",
										width : "140px",
										attributes : {
											style : "text-align: center;"
										},
										columnTemplate: function (cell, item) {
					                        $('<select />')
					                            .appendTo(cell)
					                            .shieldDropDown({
					                                dataSource: {
					                                    data: ["Active","InActive"]
					                                },
					                                valueTemplate: function(value) {
					                                	if(value == "Active"){
					                                		
					                                    return ("A");}
					                                	else if(value == "InActive"){
					                                		
					                                		return "I";}
					                                },
					                                value: (item["rating"])
					                            });
					                    }
									},
									{
										field : "source",
										title : "Source",
										width : "100px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									},
									{
										field : "curateBy",
										title : "Curated By",
										width : "60px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									}, 
									{
										field : "updateDate",
										title : "Updated Date",
										width : "60px",
										attributes : {
											style : "text-align: center; pointer-events: none;"
										}
									}, 
									{
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