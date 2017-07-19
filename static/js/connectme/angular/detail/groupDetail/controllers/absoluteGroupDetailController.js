function AbsoluteGroupDetailCtrl($scope,$http,$rootScope,$modal,Poller,PersonOrGroupIdService) {
	
	PersonOrGroupIdService.setMyId($scope.selfId);
	if($scope.isPersonLoggedIn) {
	Poller.initializePoller();
	}
	$scope.amIAdmin = false;
	
	$scope.getGroupDetails = function(url){
		//getNoOfUnreadMsgFromPersonDetailPage();
		//alert($scope.selfId);
		angular.element(document).ready(function() {
			$http.get(url).success(function(response) {
				$scope.groupDetailMemberList = response;
				$scope.groupName = $scope.groupDetailMemberList[0].groupName;
				$scope.checkAdmin($scope.groupDetailMemberList);
			});
		});
	};
	
	$scope.checkAdmin = function(memberList) {
		angular.forEach(memberList, function(item) {
			if(item.personId == $scope.selfId && item.role == "admin") {
				$scope.amIAdmin = true;
			}
		});
	};
	
	
	$scope.safeApply = function(fn) {
		  var phase = this.$root.$$phase;
		  if(phase == '$apply' || phase == '$digest')
			this.$eval(fn);
		  else
			this.$apply(fn);
	};
	
	$scope.linkedPersonsList = [];
	
	var loadPersonsUrl = DOMAIN+"searchperson/getAllConnectedPerson";
	$http.get(loadPersonsUrl).success(function(response){
		$scope.linkedPersonsList = response;
	});
	
	$scope.openGroupDetail = function (groupId,groupName) {

    	
	    $http.get(DOMAIN+"group/getAllLinkedPersons?groupId="+groupId+"&personId="+$scope.selfId).success(function(response){
			       
			       var adminOfThisGroupId = '';
			       var linkedPersonsList = [];
			       
			       var modalInstance = $modal.open({
			 		  templateUrl: 'editGroupDialog',
			 		  controller: GroupDetailCtrl,
			 		  
			 		  resolve: {
			 			getGroupId: function () {
			 			  return groupId;
			 			},
			 			getSelfId: function () {
			 			  return $scope.selfId;	
			 			}, 
			 			getPersonsLinkedToGroup: function () {
			 			  
			 			  angular.forEach(response, function(val){
			 					  if(val.role == "admin"){
			 						 adminOfThisGroupId = val.personId;
			 					  }
			 					  else{
			 						  linkedPersonsList.push(val);
			 					  }
			 			  });
			 			  
			 			  return linkedPersonsList;	
			 			},
			 			allLinkedPersons: function (){
			 				  
			 				  var onlyLinkedPersonsList = [];
			 				  angular.forEach($scope.linkedPersonsList, function(obj){
			 					if(obj.connectedType == "link"){
			 						onlyLinkedPersonsList.push(obj);
			 					}  
			 				  });
			 				  return onlyLinkedPersonsList;
			 				  
			 			},
			 			adminIdOfThisGroup: function (){
			 				return adminOfThisGroupId;
			 			},
			 			groupName: function (){
			 				return groupName;
			 			}
			 			
			 		  }
			 		});
			    });
	 
			
	};



	$scope.open = function (groupId) {

		var modalInstance = $modal.open({
		  templateUrl: 'groupMessagePopupOnDetailScreen',
		  controller: GroupMessagePopupOnDetailScreenCtrl,
		  
		  resolve: {
			id: function () {
			  return groupId;
			},
			myId: function (){
			  return $scope.selfId;
			}
		  }
		});
			
	};
	
	$scope.openPersonDetail = function(personId) {
		window.open(DOMAIN + "searchperson/"+personId, '_self');
	};
	
};




function GroupMessagePopupOnDetailScreenCtrl($scope, $modalInstance, $http, id, myId, $rootScope) {
	
	$scope.groupId = id;
	$scope.selfId = myId;
    $scope.jsonMessageList = [];
	  
    $scope.ok = function (msg) {
    	
  	  if((typeof(msg) == "undefined") || (msg.length == 0)) {
		  alert("Please enter message !!!");
		  return false;
	  }
	
    var addMessageToGroupUrl = DOMAIN+"message/addMessage?toPersonId="+$scope.personId+"&message="+msg;
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


