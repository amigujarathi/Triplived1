<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" id="ng-app" ng-app="detailModule" ng-cloak>
 <div ng-init="selfId = '${selfId}' "></div>
 
 <div id="personInfoId" ng-controller="AbsolutePersonDetailCtrl" ng-init="getPersonDetails('${personDataUrl}')">
 <div ng-init="isPersonLoggedIn = '${isPersonLoggedIn}'"></div>


    
    
<script type="text/ng-template" id="messagePopupOnDetailScreen">

  <div class="modal" role="dialog">
   <div class="modal-dialog">
    <div class="modal-content">

          <a class="flR cross_icon" href="#" ng-click="cancel()" style="float:right" >
			<img src="${images_url}/../cross_icon.gif"></img>
          </a>

          <div class="modal-header">
			<h3>Send message !!</h3>

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
    
    


   
	<div class="col-lg-9 col-md-8">
			
			<!-- <div class="timeline-cover">	
				<div class="cover">
				<div class="top">
					<img src="../assets//images/photodune-2755655-party-time-s.jpg" class="img-responsive" />					
				</div>
				<ul class="list-unstyled">
					<li><a href="index.html?lang=en&amp;skin=black-and-white&amp;layout_fixed=false&amp;v=v1.0.1-rc4"><i class="fa fa-fw fa-clock-o"></i> <span>Timeline</span></a></li>
					<li class="active"><a href="about_1.html?lang=en&amp;skin=black-and-white&amp;layout_fixed=false&amp;v=v1.0.1-rc4"><i class="fa fa-fw fa-user"></i> <span>About</span></a></li>
					<li><a href="media_1.html?lang=en&amp;skin=black-and-white&amp;layout_fixed=false&amp;v=v1.0.1-rc4"><i class="fa fa-fw icon-photo-camera"></i> <span>Photos</span> <small>(102)</small></a></li>
					<li><a href="contacts_1.html?lang=en&amp;skin=black-and-white&amp;layout_fixed=false&amp;v=v1.0.1-rc4"><i class="fa fa-fw icon-group"></i><span> Friends </span><small>(19)</small></a></li>
					<li><a href="messages.html?lang=en&amp;skin=black-and-white&amp;layout_fixed=false&amp;v=v1.0.1-rc4"><i class="fa fa-fw icon-envelope-fill-1"></i> <span>Messages</span> <small>(2 new)</small></a></li>
				</ul>
			</div>
			<div class="widget">
				<div class="widget-body padding-none margin-none">
					<div class="innerAll">
						<i class="fa fa-quote-left text-muted pull-left fa-fw"></i> 
						<p class="lead margin-none">What a fun Partyyy</p>
					</div>
				</div>
			</div>
		</div> -->


			<h3>{{personName}}</h3>

			<div class="row">
				<div class="col-sm-6">
					<div class="innerAll">
					<!-- <div class="innerAll bg-white"> -->	
						<div class="media innerB ">
							
							<div class="row">
							  <div class="col-sm-6">
								  <a href="" class="pull-left">
									<img ng-src="/static/user-images/{{personId}}/{{personId}}.jpg" alt="" width="100" class="img-circle"/>
								  </a>
							  </div>
							  <div class="col-sm-6 text-right">
									<button class="paddingTd btn btn-primary" ng-click="open(personId)">Send Message</button>
							  </div>
						    </div>
						</div>
						
					</div>
					
					<div class="innerT">
						<div class="widget">
									<div class="widget-head border-bottom bg-gray">
											<h5 class="pull-left margin-none innerAll">Sports Interested In:</h5>
										<div class="pull-right">
											<!-- <a href="" class="text-muted">
												<i class="fa fa-eye innerL"></i>
											</a> -->
										</div>
									</div>
						
														
								<span class="innerR innerB" ng-repeat="sport in personSports">
									<!-- <i class="box-generic innerAll icon-chef-hat fa fa-4x" data-toggle="tooltip" data-original-title="{{sport}}" data-placement="bottom"></i> -->
									<span class="innerAll" style=" font-size: 115%;" ><i style= "color:green" class="icon-{{sport}}">&#160;</i><strong>{{sport}}</strong></span>
								</span>
								<!-- <span class="innerR innerB">
									<i class="box-generic innerAll  icon-soccerball-fiil fa fa-4x" data-toggle="tooltip" data-original-title="Soccer" data-placement="bottom"></i>
								</span>
								<span class="innerR innerB">
									<i class="box-generic innerAll  icon-steering-wheel fa fa-4x" data-toggle="tooltip" data-original-title="Driving" data-placement="bottom"></i>
								</span>
								<span class="innerR innerB">
									<i class="box-generic innerAll  icon-swimming fa fa-4x" data-toggle="tooltip" data-original-title="Swimming" data-placement="bottom"></i>
								</span>
						 		-->
						 		<!-- TODO later, once we ensure font-icons are available for all Interest types -->
						
						</div>
					</div>
					
					
				</div>
				<div class="col-sm-6">
						<div class="widget">
							<div class="widget-head border-bottom bg-gray">
								<h5 class="innerAll pull-left margin-none">Basic Info:</h5>
								<div class="pull-right">
									<!-- <a href="" class="text-muted">
										<i class="fa fa-pencil innerL"></i>
									</a> -->
								</div>
							</div>
							<div class="widget-body">
								<div class="row innerAll">
									<div class="col-sm-6">User:</div>
									<div class="col-sm-6 text-right">
										<span style="font-size:15px"><strong>{{personName}}</strong></span>
									</div>
								</div>
								<div class="row innerAll border-top">
									<div class="col-sm-6">Date-Of-Birth:</div>
									<div class="col-sm-6 text-right">
										<span style="font-size:15px" ng-cloak><strong>{{formatDate(personBirthDate) | date: 'dd MMMM,yyyy'}}</strong></span>
									</div>
								</div>
								<div class="row innerAll border-top" ng-if="getMsgTime(personId) != ''">
									<div class="col-sm-6">Joined:</div>
									<div class="col-sm-6 text-right">
										<span style="font-size:15px"><strong>{{formatDate(getMsgTime(personId)) | date: 'dd MMMM,yyyy'}}</strong></span> <!-- TODO -->
									</div>
								</div>
								<div class="row innerAll border-top">
									<div class="col-sm-2">Link:</div>
									<div class="col-sm-10 text-right">
										<a style="font-size:15px" href="#"><strong>www.interestify.com/person/{{interestifyName}}</strong></a> <!-- TODO -->
									</div>
								</div>
							</div>
						</div>
						<div class="widget">
							<div class="widget-head border-bottom bg-gray">
								<h5 class="innerAll pull-left margin-none">Contact:</h5>
								<!-- <div class="pull-right">
									<a href="" class="text-muted">
										<i class="fa fa-facebook innerL"></i>
									</a>
									<a href="" class="text-muted">
										<i class="fa fa-twitter innerL"></i>
									</a>
									<a href="" class="text-muted">
										<i class="fa fa-dribbble innerL"></i>
									</a>
								</div> -->
							</div>
							<div class="widget-body padding-none">
								<div class="innerAll">
									<p class=" margin-none" ng-show="showTelephoneCheck"><i class="fa fa-phone fa-fw text-muted"></i> {{personPhoneNo}}</p>
									<p class=" margin-none" ng-show="!showTelephoneCheck" data-toggle="tooltip" title="The user has restricted his/her contact number visibility from public view">
										<i class="fa fa-phone fa-lock fa-fw text-muted"></i>
										<i class="fa fa-lock fa-fw text-muted" ></i>
									  
									</p>
								</div>
								<div class="border-top innerAll">
									<p class=" margin-none" ng-show="showEmailCheck"><i class="fa fa-envelope fa-fw text-muted"></i> {{personEmailId}}</p>
									<p class=" margin-none" ng-show="!showEmailCheck" data-toggle="tooltip" title="The user has restricted his/her email visibility from public view">
										<i class="fa fa-envelope fa-fw text-muted"></i>
										<i class="fa fa-lock fa-fw text-muted"></i>
									</p>
								</div>
								<div class="border-top innerAll">
									<p class=" margin-none" ng-cloak><i class="fa fa-home fa-fw text-muted"></i> {{streetName}}, {{cityName}}</p>
								</div>
								
							</div>
						</div>
				</div>
				
			</div>
						
		
		
			
		</div>
		

     
 </div>
 </div>

