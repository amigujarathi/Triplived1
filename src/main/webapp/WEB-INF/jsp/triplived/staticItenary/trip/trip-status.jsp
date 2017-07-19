<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags"  prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<body>
	
	<script type="text/javascript">
  		var currentTripDetails = ${presentTrip};
  		var attractionUrl = '${attractionUrl}';
  		var hotelUrl = '${hotelUrl}';
  		var newDestinationUrl = '${newDestinationUrl}';
  		var isLoggedIn = '${isLoggedIn}';
  	</script>
	    
	<c:set var="isLoggedIn" scope="request" value="${isLoggedIn}" />
	
	<c:if test="${isLoggedIn}">    
		<div class="inner"></div>
		
		<div class="container">
			 <div class="col-lg-6" style="padding-top:30px;" id="optionsId">
		          	<div class="row">
		          		
		          		<div class="col-lg-3">
		          			<button id="addAttractionId" type="button" class="btn btn-default" onclick="loadAttractionScreen()">Add Attraction</button>
		          		</div>		
		          		<div class="col-lg-3">
		          			<button id="addHotelId" type="button" class="btn btn-default" onclick="loadHotelScreen()">Add Hotel</button>
		          		</div>
		          		<div class="col-lg-3">
		          			<button id="addDestinationCityId" type="button" class="btn btn-default" onclick="loadCityScreen()">Add New Destination</button>
		          		</div>
		          		
		          	</div>
		     </div>
		</div>
	</c:if>
	
	<%-- <tiles:insertDefinition  name="attraction" flush="true" /> --%>
</body>
</html>