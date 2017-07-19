var messageModule = angular.module('messageModule', ['ui.bootstrap']);

function absoluteMsgHistoryController($scope,groupSharedService,$rootScope,$http,PollerOnMsgScreen,$modal,$timeout,$location, $anchorScroll, PersonIdServiceForMessageScreen) {
	
	$scope.LoadingMessageHistoryLoader = false;
	angular.element(document).ready(function() {
		PersonIdServiceForMessageScreen.setMyId($scope.selfId);
		PollerOnMsgScreen.initializePoller();
	});
	
	$scope.items = [];
	$scope.jsonMessageList = [];
	$scope.countOfMsgLoadedPerPerson = {};
	$scope.msgListPerPerson = {};
		
	$scope.safeApply = function(fn) {
		  var phase = this.$root.$$phase;
		  if(phase == '$apply' || phase == '$digest')
			this.$eval(fn);
		  else
			this.$apply(fn);
		};
	
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

	
	/*$scope.$on('handleBroadcastForOpeningMsgHistoryPage', function() {
	    //This loads the messages of the first person in the linkedPersonsList list.
                 //TODO - check case when linkedPersonsList is empty
		if($scope.linkedPersonsList.length > 0) {
			$scope.getAllPersonsLinkedToMe($scope.linkedPersonsList[0].personId,$scope.linkedPersonsList[0].personName,$scope.linkedPersonsList[0].connectedType,"normal");
		}
		else {
			TODO - check this below commented part - make sure nothing gets broken because of this undefined thing
			$scope.getAllPersonsLinkedToMe(undefined,undefined,undefined,"normal");
		}
	});*/
	
	
	$scope.$on('handleBroadcastForPolledData', function() {
	  		$scope.listOfUnreadMsgSenders = PollerOnMsgScreen.getMyData();
	  		$scope.numberOfUnreadMsgSenders = Object.keys($scope.listOfUnreadMsgSenders).length;
	  		
	  		if($scope.previouslySelectedValue == undefined) {
	  			$scope.getAllPersonsLinkedToMe(undefined,undefined,undefined,"normal");
	  		}
	  		else {
	  			$scope.getAllPersonsLinkedToMe($scope.previouslySelectedValue,$scope.previouslySelectedName,"link","poller");
	  		}
	  		
			getNoOfUnreadMsgForMessageScreen($scope.listOfUnreadMsgSenders);
	});
	
	$scope.$on('broadcastForUpdatingLinkedGroupList', function() {
			
		var id = groupSharedService.getGroupId();
		var name = groupSharedService.getGroupName();
		$scope.getAllPersonsLinkedToMe(id,name,"group","normal");
	});
	
	$scope.getNumberOfUnreadMsgOfThisPerson = function(personId){
			var val = "";
			if($scope.listOfUnreadMsgSenders[personId] > 0) {
				val = "("+$scope.listOfUnreadMsgSenders[personId]+")";
			}
			return val;
	}
	
    $scope.getAllPersonsLinkedToMe = function(idValue,nameValue,connectedValue,callSource){
    	
	    	
		$scope.linkedPersonsListTemp = [];
		$scope.linkedGroupsList = [];
	
		
	    var loadPersonsUrl = DOMAIN+"searchperson/getAllConnectedPerson";
		$http.get(loadPersonsUrl).success(function(response){
	      $scope.linkedPersonsListTemp = response;
	      
    
		  var listOfId = Object.keys($scope.listOfUnreadMsgSenders);
		  
		  //This check has been placed in order to prevent the refreshing of users in msg list unnecessarily
		  if(listOfId.length > 1 && callSource=="poller") {
			  $scope.linkedPersonsList = [];
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
		  }
		  
		  if(callSource=="normal") {
			  $scope.linkedPersonsList = [];
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
		  }
	
			/*if($scope.linkedPersonsList.length > 3){
			   addClassToElement("usersInMsgHistoryListUlId","usersInMsgHistoryListUlWithYScroll","usersInMsgHistoryListUl");	
			}
			else{
				addClassToElement("usersInMsgHistoryListUlId","usersInMsgHistoryListUl","usersInMsgHistoryListUlWithYScroll");
			}*/
			
		
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
		  if(callSource == "poller") {
			  /*$scope.highlightThisOption(idValue);
			  var x= "done";*/
		  }	
		  
		  $('ul#usersInMsgHistoryListUlId').slimscroll({
			    height: '435px',
			/*  width:'25%', */
			  alwaysVisible: true,
			  color: '#00f',
			  size: '9px'
			});
		  
		});
	    
	}
	  
	$scope.loadMessages = function(connectedType) {
		
		var loadMessagesUrl = "";
		$scope.LoadingMessageHistoryLoader = true;
		
		if(connectedType == "link"){
			loadMessagesUrl = DOMAIN+"message/getMessages?toPersonId="+$scope.personId+"&counter=1";
		}
		
		if(connectedType == "group"){
			loadMessagesUrl = DOMAIN+"groupMessage/getGroupMessages?toGroupId="+$scope.personId+"&counter=1";
		}
		
		$http.get(loadMessagesUrl).success(function(response){
		  $scope.LoadingMessageHistoryLoader = false;	
	      $scope.messageList = response;
	      $scope.msgListLoaded = true;
	      
	      $scope.countOfMsgLoadedPerPerson[$scope.personId] = 1;
	      $scope.msgListPerPerson[$scope.personId] = response;
	      
		  angular.forEach($scope.messageList, function(msgObj){
		     $scope.jsonMessageList.push(JSON.parse(msgObj));
			});
		  applyStyleOnMsgWindow();
		  
		 /* if($('ul#listOfHistoryMsgUlId').parent('.slimScrollDiv').size() > 0) {
          	$('ul#listOfHistoryMsgUlId').slimscroll({
			  start: 'bottom',
			  alwaysVisible: true,
			  color: '#00f',
			  size: '9px'
			});
		  }*/
		 
	    });
	    
	};
	
	$scope.loadMoreMessages = function(connectedType) {
		
		var loadMoreMessagesUrl = "";
		
		if(connectedType == "link"){
			loadMoreMessagesUrl = DOMAIN+"message/getMessages?toPersonId="+$scope.personId+"&counter="+(parseInt($scope.countOfMsgLoadedPerPerson[$scope.personId]) + 1);
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
		 /* if($('ul#listOfHistoryMsgUlId').parent('.slimScrollDiv').size() > 0) {
	          	$('ul#listOfHistoryMsgUlId').slimscroll({
				  start: 'bottom',
				  alwaysVisible: true,
				  color: '#00f',
				  size: '9px'
				});
			  }*/
	    });
	    
	};
	
	$scope.loadMessageWithSelectedPerson = function(value,name,connectedType){
	   
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
		  var clearUnreadMsgList = DOMAIN+"message/clearUnreadMessages?unreadMessageSenderMap="+mapString;
	       $http.get(clearUnreadMsgList).success(function(response){
                $scope.listOfUnreadMsgSenders[$scope.personId] = 0;
	     }).then(function(data) {
			   $scope.highlightThisOption(value);
			  
		 });
	   }
	   else{
	   var mapString = listOfAllId;
	   var clearUnreadMsgList = DOMAIN+"message/clearUnreadMessages?unreadMessageSenderMap="+mapString;
	    $http.get(clearUnreadMsgList).success(function(response){
                $scope.listOfUnreadMsgSenders[$scope.personId] = 0;
	    	
	    }).then(function(data) {
		   $scope.highlightThisOption(value);
	
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
		
		var addMessageBetweenPersonsUrl = DOMAIN+"message/postMessage";
		
		var msgDetails = {message: msg, personId: $scope.personId};
		 
		 $http({
			    url: addMessageBetweenPersonsUrl,
			    method: 'POST',
			    data: msgDetails,
		 		headers: {'Content-Type': 'application/json'}
			})
			.then(function(response) {
					$scope.loadMessageWithSelectedPerson($scope.personId,$scope.personName,$scope.connectedType);
					$scope.messageText = '';
			    }, 
			    function(response) { // optional
			        // failed
			    }
			);

	}
	
	if($scope.connectedType == "group") {
	    
	    var addMessageBetweenPersonsUrl = DOMAIN+"groupMessage/addGroupMessage";
	    //alert($scope.selfName);
	    
	    //var addMessageBetweenPersonsUrl = DOMAIN+"groupMessage/addGroupMessage?groupId="+$scope.personId+"&message="+msg+"&messageSender="+$scope.selfName;
	    /*$http.get(addMessageBetweenPersonsUrl).success(function(response){
			$scope.loadMessageWithSelectedPerson($scope.personId,$scope.personName,$scope.connectedType);
			$scope.messageText = '';
		});
	    */
	    var msgDetails = {message: msg, personId: $scope.personId, personName: $scope.selfName};
	    $http({
		    url: addMessageBetweenPersonsUrl,
		    method: 'POST',
		    data: msgDetails,
	 		headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
				$scope.loadMessageWithSelectedPerson($scope.personId,$scope.personName,$scope.connectedType);
				$scope.messageText = '';
		    }, 
		    function(response) { // optional
		        // failed
		    }
		);
	}
	
	
  };

  $scope.cancel = function () {
    //$modalInstance.dismiss('cancel');
    $('#myTextarea').val('');
  };
  
  $scope.clearAllMsg = function () {
	    
	  		if(!confirm("This will delete all your messages exchanged with this user, Are you sure ?")) {
	  			return false;
	  		}
	  		
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
	  
  $scope.getUserName = function(msg, connectedType){
	  
	  if(connectedType == 'link') {
		  if(msg.fromId == $scope.personId) {
			  return $scope.personName;
		  }else {
			  return "Me";
		  }
	  }
	  
	  if(connectedType == 'group') {
		  if(msg.fromId != $scope.selfId) {
			  return msg.fromName;
		  }else {
			  return "Me";
		  }
	  }
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
				if(obj.connectedType == "link" && obj.personId != selfId){
					onlyLinkedPersonsList.push(obj);
				}  
			  });
			  return onlyLinkedPersonsList;
			  
			}
			
		  }
		});
			
	};
	
    $scope.createEvent = function (personId, personName, selfId, selfName, connectedType) {

		var modalInstance = $modal.open({
		  templateUrl: 'createEventUrl',
		  controller: EventCtrl,
		  
		  resolve: {
			
			personId: function () {
			  return personId;
			},
			personName: function () {
				  return personName;
			},
			selfId: function (){
			  return parseInt(selfId);
			},
			selfName: function () {
			  return selfName;	
			},
			connectedType: function () {
				return connectedType;
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
		//$scope.openGroupDetail(personId,personName);
		  window.open(DOMAIN + "group/"+personId, '_blank');
	  }
	  else{
	  
		  window.open(DOMAIN + "searchperson/"+personId, '_blank');
			
	  }		

	  
    };
	  
}

messageModule.directive('myRepeatDirective', function($timeout) {
	  return function(scope, element, attrs) {
		    if (scope.$last){
		    	//$timeout(addColorToMsgPersonId,600,scope.previouslySelectedValue);
		    	setTimeout(addColorToMsgPersonId,600,scope.previouslySelectedValue);
		    }
		  };
		});



