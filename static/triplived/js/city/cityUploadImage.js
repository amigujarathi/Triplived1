var currentCity = "";
var cityId = "";
var attractionId = "";
var cityName = "";

var dataaa = [];
var transportType = "";

getMsgTime = function(msgTimeStamp) {
	if (msgTimeStamp !== undefined && msgTimeStamp.toString().length == 10) {
		var monthNames = [ "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" ];

		var myVar = (msgTimeStamp * 1000).toString();
		var value = new Date(parseInt(myVar.replace(/(^.*\()|([+-].*$)/g, '')));

		var monthName = monthNames[value.getMonth()].substring(0, 3);

		var msgDate = value.getDate() + " " + monthName + ","
				+ value.getFullYear() + " | " + value.getHours() + ":"
				+ value.getMinutes();

		return msgDate;
	}
}

function loadAttractionScreen() {
	document.location.href = DOMAIN + "attraction/progress?tripId=" + tripId
			+ "&cityId=" + cityId + "&subTripId=" + subTripId;
}
function loadHotelScreen() {
	document.location.href = DOMAIN + "hotel/progress?tripId=" + tripId
			+ "&cityId=" + cityId + "&subTripId=" + subTripId;
}
function loadActivityScreen() {
	document.location.href = DOMAIN + "activity/progress?tripId=" + tripId
			+ "&cityId=" + cityId + "&subTripId=" + subTripId;
}
function loadCityScreen() {
	document.location.href = DOMAIN + "city/progress?tripId=" + tripId;
}

function enableAddImageButton() {
	$("#uploadPhotoId").attr('disabled', false);
}

$(document)
		.ready(
				function() {

					$("#addAttractionId").hide();
					$("#addHotelId").hide();
					$("#addActivityId").hide();
					$("#addDestinationCityId").hide();

					$('#cityType1').prop('disabled', true);

					// create structure of the ongoing trip
					var html = '<div class="container"><div class="row" style="background-color:yellow"><div class="col-lg-12">';

					jQuery('#datetimepicker').datetimepicker();

					$("#existing-images-div").hide();

					var citiesDataSource = new Bloodhound({
						datumTokenizer : Bloodhound.tokenizers.obj
								.whitespace('value'),
						queryTokenizer : Bloodhound.tokenizers.whitespace,
						remote : 'citySuggest/%QUERY'
					});

					citiesDataSource.initialize();

					$('#cityName')
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
										$("#city_desc").val(
												item.cityDescription);
										if (item.landingImagePath != "") {

											 $("#existing-images-div").show();
											var imgSrc = document.getElementById("images-div");
											imgSrc.src = "http://119.81.58.12"+item.landingImagePath; 
											
										} else {

											$("#existing-images-div").hide();
											$("#existing_images").text('');
										}
									})

					$('#fileupload')
							.fileupload({
								dataType : 'json',
								acceptFileTypes : /(\.|\/)(jpg)$/i,
								url : DOMAIN + 'cityImageUploadPage/upload',
								singleFileUploads : false,
								formData : function(form) {

									$('<input />').attr({
										type : 'hidden',
										id : 'cityId',
										name : 'cityId',
										value : cityId,
									}).appendTo('form');
									$('<input />').attr({
										type : 'hidden',
										id : 'cityName',
										name : 'cityName',
										value : cityName,
									}).appendTo('form');

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
															} else {

															}
															return file;
														});

										$("#uploadPhotoId").attr('disabled',
												true);

									}).bind(
									'fileuploaddone',
									function(e, data) {
										if (alert('Image Uploaded !!')) {
										} else
											window.location.reload();

										if (cityType == 'Source') {

											$("#addDestinationCityId").show();
											$('#cityName').prop('disabled',
													true);
											$('#message')
													.prop('disabled', true);
											$('#submitBtnId').prop('disabled',
													true);

										}
										if (cityType == 'Destination') {
											$("#addAttractionId").show();
											$("#addHotelId").show();
											// $("#addActivityId").show();
											$('#cityName').prop('disabled',
													true);
											$('#message')
													.prop('disabled', true);
											$('#submitBtnId').prop('disabled',
													true);
										}
									});
				});

function addCityDescription() {
	var cityName = $("#cityName").val();
	var cityDesc = $("#city_desc").val();
	data = {};
	data.cityName = cityName;
	data.cityDesc = cityDesc;
	data.cityId = cityId;
	$('#responsive').modal('show');
	$.ajax({
		type : "POST",
		url : DOMAIN + 'cityImageUploadPage/updateCityDescription',
		data : data,
		success : function(data) {
			$('#responsive').modal('hide');
			alert("Description succesfully updated");
			$("#cityName").val("");
			$("#city_desc").val("");
		},
		error : function(xhr, status, error) {
			$('#responsive').modal('hide');
			alert("Something went wrong. Please try again!");
		}
	});
}

function submitForm() {

	if ($(document).find('.files').find(".start") != undefined
			&& $(document).find('.files').find(".start").length > 0) {

		$(document).find('.files').find(".start").click();

	} else {

		var data = {};

		data.cityCode = cityId;

		$.post(DOMAIN + 'cityImageUploadPage/upload', data).done(
				function(data) {
					if (cityType == 'Source') {

						$("#addDestinationCityId").show();
					}
					if (cityType == 'Destination') {
						$("#addAttractionId").show();
						$("#addHotelId").show();
						// $("#addActivityId").show();
					}
					$('#cityName').prop('disabled', true);
					$('#message').prop('disabled', true);
					$('#submitBtnId').prop('disabled', true);
					$("#uploadPhotoId").attr('disabled', true);
					$("#existing-images-div").hide();
					$("#existing_images").text('')
				});
	}
}