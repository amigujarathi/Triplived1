function AbsolutePersonDetailCtrl($scope,$http,$rootScope,$modal,Poller,PersonOrGroupIdService) {
	
	PersonOrGroupIdService.setMyId($scope.selfId);
	
	if($scope.isPersonLoggedIn) {
	Poller.initializePoller();
	}
	
	$scope.getPersonDetails = function(url){

	
	 angular.element(document).ready(function() {
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
			$scope.streetName = response[0]["streetName"];
			$scope.cityName = response[0]["cityName"];
			$scope.imagePath = "/static/user-images/"+$scope.personId+"/"+$scope.personId+".jpg";
			$scope.interestifyName = response[0]["interestifyName"];
		});
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
	
	
	$scope.formatDate = function(dateVal) {
		
		var x = new Date(dateVal);
		return x;
	//new Date(dateString)
	};
	
	
	$scope.getMsgTime = function(msgTimeStamp){
		
		if(msgTimeStamp !== undefined && msgTimeStamp.toString().length == 10) {
		     var monthNames = [ "January", "February", "March", "April", "May", "June",
		    "July", "August", "September", "October", "November", "December" ];
			
		     var myVar = (msgTimeStamp*1000).toString();
		     var value = new Date(parseInt(myVar.replace(/(^.*\()|([+-].*$)/g, '')));
			 
			 var monthName = monthNames[value.getMonth()].substring(0,3);
			 
			 var msgDate = value.getDate() + " " + monthName + "," + value.getFullYear(); 
	 
			 return msgDate;
		} else {
			return "";
		}
		
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
	
    
	var addMessageBetweenPersonsUrl = DOMAIN+"message/postMessage";
	
	$scope.msgDetails = {message: msg, personId: $scope.personId};
	 
	 $http({
		    url: addMessageBetweenPersonsUrl,
		    method: 'POST',
		    data: $scope.msgDetails,
	 		headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
		        // success
		    }, 
		    function(response) { // optional
		        // failed
		    }
		);
	
	$modalInstance.close('close');
    };

	$scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	};
};


