$(document).ready(function(){

			$("#startdatetime").keyup(function () {
                this.setCustomValidity('You should add at least one event dates.');
            });

			$("#chooseStartDateTime").datetimepicker({
				dayOfWeekStart:0,
				allowTimes:[
                  '00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30',
                  '07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30',
                  '13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30',
                  '20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30',
                ],
                formatTime: 'g:i a',
                defaultDate: new Date(),
                minDateTime: new Date(),
                onClose: setMinDate,
			});
			$("#chooseEndDateTime").datetimepicker({
				dayOfWeekStart:0,
				lang: 'en',
				allowTimes:[
                    '00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30',
                    '07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30',
                    '13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30',
                    '20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30',
                ],
                defaultDate: new Date(),
                minDateTime: new Date(),
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

		function disableDateInput(val){
		    document.getElementById("startdatetime").disabled = val;
		    document.getElementById("enddatetime").disabled = val;
		}


		function setMinDate(){
            var x=$('#chooseStartDateTime').val();
            if(x!=""){
                var date = x.split(" ")[0];
                var time = x.split(" ")[1];
                var year = date.split("/")[0];
                var month = date.split("/")[1];
                var day = date.split("/")[2];
                var hour = time.split(":")[0];
                var min = time.split(":")[1];
                $( '#chooseEndDateTime' ).datetimepicker( {
                    defaultDate: new Date(year,month-1,day,hour,min,00),
                    minDateTime: new Date(year,month-1,day,hour,min,00),
                } );
            }


        }

		//This function is temp. when DB supports table {R_id, startDate, startTime, endDate, endTime}, this will be removed.
		function deleteEventDate(start,end,list,index,len){
			var newString = "";
			var tobermvd = "";
			if(len==1){
			    tobermvd = start+"-"+end;
			}else if(index==len-1 && len>0){
			    tobermvd = ", "+start+"-"+end;
			}else{
			    tobermvd = start+"-"+end+", ";
			}
			newString = list.replace(tobermvd,"");
			return newString;
		}


