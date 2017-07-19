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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="http://www.triplived.com/static/tl/images/share/jquery.facedetection.js"></script>

<link rel="icon" href="http://www.triplived.com/static/tl/images/favicon.png">


<style type="text/css">
		table.info {
			background: url(http://www.triplived.com/static/tl/images/share/back_ground_1.jpg);
		}
		
		table {
			width:100%;
			height: 100%;
		}
		table tr, table td {
			/*  border: 0.5px solid green;  */
		}
		
		body {
		 margin: auto;
		 padding: 0px;
		 width: 600px;
		height: 312px;
		margin: auto;
		 
		}
		
	 
		span.title {
		    vertical-align: bottom;
    		font-weight: bold;
    		font-size: 17px;
    		padding-left:5px;
		}
		span.reviewTitle{
			color: red;
			font-weight: bold;
			font-size: 12px;
		}
		td.reviewTitle{
			text-align: center;
			visibility: hidden;
		}
		td.title-cell {
			vertical-align: bottom;
		}
		span.summaryBody {
			font-size:12px;
    		margin: 5px;
    		visibility: hidden;
    		display: none;
		}
		td.summaryBody{
			line-height: 15px;
			text-align: center;
			padding-right: 12px;
			visibility: hidden;
			display: none;
		}
		
		span.hotelTitle, span.attractionTitle, span.attractionTitle , span.activityTitle {
		   padding-left: 3px;
		   font-size: 12px;
		   /* color: black; */
		}
		span.hotelBody, span.attractionBody, span.attractionBody, span.activityBody {
		   padding-left: 5px;
		   /* color: black; */
		   font-size: 10px;
		}
		td.hotel, td.attraction, td.activity, td.download, td.tripsummary{
			vertical-align: bottom;
    		/* line-height: 14px;     */
    		font-size: 11px;
    		font-weight: bold;
		}
		span.tripsummary {
			/* color:red; */
		}
		td.download{
		   font-weight: normal;
		   font-size: 10px;
		   vertical-align: bottom;
		       line-height: 2px;
		}
		table  {
   			border-collapse: collapse;
		}
		
		td.line{
		    background: url(http://www.triplived.com/static/tl/images/share/arrow.png);
   			background-repeat: repeat-y;
    		background-position-x: 77%;
		}
		img.icon {
			 width:20px;
			 height:20px;
			 float:right;
			 margin-top:11px;
		 
		}
		img.uimg {
			float:left;
			font-size: 0;
			 box-shadow: 7px 7px 7px #888888;
		}
		.margin-top-13{
			margin-top: 13px;
		}
		span.downloadTitle , span.downloadBody{
		/* visibility: hidden;
		display: none; */
		margin-top: 4px;
		}
		
		.image-collage {
			height: 160px; 
			width: 120px; 
			float: left; 
			/* margin-left: 4px; */
			background-position: center center; 
			background-repeat: no-repeat; 
			overflow: hidden; 
			background-size: cover;
			box-shadow: 7px 7px 7px #888888;
		}
		.image-collage-big {
			height: 186px; 
			width: 140px; 
			float: left; 
			/* margin-left: 4px; */
			background-position: center center; 
			background-repeat: no-repeat; 
			overflow: hidden; 
			background-size: cover;
			box-shadow: 7px 7px 7px #888888;
		}
		
		.image-collage-cover {
			height: 160px; 
			width: 380px; 
			float: left; 
			margin-left: 4px;
			background-position: center center; 
			background-repeat: no-repeat; 
			overflow: hidden; 
			background-size: cover;
			box-shadow: 7px 7px 7px #888888;
		}
		
	</style>

</head>
<body>
	 <table class="info"  >
		<tr>
			
			<td width="100%" colspan="2">
				<table>
					<tr height="12.8%">
						<td colspan="2" >
							<table>
								<tr>
									<td width="5.1%" class="line">
										 <img src="http://www.triplived.com/static/tl/images/share/ic_launcher.png" class="icon img-title" style="margin-top: 16px;" />
									</td>
									<td class="title-cell">
										<span class="title"> ${fn:substring(trip.tripName, 0, 50)} <!-- New Delhi. Switzerland. New Delhi --> </span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="35%">
							<table>
								<tr height="16.4%">
									<td width="14.5%" class="line">
										  <img src="http://www.triplived.com/static/tl/images/share/location.png" class="icon"  />  
									</td>
									<td class="tripsummary">
										 <span class="hotelTitle">City visited:</span><br/>
										 <span class="hotelBody">
												${cityString}
										</span>
									</td>
								</tr>
								<c:if test ="${fn:length(hotels) eq 0 and fn:length(attractions) eq 0 and  fn:length(activities) eq 0 and fn:length(restaurants) eq   0  }" >
									<tr height="45.6%">
									    <td width="14.5%" class="line">
										</td>
										<td class="download">
											<span class="downloadBody">To view more, download triplived<img src="http://www.triplived.com/static/tl/images/share/playstorelogo.png" width="20px"  height="20px" /></span>
										</td>
									</tr>
								</c:if>
								<c:if test ="${fn:length(hotels) gt 0}" >
									<tr height="23.6%">
										<td width="14.5%" class="line">
											 <img src="http://www.triplived.com/static/tl/images/share/hotel.png" class="icon"  />
										</td>
										<td class="hotel">
											<span class="hotelTitle">Hotels checked-in:</span><br/>
											<span class="hotelBody">
												${hotelString}
											</span>
										</td>
									</tr>
								</c:if>
								<c:if test ="${fn:length(attractions) gt 0}" >
									<tr height="23.6%">
										 <td width="14.5%" class="line">
											 	<img src="http://www.triplived.com/static/tl/images/share/attraction_icon.png" class="icon"  />
										</td>
										<td class="attraction">
											<span class="attractionTitle">Attraction visited:</span><br/>
											<span class="attractionBody">
												${attractionString}
											</span>
										</td>
									</tr>
								</c:if>
								<c:if test ="${fn:length(activities) gt 0}" >
									<tr height="23.6%">
										<td width="14.5%" class="line">
											 <img src="http://www.triplived.com/static/tl/images/share/activity_icon.png" class="icon"/>
										</td>
										<td class="attraction">
											<span class="activityTitle">Activities done:</span><br/>
											 
											${activityString}
										</td>
										</td>
									</tr>
								</c:if>
								<c:choose>
									<c:when test ="${fn:length(attractions) gt 0 or  fn:length(activities) gt 0}" >
										<tr height="23.6%">
										    <td width="14.5%" class="line">
											</td>
											<td class="download">
													  <!-- <span class="downloadTitle">To view entire trip follow it on Triplived</span><br/> -->
													  <span class="downloadBody">To view more, download triplived<img src="http://www.triplived.com/static/tl/images/share/playstorelogo.png" width="20px"  height="20px" /></span>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test ="${fn:length(attractions) eq 0 and  fn:length(activities) eq 0 and fn:length(restaurants) gt   0  }" >
											
												<tr height="23.6%">
												    <td width="14.5%" class="line">
												    	<img src="http://www.triplived.com/static/tl/images/share/restaurant.png" class="icon"/>
													</td>
													<td class="attraction">
														<span class="activityTitle">Restaurant visited:</span><br/>
														 <span class="activityBody">${restaurantString}</span>		 
													</td>
												</tr>
												<tr height="23.6%">
												    <td width="14.5%" class="line">
													</td>
													<td class="download">
														 <span class="downloadBody">To view more, download triplived<img src="http://www.triplived.com/static/tl/images/share/playstorelogo.png" width="20px"  height="20px" /></span>
													</td>
												</tr>
											</c:when>
										</c:choose>
										<tr height="1.8%">
										    <td width="14.5%" class="line">
											</td>
											<td class="download1">
												  <span><!-- d --></span>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</td>
						<td> 
							<table>
								<tr>
									<td height="10.76%" class="tripsummary" style="text-align: center; color:rebeccapurple;">
										<span class="tripsummary" style="font-size: 15px ;" >
											${summary}
										</span><br/>
									</td>
								</tr>
								<tr height="21.7%">
									<td  class="summaryBody">
										<span class="summaryBody">
										    A beautiful journey to the valley of heaven. An experience so wonderful that the essence of the trip stays with you
										    for weeks to come. Journey to Switzerland was full of adrenaline and wonder and not to forget a lot of ice. Hope you guys
										    enjoy the pictures.
										</span>
									</td>
								</tr>
								<tr>
									<td height="59.6%" style="padding-bottom: 8px;">
											<c:choose>
												<c:when test="${isLessImages eq 'false'}">
													<div class="margin-top-13 image-collage" style="background-image: url(&#39;${image1}&#39;);"></div>
													<div class="image-collage-big" style="    margin-left: 3px; background-image: url(&#39;${image2}&#39;); "></div>
													<div class="margin-top-13 image-collage" style=" margin-left: 3px; background-image: url(&#39;${image3}&#39;); "></div>
											
												</c:when>
												<c:otherwise>
													<div class="image-collage-cover" style="background-image: url(&#39;${coverPage}&#39;);"></div>
												</c:otherwise>
											</c:choose>
									</td> 
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>