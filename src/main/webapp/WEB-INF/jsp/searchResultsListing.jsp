
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page isELIgnored="false"%>

<%-- <script type="text/javascript" src="${url}/jquery.min.js"></script>
<script type="text/javascript" src="${url}/add2bucket.js" ></script>
<script type="text/javascript" src="${url}/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="${url}/jquery.limit-1.2.source.js"></script>
<script type="text/javascript" src="${url}/jquery.galleryview.js"></script>
<script type="text/javascript" src="${url}/jquery.custom.js"></script>
<script type="text/javascript" src="${url}/twiter/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/twiter/typeahead/typeahead.min.js"></script>
<script type="text/javascript" src="${url}/twiter/template/hogan-2.0.0.js"></script>

<script type="text/javascript" src="${url}/jquery-ui.min.js"></script>
<script type="text/javascript" src="${url}/jquery/scroll/jquery.slimscroll.js"></script>

<script type="text/javascript" src="${url}/angular-1.2.min.js"></script>
<script type='text/javascript' src='${url}/ng-infinite-scroll.min.js'></script>
<script type="text/javascript" src="${url}/ui-bootstrap-tpls-0.6.0.min.js"></script>

<!--  Local storage -->
<script type="text/javascript" src="${url}/html5utils/store.min.js"><!-- script --></script>

<script type="text/javascript" src="${url}/connectme/angular/search/module/projectModule.js"></script>	
<script type="text/javascript" src="${url}/connectme/angular/content/services/fewLocationServices.js"></script>		
<script type="text/javascript"	src="${url}/connectme/angular/content/controllers/fewStadiumSectionController.js"></script>

	
<script type="text/javascript" src="${url}/connectme/angular/search/controllers/site_angular.js?v=4"></script>
<script type="text/javascript" src="${url}/connectme/angular/search/services/site_angular_services.js"></script>

<script type="text/javascript" src="${url}/connectme/angular/search/directive/search_listing_directives.js"></script>


<script type="text/javascript" src="${url}/html5utils/store.min.js"></script>	
 

<script src="/static/js/twiter/datepicker/bootstrap-datepicker.js" type="text/javascript"><!-- script --></script>
<script src="/static/js/jquery/validate/jquery.validate.js" type="text/javascript"><!-- script --></script>
<script src="/static/js/jquery/validate/additional-methods.js" type="text/javascript"><!-- script --></script>
<script src="/static/js/connectme/login.js" type="text/javascript"><!-- script --></script>
 --%>



 <!--
<script type="text/javascript" src="interstify-search-result.js"></script>

-->

<!-- Main Container Fluid -->
<div style="visibility: visible;" class="container-fluid menu-hidden" id="ng-app" ng-app="projectModule">
	<!-- Content -->
	<div>
		<!-- <div class="layout-app"> -->
		<div class="innerLR">
			<div class="relativeWrap">
				<div class="box-generic">
					<div ng-init="loggedInPersonId = '${userId}'"></div>
					<div ng-init="lastUpdatedLocalStorageValue = '${lastUpdatedLocalStorage}'"></div>
					<div ng-init="lastUpdatedLocalStorageForSportsList = '${lastUpdatedLocalStorageForSportsList}'"></div>
					<div ng-init="lastUpdatedLocalStorageForCitiesList = '${lastUpdatedLocalStorageForCitiesList}'"></div>
					<div ng-init="lastUpdatedLocalStorageForAreasList = '${lastUpdatedLocalStorageForAreasList}'"></div>
					<div ng-init="showListingPage = true"></div>
					<div ng-init="isAnyPersonLinkedToMe = false"></div>
					<div ng-init="isPersonLoggedIn = '${isLoggedIn}'"></div>
					<!--ayan-->
					<div id="listingPageId" ng-controller="myController" ng-cloak>
					
					
					  
					
					
					<script type="text/ng-template" id="myModalContent1">
 	
					<div class="modal" role="dialog">
  						<div class="modal-dialog">
    						<div class="modal-content">

							<a class="flR cross_icon" href="#" ng-click="cancel()" >
								<img src="${images_url}/../cross_icon.gif"></img>
        					</a>

        					<div class="modal-header">
								<h3 class="font-size-mobile">Send message</h3>
        					</div>
		
        					<div class="modal-body" ng-show="showMsgHistory"></div>
        
					<div class="modal-footer">
						<p class="hidden-xs hidden-sm">
							<textarea id="msgTextarea" ng-model="messageText" style="height:100px;width:500px;" maxlength="140"></textarea>
						</p>
						<p class="hidden-md hidden-lg">
							<textarea id="msgTextarea" ng-model="messageText" style="height:100px;width:300px;" maxlength="140"></textarea>
						</p>
						<p style="color:green"><strong>{{status}}<strong></p>
		    			<button class="btn btn-primary" ng-click="ok(messageText)">Send</button>
						<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
						<p class="">You can enter 140 chars.</p>
			
        			</div>

					
			
			
			</div>
		</div>
     </div> 
    </script>
					
					    <spring:eval var="professionList"  expression="@constantProperties.getProperty('PROFESSION_LIST')" />
						<div ng-init = activityToRegionListLoaded = false></div>
						<div ng-init="myPersonId = '${userId}'"></div>
						<div ng-init="myPersonName = '${userName}'"></div>
						<div ng-init="searchDivClose = false"></div>
						<div ng-init="lazyLoadingResults = false"></div>

						
						<div ng-init="setMyIdInService(myPersonId,myPersonName)"></div>
						<div ng-init="imagePath = '${images_url}'"></div>
						<div ng-init="showBtn = true"></div>
						<div ng-init="unreadMsg = 0"></div>
						<div ng-init="getUnreadMessagesNotification()"></div>
						<%-- <div ng-init="populateProfessionList('${professionList}')"></div> --%>
						
						
						<c:choose>
						<c:when test="${fn:endsWith(requestScope['javax.servlet.forward.request_uri'],'slSearch')}">
						 <div id="loginMessageId" class="hidden-md hidden-lg col-xs-12 col-sm-12 clearFix append_bottomHalf shadow_genrator_1 listing_div col-md-6"
						 	  style="background-color: #20b2aa;border: 3px solid #25ad9f;">
						 	<a ng-click="cancel()" href="#" class="flR cross_icon" close-login-message>
								<img src="/static/images/profiles/../cross_icon.gif">
        					</a>
							<span style="font-size:15px; color:white;">Login to create messages, form groups, plan events and much more with other folks !!</span>
						 </div>	
						</c:when>
						</c:choose>	
						<div class="row">
							
								<!--  Search Widget -->
								<jsp:include page="searchWidget.jspx" />
								<!--  /Search Widget -->
							
						</div>
						 
						 <div class="row lm" ng-hide="${userProfessionFilled}" style="display:none">
							<div class="col-md-2"><!-- HELL --></div>
							
							
							<div class="clearFix append_bottomHalf shadow_genrator_1 listing_div col-md-6 suggestDetails">
							<a ng-click="cancel()" href="#" class="flR cross_icon" slide-up>
								<img src="/static/images/profiles/../cross_icon.gif">
        					</a>
				            <div class="col-md-8 suggestDetailsProfessionText" >
				                	<p><strong>Tell us your profession, it takes a fraction of second</strong></p>
                                    <p><strong>This would help us find you more like-minded souls</strong></p>
							</div>
			                
			                <div class="col-md-4 suggestDetailsProfessionInput">
				               <div class="col-md-8">
				                    <select ng-model="myProfession" ng-options="profession as profession.professionName for profession in professions" class="suggestDetailsProfessionDropdown"></select>
				               </div>
				               
				                  <button class="suggestDetailsProfessionDropdown" ng-click="assignProfession($event, myProfession)" slide-up-button>DONE</button>
							 	
                            </div>
			               
					        </div>
						</div>
						
						<div class="row lm hidden-sm">
							<div class="col-md-2"><!-- HELL --></div>
							<div class="col-md-8">
							
								<span ng-if="listingType=='cityLevel' &amp;&amp; myControllerLocationId != undefined &amp;&amp; peopleListLoaded" style="color: gray;font-size:20px; font-weight: bold; font-family: Times New Roman,sans-serif">
                					
                					<span class="hidden-xs">Oops, we couldn't find people in your locality, Instead listing people in your city !!</span>
                					<span class="hidden-lg hidden-md hidden-sm" style="font-size:13px;">Oops, couldn't find people in your locality,</span>
                					 <span class="hidden-lg hidden-md hidden-sm" style="font-size:13px;">Instead listing people in your city !!</span>
                				</span>	
                				<span ng-if="showingRecentlyJoinedList &amp;&amp; peopleListLoaded" style="color: gray;font-size:20px; font-weight: bold; font-family: Times New Roman,sans-serif">
                					<span class="hidden-xs">{{listingMessage}}</span>
                					<span class="hidden-lg hidden-md hidden-sm" style="font-size:15px;">{{shortListingMessage}}</span>
                				</span>	
							</div>
							
						</div>
						<div class="row lm">
								<div id="listingPage" class="content " ng-show="showListingPage">
									<div class="e">
										<div class="row">
											<div class="col-md-2">
											<%-- 	<jsp:include page="/WEB-INF/jsp/questions/sportsplayed.jspx" /> --%>
											</div>
											
												<jsp:include page="userListing.jspx" />
											
											
												<jsp:include page="searchFilters.jspx" />
											</div>
										</div>	
								  	</div><!-- wrapper -->
						</div><!-- listingPage -->
						</div>
					</div><!-- mycontroller -->
				</div>
			</div>
		</div>
	</div>
</div>

 
