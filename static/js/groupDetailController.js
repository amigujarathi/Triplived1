function GroupDetailCtrl($scope,$http, getGroupId, getPersonsLinkedToGroup, allLinkedPersons, adminIdOfThisGroup, getSelfId,groupName,$modalInstance) {

	$scope.selfId = getSelfId;
	$scope.groupAdminId = adminIdOfThisGroup;
	
	$scope.groupId = getGroupId;
	$scope.originalGroupPersonsList = [];
	$scope.groupPersonsList = getPersonsLinkedToGroup;
	$scope.allLinkedPersons = allLinkedPersons;  
	$scope.groupName = groupName;
	
	$scope.addToGroupList = function(personObj){
		  
		  //TODO - understand how ng-options works with unique filter as this would help us avoid below duplicate check
		  var isAdded = false;
		  angular.forEach($scope.groupPersonsList, function(val){
			  if(val.personId == personObj.personId){
				alert("The person is already added in the Chat list");
				isAdded = true;
			  }
		  	});
		  
		  if(isAdded == false)
			  $scope.groupPersonsList.push(personObj);
			
	}
	
	$scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	
	$scope.assignValues = function(){
		angular.forEach($scope.groupPersonsList, function(val){
			$scope.originalGroupPersonsList.push(val);
		  });
			
	}
	
	$scope.modifyGroupList = function(item){
		  var itemIndex = $scope.groupPersonsList.indexOf(item); 
		  $scope.groupPersonsList.splice(itemIndex,1);
    }
	
	$scope.saveupdatedGroupListToDb = function(){
		  
		  var listOfIdInModifiedGroup = [];
		  var listOfOriginalIdInGroup = [];
		  
		  
		  angular.forEach($scope.groupPersonsList, function(val){
			  listOfIdInModifiedGroup.push(val.personId);
		  });
		  
		  angular.forEach($scope.originalGroupPersonsList, function(val){
			  listOfOriginalIdInGroup.push(val.personId);
		  });
		  
		  if(listOfIdInModifiedGroup.length < 2){
			  alert("Please select at least two participants for group messages !!!");
			  return false;
		  }
		  
		  var saveUpdatedGroupToDbUrl = DOMAIN+"group/modifyGroup?adminId="+getSelfId+"&listOfOriginalIdLinked="+listOfOriginalIdInGroup+"&listOfNewIdLinked="+listOfIdInModifiedGroup+"&groupId="+$scope.groupId;
		    
		  $http.get(saveUpdatedGroupToDbUrl).success(function(response){
			  //As of now, response returns only id
			  var nameOfGroup = '';
			  
			  /*if(listOfNamesToBeAddedToGroup.length > 2){
				nameOfGroup = listOfNamesToBeAddedToGroup[0] + "," + listOfNamesToBeAddedToGroup[1] + " & " + (listOfNamesToBeAddedToGroup.length - 2) + " others.";  
			  }
			  else {
				  nameOfGroup = listOfNamesToBeAddedToGroup[0] + " & " + listOfNamesToBeAddedToGroup[1];  
			  }*/
			  
			  //groupSharedService.setGroupIdAndName(response,"Group");
			  $modalInstance.close('close');
		    }).then(function(data) {
		    	location.reload(false);
		    });
		  
		  
	  }
  
 
  
  
};



