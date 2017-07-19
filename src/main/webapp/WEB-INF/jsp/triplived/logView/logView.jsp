<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags"  prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<body>
	
	<table>
    <!-- here should go some titles... -->
    <tr>
        <th>Logs</th>
        
    </tr>
    <c:forEach begin="1" end= "${fn:length(logList)}" step="1" varStatus="loop" >
    <tr>
        <td>
            <td>${logList[loop.index]}</td>
        </td>
    </tr>
    </c:forEach>
</table>
	
	<%-- <tiles:insertDefinition  name="attraction" flush="true" /> --%>
</body>
</html>