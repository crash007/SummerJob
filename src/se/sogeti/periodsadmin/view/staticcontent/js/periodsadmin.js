$(document).ready(function() {
	
	$(document).on('submit','#new-period-form', function(e) {
		e.preventDefault();
		console.log(e);
		console.log( $('#new-period-form').serializeArray());
		saveNew();
	});
	
	$(document).on('submit','#periods-list-form', function(e) {
		e.preventDefault();
		console.log(e);
		console.log( $('#periods-list-form').serializeArray());
		saveChanges();
	});
});

function saveChanges() {
	$.ajax({
		url : url + '/update/periods.json',
		type: "POST",
		data: $('#periods-list-form').serialize(),
		success: function(data, textStatus, jqXHR) {
		    if(data.status === 'success') {
		    	
		    } else {		        		
		        alert(data);
		    }
		 },
		 error: function(jqXHR, textStatus, errorThrown) {
			 console.log(textStatus);  
		 }
	});
}

function saveNew() {
	$.ajax({
		url : url + '/add/period.json',
		type: "POST",
		data :  $('#new-period-form').serializeArray(),
		success: function(data, textStatus, jqXHR) {
			console.log(data);
		    if(data.status === 'success') {
		        var alternativeID = data.data.alternativeID;
		        var name = data.data.name;
		        var fromdate = data.data.fromDate;
		        var todate = data.data.toDate;
		        var tableRow ='<tr id="periodId_' + alternativeID + '"><td id=period_name_' + alternativeID + '"><input id="period_name_' + alternativeID + '" type="text" value="' + name + '" name="period_name_' + alternativeID + '"></input></td><td><input id="period_fromdate_' + alternativeID + '" type="text" value="' + fromdate + '" name="period_fromdate_' + alternativeID + '"></input></td><td><input id="period_todate_' + alternativeID + '" type="text" value="' + todate + '" name="period_todate_' + alternativeID + '"></input></td>';
		        $('#periods-table tbody').append(tableRow);
		        
		        $("input[name*='new-period-name']").val('');
		        $("input[name*='new-period-fromdate']").val('');
		        $("input[name*='new-period-todate']").val('');
		    } else {		        		
		        alert(data);
		    }
		 },
		 error: function(jqXHR, textStatus, errorThrown) {
			 console.log(textStatus);  
		 }
	});
}
