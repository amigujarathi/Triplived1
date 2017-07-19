<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags"  prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<body>

							<span class="btn btn-success fileinput-button">
			                	<label>ENTER YOUR TRIP NAME</label><br>
			                </span>
			                <input type="text" name="FirstName" value="Mickey"><br>
			                
			                
			                <form:form method="POST" action="/trip/trip-start" modelAttribute="trip">
						   <table>
						    <tr>
						        <td><form:label path="tripName">ENTER YOUR TRIP NAME</form:label></td>
						        <td><form:input path="tripName" /></td>
						    </tr>
						    <tr>
						        <td><form:label path="userId">Age</form:label></td>
						        <td><form:input path="userId" /></td>
						    </tr>
						    <tr>
						        <td colspan="2">
						            <input type="submit" value="Submit"/>
						        </td>
						    </tr>
						</table>  
						</form:form>

			                <br>
							<span class="btn btn-success fileinput-button">
			                    <i class="glyphicon glyphicon-plus"></i>
			                    <a href="/city/progress">ADD CITY</a>
			                </span>
							<span class="btn btn-success fileinput-button">
			                    <i class="glyphicon glyphicon-plus"></i>
			                    <span>ADD ATTRACTION</span>
			                </span>
			                <span class="btn btn-success fileinput-button">
			                    <i class="glyphicon glyphicon-plus"></i>
			                    <span>ADD HOTEL</span>
			                </span>
			                
	<%-- <tiles:insertDefinition  name="attraction" flush="true" /> --%>
</body>
</html>