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
	           var tripId = ${param.tripId};
	           var cityType = '${cityType}';
	           var subTripId = '${subTripId}';
	    </script>			                
			         
			         
			         <div class="inner"></div>
			                
	<tiles:insertDefinition  name="cityStaticItenary" flush="true" />
	
	<%-- <tiles:insertDefinition  name="attraction" flush="true" /> --%>
</body>
</html>