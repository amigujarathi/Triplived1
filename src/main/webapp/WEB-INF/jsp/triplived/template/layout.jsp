<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags"  prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<c:choose>
	<c:when test="${fn:endsWith(requestScope['javax.servlet.forward.request_uri'],'/') or fn:endsWith(requestScope['javax.servlet.forward.request_uri'],'/home') 
	              or fn:endsWith(requestScope['javax.servlet.forward.request_uri'],'/slSearch')}">
<meta name="description" content="Interestify provides a platform to search and connect people near and around you who share same interests and hobbies. For example, you can find people
interested in playing a sport or an instrument near you and you can then connect with them by sending messages, creating groups and organizing events. Major interests
like Cricket, Hockey, Guitar, Music etc. are included." />
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
		<link rel="stylesheet" href="/static/triplived/css/bootstrap.min.css">
		<!-- Generic page styles -->
		<link rel="stylesheet" href="/static/triplived/css/style.css">
		<!-- blueimp Gallery styles -->
		<link rel="stylesheet" href="/static/triplived/css/blueimp-gallery.min.css">
		<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
		<link rel="stylesheet" href="/static/triplived/css/jquery.fileupload.css">
		<link rel="stylesheet" href="/static/triplived/css/jquery.fileupload-ui.css">
		
		
		<script src="/static/triplived/js/jquery.min.js"></script>
		
		<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
		<script src="/static/triplived/js/jquery.ui.widget.js"></script>
		
		<!-- The Templates plugin is included to render the upload/download listings -->
		<script src="/static/triplived/js/tmpl.min.js"></script>
		
		<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
		<script src="/static/triplived/js/load-image.all.min.js"></script>
		
		<!-- The Canvas to Blob plugin is included for image resizing functionality -->
		<script src="/static/triplived/js/canvas-to-blob.min.js"></script>
		
		<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
		<script src="/static/triplived/js/bootstrap.min.js"></script>
		
		<!-- blueimp Gallery script -->
		<script src="/static/triplived/js/jquery.blueimp-gallery.min.js"></script>
		
		<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
		<script src="/static/triplived/js/jquery.iframe-transport.js"></script>
		
		<!-- The basic File Upload plugin -->
		<script src="/static/triplived/js/jquery.fileupload.js"></script>
		
		<!-- The File Upload processing plugin -->
		<script src="/static/triplived/js/jquery.fileupload-process.js"></script>
		
		<!-- The File Upload image preview & resize plugin -->
		<script src="/static/triplived/js/jquery.fileupload-image.js"></script>
		
		<!-- The File Upload audio preview plugin -->
		<script src="/static/triplived/js/jquery.fileupload-audio.js"></script>
		
		<!-- The File Upload video preview plugin -->
		<script src="/static/triplived/js/jquery.fileupload-video.js"></script>
		
		<!-- The File Upload validation plugin -->
		<script src="/static/triplived/js/jquery.fileupload-validate.js"></script>
		
		<!-- The File Upload user interface plugin -->
		<script src="/static/triplived/js/jquery.fileupload-ui.js"></script>
		
		 
		<script type="text/javascript" src="/static/triplived/js/typeahead.bundle.min.js"></script>
		
	</head>
	<body >
		<div id="w">
			<div id="h">
				<tiles:insertDefinition name="connectme.guest.header"  flush="true"/>
			</div>
			<div id="c" >
				<tiles:insertAttribute name="body"/>
			</div>
		</div>
	</body>
</html>