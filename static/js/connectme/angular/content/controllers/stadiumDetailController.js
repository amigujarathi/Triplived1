var stadiumInfoModule = angular.module('stadiumInfoModule', []);

function StadiumDetailCtrl($scope,$http,$rootScope) {
	
	$scope.getStadiumDetails = function(url){
		//alert(url);
		angular.element(document).ready(function() {
			$http.get(url).success(function(response) {
				$scope.stadiumInfo = response;
				
				var sportsList = $scope.stadiumInfo.sportsPlayed.split(",");
				$scope.sportsList = [];
				angular.forEach(sportsList, function(value){
				       $scope.sportsList.push(value.replace(/_/g," "));    
				});
			});
		});
	};
	
		
};




