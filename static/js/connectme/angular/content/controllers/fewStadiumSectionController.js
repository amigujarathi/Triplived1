function FewStadiumSectionCtrl($scope,$http,$rootScope, FewLocationService) {
	
	$scope.$on('renderFewLocations', function() {
	
		if($scope.myControllerActivityId != undefined && $scope.myControllerCityId != undefined ) {
			var url = 'location/getFewLocations/' + $scope.myControllerLocationId+"/"+$scope.myControllerCityId+"/"+$scope.myControllerActivityId;
			
			if(FewLocationService.isLoad() == true) {
				$http.get(url).success(function(response) {
					$scope.locationList = response;
				});
			}
		}
	});
	
	$scope.loadFewLocations = function() {
		
		if($scope.myControllerActivityId != undefined && $scope.myControllerCityId != undefined ) {
			var url = 'location/getFewLocations/' + $scope.myControllerLocationId+"/"+$scope.myControllerCityId+"/"+$scope.myControllerActivityId;
			$http.get(url).success(function(response) {
				$scope.locationList = response;
			});
		}
	};
		
};




