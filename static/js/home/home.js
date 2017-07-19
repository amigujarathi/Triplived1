$(document).ready(function()
		{
					var ie6Detect = false;      
					if($.browser.msie && $.browser.version == "6.0")
					{
						ie6Detect = true;
					}
					
					var $win = $(window);
					var $sort = $("#fixed_part");
					var $original_top = $sort.offset().top;
					
					$win.bind("scroll resize",function()
					{
						var $win_top = $(window).scrollTop();
						var $sort_height = 0;
						
						// back to top check...
					
						if($sort.length > 0)
						{
									var $sort_top = $sort.offset().top;
									$sort_height = $sort.outerHeight() || 0;
									
									if(($win_top > $sort_top) && !$sort.is(".fixed") && !ie6Detect)
									{
										$sort.prev("div.overlap_height").css({height:$sort.outerHeight()}).show(); // +16 for append bottom class..
										$sort.css({"top":0}).removeClass("original").addClass("fixed");
									}
									
									else if($win_top < $original_top)  // Upper Fold [ SCROLL CASE... ]
									{
										$sort.prev("div.overlap_height").hide();
										$sort.removeClass("fixed").addClass("original");
									}                                                                       
						}
						
					}); 
					
					
					$('.traveller_rooms').click(function() {
										 var traveller_roomsinfo =$(this);
										 traveller_roomsinfo.addClass('bottom_radius');
		      $('.traveller_details').show();
		      return false;
		    });

						$(document).on("click", docHandler);
						$("div.traveller_details").on("click",
						function(e)
						{
							if(!$(e.target).is("a.close_room"))
							{
								
								stopPropogate(e);
							}
						});
					
					});
					
					
					function docHandler(e)
					{
						if(e.button == 0)
						{
							if($("div.traveller_details").is(":visible"))
							{
								$("div.traveller_details").hide();
							}
						}
					}

					function stopPropogate(event)
					{
						event.stopPropagation();
					}

					
	$("input.date_control_input").datepicker({
	numberOfMonths:2,
 	showButtonPanel: true,
	closeText:''
	});
	
	$("a.calender_box").click(function()
	{
		var $this = $(this);
		$this.prev("input").trigger("focus");
	});
	
	// Time slider...
	 $( ".time_slider" ).slider({
       range: true,
       min: 0,
       max: 1439,
		values: [540, 1020],
       slide: function(event, ui){ slideTime(event, ui, $(this)); },
   });
	
	slideTime();
	
	function slideTime(event, ui, currentObj){
				if(currentObj == null || currentObj == undefined){ currentObj = $(".time_slider"); }
				var val0 = currentObj.slider("values", 0),
				val1 = currentObj.slider("values", 1),
				minutes0 = parseInt(val0 % 60, 10),
				hours0 = parseInt(val0 / 60 % 24, 10),
				minutes1 = parseInt(val1 % 60, 10),
				hours1 = parseInt(val1 / 60 % 24, 10);
				
			 startTime = getTime(hours0, minutes0);
			endTime = getTime(hours1, minutes1);
			currentObj.parents("div.slider_section").find(".time_top").text(startTime + ' - ' + endTime);
			}		
	
	function getTime(hours, minutes) {
					var time = null;
					minutes = minutes + "";
					if (hours < 12) { time = "AM"; }
					else {  time = "PM"; }
					if (hours == 0) { hours = 12; }
					if (hours > 12) { hours = hours - 12; }
					if (minutes.length == 1) {minutes = "0" + minutes;}
					return hours + ":" + minutes + " " + time;
			}