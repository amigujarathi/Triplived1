<div ng-controller="groupMsgHistoryController">


 <div id="groupMsgHistoryPage" class="" ng-hide="showListingPage" style="height:600px;background-image: url(/static/images/grass.jpg);">
	<div class="wrapper msgHistory_listing_div" >

	<div class="overlap_height"></div>
    
    <div class="bar flL">
		<!-- Create a binding between the searchString model and the text field -->
		<input type="text" placeholder="Search " ng-model="searchString" class="ng-pristine ng-valid">
	</div>
	
	<div class="" style="height:70px;width:700px;">
		<span class="font_14 msgScreenHead" >Conversation History With <strong ng-cloak>{{personName}}</strong></span>
	</div>
	
	
	<ul id="usersInMsgHistoryListUlId" class="flL">
		<!-- Render a li element for every entry in the items array. Notice
			 the custom search filter "searchFor". It takes the value of the
			 searchString model as an argument.
		 -->
		<%-- <li class="usersInMsgHistoryListLi" ng-repeat="i in linkedPersonsList | searchFor:searchString" ng-class="{chatList: $index < numberOfUnreadMsgSenders}"> --%> 
		<li class="usersInMsgHistoryListLi" ng-repeat="i in linkedPersonsList | searchFor:searchString">
			<p class="linkPersonMsg" id='{{i.personId}}' ng-click="loadMessageWithSelectedPerson(i.personId,i.personName);" style="margin:0 0 0px;">
				<a href="#"><img ng-src="{{getUserImage(i.personId)}}" style="width:100px; height:100px" onError="this.src='/static/images/person_image_not_available.jpg';" /></a>
			</p>
			<p class="linkPersonMsgName" id='{{i.personId}}-name'> <a ng-click="openPersonDetailsInMsgHistory(i.personId,i.personName)" ng-cloak>{{i.personName}}({{getNumberOfUnreadMsgOfThisPerson(i.personId)}}) </a></p>
		</li>
	</ul>
	
	<ul id="listOfHistoryMsgUlId" class="flR" >
	    
		<div class="listOfMsgHistory">
    			<%-- <div ng-repeat='msg in jsonMessageList' class="msgBoxFrom" ng-class="{unreadMsgInList : $index < getNumberOfUnreadMsgOfThisPerson(personId)}"> --%>
				<div ng-repeat='msg in jsonMessageList' ng-class="{true:'msgBoxFrom', false:'msgBoxTo'}[msg.fromId==selfId]">
				  <span style="font-size: 16px;font-weight: bold;" ng-cloak>{{getMsgTime(msg.timeOfMessage)}}</span><br/>
				  <span ng-bind-html-unsafe="msg.message"></span>
				</div>
  		</div>
		
	</ul>
	
	
	<div class=" msgInHistory flR">
	<textarea style="height:100px;width:600px;" ng-model="messageText"></textarea>
	<button class="btn btn-primary" ng-click="ok(messageText)">Send</button>
	<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
	</div>
	
    </div><!-- wrapper -->
</div>
</div>
