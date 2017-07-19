<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="scripts"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Place Autocomplete using Typeahead</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/static/triplived/css/jquery-places.css" />

</head>
<body>

	<div class="container panel">
		<form class="form-search">
			<h2 class="form-search-heading">Search for a place</h2>
			<input id="search-field" type="text" class="typeahead input-block-level" placeholder="Enter a place...">
		</form>
		<div id="map"></div>
	</div>



	<script src="https://twitter.github.io/typeahead.js/js/handlebars.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script src="https://twitter.github.io/typeahead.js/releases/latest/typeahead.bundle.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyD2UIQ24YUy44ZpWymPHJaOt_2MhmcayTI"></script>
	<script src="/static/triplived/js/api/jquery.places-search.js"></script>

	<script type="text/javascript">
		var map = new google.maps.Map(document.getElementById('map'), {
			center : {
				lat : -33.8688,
				lng : 151.2195
			},
			zoom : 13,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});

		$('#search-field').placesSearch({
			"map" : map
		});
	</script>


</body>
</html>