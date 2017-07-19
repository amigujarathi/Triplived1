function msgHistoryController($scope,msgHistorySharedService,groupSharedService,$rootScope,$http,Poller,$modal,$timeout,$location, $anchorScroll) {
		
	$scope.items = [];
	$scope.jsonMessageList = [];
	$scope.countOfMsgLoadedPerPerson = {};
	$scope.msgListPerPerson = {};
		
	
	$scope.getUserImage = function(userId){
		
		var imagePath = "/static/user-images/"+userId+"/"+userId+".jpg";
		return imagePath;
	}
	
	$scope.highlightThisOption = function(id){
		addColorToMsgPersonId(id);
	}
	
	$scope.highlightThisOptionDiff = function(){
		addColorToMsgPersonId($scope.previouslySelectedValue);
	}

	$scope.$on('handleBroadcastForPassingUserIdToMsgHistory', function() {
        
        $scope.selfId = msgHistorySharedService.getMyId();
        //The selfName parameter is currently required for creating group names
        $scope.selfName = msgHistorySharedService.getMyName();
    }); 
	
	$scope.$on('handleBroadcastForOpeningMsgHistoryPage', function() {
	    //This loads the messages of the first person in the linkedPersonsList list.
                 //TODO - check case when linkedPersonsList is empty
		if($scope.linkedPersonsList.length > 0) {
			//$scope.showListingPage = false;
			$scope.getAllPersonsLinkedToMe($scope.linkedPersonsList[0].personId,$scope.linkedPersonsList[0].personName,$scope.linkedPersonsList[0].connectedType,"normal");
		}
		else {
			/*TODO - check this below commented part - make sure nothing gets broken because of this undefined thing*/
			$scope.getAllPersonsLinkedToMe(undefined,undefined,undefined,"normal");
		}
	});
	
	
	$scope.$on('handleBroadcastForPolledData', function() {
	  		$scope.listOfUnreadMsgSenders = Poller.getMyData();
	  		$scope.numberOfUnreadMsgSenders = Object.keys($scope.listOfUnreadMsgSenders).length;
			$scope.getAllPersonsLinkedToMe($scope.previouslySelectedValue,$scope.previouslySelectedName,"link","poller");
			getNoOfUnreadMsg();
			
			//This call retains the highlighted option in the left message list when the user is on the msgController view
			// and polling occurs.
			$timeout($scope.highlightThisOptionDiff, 50);
	});
	
	$scope.$on('broadcastForUpdatingLinkedGroupList', function() {
			
		var id = groupSharedService.getGroupId();
		var name = groupSharedService.getGroupName();
		//$scope.loadMessageWithSelectedPerson(id,name,"group");
		$scope.getAllPersonsLinkedToMe(id,name,"group","normal");
	});
	
	$scope.getNumberOfUnreadMsgOfThisPerson = function(personId){
	    return $scope.listOfUnreadMsgSenders[personId];
	}
	
    $scope.getAllPersonsLinkedToMe = function(idValue,nameValue,connectedValue,callSource){
    	
	    $scope.linkedPersonsList = [];	
		$scope.linkedPersonsListTemp = [];
		$scope.linkedGroupsList = [];
	
		
	    var loadPersonsUrl = DOMAIN+"searchperson/getAllConnectedPerson" ;
		$http.get(loadPersonsUrl).success(function(response){
	      $scope.linkedPersonsListTemp = response;
    
		    var listOfId = Object.keys($scope.listOfUnreadMsgSenders);
            angular.forEach($scope.linkedPersonsListTemp, function(personObj){
		    if(listOfId.indexOf(String(personObj.personId)) != -1){
			   $scope.linkedPersonsList.push(personObj);
		    }
			});
	
			angular.forEach($scope.linkedPersonsListTemp, function(personObj){
				if(listOfId.indexOf(String(personObj.personId)) == -1){
				   $scope.linkedPersonsList.push(personObj);
				}
			});
	
			if($scope.linkedPersonsList.length > 3){
			   addClassToElement("usersInMsgHistoryListUlId","usersInMsgHistoryListUlWithYScroll","usersInMsgHistoryListUl");	
			}
			else{
				addClassToElement("usersInMsgHistoryListUlId","usersInMsgHistoryListUl","usersInMsgHistoryListUlWithYScroll");
			}
			
		
		}).then(function(data) {
			
			if($scope.linkedPersonsList.length > 0) {
				$rootScope.isAnyPersonLinkedToMe = true;
			}
			//This check is to ensure that the user doesn't see a distorted UI in case of no messages.
			
			if(callSource == "normal") {
			//This check is to ensure that on poller update, the messages of a person are not updated, the messages are updated only in case where a user clicks
			//on the person(in left window) or when the message icon on the listing page is clicked.
			
			if(idValue != undefined) {
				
				if(connectedValue == "group"){
					$scope.loadMessageWithSelectedPerson(idValue,nameValue,connectedValue);
				}
				else if(connectedValue == "link"){
					$scope.loadMessageWithSelectedPerson(idValue,nameValue,connectedValue);
				}
			}
			if(idValue == undefined){
				if($scope.linkedPersonsList.length > 0){
					$scope.loadMessageWithSelectedPerson($scope.linkedPersonsList[0].personId,$scope.linkedPersonsList[0].personName,$scope.linkedPersonsList[0].connectedType);
				}
			}
		  }
		});
	    
	}
	  
	$scope.loadMessages = function(connectedType) {
		
		var loadMessagesUrl = "";
		
		if(connectedType == "link"){
			loadMessagesUrl = DOMAIN+"message/getMessages?fromPersonId="+$scope.personId+"&toPersonId="+$scope.selfId+"&counter=1";
		}
		
		if(connectedType == "group"){
			loadMessagesUrl = DOMAIN+"groupMessage?toGroupId="+$scope.personId+"&counter=1";
		}
		
		$http.get(loadMessagesUrl).success(function(response){
	      $scope.messageList = response;
	      $scope.msgListLoaded = true;
	      
	      $scope.countOfMsgLoadedPerPerson[$scope.personId] = 1;
	      $scope.msgListPerPerson[$scope.personId] = response;
	      
		  angular.forEach($scope.messageList, function(msgObj){
		     $scope.jsonMessageList.push(JSON.parse(msgObj));
			});
		  applyStyleOnMsgWindow();
		  
		  if($('ul#listOfHistoryMsgUlId').parent('.slimScrollDiv').size() > 0) {
          	$('ul#listOfHistoryMsgUlId').slimscroll({
			  start: 'bottom',
			  alwaysVisible: true,
			  color: '#00f',
			  size: '9px'
			});
		  }
		 
	    });
	    
	};
	
	$scope.loadMoreMessages = function(connectedType) {
		
		var loadMoreMessagesUrl = "";
		
		if(connectedType == "link"){
			loadMoreMessagesUrl = DOMAIN+"message/getMessages?fromPersonId="+$scope.personId+"&toPersonId="+$scope.selfId+"&counter="+(parseInt($scope.countOfMsgLoadedPerPerson[$scope.personId]) + 1);
		}
		
		if(connectedType == "group"){
			loadMoreMessagesUrl = DOMAIN+"groupMessage/getGroupMessages?toGroupId="+$scope.personId+"&counter="+(parseInt($scope.countOfMsgLoadedPerPerson[$scope.personId]) + 1);
		}
		
		$http.get(loadMoreMessagesUrl).success(function(response){
		  $scope.messageList = response;
		  if($scope.messageList.length == 0) {
			  $scope.msgListLoaded = false;
		  }
	      
	      var msgLoadCount = parseInt($scope.countOfMsgLoadedPerPerson[$scope.personId]) + 1;
	      $scope.countOfMsgLoadedPerPerson[$scope.personId] = msgLoadCount;
	      
		  angular.forEach($scope.messageList, function(msgObj){
		     $scope.jsonMessageList.push(JSON.parse(msgObj));
			});
		  applyStyleOnMsgWindow();
		  if($('ul#listOfHistoryMsgUlId').parent('.slimScrollDiv').size() > 0) {
	          	$('ul#listOfHistoryMsgUlId').slimscroll({
				  start: 'bottom',
				  alwaysVisible: true,
				  color: '#00f',
				  size: '9px'
				});
			  }
	    });
	    
	};
	
	$scope.loadMessageWithSelectedPerson = function(value,name,connectedType){
	   
	   //$scope.highlightThisOption(value);
	   $scope.previouslySelectedValue = value;
	   $scope.previouslySelectedName = name;
	   $scope.personId = value;
	   $scope.personName = name;
	   $scope.connectedType = connectedType;
	   $scope.jsonMessageList = [];
	   $scope.loadMessages(connectedType);
	   
	   
	   var listOfAllId = [];
	   var listOfUniqueId = Object.keys($scope.listOfUnreadMsgSenders);
	   angular.forEach(listOfUniqueId, function(id){
	       if(id != $scope.personId) {
			   var count = $scope.listOfUnreadMsgSenders[id];
			   for(var i = 0 ; i<count; i++){
				 listOfAllId.push(id);
			}
		   }
	   });
	   
	   if(listOfAllId.length == 0) {
		  /*The below method removes the notification color from the button at the top right corner(RED Color)*/
		  chooseMsgNotificationIcon();
	      $scope.listOfUnreadMsgSenders[$scope.personId] = 0;
		  var mapString = listOfAllId;
		  var clearUnreadMsgList = DOMAIN+"message/clearUnreadMessages?unreadMessageSenderMap="+mapString+"&personId="+$scope.selfId;
	       $http.get(clearUnreadMsgList).success(function(response){
                $scope.listOfUnreadMsgSenders[$scope.personId] = 0;
	     }).then(function(data) {
			   $scope.highlightThisOption(value);
			  // $location.hash(value);
			  // $anchorScroll();
			     var scrollTo_val = $("ul#usersInMsgHistoryListUlId li").index($("#li-"+value)) * 70 ;
				 $('ul#usersInMsgHistoryListUlId').slimScroll({scrollTo : (scrollTo_val+"px")});
		 });
	   }
	   else{
	   var mapString = listOfAllId;
	   var clearUnreadMsgList = DOMAIN+"message/clearUnreadMessages?unreadMessageSenderMap="+mapString+"&personId="+$scope.selfId;
	    $http.get(clearUnreadMsgList).success(function(response){
                $scope.listOfUnreadMsgSenders[$scope.personId] = 0;
	    	
	    }).then(function(data) {
		   $scope.highlightThisOption(value);
		 //  $location.hash(value);
		 // $anchorScroll();
		    var scrollTo_val = $("ul#usersInMsgHistoryListUlId li").index($("#li-"+value)) * 70 ;
			$('ul#usersInMsgHistoryListUlId').slimScroll({scrollTo : (scrollTo_val+"px")});
		});
	    
	  }
	   
	   
	   //This helps in moving scroller to the desired element clicked.
		    
	};
	
	$scope.ok = function (msg) {
	
	if((msg == undefined) || (msg == '')){
		  alert("Please enter msg !!!");
		  return false;
	}
	
    msg = msg.replace(/\n/g,'<br>');
    
	if($scope.connectedType == "link") {
    var addMessageBetweenPersonsUrl = DOMAIN+"message/addMessage?toPersonId="+$scope.personId+"&message="+msg;
	}
	if($scope.connectedType == "group") {
	    var addMessageBetweenPersonsUrl = DOMAIN+"groupMessage/addGroupMessage?groupId="+$scope.personId+"&message="+msg+"&messageSender="+$scope.selfName;
	}
	
	$http.get(addMessageBetweenPersonsUrl).success(function(response){
		$scope.loadMessageWithSelectedPerson($scope.personId,$scope.personName,$scope.connectedType);
		$scope.messageText = '';
	});
	//$modalInstance.close('close');
	
  };

  $scope.cancel = function () {
    //$modalInstance.dismiss('cancel');
    $('#myTextarea').val('');
  };
  
  $scope.clearAllMsg = function () {
	    
	  		if($scope.connectedType == "link") {
	  			var clearMessages = DOMAIN+"message/clearMessages?toId="+$scope.personId;
			}
			if($scope.connectedType == "group") {
			    var clearMessages = DOMAIN+"groupMessage/clearMessages?toId="+$scope.personId;
			}
			
			$http.get(clearMessages).success(function(response){
				$scope.loadMessageWithSelectedPerson($scope.personId,$scope.personName,$scope.connectedType);
				//$scope.messageText = '';
			});   
  };
	  
	$scope.getMsgTime = function(msgTimeStamp){
     var monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];
	
     var myVar = (msgTimeStamp*1000).toString();
     var value = new Date(parseInt(myVar.replace(/(^.*\()|([+-].*$)/g, '')));
	 
	 var monthName = monthNames[value.getMonth()].substring(0,3);
	 var msgDate = monthName + 
                       "-" + 
           value.getDate() + 
                       "-" + 
       value.getFullYear();
	   
      var hours = value.getHours();
	  var minutes = value.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
  
	  
	 return msgDate + "   :   " + strTime;
  }
	
	$scope.displayPersonOrGroupName = function(nameValue, connectedTypeValue){
		if(connectedTypeValue == 'group'){
			return nameValue;
		}
		else
			return nameValue;
	}	
  
    $scope.createGroup = function (personId, personName, selfId, selfName) {

		var modalInstance = $modal.open({
		  templateUrl: 'createGroupUrl',
		  controller: GroupCtrl,
		  
		  resolve: {
			
			personId: function () {
			  return personId;
			},
			personName: function () {
				  return personName;
			},
			selfId: function (){
			  return selfId;
			},
			selfName: function () {
			  return selfName;	
			},
			allLinkedPersons: function (){
			  
			  var onlyLinkedPersonsList = [];
			  angular.forEach($scope.linkedPersonsList, function(obj){
				if(obj.connectedType == "link"){
					onlyLinkedPersonsList.push(obj);
				}  
			  });
			  return onlyLinkedPersonsList;
			  
			}
			
		  }
		});
			
	};
	
		
	
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
		
  
  $scope.openPersonDetailsInMsgHistory = function (personId,personName,connectedType) {

	  if(connectedType == "group") {
		$scope.openGroupDetail(personId,personName);  
	  }
	  else{
	  
	    /*var personDtlsList= [];
	    $http.get(DOMAIN+"searchperson/getAllDetailsOfPerson?personId="+personId).success(function(response){
		       personDtlsList=response;
			   
		  var modalInstance = $modal.open({
		  templateUrl: 'myModalContent3',
		  controller: PersonDetailCtrl,
		  
		  resolve: {
			id: function () {
			  return personId;
			},
			myId: function (){
			  return $scope.myPersonId;
			},
			pName: function (){
			  return personName;
			},
			pDetails: function (){
			  return personDtlsList;
			}
		  }
		});
		
		
		});*/
		  
		  window.location.href = DOMAIN + "searchperson/getDetailsOfPersonHtml?personId="+personId;
		  //TODO - the selfId would have to be passed on the Person Detail page, since it is required for the poller on that page.
		  //window.location.href = DOMAIN + "searchperson/getDetailsOfPersonHtml?personId="+personId;
			
	  }		

	  
    };
	  
}



