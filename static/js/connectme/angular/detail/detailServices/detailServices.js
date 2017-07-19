var detailModule = angular.module('detailModule', ['ui.bootstrap','pollerModule']);

detailModule.factory('PersonOrGroupIdService', function($http) {
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
