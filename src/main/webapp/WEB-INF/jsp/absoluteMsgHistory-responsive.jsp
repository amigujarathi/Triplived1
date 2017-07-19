
<div id="ng-app" ng-app="messageModule" style="" class="menu-right-hidden " ng-cloak>
<div ng-init="selfId = '${selfId}'"></div>
<div ng-init="selfName = '${selfName}'"></div>

	<!-- Main Container Fluid -->
	<div style="visibility: visible;" class="container-fluid menu-hidden" >
		<!-- Content -->
		<div id="content" ng-controller="absoluteMsgHistoryController">
			<!-- <div class="layout-app"> -->
			<div class="innerAll" id="msgHistoryPage-responsive">
				<!-- Widget -->
				<div
					class="widget widget-messages widget-heading-simple widget-body-white">
					<div class="widget-body padding-none margin-none">

						<!--  LDFT SIDE BAR TO SHOW LIST OF CONNECTED USERS -->
						<div class="row row-merge borders">
							<div style="height: 535px;" class="col-md-3 listWrapper">
								<div class="innerAll">
									<form class="form-inline margin-none" autocomplete="off">
										<div class="input-group input-group-sm">
											<input type="text" placeholder="Search People" ng-model="searchString" class="form-control ng-pristine ng-valid"> 
												<span class="input-group-btn">
												<button class="btn btn-primary btn-xs pull-right"	type="button">
													<i class="fa fa-search"></i>
												</button>
											</span>
										</div>
									</form>
								</div>
								<div class="bg-gray text-center strong border-top innerAll half">
									<a href = "#" ng-click="createGroup(personId, personName, selfId, selfName);"><i class="fa fa-fw icon-group"></i> Create Group Messages</a>
								</div>
								
								<ul id="usersInMsgHistoryListUlId" class="list-unstyled">  
									<li class="border-bottom "  id = "li-{{i.personId}}" ng-repeat="i in linkedPersonsList | searchFor:searchString" my-repeat-directive>
										<div class="media innerAll msgContainer">
											<div class="media-object pull-left hidden-phone" id='{{i.personId}}'>
												<a href="#">
													<img ng-src="{{getUserImage(i.personId)}}" style="width:50px; height:50px;  border-radius:4px" onError="this.src='/static/images/person_image_not_available.jpg';" />
												</a>
											</div>
											<div class="media-body" id='{{i.personId}}-name'>
												<div>
													<span class="strong">{{displayPersonOrGroupName(i.personName,i.connectedType)}}{{getNumberOfUnreadMsgOfThisPerson(i.personId)}}</span> 
													<!-- <small	class="text-italic pull-right label label-default">2 days</small> -->
												</div>
												<!-- TODO - nice to have feature but should skip for now, would be gud when we have in-memory DB -->
												<!-- <div>Latest comment line...</div> -->
											</div>
											<a ng-click="loadMessageWithSelectedPerson(i.personId,i.personName, i.connectedType);" href="#" onClick="scrollOnClickForMobile();">
													<span class="link-spanner"></span>
											</a>
										</div>
									</li>
								</ul>
							</div>
							
							<!--  RIGHT SIDE BAR TO SHOW USERS MESSAGES  -->
							<div style="height: 535px;" class="col-md-9 detailsWrapper" id="messageWindow">
							  <!-- User -->
								<div class="bg-primary">
									<div class="media row">
									    <div class="col-xs-3 col-sm-3">
											<a class="pull-left" href="">
												<img class="img-responsive" ng-src="{{getUserImage(personId)}}" style="width:60px; height:60px;" 
													 onError="this.src='/static/images/person_image_not_available.jpg';" />
											</a>
											<a href="javascript:void(0);" ng-click="openPersonDetailsInMsgHistory(personId,personName,connectedType)" 
											   class="text-white pull-left innerAll strong display-block margin-none hidden-xs hidden-sm">
												<strong>{{personName}}</strong>
											</a>
										</div>
										<div class="media-body innerTB innerR col-xs-9 col-sm-9" >
											<div class="row hidden-md hidden-lg">
												<div class="ng-binding innerT half pull-right">
													<a class="text-white pull-left strong margin-none" style="padding-right:10px;">{{personName}}</a>
												</div>
											</div>
											<div class="row">
												<div class="innerT half pull-right">
													<a class=" btn btn-default bg-white btn-sm"	ng-click="createEvent(personId, personName, selfId, selfName, connectedType);">
														<i class="fa fa-calendar"></i> Create Event
													</a>
												</div>
												<div class="innerT half pull-right">
													<a data-toggle="collapse" class=" btn btn-default bg-white btn-sm"	href="#msg-area-type">
														<i class="fa fa-pencil"></i> Write Message
													</a>
												</div>
											</div>
										</div>

									</div>
								</div>
								
								<div class="collapse border-top" id="msg-area-type" style= "border: 1px solid #808080; border-radius: 2px;  height: auto;  margin: 2px;  width: 98%;">
									<textarea  id="myTextarea" style="color: #808080; font-weight: bold" ng-model="messageText" placeholder="Write your message..."	class="form-control rounded-none border-none" type="text"></textarea>
									 <button class="btn btn-xs btn-primary" ng-click="ok(messageText)">Send</button>
									 <button class="btn btn-xs btn-info" ng-click="cancel()">Cancel</button>
									 <button class="btn btn-xs btn-info" ng-click="clearAllMsg()">Clear All Messages</button>
									 You can enter <span id="charsLeft"></span> characters.
								</div>
								 
								<div id="listOfHistoryMsgUlId" class="widget border-top padding-none margin-none" >
									<div ng-hide="LoadingMessageHistoryLoader">
										<div class="media margin-none border-top innerAll"  ng-repeat='msg in jsonMessageList' ng-class="{1:'bg-gray', 0:'bg-normal'}[$index%2]">
										<a class="pull-left hidden-xs" href=""> 
											<img class="img-responsive" ng-src="{{getUserImage(msg.fromId)}}" style="width:60px; height:60px; border-radius:4px" onError="this.src='/static/images/person_image_not_available.jpg';" />
										</a>
										<div class="media-body innerTB">
											<div class="row">
												<div class="col-sm-6">
													<div class="innerT half">
														<div class="media">
															<div class="pull-left">
																<p class="strong text-inverse text-muted"> {{getUserName(msg, connectedType)}} </p>
																<span class="innerR text-muted visible-xs">{{getMsgTime(msg.timeOfMessage)}}</span>
															</div>
															<div class="media-body" ng-bind-html-unsafe="msg.message"></div>
														</div>
													</div>
												</div>
												<div class="col-sm-6 hidden-xs">
													<i class="icon-time-clock pull-right text-muted innerT half fa fa-2x"></i>
													<span class="pull-right innerR innerT text-right  text-muted">
														 {{getMsgTime(msg.timeOfMessage)}}
													</span>
												</div>
											</div>
										 </div>
										</div>							
									</div>
									<div ng-show="LoadingMessageHistoryLoader" class="progress-save widget border-top padding-none margin-none">
								     <div style="vertical-align: middle; position:relative; height: 49px; margin-left:50%;">
										<img src="${images_url}/../ajax-loader.gif"></img>
									</div>
									
								</div>
									 
								</div>
								<a href="#" ng-click="createEvent(personId, personName, selfId, selfName);"></a>
								<div class="bg-gray innerAll text-center margin-none" ng-show="msgListLoaded">
									<a class="text-muted lead" href="javascript:void(0);" ng-click="loadMoreMessages(connectedType)">
										<i class="icon-time-clock"></i> More messages..
									</a>
								</div>
								<div class="bg-gray innerAll text-center margin-none" ng-hide="msgListLoaded">
									<a class="text-muted lead" href="javascript:void(0);" ng-click="loadMoreMessages(connectedType)">
										<i class="icon-time-clock"></i> All messages Loaded !!
									</a>
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

		<div class="clearfix"></div>
		<!-- // Sidebar menu & content wrapper END -->

	</div>
	<!-- // Main Container Fluid END -->


<%-- 
<script type="text/javascript" src="${url}/jquery.min.js"></script>
<script type="text/javascript" src="${url}/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="${url}/jquery.limit-1.2.source.js"></script>
<script type="text/javascript" src="${url}/jquery.galleryview.js"></script>
<script type="text/javascript" src="${url}/jquery.custom.js?v=1"></script>
<script type="text/javascript" src="${url}/twiter/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/twiter/typeahead/typeahead.min.js"></script>
<script type="text/javascript" src="${url}/twiter/template/hogan-2.0.0.js"></script>


<script type="text/javascript" src="${url}/jquery-ui.min.js"></script>
<script type="text/javascript" src="${url}/jquery/scroll/jquery.slimscroll.js"></script>

<script type="text/javascript" src="${url}/angular.min.js"></script>
<script type='text/javascript' src='${url}/ng-infinite-scroll.min.js'></script>
<script type="text/javascript" src="${url}/ui-bootstrap-tpls-0.6.0.min.js"></script>

<!--  Local storage -->
	<script type="text/javascript" src="${url}/html5utils/store.min.js"><!-- script --></script>	

<script type="text/javascript" src="${url}/connectme/angular/message/controllers/absoluteMsgHistoryController.js"></script>
<script type="text/javascript" src="${url}/connectme/angular/event/controllers/eventController.js"></script>


<script type="text/javascript" src="${url}/connectme/angular/message/services/messageServices.js"></script>

<script type="text/javascript" src="${url}/groupController.js"></script>

<script type="text/javascript" src="${url}/html5utils/store.min.js"></script>


<script type="text/javascript" src="${url}/jquery/dateTimePicker/jquery.datetimepicker.js"></script> --%>

<%-- <link rel="stylesheet" type="text/css" href="${styles_url}/jquery.datetimepicker.css"/> --%>
<!-- 

CACTUS OUTPUT AS interestify-messages.js

 -->
<!-- <link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet"> -->

	
<script type="text/ng-template" id="createEventUrl">

<div class="modal" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
    	
    	<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" ng-click="cancel()">×</button>
			<h3 class="modal-title">Create & Schedule Events !!</h3>
		</div>
		
		<div class="modal-body">
			<div class="innerAll">
				<div class="innerLR">

					<form class="form-horizontal" role="form" id="eventCreationFormId" name="eventCreationForm" ng-submit="processForm()">
					
						<div class="form-group">
								<p class="form-control-static">
									<input class= "form-control" placeholder="Enter Event Name:" type="text" ng-model="formData.eventName" id="eventNameInput" name="event">
								</p>
						</div>

						<div class="form-group">
							<input type="text" id="datetimepicker3"/><br><br>
						</div>

						<div class="form-group">
							<a class="control-label" ng-click="showMsgArea = true" href="javascript:void(0);">Add Message(Optional):</a>
							<textarea ng-show="showMsgArea" class= "form-control"  ng-model="formData.eventMessage" style="height:200%;width:99%;" name="eventMessage" id="eventMessageId"></textarea>
						</div>

						<div class="form-group">
							<button type="submit" tabindex="6" class="btn btn-primary" id="submitEventProfile" >Create Event !!</button>
						</div>
					

					</form>
				</div>
			</div>
		</div>	
		
			
			</div>
		</div>
     </div> 


</script>




<script type="text/ng-template" id="createGroupUrl">

<div class="modal" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
    	
    	<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" ng-click="cancel()">×</button>
			<h3 class="modal-title">Create group Message !!! </h3>
		</div>
		
		<div class="modal-body">
			<div class="innerAll">
				<div class="innerLR">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-4 control-label">Group Name:</label>
							<div class="col-sm-7">
								<p class="form-control-static"><input class= "form-control" placeholder="Enter Group Name:" type="text" ng-model="selectedGroupName" id="groupNameInput"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">Add people to Group:</label>
							<div class="col-sm-7">
								<div class="input-group">
									<input class= "form-control" id="groupDetailInput" data-toggle="tooltip" title="Only people with whom you have interacted before can be part of the group" 
										placeholder="Only those with whom you have interacted before" type="text" ng-model="selectedPerson" typeahead-on-select="addToGroupList(selectedPerson);selectedPerson=''" 
										typeahead="personObj as personObj.personName for personObj in allLinkedPersons | filter:{personName: $viewValue} | limitTo:5">
									<div class="input-group-btn">
										<a href="#" id="groupDetailHelp" data-toggle="tooltip" title="Only people with whom you have interacted before can be part of the group"><img src="/static/images/question.jpg"></img></a>
									</div>
								</div>
								
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-7">
								<div ng-repeat="personValue in groupList" >
									<span style="background-color: #B0E5F8; border-radius: 6px;  float: left;  margin: 5px;  padding-left: 3px; color:#000000;">
										 {{personValue.personName}}
										 &nbsp;<button type="button" style="margin-right:7px" class="close" aria-hidden="true" ng-click="modifyGroupList(personValue);" >&times;</button>
									</span>
								</div>
					        </div>
       				   </div>
						
					   <div class="form-group">
							<label class="col-sm-4 control-label">Add Message:</label>
							<div class="col-sm-7">
								<textarea class= "form-control"  ng-model="messageText" style="height:100%;width:99%;"></textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-4 control-label">Description(Optional):</label>
							<div class="col-sm-7">
								<textarea class= "form-control"  ng-model="descText" style="height:100%;width:99%;"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>	
		
		<div class="modal-footer">
		 
		    <button class="btn btn-primary" ng-click="saveGroupListToDb(messageText, descText);">Save</button>
			<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
			
        </div>		
			</div>
		</div>
     </div> 

</script>
</div>
