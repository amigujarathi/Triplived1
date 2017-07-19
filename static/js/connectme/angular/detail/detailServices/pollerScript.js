var pollerModule= angular.module('pollerModule', ['detailModule' ]);

pollerModule.factory('Poller', function($http, $timeout, $rootScope, PersonOrGroupIdService) {
  var msgData = { response: {}, calls: 0 };
  
  var poller = function() {
	  
	var loadMsg = DOMAIN+"message/unreadMessages";
    $http.get(loadMsg).then(function(r) {
    	msgData = r.data;
    	msgData.calls++;
      $timeout(poller, 30000);
    }).then(function(data) {
    	getNoOfUnreadMsgFromPersonDetailPage(msgData);
    });
	
  };
  
  
  
  return {
    getMyData: function() {
			return data;
        },  
    initializePoller: function() {
    	poller();  	
    }
  };
  
});

