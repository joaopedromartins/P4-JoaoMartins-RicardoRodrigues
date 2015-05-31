$(function() {
	var audioPlayer = $("#audioPlayer")[0];
	
	$(".musicRow").click(function() {
		$(".musicEditRow").hide();
		var id = $(this).attr('id');
		$(".edit"+id).show();
	});
	
	
	$(".playBtn").click(function(e) {
		var music = $(this)[0].nextElementSibling.value;
		audioPlayer.src = music;
		audioPlayer.play();
		return false;
	});
	
})