function GroupCtrl($scope, $modalInstance, $http, personId, personName, selfId,selfName,allLinkedPersons,$rootScope,groupSharedService) {

	$scope.personId = personId;
	$scope.personName = personName;
	$scope.selfId = selfId;
	$scope.selfName = selfName;
	$scope.jsonMessageList = [];
	$scope.allLinkedPersons = allLinkedPersons;  
	$scope.groupList = [];
	$scope.selectedGroupName = '';
	  

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
  
  angular.element(document).ready(function() {
	  $('a#groupDetailHelp').tooltip();
	  $('a#groupDetailInput').tooltip();
  });
  
  
  $scope.addToGroupList = function(personObj){
	  
	  //TODO - understand how ng-options works with unique filter as this would help us avoid below duplicate check
	  var isAdded = false;
	  angular.forEach($scope.groupList, function(val){
		  if(val.personId == personObj.personId){
			alert("The person is already added in the Chat list");
			isAdded = true;
		  }
	  	});
	  
	  if(isAdded == false)
		  $scope.groupList.push(personObj);
	  //$scope.selectedPerson="ss";
	  //alert($scope.selectedPerson);
	  //TODO - understand why selectedPerson here is giving error undefined
	  
  }
  
  $scope.saveGroupListToDb = function(msgText, descText){
	  
	  //No blank messages allowed
	  if((typeof(msgText) == "undefined") || (msgText.length == 0)) {
		  alert("Please enter message !!!");
		  return false;
	  }
	  
	  var listOfIdToBeAddedToGroup = [];
	  var listOfNamesToBeAddedToGroup = [$scope.selfName];
	  
	  angular.forEach($scope.groupList, function(val){
		  listOfIdToBeAddedToGroup.push(val.personId);
		  listOfNamesToBeAddedToGroup.push(val.personName);
	  });
	  
	  if(listOfIdToBeAddedToGroup.length < 2){
		  alert("Please select at least two participants for group messages !!!");
		  return false;
	  }
	  
	  if((getGroupNameOfCreatedGroup() == undefined) || (getGroupNameOfCreatedGroup() == '')){
		  alert("Please enter Group Name !!!");
		  return false;
	  }
	  
	  //var mapGroup = JSON.stringify(listOfIdToBeAddedToGroup);
	  var saveGroupToDbUrl = DOMAIN+"group/createGroup?adminId="+$scope.selfId+"&listOfPersonIdToBeLinked="+listOfIdToBeAddedToGroup+"&groupName="+getGroupNameOfCreatedGroup()+"&message="+msgText+"&messageSender="+$scope.selfName+"&description="+descText;
	    
	  $http.get(saveGroupToDbUrl).success(function(response){
		  //As of now, response returns only id
		  var nameOfGroup = '';
		  
		  /*if(listOfNamesToBeAddedToGroup.length > 2){
			nameOfGroup = listOfNamesToBeAddedToGroup[0] + "," + listOfNamesToBeAddedToGroup[1] + " & " + (listOfNamesToBeAddedToGroup.length - 2) + " others.";  
		  }
		  else {
			  nameOfGroup = listOfNamesToBeAddedToGroup[0] + " & " + listOfNamesToBeAddedToGroup[1];  
		  }*/
		  
		  groupSharedService.setGroupIdAndName(response,getGroupNameOfCreatedGroup());
		  $modalInstance.close('close');
	    });
	  
	  
  }
  
  $scope.modifyGroupList = function(item){
	  var itemIndex = $scope.groupList.indexOf(item); 
	  $scope.groupList.splice(itemIndex,1);
	  
  };
  
};




//TODO - this is not working with ng-model in textbox of GroupName
getGroupNameOfCreatedGroup = function() {
	  return $("#groupNameInput").val();
};

