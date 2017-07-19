var anyEventsLinkedToMe = false;

$(document).ready(function()
{

	$("a#groupDetailHelp").bind({
		   mousemove : changeTooltipPosition,
		   mouseenter : showTooltip,
		   mouseleave: hideTooltip
	});
	
	var changeTooltipPosition = function(event) {
		  var tooltipX = event.pageX - 8;
		  var tooltipY = event.pageY + 8;
		  $('div.tooltip').css({top: tooltipY, left: tooltipX});
		};

		var showTooltip = function(event) {
		  $('div.tooltip').remove();
		  $('<div class="tooltip">I\' am tooltips! tooltips! tooltips! :)</div>')
	          .appendTo('body');
		  changeTooltipPosition(event);
		};
		
		var hideTooltip = function() {
			   $('div.tooltip').remove();
	};
	
	// Filters settings..
/*	$("#filter_data,#filter_datahotel").find("a.filter_item").on("click", function(e)
															  {
																  var $this = $(this);
																  var $getIndex = $("#filter_data a.filter_item,#filter_datahotel a.filter_item").index($this);
																
																  $("#filter_data a.filter_item,#filter_datahotel a.filter_item").each(function(index)
																									{
																										if(index!=$getIndex)
																										{
																							$("#filter_data,#filter_datahotel").find(".filter_dropdown:eq("+index+")").hide();
																										}
																										else
																										{
																											if($this.next('.filter_dropdown').is(":visible")==false)
																										   {
																											    $this.next('.filter_dropdown').slideToggle();
																										   }
																										}
																										
																									});
																  $this.parents(".filter_items_list").find("a.filter_item").removeClass("active");
																  $this.addClass("active");
																  e.preventDefault();
																  stopPropogation(e);
															  });*/
/*	
	$(document).on("click", function(e){ 
										if (e.button == 0)
										{

									  	   if($(".filter_dropdown").is(":visible") == true)
										   {
										   		$(".filter_dropdown").slideUp(300);
												$(".filter_items_list > li > a").removeClass("active");
										   }

										}
										   
									   });*/
	
 /* $('.drop-menu').click(function() {
								 var drop =$(this);
								 drop.addClass('bottom_radius');
      $('.drop-menu-content').show();
      return false;
    });
    $('body, .drop-menu-content').click(function() {
												 
												 var remove_bottom_radius = $('body, .drop-menu-content').find('.bottom_radius');
												 
												 
      $('.drop-menu-content').hide();
				 remove_bottom_radius.removeClass('bottom_radius');
				       //return false;
});*/



/*  $('a.select_bus').click(function(e) {
    var $this = $(this);
   
	$.add2cart(this,'listing_section','cart');
	e.preventDefault();

});*/

// View top tabs
/*	$("ul.buspopuptabs > li > a").click(function(e)
			{
				var $tabs = $("ul.buspopuptabs");
				var getmain_index = $("ul.buspopuptabs > li > a").index($(this));
				var getCurrentTab = $(this).attr("rel");
				$("ul.buspopuptabs a").each(function(index)
				{
					if(index!=getmain_index)
					{
						var allTabData = $tabs.find("li a:eq("+index+")").attr("rel");
						$(allTabData).hide();
					    $tabs.find("li a:eq("+index+")").removeClass("active");
					}
				});
				if($(getCurrentTab).is(":visible")==false)
				{
					$(getCurrentTab).hide().fadeIn('slow');
				}
				$(this).addClass("active");
			
				return false;
			}).filter("a:eq("+0+")").trigger("click");*/



// View top tabs
/*	$("ul.hotelpopuptabs > li > a").click(function(e)
			{
				var $tabs = $("ul.hotelpopuptabs");
				var getmain_index = $("ul.hotelpopuptabs > li > a").index($(this));
				var getCurrentTab = $(this).attr("rel");
				$("ul.hotelpopuptabs a").each(function(index)
				{
					if(index!=getmain_index)
					{
						var allTabData = $tabs.find("li a:eq("+index+")").attr("rel");
						$(allTabData).hide();
					    $tabs.find("li a:eq("+index+")").removeClass("active");
					}
				});
				if($(getCurrentTab).is(":visible")==false)
				{
					$(getCurrentTab).hide().fadeIn('slow');
				}
				$(this).addClass("active");
			
				return false;
			}).filter("a:eq("+0+")").trigger("click");*/

//   For review tab
/*	$("ul.ratingreview_tab > li > a").click(function(e)
			{
				var $tabs = $("ul.ratingreview_tab");
				var getmain_index = $("ul.ratingreview_tab > li > a").index($(this));
				var getCurrentTab = $(this).attr("rel");
				$("ul.ratingreview_tab a").each(function(index)
				{
					if(index!=getmain_index)
					{
						var allTabData = $tabs.find("li a:eq("+index+")").attr("rel");
						$(allTabData).hide();
					    $tabs.find("li a:eq("+index+")").removeClass("active");
					}
				});
				if($(getCurrentTab).is(":visible")==false)
				{
					$(getCurrentTab).hide().fadeIn('slow');
				}
				$(this).addClass("active");
			
				return false;
			}).filter("a:eq("+0+")").trigger("click");
*/

//   For galleryView slider
if($('.myGallery').length > 0)
{
    $('.myGallery').galleryView({
        transition_speed: 900, 		//INT - duration of panel/frame transition (in milliseconds)
        transition_interval: 900, 		//INT - delay between panel/frame transitions (in milliseconds)
        easing: 'swing', 			//STRING - easing method to use for animations (jQuery provides 'swing' or 'linear', more available with jQuery UI or Easing plugin)
        show_panels: true,
        show_panel_nav: true,
        enable_overlays: false,
        panel_width:300, 			//INT - width of gallery panel (in pixels)
        panel_height: 220, 			//INT - height of gallery panel (in pixels)
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
        frame_gap: 12
    });
}


		
		/* ---------------- Overlays Settings   ---------------- */
		/* ------ Overlays Initialize ------ */
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		/*$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		$( ".seat_buspopup").dialog({
			width:865	,
			dialogClass: "overlay_popup",
			modal: true,
			autoOpen:false
		});

		 ------ Overlays Open ------ 
		$(".seat_buspopup_open").on("click", openOverlay);

		 ------ Overlays Close ------ 
		$(".seat_buspopup_close").on("click", closeOverlay);*/



/* ---------------- Overlays Settings-For-Hotel   ---------------- */
		/* ------ Overlays Initialize ------ */
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		/*$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		$( ".hotelpup").dialog({
			width:820,
			dialogClass: "overlay_popup",
			modal: true,
			autoOpen:false
		});

		 ------ Overlays Open ------ 
		$(".hotel_buspopup_open").on("click", openOverlay);

		 ------ Overlays Close ------ 
		$(".hotel_buspopup_close").on("click", closeOverlay);*/



/* ---------------- Overlays Settings   ---------------- */
		/* ------ Overlays Initialize ------ */
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		/*$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		$( ".checkoutpopup").dialog({
			width:840	,
			dialogClass: "overlay_popup",
			modal: true,
			autoOpen:true
		});

		 ------ Overlays Close ------ 
		$(".checkoutpopup_close").on("click", closeOverlay);
*/

});  //end of domReady().

// Prevent propogation...
/*var stopPropogation = function(e)
{
		if (e.button == 0)
		{
			e.stopPropagation();
		}
}*/

 /* ------- Register Objects for close and open buttons Mapping... ------- */
/*var objectOvelayMap_1 = { seat_buspopup_open:"seat_buspopup",hotel_buspopup_open:"hotelpup"};
var objectOvelayMap = { seat_buspopup_close:"seat_buspopup",hotel_buspopup_close:"hotelpup",checkoutpopup_close:"checkoutpopup"};*/

/*function openOverlay()
{
	var thisId = this.className.split(" ")[1];
	for(var x in objectOvelayMap_1)
	{
		if( thisId === x)
		{
			$("."+objectOvelayMap_1[x]).dialog("open");
			$("."+objectOvelayMap_1[x]).find(".close_popup").show();
		}
	}
	return false;
}

function closeOverlay()
{
	var thisId = this.className.split(" ")[1];
	for(var x in objectOvelayMap)
	{
		if( thisId === x)
		{
			$("."+objectOvelayMap[x]).dialog("close");
			$("."+objectOvelayMap[x]).find(".close_popup").hide();
		}
	}
	return false;
}
*/
function addColorToMsgPersonId(id){
$('ul#usersInMsgHistoryListUlId li').removeClass('bg-primary');
	$("ul#usersInMsgHistoryListUlId li#li-"+id).addClass("bg-primary");
	
	
}

function specific(){
	$('ul#usersInMsgHistoryListUlId li').removeClass('bg-primary');
		$("ul#usersInMsgHistoryListUlId li#li-48761341803494").addClass("bg-primary");
		
		
	}


function chooseMsgNotificationIcon(){
	//$("#switchToHistoryMsgScreenBtnId").removeClass('switchToHistoryMsgScreenBtnColor');
	$("#red_mail_id").hide();
	$("#green_mail_id").show();
	 //$('#collapseOne').collapse('hide');
}


function addClassToElement(id, addClassName, removeClassName){
	$("#"+id).removeClass(removeClassName);
	$("#"+id).addClass(addClassName);
}	

function applyStyleOnMsgWindow(){
	setTimeout("applyScrollerOnMsgWindow()", 100);
}

function applyScrollerOnMsgWindow(){
	var totalHeight = 0;
	$(".listOfMsgHistory").children().each(function(){
	    totalHeight = totalHeight + $(this).outerHeight();
	});
//	alert(totalHeight);
	
	if(totalHeight > 250){
		$("#listOfHistoryMsgUlId").addClass("listOfHistoryMsgUlWithYScroll");
		$("#listOfHistoryMsgUlId").removeClass("listOfHistoryMsgUl");
	
	}
	else{
		$("#listOfHistoryMsgUlId").removeClass("listOfHistoryMsgUlWithYScroll");
		$("#listOfHistoryMsgUlId").addClass("listOfHistoryMsgUl");
	}
	
}

function scrollOnClickForMobile() {
    $('html, body').animate({
        scrollTop: $("#messageWindow").offset().top
    }, 1000);
};

function scrollImageOnLandingPage() {
    
}

/*****************MESSAGE SCREEN**** EVENT SCREEN******************************************/

//TODO : checks if div available
$('div#listOfHistoryMsgUlId').slimscroll({
	  height: '400px',
	  width:'99%',
	  alwaysVisible: false,
	  color: '#00f',
	  size: '9px'
	});
	
	
$('#myTextarea').limit('140','#charsLeft');


$('div#listOfHistoryMsgUlId').slimscroll({
	  height: '400px',
	  width:'99%',
	  alwaysVisible: false,
	  color: '#00f',
	  size: '9px'
	});
	
	
$('#myTextarea').limit('140','#charsLeft');

/*****************MESSAGE SCREEN**********************************************/

	
		


