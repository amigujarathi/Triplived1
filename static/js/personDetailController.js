function PersonDetailCtrl($scope, $modalInstance, $http, id, myId, pName, pDetails) {

	$scope.personId = id;
	$scope.selfId = myId;
	$scope.jsonMessageList = [];
	$scope.personName = pName;
	$scope.personDtls = pDetails;
	
$scope.tabs = [
    { title:"Personal Details", content:"Dynamic content 1" , content1:"New data 1"},
    { title:"Places you normally play", content:"Dynamic content 2" , content1:"New data 2"},
	{ title:"Photo Gallery", content:"Dynamic content 2" , content1:"New data 2"}
  ];
  
 $scope.listOfImages = [
 "http://www.hdwallpaperstop.com/wp-content/uploads/2013/05/Download-Cricket-Bat-and-Ball-Wallpaper-of-sports.jpg",
 "http://www.wallneer.com/wp-content/uploads/2013/08/free_hd_wallpapers_for_mobile_320x240.jpg",
 "http://media.zenfs.com/en_IN/News/healthmeup_india_lifestyle/paratha11-20131030-134013-681.jpg",
 "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/c18.18.232.232/s160x160/548254_10150957607581032_409308563_n.jpg"]; 
 
 
 $scope.getAllDetailsOfPerson = function(){
    $http.get(DOMAIN+"searchperson/getAllDetailsOfPerson?personId="+$scope.personId).success(function(response){
		       $scope.personDtlsList=response;
		    });
 }
 
  
};

projectModule.directive('myLastIt', function($timeout) {
	  return function(scope, element, attrs) {
	    if (scope.$last){
	    	 $timeout(renderHtlImages,600);
	    }
		
							
	  };
	});

function renderHtlImages(){
	
	$('div.htlImages').html($('div.htlImagesOrg').hide().html());
	//$('div.busImagesOrg').hide();
	$('div.htlImages ul.myGallery').removeClass('myGallery').addClass('htlGallery');

	
	//alert($('.htlGallery').length);
	//alert($('.myGallery').length);
	if($('.htlGallery').length > 0)
	{
	    $('.htlGallery').galleryView({
	        transition_speed: 900, 		//INT - duration of panel/frame transition (in milliseconds)
	        transition_interval: 900, 		//INT - delay between panel/frame transitions (in milliseconds)
	        easing: 'swing', 			//STRING - easing method to use for animations (jQuery provides 'swing' or 'linear', more available with jQuery UI or Easing plugin)
	        show_panels: true,
	        show_panel_nav: true,
	        enable_overlays: false,
	        panel_width:200, 			//INT - width of gallery panel (in pixels)
	        panel_height: 160, 			//INT - height of gallery panel (in pixels)
	        panel_animation: 'crossfade', 	//STRING - animation method for panel transitions (crossfade,fade,slide,none)
	        panel_scale: 'crop',
	        overlay_position: 'bottom',
	        start_frame: 1,
	        show_filmstrip: true,
	        show_filmstrip_nav: false,
	        enable_slideshow: false,
	        autoplay: false,
	        show_captions: false,
	        filmstrip_size: 5,
	        frame_width: 50,
	        frame_height: 50,
	        frame_opacity: 0.8,
	        frame_gap: 5
	    });
	}
}


function submitAddressForm() {
$("#addressinformation").submit();
}