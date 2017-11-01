$(document).ready(function(){
			$("#chooseStartDateTime").datetimepicker({
				dayOfWeekStart:0,
				stepMinute: 30,
				lang: 'en',
				minDate: 0,
				formatTime: 'g:i a',
			});
			$("#chooseEndDateTime").datetimepicker({
				dayOfWeekStart:0,
				lang: 'en',
				//minDate will be modified to create a condition that startDateTime < endDateTime
				//currerntly, minDate == today
				minDate: new Date(getDateTime("chooseStartDateTime","startdatetime")),
				formatTime: 'g:i a',
			});
			document.getElementById("chooseStartDateTime").value = '';
			document.getElementById("startdatetime").value = '';
			document.getElementById("chooseEndDateTime").value = '';
			document.getElementById("enddatetime").value = '';
		});

		function setDateTime(e, inputId){
			document.getElementById(inputId).value = e.value;
		}

		function getDateTime(pickerId, inputId){
			var x = document.getElementById(pickerId).value;
			document.getElementById(pickerId).value = '';
			document.getElementById(inputId).value = '';
			return x;
		}

		//This function is temp. when DB supports table {R_id, startDate, startTime, endDate, endTime}, this will be removed.
		function deleteEventDate(start,end,list){
			var newString = "";
			var tobermvd = start+"-"+end+", ";
			newString = list.replace(tobermvd,"");
			return newString;
		}

		function updateDateTime(aString){
		    //aString = "2017/11/11 13:20-2017/11/11 14:30, ";
		    var array = aString.split(", ");
		    var dates = [];

            for(i = 0 ; i < array.length ; i++){
                var dateTime = array[i].split("-");
                dates.push( {
                    start:dateTime[0],
                    end:dateTime[1]
                } );
            }
		    return dates;
		}
