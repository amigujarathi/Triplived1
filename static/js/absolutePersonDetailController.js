var detailModule = angular.module('detailModule', ['ui.bootstrap']);

function AbsolutePersonDetailCtrl($scope,$http,$rootScope,$modal) {
	
	$scope.getPersonDetails = function(url){
		getNoOfUnreadMsgFromPersonDetailPage();
		$http.get(url).success(function(response) {
			$scope.personDetail = response;
			
			$scope.personId = response[0]["id"];
			$scope.personName = response[0]["name"];
			$scope.personEmailId = response[0]["email"];
			$scope.personPhoneNo = response[0]["phoneNo"];
			$scope.personBirthDate = response[0]["birthDate"];
			$scope.personSports = response[0]["sportName"];
			$scope.showEmailCheck = response[0]["showEmailCheck"];
			$scope.showTelephoneCheck = response[0]["showTelephoneCheck"];
			$scope.imagePath = "/static/user-images/"+$scope.personId+"/"+$scope.personId+".jpg";
		});
	};
	
	$scope.safeApply = function(fn) {
		  var phase = this.$root.$$phase;
		  if(phase == '$apply' || phase == '$digest')
			this.$eval(fn);
		  else
			this.$apply(fn);
	};
	
/*	$scope.$on('handleBroadcastForPolledDataOnPersonDetailPage', function() {
		  var jsonMap = PollerOnPersonDetailPage.getMyData()
		  var unreadMsgCount = 0;
		  angular.forEach(jsonMap, function(id){
		       unreadMsgCount += id;    
		  }); 
		 $scope.dataFromPoller = unreadMsgCount;
		 getNoOfUnreadMsgFromPersonDetailPage(); 
	});
*/	
	$scope.open = function (personId) {

		var modalInstance = $modal.open({
		  templateUrl: 'messagePopupOnDetailScreen',
		  controller: MessagePopupOnDetailScreenCtrl,
		  
		  resolve: {
			id: function () {
			  return personId;
			},
			myId: function (){
			  return $scope.selfId;
			}
		  }
		});
			
	};
};


function MessagePopupOnDetailScreenCtrl($scope, $modalInstance, $http, id, myId, $rootScope) {
	
	$scope.personId = id;
	$scope.selfId = myId;
    $scope.jsonMessageList = [];
	  
    $scope.ok = function (msg) {
    	
  	  if((typeof(msg) == "undefined") || (msg.length == 0)) {
		  alert("Please enter message !!!");
		  return false;
	  }
	
    //alert($scope.personId);
	var addMessageBetweenPersonsUrl = DOMAIN+"message/addMessage?toPersonId="+$scope.personId+"&message="+msg;
	$http.get(addMessageBetweenPersonsUrl).success(function(response){
		//TODO
		var x = "df";
	});
	
	$modalInstance.close('close');
    };

	$scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	};
};

/*
detailModule.factory('PollerOnPersonDetailPage', function($http, $timeout, $rootScope) {
	  var data = { response: {}, calls: 0 };
	  var myId = "";
	  var poller = function() {
		var loadMsg = DOMAIN+"message/unreadMessages";
	    $http.get(loadMsg).then(function(r) {
	      data = r.data;
		  
		  $rootScope.$broadcast('handleBroadcastForPolledDataOnPersonDetailPage');
	      data.calls++;
	      $timeout(poller, 10000);
	    });
	    
	  };
	  //poller();
	  //TODO - has been commented for now, but would be needed once we show the message notification on detail page
	  
	  return {
	    getMyData: function() {
				return data;
	        }
	        
	  };
	  
	  
});*/
