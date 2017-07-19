messageModule.filter('searchFor', function(){

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



messageModule.factory('PollerOnMsgScreen', function($http, $timeout, $rootScope, PersonIdServiceForMessageScreen) {
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
	  //poller();
	  
	  return {
	    getMyData: function() {
				return data;
	        },
        initializePoller: function() {
        	poller();  	
        }
	    
	  };
	  
});


messageModule.factory('PersonIdServiceForMessageScreen', function($http) {
	var myId = '';
	
	return {
        setMyId: function(variableId) {
            myId = variableId;
        },
        getMyId: function() {
            return myId;
        },
	};
	
});


messageModule.factory('groupSharedService', function($http,$rootScope) {  
	  var groupId = "";
	  var groupName = "";

	    return {
	        setGroupIdAndName: function(variableId, variableName) {
	        	groupId = variableId;
	        	groupName = variableName;
	        	$rootScope.$broadcast('broadcastForUpdatingLinkedGroupList');
	        },
	        getGroupId: function() {
	            return groupId;
	        },
	        getGroupName: function() {
	        	return groupName;
	        }
		};
});

