<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


  
<script type="text/javascript">
	var DOMAIN = "${context}" ;
</script>
 

<header>
	<nav class="navbar navbar-default navbar-fixed-top">
	<div id="navbar-example" class="navbar navbar-static">
		<!-- <span style= "margin-top:7px;position:absolute; color:#F37220; font-size: 30px;">
<img src="/static/images/Interestify_Logo_Small.png"></img></span> -->
    	<span style="position:absolute; color:#F37220; font-size: 30px; padding-left: 20px">
			<a class="logo" style="text-decoration: none;" href="#">
				TripLived <span style=" color:#F37220; font-size: 10px;"> beta</span>
			</a> 
 
		<!--	<img src="/static/images/Interestify_Logo.png" onClick="switchToListingPageScreen()"></img> -->
		</span>
		
<div class="container" style="width: auto;">
		 	<div class="navbar-header">
          		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            		<span class="sr-only">triplived <span style=" color:#F37220; font-size: 10px;"> beta</span></span>
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
          		</button>
        	</div>
			<div class="navbar-collapse collapse off" style="height: auto;">
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-top: 8px; padding-right: 10px;">
						<button type="button" class="btn btn-default" onclick="endTrip()">End Trip</button>
					</li>
					<li id="logged-in-user"  class = "noshow">
						<div style= "text-align: left; padding-top: 14%; ">
							<tiles:insertTemplate template="../login/facebook/loginwithfacebook.jsp" />
						</div>
					</li>
				</ul>
			</div>
			<!-- /.nav-collapse -->
		</div>
	</div>
</nav>
</header>
