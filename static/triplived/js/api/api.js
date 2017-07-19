$(document).ready(function() {

	var citiesDataSource = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('value'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		remote : '../citySuggest/%QUERY'
	});

	citiesDataSource.initialize();

	$('#cityname').typeahead({
		hint : true,
		highlight : true,
		minLength : 1
	}, {
		name : 'cities',
		displayKey : 'cityName',
		source : citiesDataSource.ttAdapter()

	}).on('typeahead:selected', function(evt, item) {
		cityId = item.id;
		currentCity = item.cityName;
	});

});

function submitForm() {

	var data = {};
	data.cityId = cityId;
	$.post(DOMAIN + 'attraction/upload/ajax-post-data', data).done(
			function(data) {
				$('#responsive').modal('hide');
			});
}


var allGoogleCategories = [ "accounting", "airport", "amusement_park",
                    		"aquarium", "art_gallery", "atm", "bakery", "bank", "bar",
                    		"beauty_salon", "bicycle_store", "book_store", "bowling_alley",
                    		"bus_station", "cafe", "campground", "car_dealer", "car_rental",
                    		"car_repair", "car_wash", "casino", "cemetery", "church", "city_hall",
                    		"clothing_store", "convenience_store", "courthouse", "dentist",
                    		"department_store", "doctor", "electrician", "electronics_store",
                    		"embassy", "establishment (deprecated)", "finance (deprecated)",
                    		"fire_station", "florist", "food (deprecated)", "funeral_home",
                    		"furniture_store", "gas_station", "general_contractor (deprecated)",
                    		"grocery_or_supermarket", "gym", "hair_care", "hardware_store",
                    		"health (deprecated)", "hindu_temple", "home_goods_store", "hospital",
                    		"insurance_agency", "jewelry_store", "laundry", "lawyer", "library",
                    		"liquor_store", "local_government_office", "locksmith", "lodging",
                    		"meal_delivery", "meal_takeaway", "mosque", "movie_rental",
                    		"movie_theater", "moving_company", "museum", "night_club", "painter",
                    		"park", "parking", "pet_store", "pharmacy", "physiotherapist",
                    		"place_of_worship (deprecated)", "plumber", "police", "post_office",
                    		"real_estate_agency", "restaurant", "roofing_contractor", "rv_park",
                    		"school", "shoe_store", "shopping_mall", "spa", "stadium", "storage",
                    		"store", "subway_station", "synagogue", "taxi_stand", "train_station",
                    		"transit_station", "travel_agency", "university", "veterinary_care",
                    		"zoo" ];

                    var requiredCategories = [ "airport", "amusement_park", "aquarium",
                    		"art_gallery", "bakery", "bar", "bowling_alley", "bus_station", "cafe",
                    		"campground", "casino", "cemetery", "church", "city_hall",
                    		"convenience_store", "courthouse", "department_store", "embassy",
                    		"florist", "food (deprecated)", "funeral_home",
                    		"grocery_or_supermarket", "health (deprecated)", "hindu_temple",
                    		"jewelry_store", "library", "local_government_office", "lodging",
                    		"meal_delivery", "meal_takeaway", "mosque", "movie_theater",
                    		"moving_company", "museum", "night_club", "park", "parking",
                    		"pharmacy", "physiotherapist",
                    		"place_of_worship (deprecated)", "post_office", "real_estate_agency",
                    		"restaurant", "rv_park", "school", "shopping_mall", "spa", "stadium",
                    		"storage", "store", "subway_station", "synagogue", "taxi_stand",
                    		"train_station", "transit_station", "travel_agency", "university",
                    		"veterinary_care", "zoo" ];

                    String.prototype.hashCode = function(){
                        var hash = 0;
                        if (this.length == 0) return hash;
                        for (var i = 0; i < this.length; i++) {
                            var character = this.charCodeAt(i);
                            hash = ((hash<<5)-hash)+character;
                            hash = hash & hash; // Convert to 32bit integer
                        }
                        return hash;
                    };


                    var requiredCategoriesArrayHash = {};
                    for( var i=0; i< requiredCategories.length; i++){
                    	requiredCategoriesArrayHash[requiredCategories[i]] ="E";
                    }
