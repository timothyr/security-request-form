function getArrayDateTimes( aString ) {
    var dates = [];
    var array = aString.split(", ");

    for(i = 0 ; i < array.length ; i++){

        var dateTime = array[i].split(" ");
        var start =
        dates.push( {
            startDate:dateTime[0],
            startTime:dateTime[1],
            endDate:dateTime[2],
            endTime:dateTime[3]
        } );
    }
    return dates;
}

function updateDateTime(aString){
    if (!aString || aString == 'not set') {
        return [];
    }
	var array = aString.split(", ");
	var dates = [];

    for(i = 0 ; i < array.length ; i++){
        var dateTime = array[i].split("-"); //[ "2017/11/01 12:30", "2018/01/01 01:30"]
        var startDate = (dateTime[0].split(" "))[0].split("/");
        var startTime = (dateTime[0].split(" "))[1].split(":");
        var endDate = (dateTime[1].split(" "))[0].split("/");
        var endTime = (dateTime[1].split(" "))[1].split(":");

        var start = new Date(startDate[0],startDate[1]-1,startDate[2],startTime[0],startTime[1]);
        var end = new Date(endDate[0],endDate[1]-1,endDate[2],endTime[0],endTime[1]);

        var diff = Math.abs(end.valueOf() - start.valueOf()) / 3600000;
        dates.push( {
            start:dateTime[0],
            end:dateTime[1],
            hours: diff,
        } );
    }
    return dates;
}

function generateEventDates(eventDates){
    var newEventDates = eventDates;
    var start = document.getElementById("chooseStartDateTime").value;
    var end = document.getElementById("chooseEndDateTime").value;
    if(start!=''&&end!=''){
        if(newEventDates==''){
            newEventDates += start +"-"+ end;
        }else{
        	newEventDates += ", "+start +"-"+ end;
        }
    }
    document.getElementById("chooseStartDateTime").value = '';
    document.getElementById("startdatetime").value = '';
    document.getElementById("chooseEndDateTime").value = '';
    document.getElementById("enddatetime").value = '';
    return newEventDates;
}

function hidePickerDiv(val){
    document.getElementById("picker_div").hidden = val;
}


