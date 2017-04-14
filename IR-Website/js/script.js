//// With JQuery
//$('#briNew').slider({
//	formatter: function(value) {
//		return 'Current value: ' + value;
//	}
//});

//// Without JQuery
//var slider = new Slider('#briNew', {
//	formatter: function(value) {
//		return 'Current value: ' + value;
//	}
//});


// With JQuery
$('#satNew').slider({
	formatter: function(value) {
		return 'Current value: ' + value;
	}
});

//// Without JQuery
//var slider = new Slider('#satNew', {
//	formatter: function(value) {
//		return 'Current value: ' + value;
//	}
//});


// With JQuery
$("#briNew").slider();
$("#briNew").on("slide", function(slideEvt) {
	$("#briNewVal").text(slideEvt.value);
});

// With JQuery
$("#satNew").slider();
$("#satNew").on("slide", function(slideEvt) {
	$("#satNewVal").text(slideEvt.value);
});