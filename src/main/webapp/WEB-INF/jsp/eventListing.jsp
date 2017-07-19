<div id="ng-app" ng-app="eventModule" style="" class="menu-right-hidden" ng-cloak>
<div ng-init="selfId = '${selfId}'"></div>

<!--
<script type="text/javascript" src="/static/js/events.js"></script>
-->
	<!-- Main Container Fluid -->
	<div style="visibility: visible;" class="container-fluid menu-hidden" >
		<!-- Content -->
		<div id="eventContent" ng-controller="eventListingController">
		<div ng-init="getAllEventsLinkedToMe('normal')"></div>
			<!-- <div class="layout-app"> -->
			<div class="innerAll" id="msgHistoryPage-responsive">
				<!-- Widget -->
				<div class="widget widget-messages widget-heading-simple widget-body-white">
					<div class="widget-body padding-none margin-none">

						<!--  LDFT SIDE BAR TO SHOW LIST OF CONNECTED USERS -->
						<div class="row row-merge borders">
							<div style="height: 535px;" class="col-md-3 listWrapper">
								<div class="innerAll">
									<form class="form-inline margin-none" autocomplete="off">
										<div class="input-group input-group-sm">
											<input type="text" placeholder="Search Events" ng-model="searchString" class="form-control ng-pristine ng-valid"> 
												<span class="input-group-btn">
												<button class="btn btn-primary btn-xs pull-right"	type="button">
													<i class="fa fa-search"></i>
												</button>
											</span>
										</div>
									</form>
								</div>
								
								<ul id="usersInMsgHistoryListUlId" class="list-unstyled">  
									<li class="border-bottom "  id = "li-{{i.eventId}}" ng-repeat="i in linkedEventsList | searchFor:searchString" >
										<div class="media innerAll msgContainer">
											<div class="media-object pull-left hidden-phone" id='{{i.eventId}}'>
												<a href="#">
													<img ng-src="{{getUserImage(i.eventId)}}" style="width:50px; height:50px;  border-radius:4px" onError="this.src='/static/images/person_image_not_available.jpg';" />
												</a>
											</div>
											<div class="media-body" id='{{i.eventId}}-name'>
												<div>
													<span class="strong">{{displayEventName(i.eventName)}}</span> 
													<!-- <small	class="text-italic pull-right label label-default">2 days</small> -->
												</div>
											</div>
											<a ng-click="loadEventDetails(i.eventId, i.eventName, i.eventDate, i.eventType, i.isAdmin);" href="#">
													<span class="link-spanner"></span>
											</a>
										</div>
									</li>
								</ul>
							</div>
							
							<!--  RIGHT SIDE BAR TO SHOW USERS MESSAGES  -->
							<div style="height: 535px;" class="col-md-9 detailsWrapper">
							  <div class="bg-primary">
									<div class="media">
										<div class="media-body innerTB innerR" >
											
											
											<a href="javascript:void(0);" class="text-white pull-left innerAll strong display-block margin-none">
												<span style="font-size: 20px;">{{eventInfo.eventName}}</span> 
												<span style="font-size: 10px;">&nbsp; on &nbsp;</span> 
												<span style="font-size: 20px;">{{eventInfo.eventDate}}</span>
											</a>
											<a href="javascript:void(0);" class="text-white pull-left innerAll strong display-block margin-none" ng-click="showEventVoteModal(eventInfo.eventId)">
												<span style="font-size: 15px;" ng-show="eventInfo.yesOrNo">Attending ?</span>
												<span style="font-size: 15px;" ng-show="eventInfo.yesAttending">Yes, you are attending</span>
												<span style="font-size: 15px;" ng-show="eventInfo.noAttending">You have declined</span>		
											</a>
											
											<div class="innerT half pull-right">
												<a data-toggle="collapse" class=" btn btn-default bg-white btn-sm"	href="#event-msg-area-type">
													<i class="fa fa-pencil"></i> Event updates
												</a>
											</div>
											
										</div>

									</div>
								</div>
								
								
								
								<div class="collapse border-top" id="event-msg-area-type" style= "border: 1px solid #808080; border-radius: 2px;  height: auto;  margin: 2px;  width: 98%;">
									<textarea  id="myTextarea" style="color: #808080; font-weight: bold" ng-model="messageText" placeholder="Write your message..."	class="form-control rounded-none border-none" type="text"></textarea>
									 <button class="btn btn-xs btn-primary" ng-click="ok(messageText)">Send</button>
									 <button class="btn btn-xs btn-info" ng-click="cancel()">Cancel</button>
									 <!-- <button class="btn btn-xs btn-info" ng-click="clearAllMsg()">Clear All Messages</button> -->
									 You can enter <span id="charsLeft"></span> characters.
								</div>
								
								<div id="listOfHistoryMsgUlId" class="widget border-top padding-none margin-none" >
									<div >
									<div style="padding:10px 10px;">
										<div class="">
											<div class="media">
												<div class="media-body innerTB innerR" >
													
													
													<p class="text-white pull-left innerAll strong display-block margin-none">
														<a style="font-size: 20px;"> Event Updates </a> 
													</p>	
										
						
						
													<div class="innerT half pull-right">
														<a href="#" class="col-md-3 glyphicons group" ng-click="showEventParticipantsModal(eventDetails, participantYesNoList)"><i></i></a>
													</div>
													
													
													
												</div>
		
											</div>
										</div>
										
									</div>
										<div class="media margin-none border-top innerAll"  ng-repeat="msg in eventMessageDetails">
												<p class="col-sm-6"><strong>{{msg.message}}</strong></p>
												<p class="col-sm-6 text-right"><strong>by {{msg.fromName}} on {{getMsgTimeStamp(msg.timeOfMessage)}}</strong></p>
												
										</div>							
									</div>
									
									 
								</div>

								
									
							</div>
						</div>
					</div>
				</div>
				<!-- // Widget END -->
			</div>



			<!-- </div> -->
		</div>
		<!-- // Content END -->


		

	</div>
	<!-- // Main Container Fluid END -->



<%-- <script type="text/javascript" src="${url}/jquery.min.js"></script>
<script type="text/javascript" src="${url}/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="${url}/jquery.limit-1.2.source.js"></script>
<script type="text/javascript" src="${url}/jquery.galleryview.js"></script>
<script type="text/javascript" src="${url}/jquery.custom.js"></script>
<script type="text/javascript" src="${url}/twiter/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/twiter/typeahead/typeahead.min.js"></script>
<script type="text/javascript" src="${url}/twiter/template/hogan-2.0.0.js"></script>

<script type="text/javascript" src="${url}/jquery.ui.slider.js"></script>
<script type="text/javascript" src="${url}/jquery-ui.min.js"></script>
<script type="text/javascript" src="${url}/jquery/scroll/jquery.slimscroll.js"></script>

<script type="text/javascript" src="${url}/angular.min.js"></script>
<script type='text/javascript' src='${url}/ng-infinite-scroll.min.js'></script>
<script type="text/javascript" src="${url}/ui-bootstrap-tpls-0.6.0.min.js"></script>

<!--  Local storage -->
	<script type="text/javascript" src="${url}/html5utils/store.min.js"><!-- script --></script>	
	
 
<script type="text/javascript" src="${url}/connectme/angular/message/controllers/absoluteMsgHistoryController.js"></script>
<script type="text/javascript" src="${url}/connectme/angular/event/controllers/eventListingController.js"></script>




<script type="text/javascript" src="${url}/groupController.js"></script>

<script type="text/javascript" src="${url}/html5utils/store.min.js"></script>
 --%>
<!-- 
 CACTUS : Interestify-events.min.js
 -->
	
 


 <script type="text/ng-template" id="eventParticipantsId">

  <div class="modal" role="dialog">
   <div class="modal-dialog">
    <div class="modal-content">

        <a class="flR cross_icon" href="#" ng-click="cancel()" style="float:right">
			<img src="${images_url}/../cross_icon.gif"></img>
        </a>

        <div class="modal-header">
			<h3>Event participants !!</h3>
		</div>
        <div class="modal-footer" style="margin-top: 0 !important">
		<div>
		 <div ng-repeat="detail in participantList" >
			<p class="col-sm-4" ng-show="detail.personId == selfId"><strong>{{detail.personName}}</strong></p>
			<p class="col-sm-4" ng-show="detail.personId != selfId">{{detail.personName}}</p>
			<p class="col-sm-4" ng-show="detail.personId == selfId"><strong>{{detail.role}}</strong></p>
			<p class="col-sm-4" ng-show="detail.personId != selfId">{{detail.role}}</p>
			<p class="col-sm-4" ng-show="detail.personId == selfId"><strong>{{detail.participantStatus}}</strong></p>
			<p class="col-sm-4" ng-show="detail.personId != selfId">{{detail.participantStatus}}</p>


		 </div>
		</div>
		</div>
		
  
			</div>
		</div>
   </div> 
</script>


 <script type="text/ng-template" id="eventVoteId">

  <div class="modal" role="dialog">
   <div class="modal-dialog">
    <div class="modal-content">

        <a class="flR cross_icon" href="#" ng-click="cancel()" style="float:right">
			<img src="${images_url}/../cross_icon.gif"></img>
        </a>

        <div class="modal-header">
			<h3>Are you attending !!</h3>
		</div>
        <div class="modal-footer" style="margin-top: 0 !important">
		<div>
		 	<button class="col-sm-4 btn btn-primary" ng-click="isParticipating('yes');">Yup, I am going</button>
			<button class="col-sm-4 btn btn-primary" style="float:right" ng-click="isParticipating('no');">No, I will skip</button>
		</div>
		</div>
		
  
	</div>
   </div>
  </div>
 
</script>

 
</div>
		