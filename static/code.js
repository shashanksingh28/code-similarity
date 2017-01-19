function updateJaccard(results){
	console.log(results);
	document.getElementById("proposed_recos").value = results;
}

function updateText(results){
	console.log(results);
	document.getElementById("proposed_recos").value = results;
}

function postRequests(){
	var text = document.getElementById("method_text").value;
	alert(text);
	$.ajax({
        url: 'http://localhost:5000/equalJaccard',
		type: 'POST',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		crossDomain: true,
		data: text,		
		success: updateJaccard
	});
	
	$.ajax({
        url: 'http://localhost:5000/cosine',
		type: 'POST',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		crossDomain: true,
		data: text,		
		success: updateText
    });
	/*var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://127.0.0.1:5000/equalJaccard",
        "method": "POST",
        "headers": {
            "cache-control": "no-cache"
        },
        "data": text
    }

    $.ajax(settings).done(function (response) {
        console.log(response);
    });*/
}

