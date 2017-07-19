
 <div class="container">
      
      <div class="row">
      
        <div class="col-lg-12">
          <h1 class="page-header">Contact Us <small>We'd Love to Get Your Feedback!</small></h1>
        </div>
      </div><!-- /.row -->
      
      <div class="row">

        <div class="col-sm-8">
          <h3>Let's Get In Touch!</h3>
          <p>
          	We would really appreciate, if you can provide us some valuable feedback or suggestions around our idea.
          	You can report any bug, features you would like to have, queries around this site, any thing you
          	can think which can enhance or empower the current site.
          </p>
            <form id="contactusform" name="contactusform" role="form" method="POST" action="${context}pages/contactus">
            	<div class="row" id="smsgs">&nbsp;</div>
	            <div class="row">
	              <div class="form-group col-lg-4">
	                <label for="name">Name</label>
	                <input placeholder="Name" type="text" name="name" class="form-control" id="name">
	              </div>
	              <div class="form-group col-lg-4">
	                <label for="email">Email Address</label>
	                <input placeholder="Email" type="email" name="email" class="form-control" id="email">
	              </div>
	              <div class="form-group col-lg-4">
	                <label for="mobile">Mobile Number</label>
	                <input placeholder="Phone number" size="10"  type="number"  name="mobile" class="form-control" id="mobile">
	              </div>
	              <div class="clearfix"></div>
	              <div class="form-group col-lg-12">
	                <label for="message">Message</label>
	                <textarea placeholder="At lease 50 characters of your thoughts/ ideas/ sugesstions about our site and ideas which we can be benificial to us." name="message" class="form-control" rows="6" id="message"></textarea>
	              </div>
	              <div class="form-group col-lg-12">
	                <button id="submitcontactus" type="submit" class="btn btn-primary">Submit</button>
	                <span class="contactussubmit">&#160;</span>
	              </div>
              </div>
            </form>
        </div>

        <div class="col-sm-4">
		<%-- 
		  <h3>Modern Business</h3>
          <h4>A Start Bootstrap Template</h4>
          <p>
            5555 44th Street N.<br>
            Bootstrapville, CA 32323<br>
          </p> 
           <p><i class="fa fa-phone"></i> <abbr title="Phone">P</abbr>: (555) 984-3600</p>
           
         --%> 
          <p><i class="fa fa-envelope-o"></i> <abbr title="Email">E</abbr>: <a href="mailto:admin@interestify.com">admin@interestify.com</a></p>
         <%-- <p><i class="fa fa-clock-o"></i> <abbr title="Hours">H</abbr>: Monday - Friday: 9:00 AM to 5:00 PM</p> --%>
          <ul class="list-unstyled list-inline list-social-icons">
            <li class="tooltip-social facebook-link"><a href="https://www.facebook.com/interestify" target="blank" data-toggle="tooltip" data-placement="top" title="Facebook"><i class="fa fa-facebook-square fa-2x"></i></a></li>
            <li class="tooltip-social linkedin-link"><a href="#linkedin-company-page" data-toggle="tooltip" data-placement="top" title="LinkedIn"><i class="fa fa-linkedin-square fa-2x"></i></a></li>
            <li class="tooltip-social twitter-link"><a href="#twitter-profile" data-toggle="tooltip" data-placement="top" title="Twitter"><i class="fa fa-twitter-square fa-2x"></i></a></li>
            <li class="tooltip-social google-plus-link"><a href="#google-plus-page" data-toggle="tooltip" data-placement="top" title="Google+"><i class="fa fa-google-plus-square fa-2x"></i></a></li>
          </ul>
        </div>
      </div><!-- /.row -->
    </div><!-- /.container -->
 <!-- /.container -->
