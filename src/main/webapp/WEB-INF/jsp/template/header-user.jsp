<jsp:root
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:tiles="http://tiles.apache.org/tags-tiles" version="2.0">

	<jsp:directive.page isELIgnored="false" />
	<jsp:directive.page contentType="text/html;charset=UTF-8" />
	<jsp:output omit-xml-declaration="yes" />
	
	
	<c:set var="user"  value="${sessionScope['logged_in_user']}"/>



	<script type="text/javascript">
		var DOMAIN = "${context}" ;
	    var LOGGED_IN_USER = "${user.id}";
	</script>
	 
	<style type="text/css">
		.navbar-nav > li#logged-in-user > a {
    		padding-bottom: 5px;
    		padding-top: 8px;
		}
		
		/*a.logo:link    {color:#F37220;}
		a.logo:visited {color:#F37220;}
		a.logo:hover   {color:#F37220;}
		a.logo:active  {color:#F37220;} */
		span.msgs-red { background-color: #D9534F}
		span.msgs-green { background-color: #5CB85C;}
	</style>
<header class="">
 <nav class="navbar navbar-default navbar-fixed-top">
	<div id="navbar-example" class="navbar navbar-static">
		<span style="position:absolute; color:#F37220; font-size: 30px; padding-left: 20px">
			<a class="logo" style="text-decoration: none;" href="#">
				Triplived <span style=" color:#F37220; font-size: 10px;"> beta</span>
			</a> 
 
			<!-- <img src="/static/images/Interestify_Logo.png" onClick="switchToListingPageScreen()"></img> -->
		</span>
		<div class="container" style="width: auto;">
 			<div class="navbar-collapse collapse off" style="height: auto;">
			   
				<ul class="nav navbar-nav navbar-right header">
					<li id="logged-in-user"  class = "noshow">
						<tiles:insertTemplate template="../template/user-image.jspx" />
						<ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
							<li>
								<a href="${context}home/logout" id="drop3" role="button">Logout</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
							
			<!-- /.nav-collapse -->
		</div>
		<!-- /.container -->
	</div>
</nav>
 	
</header>
 
</jsp:root>
