$(function(){
	$('#slideshowHolder').jqFancyTransitions({ 
		effect: 'wave', // wave, zipper, curtain
		width: 900, // width of panel
		height: 292, // height of panel
		strips: 30, // number of strips
		delay: 3000, // delay between images in ms
		stripDelay: 30, // delay beetwen strips in ms
		position: 'alternate', // top, bottom, alternate, curtain
		direction: 'fountainAlternate', // left, right, alternate, random, fountain, fountainAlternate
		navigation: true, // prev and next navigation buttons
		links: true // show images as links
	});
	
	//arrow links
	$("a.more").hover(function(){
		$(this).find("img").stop().animate({marginRight: "8"}, "fast")
	}, function(){
		$(this).find("img").stop().animate({marginRight: "4"}, "normal")
	});
	
	//description
	$(".img-list1 li a").hover(function(){
		$(this).find(".description").stop().animate({bottom: "0"}, "fast")
	}, function(){
		$(this).find(".description").stop().animate({bottom: "-70"}, "fast")
	});
});