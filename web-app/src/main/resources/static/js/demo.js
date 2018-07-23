/**
 *	Trail Recommendation Demo File  
 */
$(function(){
	
	var xApiToken = null;
	var userId = null;
	
	function runDemo() {
		userId = $("#demo").find("input[name=userId]").val();
		xApiToken = $("#demo").find("input[name=xApiToken]").val();
		
		
		//validation
		if (userId == ""  || userId == null){
			alert("Please provide User ID");
			return false;
		}
		
		if (xApiToken == ""  || xApiToken == null){
			alert("Please provide X-Api-Token");
			return false;
		}
		
		var query = "/cards"
		var body = "{ \"xApiToken\": \""+xApiToken+"\"}" 
		$.ajaxSetup({
			   headers:{
				   'Content-Type': 'application/json'
			   }
			});
		$.post(query, body, function(data) {
						var t = $("#mainbox").empty();
						if (!data)
							return;
						
						$("#details").hide();
						$("#recommendations").hide();
						$("#cards").show();
						
						data
						.forEach(function(value) {
							if(value.resource !== undefined )
								$("<div class=\"card\">"+
										"<p id = \"cardId\">"+value.id+"</p>"+		
										"<img src=\""+value.resource.imageUrl+"\"/>"+
										"<h1>"+value.resource.title+"</h1>"+
										"<button id=\"cardbutton-"+value.id+"\" class=\"btn btn-default card-class\" type=\"button\">View Card</button>"+
										"</div>"
								)
								.appendTo(t)
						});
						
						showAlert("Cards Retrieved", true)
					}, "json");
		return false;
	}

	$( "#mainbox" ).on( "click", "button", function() {
		let idVal = $(this).attr('id');
		let idSplit = idVal.split("-");
		let cardId = idSplit.length > 1 ? idSplit[1] : null;

		if(cardId){
			//get Cards Details
			var query = "/cards/"+cardId
			var body = "{ \"xApiToken\": \""+xApiToken+"\"}" 
			$.ajaxSetup({
				   headers:{
					   'Content-Type': 'application/json'
				   }
				});
			$.post(query, body, function(data) {
						var t = $("#cardbox").empty();
						if (!data)
							return;

						$("#cards").hide();
						$("#details").show();
						if(data.resource !== undefined )
							$("<div class=\"card-details\">"+
									"<p id = \"cardId\">"+data.id+"</p>"+		
									"<img src=\""+data.resource.imageUrl+"\"/>"+
									"<h1>"+data.resource.title+"</h1>"+
									"<p>"+data.resource.description+"</p>"+
									"</div>"
							)
							.appendTo(t)

						showAlert("Cards Retrieved", true)
					}, "json");


			//create cardId view records in graph DB
//			var query = "recommendation/events/cards"
//			var body = "{ \"orgId\": 1434, \"cardId\": "+cardId+", \"userId\": "+userId+", \"cardEvent\": \"card_viewed\"}" 
//			$.ajaxSetup({
//					   headers:{
//						   'Content-Type': 'application/json'
//					   }
//				});
//			alert(body);
//			$.post(query, body, function(data) {
//					alert(data);
//					$('#msg').html("cards event created").fadeIn('slow');
//				    $('#msg').delay(5000).fadeOut('slow');
//			}, "json");

			//get recommendation
			var query = "recommendation/recommend/users/"+userId

			$.get(query, function(data) {
						if (!data)
							return;

						fetchRecommendedCards(data.recommendedCards);
						
						showAlert("Recommended Cards Retrieved", true)
					}, "json");

			return false;
		}
	});

	function fetchRecommendedCards(recommendedCards){
		var output = "";
		$.each(recommendedCards, function( index, value ) {
			var query = "/cards/"+value
			var body = "{ \"xApiToken\": \""+xApiToken+"\"}" 
			$.ajaxSetup({
				   headers:{
					   'Content-Type': 'application/json'
				   }
				});
			$.post(query, body, function(data) {
						if (!data)
							return;

						if(data.resource !== undefined )
							output += "<div class=\"card\">"+
							"<p id = \"cardId\">"+data.id+"</p>"+		
							"<img src=\""+data.resource.imageUrl+"\"/>"+
							"<h1>"+data.resource.title+"</h1>"+
							"</div>"

							if(recommendedCards.length-1 == index )
							{	
								$("#recommendations").show();

								var t = $("#recommendbox");
								t.empty();
								$(output).appendTo(t)
								
								showAlert("Cards Retrieved", true)
							}		

					}, "json");

		});

	}

	function showAlert(message, success){
		
		if(success)
			$('#msg').html('<div class="alert alert-success" role="alert"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <strong>'+message+'</strong></div>').fadeIn('slow');
		else
			$('#msg').html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span> <strong>'+message+'</strong></div>').fadeIn('slow');
		$('#msg').delay(5000).fadeOut('slow');
	}
	
	$("#demo").submit(runDemo);
	
	$("#view-trail-recommendation").click(function() {
		$("#main").hide();
		$("#trail-recommendation").show();
	})
})