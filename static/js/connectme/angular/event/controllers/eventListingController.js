var eventModule = angular.module('eventModule', ['ui.bootstrap']);


function eventListingController($scope,$rootScope,$http,$modal,$timeout,$location, $anchorScroll, eventServiceForParticipantStatus, PollerOnEventPage) {
	
	PollerOnEventPage.initializePoller();
	
	$scope.showModalPop = function() {
		angular.element(document.querySelector('#myModalPolicies')).modal('show');
	};
	
	angular.element(document).ready(function() {
		
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
	};
	
	$scope.highlightThisOption = function(id){
		addColorToMsgPersonId(id);
	};
	
	$scope.highlightThisOptionDiff = function(){
		addColorToMsgPersonId($scope.previouslySelectedValue);
	};

	
	
	
    $scope.getAllEventsLinkedToMe = function(callSource){
    	
	    	
		$scope.linkedPersonsListTemp = [];
		$scope.linkedGroupsList = [];
	
		
	    var loadEventsUrl = DOMAIN+"event/getEvents";
		$http.get(loadEventsUrl).success(function(response){
	      $scope.linkedEventsListTemp = response;
	      
    
		  
		    $scope.linkedEventsList = [];
	        angular.forEach($scope.linkedEventsListTemp, function(eventObj){
					   $scope.linkedEventsList.push(eventObj);
				});
		
			}).then(function(data) {
			
			if($scope.linkedEventsList.length > 0) {
				$rootScope.isAnyEventLinkedToMe = true;
			}
			
			$scope.loadEventDetails($scope.linkedEventsList[0].eventId, $scope.linkedEventsList[0].eventName, $scope.linkedEventsList[0].eventDate, $scope.linkedEventsList[0].eventType, $scope.linkedEventsList[0].isAdmin);
		  
		  $('ul#usersInMsgHistoryListUlId').slimscroll({
			    height: '435px',
			/*  width:'25%', */
			  alwaysVisible: true,
			  color: '#00f',
			  size: '9px'
			});
		  
		});
	    
	};
	
	$scope.ok = function(msg) {
		
		if((msg == undefined) || (msg == '')){
			  alert("Please enter msg !!!");
			  return false;
		}
		
	    	msg = msg.replace(/\n/g,'<br>');
	    
	    	var addMessageBetweenPersonsUrl = DOMAIN+"event/postUpdate";
			
			var msgDetails = {message: msg, personId: $scope.eventInfo.eventId};
			 
			 $http({
				    url: addMessageBetweenPersonsUrl,
				    method: 'POST',
				    data: msgDetails,
			 		headers: {'Content-Type': 'application/json'}
				})
				.then(function(response) {
						if($scope.currentSelectedEventDetails != undefined) {
							$scope.loadEventDetails($scope.currentSelectedEventDetails.eventId,$scope.currentSelectedEventDetails.eventName, $scope.currentSelectedEventDetails.eventDate, $scope.currentSelectedEventDetails.eventType, $scope.currentSelectedEventDetails.eventAdmin);
						}
						$scope.messageText = '';
				    }, 
				    function(response) { // optional
				        // failed
				    }
				);

		};
	  
	$scope.loadEventDetails = function(eventId, eventName, eventDate, eventType, eventAdmin) {
		
		/*
		 * currentSelectedEventDetails
		 * This currentSelectedEventDetails object has been created to store all details required to load an event, as it would be helpful in cases
		 * where we need to load an event without actually clicking it, like for ex. in case when an event update is sent, the event is again loaded.
		 */
		$scope.currentSelectedEventDetails = {};
		$scope.currentSelectedEventDetails.eventId = eventId;
		$scope.currentSelectedEventDetails.eventName = eventName;
		$scope.currentSelectedEventDetails.eventDate = eventDate;
		$scope.currentSelectedEventDetails.eventType = eventType;
		$scope.currentSelectedEventDetails.eventAdmin = eventAdmin;
		/*
		 * <!--currentSelectedEventDetails -->
		 */
		
		var loadEventDetailUrl = "";
		$scope.eventDetails = "";
		$scope.eventInfo = {};
		$scope.eventMessageDetails = [];
		$scope.eventInfo.eventName = eventName;
		$scope.eventInfo.eventDate = $scope.getMsgTime(eventDate);
		$scope.eventInfo.eventId = eventId;
		$scope.eventInfo.yesOrNo = true;
		$scope.eventInfo.yesAttending = false;
		$scope.eventInfo.noAttending = false;
				
		loadEventDetailUrl = DOMAIN+"event/getEventDetails/"+eventId+"/"+eventType;
		
		
		$http.get(loadEventDetailUrl).success(function(response){
		  
			$scope.eventDetails = response.memberDetails;
			 
			angular.forEach(response.messageDetails, function(msgObj){
					$scope.eventMessageDetails.push(JSON.parse(msgObj));
				});
			
			angular.forEach($scope.eventDetails, function(value) {
				if(value.personId == eventAdmin) {
					value.role = 'Event Creator';
				} else {
					value.role = 'Event Participant';
				}
			});
			
			var yesList = response.participantList[0].yesParticipantList;
			var noList = response.participantList[0].noParticipantList;
			$scope.participantYesNoList = response.participantList;
			
			if(yesList.indexOf(parseInt($scope.selfId)) > -1) {
				$scope.eventInfo.yesOrNo = false;
				$scope.eventInfo.noAttending = false;
				$scope.eventInfo.yesAttending = true;
			}
			if(noList.indexOf(parseInt($scope.selfId)) > -1) {
				$scope.eventInfo.yesOrNo = false;
				$scope.eventInfo.yesAttending = false;
				$scope.eventInfo.noAttending = true;
			}
	      
			
			applyStyleOnMsgWindow();
			$scope.highlightThisOption(eventId);
				 
	    });
	    
	};
	
	$scope.getMsgTime = function(msgTimeStamp){
     var monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];
	
     var myVar = (msgTimeStamp).toString();
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
  };
  
  $scope.getMsgTimeStamp = function(msgTimeStamp){
	     var monthNames = [ "January", "February", "March", "April", "May", "June",
	    "July", "August", "September", "October", "November", "December" ];
		
	     var myVar = (msgTimeStamp * 1000).toString();
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
	  };
	
	$scope.displayEventName = function(nameValue){
			return nameValue;
	};	
	
	
	$scope.showEventParticipantsModal = function(list, yesNoList) {

		var modalInstance = $modal.open({
		  templateUrl: 'eventParticipantsId',
		  controller: EventParticipantsController,
		  
		  resolve: {
			participantList: function () {
			  return list;
			},
			selfId: function () {
				return $scope.selfId;
			},
			participantYesNoList: function() {
				return yesNoList;
			}
		  }
		});
			
	};
	
	
	$scope.showEventVoteModal = function(eventIdVal) {

		var modalInstance = $modal.open({
		  templateUrl: 'eventVoteId',
		  controller: EventVoteController,
		  
		  resolve: {
			selfId: function () {
				return $scope.selfId;
			}, 
			eventId: function () {
				return eventIdVal;
			}
		  }
		});
			
	};
  
	$scope.$on('broadCastEventForParticipantStatusService', function() {
		
		if(eventServiceForParticipantStatus.getMyStatus() === 'yes') {
			$scope.eventInfo.yesOrNo = false;
			$scope.eventInfo.noAttending = false;
			$scope.eventInfo.yesAttending = true;
		} else {
			$scope.eventInfo.yesOrNo = false;
			$scope.eventInfo.yesAttending = false;
			$scope.eventInfo.noAttending = true;
		}
		 
	});
	

}



function EventParticipantsController($scope, $modalInstance, $http, participantList, participantYesNoList, selfId, $rootScope) {
	
	$scope.participantList = [];
	
	angular.forEach(participantList, function(obj) {
				
		if(participantYesNoList[0].yesParticipantList.indexOf(parseInt(obj.personId)) > -1) {
			obj.participantStatus = "Attending";
			$scope.participantList.push(obj);
		}
		else if(participantYesNoList[0].noParticipantList.indexOf(parseInt(obj.personId)) > -1) {
			obj.participantStatus = "Not Attending";
			$scope.participantList.push(obj);
		}
		else {
			obj.participantStatus = "Yet to Decide";
			$scope.participantList.push(obj);
		}
		
	});
	$scope.selfId = selfId;

	$scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	};
};

function EventVoteController($scope, $modalInstance, $http, selfId, $rootScope, eventId, eventServiceForParticipantStatus) {
	
	
	$scope.selfId = selfId;
	

	$scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	};
	
	$scope.isParticipating = function(value) {
		var voteUrl = DOMAIN+"event/isParticipating/"+eventId+"/"+value;
		
		$http.get(voteUrl).success(function(response) {
			eventServiceForParticipantStatus.setMyStatus(value);
			$modalInstance.dismiss('cancel');
		});
		
	};
};

eventModule.factory('eventServiceForParticipantStatus', function($http, $rootScope) {
	var myStatus = '';
	
	return {
        setMyStatus: function(varStatus) {
            myStatus = varStatus;
            $rootScope.$broadcast('broadCastEventForParticipantStatusService');
        },
        getMyStatus: function() {
            return myStatus;
        },
	};
	
});

eventModule.factory('PollerOnEventPage', function($http, $timeout, $rootScope) {
	  var msgData = { response: {}, calls: 0 };
	  
	  var poller = function() {
		  
		var loadMsg = DOMAIN+"message/unreadMessages";
	    $http.get(loadMsg).then(function(r) {
	    	msgData = r.data;
	    	msgData.calls++;
	      $timeout(poller, 30000);
	    }).then(function(data) {
	    	getNoOfUnreadMsgFromEventPage(msgData);
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




eventModule.filter('searchFor', function(){

	return function(arr, searchString){
		if(!searchString){
			return arr;
		}

		var result = [];
		searchString = searchString.toLowerCase();

		// Using the forEach helper method to loop through the array
		angular.forEach(arr, function(item){

			if(item.eventName.toLowerCase().indexOf(searchString) !== -1){
				result.push(item);
			}

		});

		return result;
	};

});
