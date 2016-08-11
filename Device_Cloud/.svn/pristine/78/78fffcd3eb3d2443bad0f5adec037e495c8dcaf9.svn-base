$(document).ready(function() {     	
    	
    	// MENU - SHOW HIDE
    	
    	//$(".gx-menu-holder").show(); 
    	$(".gx-nav-bar-left>li.gx-menu").click(function(e){    		 
		    $(".gx-menu-holder").fadeToggle('100');
		     e.stopPropagation();
		});		
		$(".gx-menu-holder").click(function(e){
		    e.stopPropagation();
		});		
		$(document).click(function(){
		    $(".gx-menu-holder").fadeOut('100');
		}); 
    	
    	// MENU - ACCORDION 
    	 var checkCookie = $.cookie("gx-menu-accordion-cookie");
		  if (checkCookie != "") {
			$('.gx-menu-accordion > li > a:eq('+checkCookie+')').addClass('active').next().show();
			$('.gx-menu-accordion>li ul li a:eq('+checkCookie+')').addClass('active').next().show();
		  }
		  
		  $('.gx-menu-accordion > li > a').click(function(){
		      var menuIndex = $('.gx-menu-accordion > li > a').index(this); 
		      
			  $.cookie("gx-menu-accordion-cookie", menuIndex); 
			  
			  $('.gx-menu-accordion li ul').slideUp('6000');
			   
			   if ($(this).next().is(":visible")){
				   $(this).next().slideUp('6000', function() {
						$('.gx-menu-accordion li a').removeClass('active');
				}); 
				   
			   } else {
			   		$(this).next().slideToggle('100', 'easeOutBounce');  
			   }
			   $('.gx-menu-accordion li a').removeClass('active');
			   $(this).addClass('active');
			    
		  });           
		  
        // ADD FAVORITES
        $(".gx-favorites").hide();
        $(function(){
		  $(".gx-favorites").click(function() {
		    if ($(this).attr("class") == "gx-favorites") {
		      this.src = this.src.replace("-remove","-add");
		    } else {
		      this.src = this.src.replace("-add","-remove");
		    }
		    $(this).toggleClass("add");
		  });
		});
 
    });