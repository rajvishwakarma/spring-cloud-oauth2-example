/**
 * 
 */
$(function() {
		
	function imports() {
		var orgId = $("#imports").find("input[name=orgId]").val();
		var startDate = $("#imports").find("input[name=startDate]").val();
		var endDate = $("#imports").find("input[name=endDate]").val();
		var limit = $("#imports").find("input[name=limit]").val();

		var query = "recommendation/import/cards/events?";
		//var query = "recommendation/import/cards/sample?";
		
		if (limit != null && limit != "")
			query = query + "limit="+limit;
		
		if (startDate != null && startDate != "")
			query = query + "&startDate="+startDate;
		
		if (endDate != null && endDate != "")
			query = query + "&endDate="+endDate;
		
		if (orgId != null && orgId != "")
			query = query + "&orgId="+orgId;

		$.get(query, function(data) {
			
			showAlert(data.message, true)
		}, "json");
		return false;
	}

	function recommend() {
		var userId = $("#recommend").find("input[name=userId]").val();
		var userIds = $("#recommend").find("input[name=userIds]").val();
		var orgId = $("#recommend").find("input[name=orgId]").val();
		var limit = $("#recommend").find("input[name=limit]").val();
		
		//validation
		if ((userIds == null || userIds == "") && (userId == ""  || userId == null) && (orgId == null || orgId == "")){
			alert("Please provide One input out of (User Id, User Ids & Org Id)");
			return false;
		}
		
		if ((userIds != "" && userId != ""  &&  orgId != "") || 
				(userIds != "" && userId != ""  &&  orgId == "") ||
				  	(userIds == "" && userId != ""  &&  orgId != "") ||
				  		(userIds != "" && userId == ""  &&  orgId != "")
				){
			alert("Please provide only one input at a time out of (User Id, User Ids & Org Id)");
			return false;
		}
			
		var query = "recommendation/recommend/users";

		if (userId != null && userId != "")
			query = query + "/" + userId;

		if (userIds != null || userIds != "" || userId != ""  || userId != null || limit != null
				|| limit != "" || orgId != null || orgId != "")
			query = query + "?";

		if (userIds != null && userIds != "")
			query = query + "user-ids=" + userIds;
		if (orgId != null && orgId != "")
			query = query + "orgId=" + orgId;
		if (limit != null && limit != "")
			query = query + "&limit=" + limit;

		$.get(query, function(data) {
			var t = $("table#results tbody").empty();
			if (!data)
				return;

			if (Array.isArray(data))
				showAlert(data[0].message, true)
			else 
				showAlert(data.message, true)		

		}, "json");
		return false;
	}
	
	function showAlert(message, success){

		if(success)
			$('#msg').html('<div class="alert alert-success" role="alert"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <strong>'+message+'</strong></div>').fadeIn('slow');
		else
			$('#msg').html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span> <strong>'+message+'</strong></div>').fadeIn('slow');
		$('#msg').delay(5000).fadeOut('slow');
	}
	
	$("#imports").submit(imports);
	$("#recommend").submit(recommend);

})