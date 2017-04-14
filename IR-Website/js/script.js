
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


// With JQuery
$("#tempNew").slider();
$("#tempNew").on("slide", function(slideEvt) {
	$("#tempNewVal").text(slideEvt.value);
});