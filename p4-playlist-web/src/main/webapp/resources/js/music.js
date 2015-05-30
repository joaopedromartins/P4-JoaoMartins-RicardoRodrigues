$(function() {
	
	$(".musicRow").click(function() {
		$(".musicEditRow").hide();
		var id = $(this).attr('id');
		$(".edit"+id).show();
	});
	
	
	
	
})