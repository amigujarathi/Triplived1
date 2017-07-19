<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags"  prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<body>
	
	<h1>Thank you for associating with us. We hope you had an awesome trip !!</h1>
	<h2>
		<a href="${context}trip" role="button">Click here to start a new trip
		</a>
	</h2>
	
	<%-- <tiles:insertDefinition  name="attraction" flush="true" /> --%>
</body>
</html>