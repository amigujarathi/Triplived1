projectModule.factory('msgHistorySharedService', function($http,$rootScope) {  
  var myId = "";
  var myName = "";

    return {
        setMyIdAndName: function(variableId, variableName) {
            myId = variableId;
            myName = variableName;
			$rootScope.$broadcast('handleBroadcastForPassingUserIdToMsgHistory');
        },
        getMyId: function() {
            return myId;
        },
        getMyName: function() {
        	return myName;
        },
		openMsgHistoryPage: function(){
			$rootScope.$broadcast('handleBroadcastForOpeningMsgHistoryPage');
		},
    };
});

/*projectModule.filter('searchFor', function(){

	return function(arr, searchString){
		if(!searchString){
			return arr;
		}

		var result = [];
		searchString = searchString.toLowerCase();

		// Using the forEach helper method to loop through the array
		angular.forEach(arr, function(item){

			if(item.personName.toLowerCase().indexOf(searchString) !== -1){
				result.push(item);
			}

		});

		return result;
	};

});

*/
projectModule.factory('Poller', function($http, $timeout, $rootScope) {
  var data = { response: {}, calls: 0 };
  var myId = "";
  var poller = function() {
	var loadMsg = DOMAIN+"message/unreadMessages";
    $http.get(loadMsg).then(function(r) {
      data = r.data;
	  
	  $rootScope.$broadcast('handleBroadcastForPolledData');
      data.calls++;
      $timeout(poller, 30000);
    });
    
  };
  if($rootScope.isPersonLoggedIn == "true") {
  poller();
  }
  
  return {
    getMyData: function() {
			return data;
        },
    
    forcePoll: function(){
    	poller();
        }
    
        
  };
  
});


