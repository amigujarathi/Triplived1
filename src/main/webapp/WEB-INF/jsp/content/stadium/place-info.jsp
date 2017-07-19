
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:eval expression="logged_in_user" var="user"></spring:eval>



<link rel="stylesheet" type="text/css"	href="/static/css/gallery/gallery.css" />
<link href="/static/css/font-awesome/css/font-awesome.min.css"	rel="stylesheet">

<script src="${minified_url}/js/index.min.js"><!-- script --></script>

<script type="text/javascript" src="${url}/twiter/js/bootstrap.js"></script>

<script type="text/javascript" src="${url}/jquery.min.js"></script>
<script type="text/javascript" src="${url}/jquery.lazyload.min.js"></script>

<script type="text/javascript" src="${url}/angular-1.2.min.js"></script>
<script type='text/javascript' src='${url}/ng-infinite-scroll.min.js'></script>
<script type="text/javascript" src="${url}/ui-bootstrap-tpls-0.6.0.min.js"></script>


<script type="text/javascript"	src="${url}/jquery/template/jquery.tmpl.min.js"></script>
<script type="text/javascript"	src="${url}/jquery/animation/jquery.easing.1.3.js"></script>
<script type="text/javascript"	src="${url}/jquery/animation/jquery.elastislide.js"></script>
<script type="text/javascript"  src="${url}/jquery/gallery/gallery.js"></script>
<script type="text/javascript" src="${url}/html5utils/store.min.js"></script>
<script type="text/javascript"	src="${url}/connectme/angular/content/controllers/stadiumDetailController.js?v=2"></script>


<noscript>
	<style>
.es-carousel ul {
	display: block;
}

div.widget-body div.innerAll p {
	font-weight:bold;
}
</style>
</noscript>

<script id="img-wrapper-tmpl" type="text/x-jquery-tmpl">	
	<div class="rg-image-wrapper">
		{{if itemsCount > 1}}
			<div class="rg-image-nav">
				<a href="#" class="rg-image-nav-prev">Previous Image</a>
				<a href="#" class="rg-image-nav-next">Next Image</a>
			</div>
		{{/if}}
		<div class="rg-image"></div>
		<div class="rg-loading"></div>
		<div class="rg-caption-wrapper">
			<div class="rg-caption" style="display:none;">
				<p></p>
			</div>
		</div>
	</div>
</script>


<!-- <link type="text/css" media="screen" href="http://www.appelsiini.net/css/appelsiini.css" rel="stylesheet"> -->
<div class="container" ng-app="stadiumInfoModule">

	<div id="groupInfoId" id="ng-app" ng-controller="StadiumDetailCtrl"	ng-init="getStadiumDetails('${stadiumDataUrl}')" ng-cloak>

		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header" ng-cloak>{{stadiumInfo.stadiumName}}</h1>
			</div>
		</div>

		<div class="row">
		    <div class="col-sm-6 col-lg-6 col-md-6">
				<div class="widget">
					<div class="widget-body">

						<div class="row innerAll">
							<p class="col-sm-6">Address:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.address}}</strong></span>
							</div>
						</div>
						<div class="row innerAll">
							<p class="col-sm-6">Area:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.street}}</strong></span>
							</div>
						</div>
						
						<div class="row innerAll">
							<p class="col-sm-6">City:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.city}}</strong></span>
							</div>
						</div>
						<div class="row innerAll">
							<p class="col-sm-6">Contact No:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.phoneNo}}</strong></span>
							</div>
						</div>
						<div class="row innerAll">
							<p class="col-sm-6">Sports played here:</p>
							<div class="col-sm-6 text-right">
								<select>
									<option ng-repeat="item in sportsList">{{item}}</option>
								</select>
							</div>

						</div>
						<div class="row innerAll">
							<p class="col-sm-6">WebSite:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.webSite}}</strong></span>
							</div>
						</div>
						<div class="row innerAll">
							<p class="col-sm-6">E-mail:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.emailId}}</strong></span>
							</div>
						</div>
						<div class="row innerAll">
							<p class="col-sm-6">Other Info:</p>
							<div class="col-sm-6 text-right">
								<span style="font-size: 15px"><strong>{{stadiumInfo.otherInformation}}</strong></span>
							</div>
						</div>

					</div>
				</div>

			</div>
			<div class="col-sm-6 col-lg-6 col-md-6">
				<div class="container">


					<div id="rg-gallery" class="rg-gallery ">
						<div class="rg-thumbs">
							<!-- Elastislide Carousel Thumbnail Viewer -->
							<div class="es-carousel-wrapper">
								<div class="es-nav">
									<span class="es-nav-prev"><span></span>Previous</span> <span
										class="es-nav-next"><span></span>Next</span>
								</div>
								<div class="es-carousel">
									<ul id="carous">
										<script type="text/javascript">
					var images = { "imgs" : [
								"http://img.mmtcdn.com/hotels/200701211455342954/r/Swimming_Pool.jpg",
								"http://img.mmtcdn.com/hotels/200803311126319814/r/DSC00868.jpg",
								"http://img.mmtcdn.com/hotels/200701211455342954/r/Rejuve_The_Spa.jpg",
								"http://img.mmtcdn.com/hotels/200803311126319814/r/Exterior_View.jpg",
								"http://img.mmtcdn.com/hotels/200803311126319814/r/Hotel_Interior.jpg",
								"http://img.mmtcdn.com/hotels/200803311126319814/r/Hotel_Interior.jpg",
								"http://img.mmtcdn.com/hotels/200701211455342954/r/Swimming_Pool.jpg",
								"http://img.mmtcdn.com/hotels/200701211455342954/r/Hotel_Front_View.jpg",
								"http://img.mmtcdn.com/hotels/200701211455342954/r/Kitty%20Su_Night_Club_4.jpg",
								"http://img.mmtcdn.com/hotels/200701211455342954/r/The_Grill_European_Restaurant.jpg"
								]
						} ;
						
						for( var i = 0; i <= images.imgs.length ; i++){
							var c = '<li><a href="#"> <img src="'+images.imgs[i]+'" data-large="'+images.imgs[i]+'" alt="image03" /></a></li>' ;
							document.write(c);            
						}
						
					</script>
									</ul>
								</div>
							</div>
							<!-- End Elastislide Carousel Thumbnail Viewer -->
						</div>
						<!-- rg-thumbs -->
					</div>
					<!-- rg-gallery -->

				</div>

			</div>
		</div>


	</div>
</div>

<!--  <script type="text/javascript" charset="utf-8">
  $(function() {
    $("img.lazy").lazyload({container: $("#containerImage")});
  });
  </script> -->

