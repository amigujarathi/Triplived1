projectModule.factory('mySharedService', function($http,$rootScope) {  
			
			angular.element(document).ready(function() {
			var sharedService = {};
			
			/*
			 The extra condition inside if is for refreshing the local storage when the sports list changes. This value is picked from 
			 environment.properties file
			*/
		 	if( (store.get("lastUpdatedLocalStorageForSportsList") == undefined) || (store.get("allActivityListInStore") == undefined) || (store.get("lastUpdatedLocalStorageForSportsList") != $rootScope.lastUpdatedLocalStorageForSportsList)) {
				$http.get(DOMAIN+"searchActivity/getActivityList").success(function(response){
				    $rootScope.allActivityList = response;
				    $rootScope.activityList = response;
				    $rootScope.activitySelected = true;
					store.set("allActivityListInStore", response);
					store.set("lastUpdatedLocalStorageForSportsList",$rootScope.lastUpdatedLocalStorageForSportsList);
					
					/*$rootScope.categoryList = [];
					angular.forEach($rootScope.allActivityList, function(item) {
						if($rootScope.categoryList.indexOf(item.categoryName) == -1) {
							$rootScope.categoryList.push(item.categoryName);		
						}
					});*/
					
				});
		 	}
		 	else {
		 		$rootScope.activityList = store.get("allActivityListInStore");
		 		$rootScope.allActivityList = store.get("allActivityListInStore");
				$rootScope.activitySelected = true;
				
				/*$rootScope.categoryList = [];
				angular.forEach($rootScope.allActivityList, function(item) {
					if($rootScope.categoryList.indexOf(item.categoryName) == -1) {
						$rootScope.categoryList.push(item.categoryName);		
					}
				});*/
		 	}
			
		 	/*if((store.get("lastUpdatedLocalStorageForCitiesList") == undefined) || (store.get("cities") == undefined) || (store.get("lastUpdatedLocalStorageForCitiesList") != $rootScope.lastUpdatedLocalStorageForCitiesList)) {
		 		$http.get(DOMAIN+"region/getAllCities").success(function(response){
		 			$rootScope.cityList = response;
		 			store.set("cities",response);
		 			store.set("lastUpdatedLocalStorageForCitiesList",$rootScope.lastUpdatedLocalStorageForCitiesList);
				});
		 	}
		 	else {
		 		$rootScope.cityList = store.get("cities");
		 	}*/
				   
		 	//TODO  why is below lines needed 
			sharedService.message = 'dummy';
			$rootScope.htlDtlsLoaded = false;
				   
			sharedService.prepForBroadcast = function(msg) {
				this.message = msg;
				this.htlList.push({name:msg, description:msg});
				$rootScope.test1 = "msg";
				$rootScope.$broadcast('handleBroadcast');
				$rootScope.htlDtlsLoaded = true;
			};
			
			return sharedService;
			});
});




function myController($scope,$http,mySharedService,msgHistorySharedService,$rootScope,$modal,$log,Poller,$timeout, FewLocationService) {
	
	
       /* $scope.setCategoryDropdown = function() {
        	$scope.showCategoryDropdown = true;
        };
        
		$scope.showCategoryDropdown = false;
		
		$scope.isCategoryInStore = function() {
			if(store.get('lastSearchedCategory') != undefined) {
				return true;
			}
		};*/
		
		/*$scope.categoryChange = function() {
			$rootScope.activityList = [];
			//alert('change' + $scope.selectedCategory);
			angular.forEach($rootScope.allActivityList, function(item) {
				if(item.categoryName == $scope.selectedCategory) {
					$rootScope.activityList.push(item);
				}
				
			});
			
			//store.set('lastSearchedCategory', $scope.selectedCategory);
		};*/
		
		
		/*var setCategory = function() {
			
				if(store.get('lastSearchedCategory') != undefined) {
					$scope.selectedCategory = store.get('lastSearchedCategory');
				}else {
					$scope.selectedCategory = $rootScope.categoryList[1];
				}	
				$scope.categoryChange();
			
		};*/
		
		/*$scope.$watch('categoryList', function (newValue, oldValue) {
			if(oldValue == undefined && newValue != undefined) {
				setCategory();
			}
		});*/
		
		//$timeout(setCategory, 30000);
	
	/*if(store.get('lastSearchedCategory') != undefined) {
		  $scope.showCategory(store.get('lastSearchedCategory'));
	}
	
	$scope.showCategory = function(category) {
		$scope.selectedCategory = category;
	};*/
	
	
	$scope.truncateLength = function(value) {
		if(value != undefined) {
			var tempString = value.substring(0,10);
			if(value.length > 10) {
				tempString += "..";
			}
			return tempString;
		}
	};
	
	$scope.showingRecentlyJoinedList = false;
	
	var getProfessions = DOMAIN+"profession/getProfessions";
	$http.get(getProfessions).success(function(response){
		//alert(response);
		$scope.professions = response;
	});
	
	$scope.assignProfession= function($event, professionObj) {
		var assignProfession = DOMAIN+"profession/assignProfession/"+professionObj.professionId;
		$http.get(assignProfession).success(function(response){
		   
		});
	};
	 
	$scope.populateProfessionList = function(list) {
		$scope.professions = list.split(",");
		
	};
				   
	$scope.startsWith = function(state, viewValue) {
		  return state.substr(0, viewValue.length).toLowerCase() == viewValue.toLowerCase();
	}; 
	
	$scope.getUserImage = function(user){
		
		if(user.userFrom == 'FACEBOOK') {
			return "http://graph.facebook.com/"+user.personId+"/picture?width=35&amp;height=35";
		}
		if(user.userFrom == 'SITE'){
			return imagePath = "/static/user-images/"+user.personId+"/"+user.personId+".jpg";
		}
		return "static/images/default-user.jpg";
		
	};
	
	$scope.$on('handleBroadcastForPolledData', function() {
	  var jsonMap = Poller.getMyData();
	  var unreadMsgCount = 0;
	  angular.forEach(jsonMap, function(id){
	       unreadMsgCount += id;    
	}); 
	
      $scope.dataFromPoller = unreadMsgCount;
	  $scope.getUnreadMessagesNotification();
	});
	
			
	$scope.lastMsgSeenTime = function(myId) {
		var setLastMsgSeenTime = DOMAIN+"addOrUpdate/lastMsgReadTime?personId="+myId;
		$http.get(setLastMsgSeenTime).success(function(response){
		});
	};
	
	//will not use this: after merger of city and region boxes
	$scope.showSelectedValue = function(activity, city){
		
		$scope.activityToRegionListLoaded = true;
		var valueId = activity.activityId;
		var cityId = city.id;
		
		
		if((store.get("mapOfUserRegisteredCityWithRegions") == undefined) || (store.get('mapOfUserRegisteredCityWithRegions')[cityId] == undefined) || (store.get('mapOfUserRegisteredCityWithRegions')[cityId].length == 0) ) {
			
			var getActivtyToRegionListUrl = DOMAIN+"region/getAllRegionsListForSelectedActivity?activityId="+valueId+"&cityId="+cityId;
			$http.get(getActivtyToRegionListUrl).success(function(response){
			    $scope.activityToRegionList=response;
			    $scope.activityToRegionListLoaded=false;
			    //store.set("lastUpdatedLocalStorageForAreasList",$rootScope.lastUpdatedLocalStorageForAreasList);
			    
			    var mapOfUserRegisteredCityWithRegions = new Object();
	    		mapOfUserRegisteredCityWithRegions[cityId] = response;
	    		store.set('mapOfUserRegisteredCityWithRegions', mapOfUserRegisteredCityWithRegions);
	    		 
			    
			  //  $("span#s-city").html("&#160;|&#160; City: "+ city.city);
			   // $("div#city_dropdown").css("display", "none");
			   // $("div#region_dropdown").css("display", "block");
			    
			 	// $("div#search-city-icon").find("a").addClass("nothighlighted");
				// $("div#search-city-icon").find("span.right-caret").addClass("right-caret-nothighlighted");
			    
			});
		}
		else {
			
		    $scope.activityToRegionList = store.get('mapOfUserRegisteredCityWithRegions')[cityId];
		    $scope.activityToRegionListLoaded = false;
		
		   // $("span#s-city").html("&#160;|&#160; City: "+ $scope.selectedCity.city);
		   // $("div#city_dropdown").css("display", "none");
		    //$("div#region_dropdown").css("display", "block");
		    
		 	 //$("div#search-city-icon").find("a").addClass("nothighlighted");
			// $("div#search-city-icon").find("span.right-caret").addClass("right-caret-nothighlighted");
			
		}
		//TODO - remove duplicate code from if and the else method

	};
	
	$scope.showLocation = function(location){
		 
		/* if(location.street == 'Please enter region for more relevant results') {
			 $("span#s-location").html("&#160;|&#160; Region: "+ location.street ).css({color:'red'});
		 }else {
			 $("span#s-location").html("&#160;|&#160; Region: "+ location.street ).css({color:'black'});
		 }*/
	 
	};
	
	$scope.showActivity = function(activity){
		 
		
		/*
		 * This is just for cases where the user logs in the first time ever and uses geolocation to enter his city.
		 * Hence as soon as he starts searching for sport, the query gets fired to get his regions of that city
		 */
		if($scope.selectedCity != null) {
			$scope.showSelectedValue($scope.selectedActivity,$scope.selectedCity);
		}
	};
	
	
	$scope.getLocation = function(val) {
	    return $http.get(DOMAIN+'region/getAddressOfStreet', {
	      params: {
	    	  streetAddress: val,
	      }
	    }).then(function(response){
	    	
	    	return response.data;
	    	/*return response.data
	    	$scope.userSearchedLocations = response.data;*/
	     /* return response.data.map(function(item){
	        return item;
	      });*/
	    });
	  };
	  
	
	$scope.clearLocationSearchBox = function(){
	     $scope.selectedLocation = "";
		// $scope.peopleListLoaded=false;
		 $scope.clearAllChkBoxes();
		 $scope.userselectedlocation = undefined;
	};
	
	$scope.clearActivitySearchBox = function(){
	     $scope.selectedActivity="";
		 $scope.peopleListLoaded=false;
		 $scope.clearAllChkBoxes();
	};
		
	$scope.open = function (personId, isLoggedIn) {

		if(!isLoggedIn) {
			alert("Please login to the site in order to send messages");
			return false;
		}
		
		var modalInstance = $modal.open({
		  templateUrl: 'myModalContent1',
		  controller: ModalInstanceCtrl,
		  
		  resolve: {
			id: function () {
			  return personId;
			},
			myId: function (){
			  return $scope.myPersonId;
			}
		  }
		});
			
	};
	
	$scope.openPersonDetails = function (personId,personName) {
		window.open(DOMAIN + "searchperson/"+personId, '_blank');
	};
	
	
	
	$scope.setMyIdInService = function(myId, myName){
	     msgHistorySharedService.setMyIdAndName(myId, myName);
	};
	
	$scope.switchToMsgHistoryPage = function(){
	     //alert(myId);
	     msgHistorySharedService.openMsgHistoryPage();
	};
	
	$scope.filterPersons = function(loggedInUserId) {
		return function(person) {
	        return person.personId != loggedInUserId;
	    };
	};
	
	 $scope.filterMap = {};
	 $scope.filterGenderMap = {};
	 $scope.filterAgeGroupMap = {};
		
	 var counter = 0;
	 
	 $scope.loadListingResultsNew = function($item, $model, $label){
		
		 	$scope.selectedCity = {};
			$scope.selectedCity.id = $item.cityid;
			$scope.selectedCity.city = $item.city;
			
			$scope.selectedLocation = {};
			$scope.selectedLocation.id = $item.streetid;
			$scope.selectedLocation.street = $item.street;
			
		   store.set("userSearhedArea", $item);
			
		 $scope.loadListingResults($scope.selectedActivity, $scope.selectedLocation, $scope.selectedCity, true, undefined, false);
		 
	 };
	 
	 $scope.label = function(item) {
		 if(item != null){
		    return item.street + ', ' + item.city ;
		 }
	};
	 
	 
	 //TODO to remove after merge of city and resion boxes
	 $scope.loadListingResults = function(activity, location, city, loadingFirstTime, typeOfFilter, searchWithoutLocation, isNewUser){
	  
	  $scope.searchDivClose = true;
	 /* if($scope.selectedCategory != undefined) {
		  store.set('lastSearchedCategory', $scope.selectedCategory);
	  }*/
	  
	  $scope.allFieldsSelected = true;
	  
	  $scope.displayMsg = "";
	  
 	  if(isNewUser){
 		  $scope.showingRecentlyJoinedList = true;	
 	  }else {
 		  $scope.showingRecentlyJoinedList = false;
 	  }
 	  
	  store.set('lastSearchedLocation', location);
	  store.set('lastSearchedCity', city);
	  store.set('lastSearchedSport', activity);
	  
	  
	  
	  /*
	   * This category is only for the UI purpose, it is never passed back to backend
	   */
	  //store.set('lastSearchedCategory', $scope.selectedCategory);
	  
	  
		
	  
	  
	  if(activity == undefined) {
		  $scope.myControllerActivityId = undefined;
	  }else {
		  $scope.myControllerActivityId = activity.activityId;  
	  }
      
      
      if(location == undefined) {
    	  $scope.myControllerLocationId = undefined;  
      }else {
    	  $scope.myControllerLocationId = location.id;
      }
      
      if(city == undefined) {
    	  $scope.myControllerCityId = undefined;  
      }else {
    	  $scope.myControllerCityId = city.id;  
      }
	  
      FewLocationService.setLoad(true);
	  $rootScope.$broadcast('renderFewLocations');
	  
	  if(typeOfFilter == undefined) {
		  $scope.listingType = undefined;
	  }
	  
	  if(typeOfFilter == "genderFilter"){
	    angular.forEach($scope.genderList, function(item){
		     $scope.filterGenderMap[item] = $scope.genderList[item];
		});
		$scope.filterMap["filterGenderMap"] = $scope.filterGenderMap;
	  }
	  
	  if(typeOfFilter == "ageFilter"){
	    angular.forEach($scope.ageGroupList, function(item){
		     $scope.filterAgeGroupMap[item] = $scope.ageGroupList[item];
		});
		$scope.filterMap["filterAgeGroupMap"] = $scope.filterAgeGroupMap;
	  }
	  
	  if(loadingFirstTime == true){
	    counter = 0;
	    
	    /*
	     * This check is in case, when a filter is applied and the user changes the street, this check ensures the refreshed list is shown.
	     */
	    if(typeOfFilter == undefined) {
	    	$scope.filterMap = {};
	    	$scope.filterGenderMap = {};
	    	$scope.filterAgeGroupMap = {};
	    	$scope.genderList['M'] = false;
	    	$scope.genderList['F'] = false;
	    	
	    	$('input:checkbox').removeAttr('checked');
		}
	    
	  }
	  
	  //This is for the main loader on listing page
	  if(counter == 0) {
		  $scope.peopleListLoaded = false;
	  }
	  
	  
	  var modifiedUrl = '';
	  /*
	   * The reason for the below is to fetch city level results based on any critera such as geolocation, or locationFreeSearch which may come up in future
	   */
	  
	  if(($scope.myControllerLocationId == undefined) && ($scope.myControllerCityId != undefined) && ($scope.myControllerActivityId != undefined)) {
		  modifiedUrl = DOMAIN+"filter/applyFiltersOnPerson?cityId="+$scope.myControllerCityId +"&activityId="+$scope.myControllerActivityId+
		  			   "&filterMsg="+JSON.stringify($scope.filterMap)+"&counter="+counter+"&listingType="+$scope.listingType;
	  } else if(($scope.myControllerLocationId == undefined) && ($scope.myControllerCityId == undefined) && ($scope.myControllerActivityId == undefined)) {
		  modifiedUrl = DOMAIN+"filter/applyFiltersOnPerson?filterMsg="+JSON.stringify($scope.filterMap)+"&counter="+counter+"&listingType="+$scope.listingType;
		  $scope.allFieldsSelected = false;
	  }else if(($scope.myControllerLocationId == undefined) && ($scope.myControllerActivityId == undefined) && ($scope.myControllerCityId != undefined)) {
		  modifiedUrl = DOMAIN+"filter/applyFiltersOnPerson?cityId="+$scope.myControllerCityId + "&filterMsg="+JSON.stringify($scope.filterMap)+"&counter="+counter+
		  				"&listingType="+$scope.listingType;
		  $scope.allFieldsSelected = false;
	  }
	  else if(($scope.myControllerLocationId != undefined) && ($scope.myControllerCityId != undefined) && ($scope.myControllerActivityId != undefined)) {
		  modifiedUrl = DOMAIN+"filter/applyFiltersOnPerson?cityId="+$scope.myControllerCityId +"&activityId="+$scope.myControllerActivityId+"&locationId="+
			  			$scope.myControllerLocationId+"&filterMsg="+JSON.stringify($scope.filterMap)+"&counter="+counter+"&listingType="+$scope.listingType;
	  }
	  
		$http.get(modifiedUrl).success(function(response){
			
			$scope.listingType = response.listingType; 
			
		    $scope.activityToPeopleList=$scope.activityToPeopleListTemp;
			if(counter==0) {
			  $scope.activityToPeopleList=response.resultList;
			  $scope.activityToPeopleListTemp=response.resultList;
			  
			  
			  
			}
			else {
			        $scope.lazyLoadingResults = false;
					angular.forEach(response.resultList, function(item) {
					$scope.activityToPeopleList.push(item);
					$scope.activityToPeopleListTemp=$scope.activityToPeopleList;
				});
			}
		    $scope.peopleListLoaded = true;
		    $scope.searchDivClose = false;
			counter +=5;
			
			if(response.resultList.length==0){
			    $scope.allResultsLoaded = false;
				if(loadingFirstTime == false){
			      $scope.allResultsLoaded = true;
				  $scope.displayMsg = "All People shown !!";
				}
				if(loadingFirstTime == true){
				$scope.allResultsLoaded = true;
			      $scope.displayMsg = "No People with matching interests Found !!";
				}
			 }
			 else {
				  $scope.allResultsLoaded = false;
			 }
			 
			});
		
	
	};
	 
	$scope.clearAllChkBoxes = function(){
	
		angular.forEach($scope.genderList, function(item){
		     $scope.genderList[item] = false;
		});
		
		angular.forEach($scope.ageGroupList, function(item){
		     $scope.ageGroupList[item] = false;
		});
	};
	 
	$scope.genderList = ['M','F'];
	$scope.ageGroupList = ['0-10','10-20','20-30','30-40','40-50'];
	
	$scope.getUnreadMessagesNotification = function() {
	  if($scope.isPersonLoggedIn) {
	  getNoOfUnreadMsg();
	  }
	};
	
	$scope.safeApply = function(fn) {
	  var phase = this.$root.$$phase;
	  if(phase == '$apply' || phase == '$digest')
		this.$eval(fn);
	  else
		this.$apply(fn);
	};
	
	$scope.getMsgTime = function(msgTimeStamp){
    /* var monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];*/
	
     var myVar = (msgTimeStamp*1000).toString();
     var value = new Date(parseInt(myVar.replace(/(^.*\()|([+-].*$)/g, '')));
     
     var daysBefore = ((new Date() - value)/(1000*60*60*24)).toFixed(0);
     if(daysBefore === "0") {
    	 return "Today.";
     } else {
    	 return daysBefore + " days ago.";
     }
	 
	
  };
 
                    
	$scope.getPersonGender = function(genderValue) {
		if(genderValue == "M") {
			
			return "Male";
			//return '<a href=""><i class="fa fa-male"></i> fa-male</a>';
		}
		else {
			return "Female";
			//return '<a href=""><i class="fa fa-female"></i> fa-female</a>';
		}
	};
		
	
	if(store.get('userSearhedArea') != undefined){
		
		var userselectedlocation = store.get('userSearhedArea') ;
		$scope.userselectedlocation = userselectedlocation;
	}

	/*
	 * This call is the default call, when a user logs in to the app.
	 * His last search results of the same browser that he is using are shown
	 */		
	
	
	
	if(store.get('lastSearchedCity') != undefined){
		$scope.selectedCity = store.get('lastSearchedCity');	
	
		if(store.get('lastSearchedSport') != undefined) {
			$scope.selectedActivity = store.get('lastSearchedSport');
			
			if(store.get('lastSearchedLocation') != undefined) {
				$scope.selectedLocation = store.get('lastSearchedLocation');
				$scope.loadListingResults($scope.selectedActivity, $scope.selectedLocation, $scope.selectedCity, true, undefined, false);
				
			}else {
				$scope.selectedLocation = {};
				$scope.selectedLocation.id = undefined;
				$scope.selectedLocation.street = 'Please enter region for more relevant results';
				$scope.loadListingResults($scope.selectedActivity, undefined, $scope.selectedCity, true, undefined, true);
			}
			
			$scope.showLocation($scope.selectedLocation);
			$scope.showActivity($scope.selectedActivity);
			$scope.showSelectedValue($scope.selectedActivity, $scope.selectedCity);
		}else {
			$scope.loadListingResults(undefined, undefined, $scope.selectedCity, true, undefined, false, true);
			$scope.listingMessage = "Recently Joined People in your city and growing tremendously day by day !!!";
			$scope.shortListingMessage = "Recently Joined People in your city!!!"; //for smaller devices
		}
		
	}	
	
	
	/*
	 * When a user comes for the first time, we show him the latest visitors who have joined our site, to avoid blank listing page
	 */
	if(store.get('lastSearchedCity') == undefined && store.get('lastSearchedSport') == undefined && store.get('lastSearchedLocation') == undefined){
		
		$scope.showingRecentlyJoinedList = true;
		
		if(store.get("geoCityInterestify") != undefined && !(store.get("geoCityInterestify") == "NotFound")) {
				$scope.selectedCity = {};
				$scope.selectedCity.id = store.get("geoCityInterestify").split('##')[2];
				$scope.selectedCity.city = store.get("geoCityInterestify").split('##')[1];
				
				$scope.loadListingResults(undefined, undefined, $scope.selectedCity, true, undefined, false, true);
				$scope.listingMessage = "Recently Joined People in your city and growing tremendously day by day !!!";
				$scope.shortListingMessage = "Recently Joined People in your city!!!"; //for smaller devices
		}else {
				$scope.loadListingResults(undefined, undefined, undefined, true, undefined, false, true);
				$scope.listingMessage = "Recently Joined People in our community and growing tremendously day by day !!!";
				$scope.shortListingMessage = "Recently Joined People in our community!!!"; //for smaller devices
		}
	}
	
	
	/*
	 * This method is for checking if events exist for a user so that the event screen is not displayed in case of no events.
	 * TODO: execute code on java side to only just check and return true or false, not the entire list
	 */
	$scope.checkUserEvents = function() {
		var getEventsUrl = DOMAIN + "event/getEvents";
		$http.get(getEventsUrl).success(function(response){
			
			if(response.length > 0) {
				anyEventsLinkedToMe = true;
			}else {
				anyEventsLinkedToMe = false;
			}
		});
	};
	
	$scope.checkUserEvents();
}



function ModalInstanceCtrl($scope, $modalInstance, $http, id, myId,$rootScope,Poller) {
	
	
	$scope.personId = id;
	$scope.selfId = myId;
	$scope.showMsgHistory = false;
	$scope.status = "";

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
		if(!$rootScope.isAnyPersonLinkedToMe) {
			$rootScope.isAnyPersonLinkedToMe = true;
		}
		$scope.status = "Your message has been sent !!";
		
		setTimeout(function() {
			$modalInstance.close('close');
		}, 1000);
		
	});
	
	
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
};












