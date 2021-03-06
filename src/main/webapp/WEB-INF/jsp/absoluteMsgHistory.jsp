<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page isELIgnored="false"%>


<link rel="stylesheet" type="text/css" href="${styles_url}/screen.css"/>
<!--<link rel="stylesheet" type="text/css" href="${css_url}/listingPage.css"/> -->
 <!-- <link rel="stylesheet" type="text/css" href="http://getbootstrap.com/dist/css/bootstrap.css" >  -->   
 <link rel="stylesheet" href="${css_url}/twiter/bootstrap.css">
<!-- <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet"> -->
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="styles/ie.css" />
    <style>
   	 .hover_block{ background-color:#000; opacity:1; filter:alpha(opacity=100); }
    </style>
<![endif]-->
<!--[if IE 6]>
	<link rel="stylesheet" type="text/css" href="styles/ie6.css" />
<![endif]-->
<!-- scripts -->
<script type="text/javascript" src="${url}/jquery.min.js"></script>
<script type="text/javascript" src="${url}/add2bucket.js" ></script>
<script type="text/javascript" src="${url}/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="${url}/jquery.limit-1.2.source.js"></script>
<script type="text/javascript" src="${url}/jquery.galleryview.js"></script>
<script type="text/javascript" src="${url}/jquery.custom.js"></script>
<script type="text/javascript" src="${url}/twiter/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/twiter/typeahead/typeahead.min.js"></script>
<script type="text/javascript" src="${url}/twiter/template/hogan-2.0.0.js"></script>

<%-- <script type="text/javascript" src="${url}/jquery.ui.slider.js"></script> --%>
<script type="text/javascript" src="${url}/jquery-ui.min.js"></script>
<script type="text/javascript" src="${url}/jquery/scroll/jquery.slimscroll.js"></script>

<script type="text/javascript" src="${url}/angular.min.js"></script>
<script type='text/javascript' src='${url}/ng-infinite-scroll.min.js'></script>
<script type="text/javascript" src="${url}/ui-bootstrap-tpls-0.6.0.min.js"></script>

<!--  Local storage -->
	<script type="text/javascript" src="${url}/html5utils/store.min.js"><!-- script --></script>	

<script type="text/javascript" src="${url}/connectme/angular/message/controllers/absoluteMsgHistoryController.js"></script>

<script type="text/javascript" src="${url}/connectme/angular/message/services/messageServices.js"></script>

<script type="text/javascript" src="${url}/groupController.js"></script>

<script type="text/javascript" src="${url}/html5utils/store.min.js"></script>	

<section class="container1">
<body ng-app="messageModule" ng-cloak>


<div ng-controller="absoluteMsgHistoryController">

<div ng-init="selfId = '${selfId}'"></div>
<div ng-init="selfName = '${selfName}'"></div>
	
	 <div id="msgHistoryPage" class="" style="height:600px;">
	 	<div class="wrapper msgHistory_listing_div" >
	
<script type='text/javascript'>
$(document).ready(function() {
	
});
</script>
	
<script type="text/ng-template" id="createGroupUrl">

<div class="modal" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
 		<a class="flR cross_icon" href="#" ng-click="cancel()">
			<img src="${images_url}/../cross_icon.gif"></img>
        </a>
 
        <div class="modal-header">
			<h3>Create group Message !!! 	
			</h3>
			<input placeholder="Group Name:" type="text" ng-model="selectedGroupName" id="groupNameInput">
        </div>
		
        <div class="modal-body" >
			<input id="groupDetailInput" data-toggle="tooltip" title="Only people with whom you have interacted before can be part of the group" placeholder="Add people to Group" type="text" ng-model="selectedPerson" typeahead-on-select="addToGroupList(selectedPerson);selectedPerson=''" 
			typeahead="personObj as personObj.personName for personObj in allLinkedPersons | filter:{personName: $viewValue} | limitTo:5">
			<a href="#" id="groupDetailHelp" data-toggle="tooltip" title="Only people with whom you have interacted before can be part of the group"><img src="/static/images/question.jpg"></img></a>
			
        </div>

		<div style="padding:8px;margin-bottom:30px" >
			<div ng-repeat="personValue in groupList" >
				<span style="background-color: #B0E5F8; border-radius: 6px;  float: left;  margin: 5px;  padding-left: 3px; color:#000000; height:22px">
					 {{personValue.personName}}
					 &nbsp;<button type="button" style="margin-right:7px" class="close" aria-hidden="true" ng-click="modifyGroupList(personValue);" >&times;</button>
				</span>
			</div>
        </div>

		<div class="modal-footer">
			<p>
				<textarea ng-model="messageText" style="height:100px;width:500px;"></textarea>
			</p>
		    <button class="btn btn-primary" ng-click="saveGroupListToDb(messageText);">Save</button>
			<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
			
        </div>
			
			
			</div>
		</div>
     </div> 
    </script>
    
    

<script type="text/ng-template" id="editGroupDialog">
<div ng-init="assignValues()"></div> 	
<div class="modal" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
 
        <a class="flR cross_icon" href="#" ng-click="cancel()">
			<img src="${images_url}/../cross_icon.gif"></img>
        </a>

        <div class="modal-header">
			<h3>View Group Info!!!</h3>
        </div>
		
        
		<div class="modal-body" ng-show = "groupAdminId == selfId" >
			<input placeholder="Select and add your linked Persons:" type="text" ng-model="selectedPerson" typeahead-on-select="addToGroupList(selectedPerson);selectedPerson=''" 
			typeahead="personObj as personObj.personName for personObj in allLinkedPersons | filter:{personName: $viewValue} | limitTo:5">
        </div>
		<div style="padding:8px;margin-bottom:30px" >
			<div ng-repeat="personValue in groupPersonsList" >
				<span style="background-color: #B0E5F8; border-radius: 6px;  float: left;  margin: 5px;  padding-left: 3px; color:#000000; height:22px">
					 {{personValue.personName}}
					 &nbsp;
					<button type="button" style="margin-right:7px" class="close" aria-hidden="true" ng-click="modifyGroupList(personValue);" ng-show = 'groupAdminId == selfId'>
					  &times;	</button>
				</span>
			</div>
        </div>
		<div class="modal-footer" ng-show = "groupAdminId == selfId">

		    <button class="btn btn-primary" ng-click="saveupdatedGroupListToDb();">Save</button>
			<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
			
        </div>
			
			</div>
		</div>
     </div> 

</script>    

	
				
		<div class="overlap_height"></div>
		<div class="msg-searchbox">
			<div class="pull-left col-lg-3 bar">
				<!-- Create a binding between the searchString model and the text field -->
				<input type="text" placeholder="Search" ng-model="searchString" class="ng-pristine ng-valid">
			</div>
			<div class="pull-right col-lg-8" >
				<span class="font_14 msgScreenHead" >
					Message History With <strong> <a class="person-detail" ng-click="openPersonDetailsInMsgHistory(personId,personName,connectedType)" ng-cloak>{{personName}}</a> </strong>
					
				</span>	
					<!-- <span class="font_14 msgScreenHead" >Conversation With <strong ng-cloak>{{displayPersonOrGroupName(personName,connectedType)}}</strong></span> -->
					
				
			</div>
		</div>
		<div class="col-lg-3">
		<div class= "ro">
			<ul id="usersInMsgHistoryListUlId" class="pull-left msg-list row"  >
				<!-- Render a li element for every entry in the items array. Notice
					 the custom search filter "searchFor". It takes the value of the
					 searchString model as an argument.
				 -->
				<%-- <li class="usersInMsgHistoryListLi" ng-repeat="i in linkedPersonsList | searchFor:searchString" ng-class="{chatList: $index < numberOfUnreadMsgSenders}"> --%> 
				<li class="usersInMsgHistoryListLi" id = "li-{{i.personId}}"  ng-click="loadMessageWithSelectedPerson(i.personId,i.personName, i.connectedType);" ng-repeat="i in linkedPersonsList | searchFor:searchString" my-repeat-directive>
					<div class="linkPersonMsg pull-left" id='{{i.personId}}'  style="margin:0 0 0px;">
						<a href="#">
							<img ng-src="{{getUserImage(i.personId)}}" style="width:50px; height:50px" onError="this.src='/static/images/person_image_not_available.jpg';" />
						</a>
					</div>
					<div class="linkPersonMsgName pull-left" id='{{i.personId}}-name' >
	                    <a ng-cloak>{{displayPersonOrGroupName(i.personName,i.connectedType)}}{{getNumberOfUnreadMsgOfThisPerson(i.personId)}} </a>
						 <!--  <a ng-click="openPersonDetailsInMsgHistory(i.personId,i.personName)" ng-cloak>{{i.personName}}({{getNumberOfUnreadMsgOfThisPerson(i.personId)}}) </a> -->
					</div>
				</li>
			</ul>
		</div>
		<div class="ro">
			<button id="cgm" class="btn btn-primary" ng-click="createGroup(personId, personName, selfId, selfName);">Create Group Messages</button>
		</div>
</div>
		<ul id="listOfHistoryMsgUlId" class="pull-right" >
		    
			<div class="listOfMsgHistory">
	    			<%-- <div ng-repeat='msg in jsonMessageList' class="msgBoxFrom" ng-class="{unreadMsgInList : $index < getNumberOfUnreadMsgOfThisPerson(personId)}"> --%>
					<div ng-repeat='msg in jsonMessageList' ng-class="{true:'msgBoxFrom', false:'msgBoxTo'}[msg.fromId==selfId]">
					  <span ng-cloak class="msg-date"> {{msg.fromName}} {{getMsgTime(msg.timeOfMessage)}}</span><br/>
					  <span style="font-size: 16px;" ng-bind-html-unsafe="msg.message"></span>
					</div>
					<div style="float:left;width:80%" ng-show="msgListLoaded">
					<!-- ng-show="msgListLoaded" -->
	    				<a class="flL button backgroundColorOfLoadLink" href="javascript:void(0);" ng-click="loadMoreMessages(connectedType)">Show More</a>
					</div>
					<div style="float:left;width:80%" ng-hide="msgListLoaded">
					<!-- ng-show="msgListLoaded" -->
	    				<label class="flL button backgroundColorOfLoadLink" >Messages Loaded</label>
					</div>
	  		</div>
		</ul>
		<div class=" msgInHistory flR">
			<textarea id="myTextarea" style="border-radius: 6px; height: 50%; margin-bottom: 8px; width: 91%;" ng-model="messageText"></textarea>
			<button class="btn btn-primary" ng-click="ok(messageText)">Send</button>
			<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
			<button class="btn btn-warning" ng-click="clearAllMsg()">Clear All Messages</button>
			You can enter <span id="charsLeft"></span> characters.
		</div>
	    </div><!-- wrapper -->
	</div>
</div>

<script type="text/javascript">
$('ul#listOfHistoryMsgUlId').slimscroll({
	  height: '55%',
	  width:'75%',
	  alwaysVisible: true,
	  color: '#00f',
	  size: '9px'
	});

/**
 * Users list on left
 */
 $('ul#usersInMsgHistoryListUlId').slimscroll({
	    height: '355px',
	/*  width:'25%', */
	  alwaysVisible: true,
	  color: '#00f',
	  size: '9px'
	});
 
 
 $('#myTextarea').limit('140','#charsLeft');


</script>



</section>




</body>
</html>
