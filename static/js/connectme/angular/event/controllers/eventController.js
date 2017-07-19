function EventCtrl($scope, $modalInstance, $http, personId, personName, selfId, selfName, connectedType, $rootScope) {

	$scope.personId = personId;
	$scope.personName = personName;
	$scope.selfId = selfId;
	$scope.selfName = selfName;
	$scope.jsonMessageList = [];
	$scope.showMsgArea = false;  
  
  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
  
  $scope.formData = {"otherId": $scope.personId, "creatorId": $scope.selfId, "connectedType": connectedType};
  
  $scope.processForm = function() {
	  
	  //TODO - move to Angular calendar, tried that but was facing css issues with bootstrap calendars.
	  var eventDate = $('#datetimepicker3').val();
	  
	  if($scope.formData.eventName == undefined || $scope.formData.eventName == '') {
		  alert("Please enter Event Name");
		  return false;
	  }
	  
	  if(eventDate == ''){
		  alert("Please enter Event date and time");
		  return false;
	  }else {
		  if(new Date(eventDate) < new Date()) {
			  alert("Please create an event for a future timeperiod. Past dates are not allowed");
			  return false;
		  }
		  $scope.formData.eventDate = eventDate;
	  }
	  
	  
	  
		$http({
	        method  : 'POST',
	        url: "/event",
			data    : JSON.stringify($scope.formData)  // pass in data as Json
			
	    })
	        .success(function(data) {
	            console.log(data);

	            if (data == "true") {
	            	//if successful, bind success message to message
	                //$scope.errorName = data.errors.name;
	                //$scope.errorSuperhero = data.errors.superheroAlias;
	            	alert("Your event has been created successfully !!");
	            	$modalInstance.close('close');
	            } else {
	            	// if not successful, bind errors to error variables
	                $scope.message = data.message;
	                alert("Your event could not be created, Please try creating one again.");
	                $modalInstance.close('close');
	            }
	        });
		
		
	};
  
	setTimeout(function() {
		onModalLoad();}, 0);
	
	
	
};


function onModalLoad() {
		$('#datetimepicker3').datetimepicker({
			inline:true
		});
		
		
};


