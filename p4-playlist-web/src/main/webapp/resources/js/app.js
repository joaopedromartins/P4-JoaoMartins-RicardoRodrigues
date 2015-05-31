$(function() {
	var account = $("#accountScreen");
	var playlist = $("#playlistScreen");
	var accountBtn = $("#accountNav");
	var playlistBtn = $("#playlistNav");
	
	playlistBtn.click(function() {
		playlistBtn.addClass("selectedLink");
		accountBtn.removeClass("selectLink");
		playlist.show();
		account.hide();
		
		return false;
	});
	
	accountBtn.click(function() {
		accountBtn.addClass("selectedLink");
		playlistBtn.removeClass("selectLink");
		account.show();
		playlist.hide();
		
		return false;
	});
	
	
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
});