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
		$("li.selected").removeClass("selected");
		playlistBtn.parent().addClass("selected");
		$(".accountDetailsForm").hide();
		$("#accountDetails").css("width", "400px");
		
		return false;
	});
	
	accountBtn.click(function() {
		accountBtn.addClass("selectedLink");
		playlistBtn.removeClass("selectLink");
		account.show();
		playlist.hide();
		$("li.selected").removeClass("selected");
		accountBtn.parent().addClass("selected");
		
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
	
	
	$("#usernameEditor").click(function(e) {
		$(".accountDetailsForm").hide();
		$("#accountDetails").css("width", "550px");
		$("#usernameDetailsForm").show();
		return false;
	});
	
	$("#emailEditor").click(function(e) {
		$(".accountDetailsForm").hide();
		$("#accountDetails").css("width", "550px");
		$("#emailDetailsForm").show();
		return false;
	});
	
	$("#passwordEditor").click(function(e) {
		$(".accountDetailsForm").hide();
		$("#accountDetails").css("width", "755px");
		$("#passwordDetailsForm").show();
		return false;
	});
});