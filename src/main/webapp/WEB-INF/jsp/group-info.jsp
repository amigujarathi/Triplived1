 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:eval expression="logged_in_user" var="user"></spring:eval>

 <title>About </title>
 
 
<script src="${minified_url}/js/index.min.js"><!-- script --></script>

<script type="text/javascript" src="${url}/twiter/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/twiter/typeahead/typeahead.min.js"></script>
<script type="text/javascript" src="${url}/twiter/template/hogan-2.0.0.js"></script>

<%-- <script type="text/javascript" src="${url}/angular.min.js"></script> --%>
<script type="text/javascript" src="${url}/angular-1.2.min.js"></script>
<script type='text/javascript' src='${url}/ng-infinite-scroll.min.js'></script>
<script type="text/javascript" src="${url}/ui-bootstrap-tpls-0.6.0.min.js"></script>


<!--  Local storage -->
<script type="text/javascript" src="${url}/html5utils/store.min.js"></script>	

 
<script type="text/javascript" src="${url}/connectme/angular/detail/groupDetail/controllers/absoluteGroupDetailController.js?v=1"></script> 
<script type="text/javascript" src="${url}/connectme/angular/detail/detailServices/pollerScript.js"></script> 
<script type="text/javascript" src="${url}/connectme/angular/detail/detailServices/detailServices.js"></script>
<script type="text/javascript" src="${url}/groupDetailController.js?v=1"></script>

 <div class="container" ng-app="detailModule">
 <div ng-init="selfId = '${selfId}' "></div>
 <div ng-init="groupId = '${groupId}' "></div>
 <div ng-init="isPersonLoggedIn = '${isPersonLoggedIn}'"></div>
 
 <div id="groupInfoId" id="ng-app" ng-controller="AbsoluteGroupDetailCtrl" ng-init="getGroupDetails('${groupDataUrl}')">
 
 
 <script type="text/ng-template" id="editGroupDialog">
<div ng-init="assignValues()"></div>
<div class="modal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" ng-click="cancel()">×</button>
				<h3 class="modal-title">Edit Group!!!</h3>
			</div>


			<div class="modal-body" ng-show="groupAdminId == selfId">
				<div class="innerAll">
					<div class="innerLR">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<div class="col-sm-7">
								<input placeholder="Select and add your linked Persons:"
									type="text" ng-model="selectedPerson"
									typeahead-on-select="addToGroupList(selectedPerson);selectedPerson=''"
									typeahead="personObj as personObj.personName for personObj in allLinkedPersons | filter:{personName: $viewValue} | limitTo:5">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-7">
								<div ng-repeat="personValue in groupPersonsList">
									<span
										style="background-color: #B0E5F8; border-radius: 6px; float: left; margin: 5px; padding-left: 3px; color: #000000;">
										{{personValue.personName}} &nbsp;
										<button type="button" style="margin-right: 7px" class="close"
											aria-hidden="true" ng-click="modifyGroupList(personValue);"
											ng-show='groupAdminId == selfId'>&times;</button>
									</span>
								</div>
							</div>
						</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer" ng-show="groupAdminId == selfId">
				<button class="btn btn-primary"
					ng-click="saveupdatedGroupListToDb();">Save</button>
				<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
			</div>
		</div>
	</div>
</div>
</script>    
 
 
 <script type="text/ng-template" id="groupMessagePopupOnDetailScreen">

  <div class="modal" role="dialog">
   <div class="modal-dialog">
    <div class="modal-content">

        <a class="flR cross_icon" href="#" ng-click="cancel()" style="float:right">
			<img src="${images_url}/../cross_icon.gif"></img>
        </a>

        <div class="modal-header">
			<h3>Send message !!</h3>
		</div>
        <div class="modal-footer">
        <p>
		<textarea id="msgTextarea" ng-model="messageText" style="height:100px;width:500px;" maxlength="140"></textarea>
		</p>
		    <button class="btn btn-primary" ng-click="ok(messageText)">Send</button>
			<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
			You can enter 140 chars.
		
        </div>
		
  
			</div>
		</div>
   </div> 
</script>
 
 


     <div class="row">
         <div class="col-lg-12">
             <h1 class="page-header" ng-cloak>{{groupName}}</h1>
         </div>
     </div>

     <div class="row">

         
         
         <div class="col-sm-6">
					<div class="innerAll">
					<!-- <div class="innerAll bg-white"> -->	
						<div class="media innerB ">
							
							<div class="row">
						         <div class="col-sm-6">
								  <a href="" class="pull-left">
									<img ng-src="/static/user-images/{{groupId}}/{{groupId}}.jpg" alt="" width="200" class="img-circle"/>
								  </a>
							     </div>
							     
								 <div class="col-sm-6">
								     <span>{{groupDetailMemberList[0].description}}
								     </span>
							     </div>
								 
	     			 	    </div>
						</div>
					</div>
						
		</div>
				
         	 
			<div class="col-sm-6">
			   <div class="widget">
							<div class="widget-head border-bottom bg-gray">
								<h5 class="innerAll pull-left margin-none">Group Members</h5>
								
								<div class="pull-right">
									<a ng-show="amIAdmin" class="col-sm-12" href="#" ng-click="openGroupDetail(groupId, groupName)">Edit Group</a>
								</div>
							</div>
							<div class="widget-body">
								<div class="row innerAll" ng-repeat="memberObj in groupDetailMemberList">
									<p ng-show="memberObj.personId==selfId" class="col-sm-6">{{memberObj.personName}}</p>
									<a ng-show="memberObj.personId!=selfId" href="#" ng-click="openPersonDetail(memberObj.personId)" class="col-sm-6">{{memberObj.personName}}</a>
									<div class="col-sm-6 text-right">
										<span style="font-size:15px"><strong>{{memberObj.role}}</strong></span>
									</div>
								</div>
								
				
							</div>
				 </div>
			  </div>
						
							    
     </div>

     
 </div>
 </div>

