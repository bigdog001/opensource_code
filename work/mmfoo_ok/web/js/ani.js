$(function(){
	var page = 1;
    var i = 4; 
	$('span#buttom_right').click(function(){
		var $pictureShow = $('div#down');
		var downwidth = $pictureShow.width();
		var len = $('div#downContent').find('li').length;
		var page_number = Math.ceil(len/i);
		if( !$('div#downContent').is(":animated") ){
		if( page == page_number){
			$('div#downContent').animate({left:'0px'},"slow");
		page = 1;
		}else{
			$('div#downContent').animate({left:'-='+downwidth},"slow");
			page++;
		}
			}
		$('div#up span').eq((page-1)).addClass("current").siblings().removeClass("current");	
		});
    $('span#buttom_left').click(function(){
		var $pictureShow = $('div#down');
		var downwidth = $pictureShow.width();
		var len = $('div#downContent').find('li').length;
		var page_number = Math.ceil(len/i);
		if( !$('div#downContent').is(":animated") ){
		 if(page == 1){
	$('div#downContent').animate({left: '-='+downwidth*(page_number-1)},"slow");
			page = page_number;
			}else{
			$('div#downContent').animate({left:'+='+downwidth},"slow");	
			page--;	
				}
		}
		$('div#up span').eq((page-1)).addClass("current").siblings().removeClass("current");	
		});   
	});