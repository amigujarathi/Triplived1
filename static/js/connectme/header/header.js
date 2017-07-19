$(document).ready(
	function() {
		var arr = window.location.href.split('/');
		var page = arr[arr.length - 1];
		if ((page == "termsandconditions") || (page == "aboutus")
				|| (page == "contactus") || (page == "profile")) {
			disableMsgButtons();
		}
});

function disableMsgButtons() {
	$("a#green_mail_id").hide();
	$("a#red_mail_id").hide();
}

function getNoOfUnreadMsg() {
	var scope = angular.element("#listingPageId").scope();

	scope.safeApply(function() {
		//document.getElementById("unreadMsgTextBox").value = scope.dataFromPoller;
		if (scope.dataFromPoller > 0) {
			//$("#switchToHistoryMsgScreenBtnId").addClass('switchToHistoryMsgScreenBtnColor');
			$("a#red_mail_id").show();
			$("a#green_mail_id").hide();
		} else {
			$("a#red_mail_id").hide();
			$("a#green_mail_id").show();
		}

	});
}

function getNoOfUnreadMsgFromEventPage(jsonMap) {
	var scope = angular.element("#eventContent").scope();

	scope.safeApply(function() {

		var unreadMsgCount = 0;
		angular.forEach(jsonMap, function(id, value) {
			if (value != "calls") {
				unreadMsgCount += id;
			}
		});

		if (unreadMsgCount > 0) {
			$("a#red_mail_id").show();
			$("a#green_mail_id").hide();
		} else {
			$("a#red_mail_id").hide();
			$("a#green_mail_id").show();
		}

	});

}

function getNoOfUnreadMsgForMessageScreen(jsonMap) {
	var scope = angular.element("#msgHistoryPage").scope();
	var scope = angular.element("#msgHistoryPage-responsive").scope();

	scope.safeApply(function() {

		var unreadMsgCount = 0;
		angular.forEach(jsonMap, function(id, value) {
			if (value != "calls") {
				unreadMsgCount += id;
			}
		});

		if (unreadMsgCount > 0) {
			$("a#red_mail_id").show();
			$("a#green_mail_id").hide();
		} else {
			$("a#red_mail_id").hide();
			$("a#green_mail_id").show();
		}

	});

}

function getNoOfUnreadMsgFromPersonDetailPage(isNewMsgFlag) {

	var scopeOfPersonDetailPage = '';

	if (angular.element("#personInfoId").scope() != undefined) {
		scopeOfPersonDetailPage = angular.element("#personInfoId").scope();
	}
	if (angular.element("#groupInfoId").scope() != undefined) {
		scopeOfPersonDetailPage = angular.element("#groupInfoId").scope();
	}

	scopeOfPersonDetailPage.safeApply(function() {

		var jsonMap = isNewMsgFlag;
		var unreadMsgCount = 0;
		angular.forEach(jsonMap, function(id, value) {
			if (value != "calls") {
				unreadMsgCount += id;
			}
		});

		if (unreadMsgCount > 0) {
			$("a#red_mail_id").show();
			$("a#green_mail_id").hide();
		} else {
			$("a#red_mail_id").hide();
			$("a#green_mail_id").show();
		}

	});

}

function switchToHistoryMsgScreen() {
	var scope = angular.element("#listingPageId").scope();

	scope.safeApply(function() {

		if (scope.isAnyPersonLinkedToMe == true) {
			scope.showListingPage = false;
			scope.showBtn = false;
			//scope.lastLoggedTime();
			//scope.switchToMsgHistoryPage();
			switchToNewHistoryMsgScreen();
		} else {
			alert("You currently have no messages from any user");
		}

	});
}

function switchToNewHistoryMsgScreen() {
	//var id = LOGGED_IN_USER;
	window.location = DOMAIN + "message/responsive";
}

function switchToEventScreen() {
	if (anyEventsLinkedToMe == true) {
		window.location = DOMAIN + "event";
	} else {
		alert('You do not have any events yet');
	}

}

function switchToListingPageScreen() {

	// This check is to handle click on logo when we are on a non-angular page
	if (typeof angular === 'undefined') {
		window.location = DOMAIN + "home";
	}
	;

	var scope = angular.element("#listingPageId").scope();

	if (scope == undefined) {
		window.location = DOMAIN + "home";
	}
	;
	//var scope = angular.element(document.getElementById('#listingPageId')).scope().info('me')
	scope.safeApply(function() {
		scope.showListingPage = true;
		//scope.showBtn=false;
		//scope.switchToMsgHistoryPage();
	});
}

var url = window.location.href;
if (url.indexOf("extended") != -1) {
	$("a#red_mail_id").hide();
	$("a#green_mail_id").hide();
}
$("#calendarIconHeader").attr('title', 'Click here to load Events screen');
$("#homeIconHeader").attr('title', 'Click here to go to Home screen');
$("#messageIconHeader").attr('title', 'Click here to load Message screen');

function changeimage(){
	
	var userImg = $("#uploaded-img")[0] ;
	userImg.src = "/static/images/default-user.jpg";
	return ;

}

$(function(){
	  var hash = window.location.hash;
	  hash && $('div.tabsbar ul li a[href="' + hash + '"]').tab('show');

	  $('div.tabsbar a').click(function (e) {
	    $(this).tab('show');
	    var scrollmem = $('body').scrollTop();
	    window.location.hash = this.hash;
	    $('html,body').scrollTop(scrollmem);
	  });
	});