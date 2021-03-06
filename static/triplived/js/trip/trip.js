var currentCity = "";
var cityId = "";
var attractionId = "";

var dataaa = [];

var tripDetails = {};

function loadCity() {
 		document.location.href = DOMAIN+"city/progress?tripId="+tripDetails;
			+ tripDetails;
}

$("#showLoaderId").hide();

$(document)
		.ready(
				function() {

					$("#continueTripId").hide();

					jQuery('#datetimepicker').datetimepicker();

					$("#existing-images-div").hide();

					var citiesDataSource = new Bloodhound({
						datumTokenizer : Bloodhound.tokenizers.obj
								.whitespace('value'),
						queryTokenizer : Bloodhound.tokenizers.whitespace,
						remote : '../citySuggest/%QUERY'
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
										currentCity = item.cityname;

										$
												.ajax({
													type : "GET",
													contentType : "application/json; charset=utf-8",
													url : '../attractionSuggest'
															+ "/"
															+ cityId
															+ "/*",
													success : function(data) {
														dataaa = {};
														var selectList = $("#attractionName");
														$("#attractionName")
																.empty();
														for ( var i = 0; i < data.length; i++) {
															var option = document
																	.createElement("option");
															option.value = data[i].id;
															option.text = data[i].attractionName;
															selectList[0].options
																	.add(option);

															dataaa[data[i].id] = data[i];

														}

													},
													dataType : "JSON"
												});

									})

					$("#attractionName")
							.on(
									'change',
									function() {

										if (this.value == "") {
											return;
										}
										attractionId = this.value;

										var c = dataaa[attractionId];

										$("#latitude").val("");
										$("#longitude").val("");
										$("#message").val("");
										$('#longitude').prop('readonly', false);
										$('#latitude').prop('readonly', false);
										$("#existing-images-div").hide();

										$("#existing_images").text('')

										if (c.images != "") {

											var rows = $();
											$
													.each(
															c.images,
															function(index, img) {
																var row = $('<tr class="template-download">'
																		+ '<td class="name"></td>'
																		+ '<td class="largeImage"></td>'
																		+ '<td class="imagename"></td>'
																		+ '</tr>');
																row
																		.find(
																				'.name')
																		.append(
																				"<img height='300px' width ='300px' src =http://119.81.58.12"
																						+ img
																								.split("___")[0]
																						+ " />");
																row
																		.find(
																				'.largeImage')
																		.append(
																				"<a target ='blank' href=http://119.81.58.12"
																						+ img
																								.split("___")[0]
																						+ " > Large Image</a>");
																row
																		.find(
																				'.imagename')
																		.text(
																				img
																						.split("___")[1]);

																rows = rows
																		.add(row);
															});

											$("#totalimages").val(c.length);
											$("#existing-images-div").show();
											$("#existing_images").append(rows);
										} else {

											$("#existing-images-div").hide();
											$("#existing_images").text('');
										}
										// alert(c.latitude + "-"+c.longitude);

									});

					$('#fileupload')
							.fileupload({
								dataType : 'json',
								acceptFileTypes : /(\.|\/)(jpg)$/i,
								url : DOMAIN + 'trip/ajax-post',
								singleFileUploads : false,
								formData : function(form) {
									return form.serializeArray();
								}
							})
							.on('fileuploadprocessalways', function(e, data) {
								var currentFile = data.files[data.index];
								if (data.files.error && currentFile.error) {
									// there was an error, do something about it
									alert(currentFile.error);
								}
							})
							.bind(
									'fileuploadadd',
									function(e, data) {
										var currentfiles = [];
										$(this).fileupload('option').filesContainer
												.children()
												.each(
														function() {
															currentfiles
																	.push($
																			.trim($(
																					'.name',
																					this)
																					.text()));
														});

										data.files = $
												.map(
														data.files,
														function(file, i) {
															if ($
																	.inArray(
																			file.name,
																			currentfiles) >= 0) {
																alert("File "
																		+ file.name
																		+ " is already present.");
																$(
																		'.fileupload-process')
																		.hide();
																return null;
															}
															return file;
														});
									}).bind(
									'fileuploaddone',
									function(e, data) {
										tripDetails = data;
										$('#presentationId').hide();
										$('#tripName').prop('disabled', true);
										$('#message').prop('disabled', true);
										$("#continueTripId").show();
										$('#submitBtnId')
												.prop('disabled', true);
									});

					$("#addCityId")
							.on(
									'click',
									function() {
										// alert(tripDetails);
		 		document.location.href = DOMAIN+"city/progress?tripId="+tripDetails;
												+ tripDetails;
									});
				});

function submitForm() {
	
	if($("#tripName").val() == ""){
	   	 $('.fileupload-process').hide();
	   	 alert("Please enter trip name");
	   	 return null;
	}
	
	if($("#datetimepicker").val() == ""){
	   	 $('.fileupload-process').hide();
	   	 alert("Please enter trip name");
	   	 return null;
	}

	if ($(document).find('.files').find(".start") != undefined
			&& $(document).find('.files').find(".start").length > 0) {

		$(document).find('.files').find(".start").click();

	} else {

		var data = {};
		data.tripName = $("#tripName").val();
		data.tripDescription = $("#message").val();
		data.timeStamp = $("#datetimepicker").val();
		$("#showLoaderId").show();
		$.post(DOMAIN + 'trip/ajax-post', data).done(function(data) {
			$("#showLoaderId").hide();
			tripDetails = data;
			$('#presentationId').hide();
			$('#tripName').prop('disabled', true);
			$('#message').prop('disabled', true);
			$("#continueTripId").show();
			$('#submitBtnId').prop('disabled', true);
		});
	}
}
