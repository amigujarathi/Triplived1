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
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>TripLived : ${trip.tripName}: by ${trip.user.name}</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.triplived.com/static/triplived-web/css/home/home-style-new.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="http://www.triplived.com/static/tl/images/share/jquery.facedetection.js"></script>

<link rel="icon" href="http://www.triplived.com/static/tl/images/favicon.png">


<style type="text/css">
body {
		 
		 width: 600px;
		height: 312px;
		 
		}
.trend_trip .col-lg-6 .detail {
	width: 40%;
	text-align: center;
	/*background: rgba(255, 222, 68, 0.5); */
	background: rgba(255, 222, 68, 0.8);
	box-shadow: -25px 0px 0px rgba(255, 222, 68, 0.39);
	    padding: 0;
	      padding-left:10px;
        padding-right:10px;
	    
}

.detail .about_trip h3 {
	font-size: 21px;
	line-height: 29px;
	font-family: Tillana;
	font-weight: bold;
	margin: 26% 0 3% 0;
	/* min-height: 150px; */
	border-top: 2px solid #555;
	border-bottom: 2px solid #555;
	padding-top: 6px;
	padding-bottom: 6px;
	color: black;
}

.detail .about_trip p .read_more {
    /* text-decoration: underline; */
    /* color: rebeccapurple; */
   text-decoration: none;
    /* color: rebeccapurple; */
    color: black;
    border-bottom: 1px solid #333;
    vertical-align: super;
    font-weight: bold;
    font-family: Tillana;
}

 

i.right{
    border-top: 10px solid rgba(246, 246, 246, 0.02);
    border-left: 20px solid #333;
    border-bottom: 10px solid rgba(0,0,0,.0001);
    font-size: 0px;
    line-height: 0%;
    width: 0px;
    /* padding-right: 19px; */
    margin-left: 5px;
}
i.left{
	    border-top: 10px solid rgba(246, 246, 246, 0.02);
    border-right: 20px solid #333;
    border-bottom: 10px solid rgba(0,0,0,.0001);
    font-size: 0px;
    line-height: 0%;
    width: 0px;
    /* padding-right: 19px; */
    margin-right: 5px;
}

.detail .about_trip p {
    font-size: 15px;
       line-height: 9px;
    margin-top: 20%;
}

div.tllogo{
    position: absolute;
   	right: 2px;
    top: 0px;
}
.trend_trip {
    
    margin: 0;
    padding-top: 0;
}
.trend_trip .common_box {
    margin: 0;
}
</style>

</head>
<body style="border: 7px inset #c19a3b;">
	<div class="trend_trip" style="">
		<div class="container_">
			<div class="row" style="margin-right: 0px;    margin-left: 0px;">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 " style=" padding-left: 0px; padding-right: 0px" id="box-${trip.tripId}">
					<div class="common_box text-xs-center" >
						<a class="${draftTripClass}" title="${trip.tripName}" href="/trip/${trip.tripId}/${fn:replace(trip.tripName, ' ','-' )}"> 
							<img src="${trip.tripCoverUri}" style="height: 298px;" alt="">
						</a>
						<section class="detail">
							<%-- <section class="user">
							<!-- <span class="img_box"><img src="../../../static/triplived-web/img/home/user_thumbnail.png"></span> -->
							<span class="img_box img-circle img-responsive centerCropp1 "
								style="background-size: cover; height: 44px; width: 44px; margin-right: 5px; background-image: url('/triplived/user/picture/${trip.user.userId}?&amp;size=small');"></span> <span
								class="name">${trip.user.name}</span>
						</section> --%>
							<section class="about_trip">
								<h3>${trip.tripName}</h3>
								<h5>by ${trip.user.name}</h5>
								
								<p class= " " style="background: url('')">
									<i class="left"></i>
									<a href="/trip/${trip.tripId}/${fn:replace(trip.tripName, ' ','-' )}" class="read_more logo"> Read Full Story</a> 
									<i class="right"></i>
								</p>
								<div class="tllogo">
									<img src="http://www.triplived.com/static/triplived-web/img/header.jpg" style="height: 40px" alt="">
								</div>
							</section>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>