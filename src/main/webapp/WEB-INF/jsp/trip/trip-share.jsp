<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:eval expression="trip" var="trip"></spring:eval>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>TripLived : ${trip.tripName}: by ${trip.user.name}</title>

<meta property="og:title" content="${trip.tripName}" />
<meta property="og:site_name" content="TripLived"/>
<meta property="og:url" content="http://www.triplived.com/triplived/trip/viewTrip/${trip.tripId}" />
<c:if test="${not empty trip.additionalProperties.tripSummary}">
	<meta property="og:description" content="${trip.additionalProperties.tripSummary}" />
</c:if>

<meta property="fb:app_id" content="529598833847568" />
<meta property="og:image" content="http://www.triplived.com/triplived/trip/createHeaderImage/${trip.tripId}" />

<meta property="og:type" content="article" />
<meta property="article:author" content="${trip.user.additionalProperties.userFbId}" />
<meta property="article:publisher" content="https://www.facebook.com/triplived" />
<meta property="article:section" content="Travel" />

<c:forEach items="${trip.tripCategories}" var="cat">
	<meta property="article:tag" content="${cat}" />
</c:forEach>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
	crossorigin="anonymous">

<link href='https://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>

<link rel="icon"
	href="http://www.triplived.com/static/tl/images/favicon.png">

<style>
.embed-container {
	position: relative;
	padding-bottom: 56.25%;
	height: 0;
	overflow: hidden;
	max-width: 100%;
}

.embed-container iframe,.embed-container object,.embed-container embed {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}

@media only screen and (min-width:750px) and (max-width: 992px) {
	.container {
		width: 750px !important;
	}
}

@media only screen and (min-width:992px) and (max-width: 1596px) {
	.container {
		width: 750px !important;
	}
}

.DocumentItem {
	border: 1px solid black;
	padding: 0;
}

.list-inline {
	white-space: nowrap;
}

span.circ {
	width: 5px;
	height: 5px;
	background: black;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	display: inline-block;
	margin: 4px;
}

span.circle {
	width: 20px;
	height: 20px;
	background: black;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	border-radius: 8px;
	display: inline-block;
}

body {
	font-family: Ubuntu;
}

.tags {
	margin-left: 2px;
	border-radius: 4px;
	border-style: solid;
	border-width: 2px;
	border-color: #ffde44;
	box-sizing: border-box;
	background-color: rgba(0, 0, 0, 0);
	width: 66px;
	color: #ffffff;
	font-size: 13px;
	padding: 3px;
	text-align: left;
}

span.src_dest {
	color: #ffffff;
	font-size: 30px;
	font-weight: 700;
	text-align: left;
	text-shadow: 3px 5px 6px rgba(0, 0, 0, 0.24);
}

span.title {
	color: #ffffff;
	font-size: 28px; /* Approximation due to font substitution */
	font-weight: 400;
	line-height: 30px; /* Approximation due to font substitution */
	text-align: left;
}

span.user {
	color: #ffffff;
	font-size: 16px; /* Approximation due to font substitution */
	font-weight: 400;
	line-height: 20px; /* Approximation due to font substitution */
	text-align: left;
}

div.destinationText {
	background-color: white;
	color: black;
	text-shadow: none;
	font-family: Helvetica;
	color: #000000;
	font-size: 24px;
	font-weight: 400;
	line-height: 55px;
	text-align: center;
}

span.days_in {
	font-family: Ubuntu;
	color: rgba(52, 73, 94, 0.5);
	font-size: 25px; /* Approximation due to font substitution */
	font-weight: bold;
	line-height: 37px; /* Approximation due to font substitution */
	text-align: center;
	padding-bottom: 20px;
}

div.summary {
	font-family: Ubuntu;
	color: #34495e;
	font-size: 25px; /* Approximation due to font substitution */
	font-weight: 500;
	line-height: 37px; /* Approximation due to font substitution */
	text-align: center;
	padding-top: 15px;
	font-style: italic;
}

div.tripsummary {
	border-top: 3px solid #ffde44;
	border-bottom: 3px solid #ffde44;
	text-shadow: none;
	color: black;
	background-color: #FFFCEC;
}

div.carousel-caption {width; 100%;
	text-align: left;
	left: 0%;
	right: 0%;
	padding-bottom: 0%;
	padding-top: 0%;
	bottom: 0px;
}

div#navlist {
	overflow-x: scroll;
	overflow-y: hidden;
	width: 100%;
	padding: 0 15px;
}

div.gradi {
	display: inline-block;
	background: -moz-linear-gradient(top, rgba(0, 0, 0, 0) 0%,
		rgba(0, 0, 0, 0.65) 100%); /* FF3.6+ */
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(0, 0
		, 0, 0.65)), color-stop(100%, rgba(0, 0, 0, 0)));
	/* Chrome,Safari4+ */
	background: -webkit-linear-gradient(top, rgba(0, 0, 0, 0) 0%,
		rgba(0, 0, 0, 0.65) 100%); /* Chrome10+,Safari5.1+ */
	background: -o-linear-gradient(top, rgba(0, 0, 0, 0) 0%,
		rgba(0, 0, 0, 0.65) 100%); /* Opera 11.10+ */
	background: -ms-linear-gradient(top, rgba(0, 0, 0, 0) 0%,
		rgba(0, 0, 0, 0.65) 100%); /* IE10+ */
	background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0%,
		rgba(0, 0, 0, 0.65) 100%); /* W3C */
	filter: progid:DXImageTransform.Microsoft.gradient(    startColorstr='#a6000000',
		endColorstr='#00000000', GradientType=0); /* IE6-9 */
}

body {
background-color: rgb(78, 78, 78);
}
</style>

</head>
<body>
	<div class="container">
		<div class="row">

			<div class="col-md-12" style="padding: 0px">

				<div class="col-md-12 carousel-caption "
					style="padding: 0px; top: 0">
					<span class="logo pull-left"> <img
						src="http://www.triplived.com/static/tl/images/logo_n.png"
						width="50" style="margin: 5px" />
					</span>
				</div>

				<div class="image-container">
					<img src="${trip.tripCoverUri}" class="img-responsive"
						style="width: 100%; max-height: 100%" />
				</div>

				<div class="carousel-caption gradi">

					<div class="col-md-12" style="padding-bottom: 2px">
						<span> <img
							src="https://graph.facebook.com/${trip.user.additionalProperties.userFbId}/picture?type=large"
							class="img-responsive img-circle"
							style="height: 50px; width: 50px; display: inline">
						</span>

						<%--
							<span style="">  
							<c:forEach	items="${trip.additionalProperties.travellers}" var="traveller">

								<c:choose>
								<c:when test="${traveller.image.url ne null}">
									<img src="${traveller.image.url}" class="img-responsive img-circle"	style="height: 35px; width: 35px; display: inline">
								</c:when>
								<c:otherwise>
									<span style="background-color:green; " class="circle">
										<c:set var="name" value="${fn:substring(traveller.name, 0, 1)}" />
										${name}
									</span>
								</c:otherwise>
								</c:choose>
							</c:forEach>
							</span>
--%>

					</div>
					<div class="col-md-12">
						<span class="title">${trip.tripName}</span> <span class="user">by
							${trip.user.name}</span>
					</div>

					<div class="col-md-12" style="padding-bottom: 10px">
						<c:if test="${trip.additionalProperties.given_src_name ne null}">
							<span class="src_dest">
								${trip.additionalProperties.given_src_name} to
								${trip.additionalProperties.given_dest_name}</span>
						</c:if>
					</div>
					<div class="row" style="margin: 0px; padding-bottom: 20px">
						<div class="col-md-12">
							<c:forEach items="${trip.tripCategories}" var="cat">
								<span class="tags"> ${cat} </span>
							</c:forEach>
							<c:if test="${trip.additionalProperties.likes gt 0}">
								<span class="pull-right" style="padding-right: 12px">Liked
									by <fmt:formatNumber value="${trip.additionalProperties.likes}"  maxFractionDigits="0" />  person</span>
							</c:if>
						</div>
					</div>
				</div>

			</div>
			<div class="col-md-12 destinationText">
				<div id="navlist">
					<ul class="list-inline">
						<c:forEach items="${trip.subTrips}" var="subtrip" varStatus="stat">
							<li><span>${subtrip.toCityDTO.cityName}</span></li>

							<c:if test="${!stat.last}">
								<li><span class="circ"></span></li>
							</c:if>

						</c:forEach>
					</ul>
				</div>
			</div>


			<div class="col-md-12  tripsummary">
				<div class="summary" style="padding-bottom: 10px;">
					<c:if test="${not empty trip.additionalProperties.tripSummary}">
						${trip.additionalProperties.tripSummary}
						<br />
					</c:if>

					<!-- 
					
					  SimpleDateFormat df = new SimpleDateFormat("MMMM");//TODO to move up
            String monthName = df.format(firstDate)
            int days = CosCalendarUtils.CountDays(firstDate, lastDay);
            monthSummary.setText(days + " days" + " " + "in " + monthName);
            
					 -->


					<c:forEach items="${trip.subTrips}" var="subtrip" varStatus="stat">

						<c:if test="${stat.first}">
							<c:set var="first" value="${subtrip.toCityDTO.timestamp}"
								scope="request" />
						</c:if>

						<c:if test="${stat.last}">
							<c:set var="last" value="${subtrip.toCityDTO.timestamp}"
								scope="request" />

						</c:if>

					</c:forEach>


					<jsp:useBean id="firstDate" class="java.util.Date" />
					<jsp:useBean id="lastDate" class="java.util.Date" />
					<jsp:setProperty name="firstDate" property="time" value="${first}" />
					<jsp:setProperty name="lastDate" property="time" value="${last}" />

					<spring:eval
						expression="T(com.triplived.util.TripLivedUtil).CountDays(firstDate, lastDate)"
						var="daysin" />

					<span class="days_in"> ${daysin} days in <fmt:formatDate
							value="${firstDate}" pattern="MMM" />

					</span>
				</div>
			</div>
			<%--
			<c:if test="${trip.tripVideoUrl ne null}">
				<div class="col-md-12 "
					style="padding-top: 12px; text-align: center">
					<div>
						<div class='embed-container'>
							<iframe src='${trip.tripVideoUrl}?autoplay=0' frameborder='0'
								allowfullscreen></iframe>
						</div>
					</div>
				</div>
			</c:if>
--%>

		</div>

		<div class="row" style="background-color:white; padding-bottom: 20px">
				<div class="center-block summary" style="margin: 0 auto; font-size: 22px;
    line-height: 26px;
    font-style: normal;
    padding-bottom: 17px; padding-left:5px;padding-right:5px;">View the entire timeline and trip video on the TripLived Android application</div> 
				<div class="" style="margin: 0 auto;"> 
				  <a
					href="https://play.google.com/store/apps/details?id=com.cos.triplived"
					class="googleplay-btn"> <img
						src="http://triplived.com/static/tl/images/play-store-image-small.png"
						class='img-responsive center-block ' alt="android">
				</a>
				</div>

		</div>


	</div>



	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"
		integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ=="
		crossorigin="anonymous"></script>
</body>
</html>