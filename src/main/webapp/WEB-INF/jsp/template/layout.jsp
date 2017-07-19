<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags"  prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<c:choose>
	<c:when test="${fn:endsWith(requestScope['javax.servlet.forward.request_uri'],'/') or fn:endsWith(requestScope['javax.servlet.forward.request_uri'],'/home')}">
<meta name="description" content="" />
	</c:when>

</c:choose>

	<c:url value="/" var="context" scope="request"/>

   <c:set var="pageType" scope="request">
    	<tiles:insertAttribute name="pageType" />
   </c:set>


	<c:choose>
		<c:when test="${fn:contains(context, ';')}">
			<c:set var="context" value="${fn:substringBefore(context, ';')}" scope="request"></c:set>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>

	<spring:url value="/user-images" var="user_images" context="/static" scope="request"/>
	<spring:url value="/images" var="images" context="/static" scope="request"/>
	<spring:url value="/js" var="url" context="/static"  scope="request"/>
	<spring:url value="/css" var="css_url"  context="/static" scope="request" />
	<spring:url value="/styles" var="styles_url" context="/static" scope="request" />
	<spring:url value="/images/profiles" var="images_url" context="/static" scope="request" />
	
	<spring:url value="/m" var="minified_url" context="/static" scope="request" />
 
 
 	<c:set var="user"  value="${sessionScope['logged_in_user']}" scope="request"/>
 
	<head>
		<link rel="shortcut icon" href="${images}/favicon.ico" >
		
		<title><tiles:insertAttribute name="pagetitle"/></title>
		<scripts:load-scripts />
		
	</head>
	<body >
		<div id="w">
			<div id="h">
				<c:choose>
					<c:when test="${user != null}">
						<tiles:insertDefinition  name="connectme.user.header" flush="true" />
					</c:when>
					<c:otherwise>
						<tiles:insertDefinition  name="connectme.guest.header" flush="true" />
						<!--  tiles:insertDefinition name="connectme.guest.header"  flush="true" / -->
					</c:otherwise>
				</c:choose>
			</div>
			<div id="c" style="background-color: #F8F8F8;">
				<tiles:insertAttribute name="body"/>
			</div>
			<div id="f">
				<tiles:insertAttribute name="footer" flush="true"/>
			</div>
		</div>
	</body>
	
	<scripts:write-scripts />
</html>