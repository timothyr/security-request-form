$(document).ready(function(){
            var tmp = generateAllowingTimes();

			$("#chooseStartDateTime").datetimepicker({
            	dayOfWeekStart:0,
            	lang: 'en',
            	allowTimes: tmp,
                formatTime: 'g:i a',
                defaultDate: (new Date()).setMinutes( generateSetMin( (new Date()).getMinutes() ) ),
                minDateTime: new Date(),
                onClose: setEndMinDate,
            });

            $("#chooseEndDateTime").datetimepicker({
                dayOfWeekStart:0,
            	lang: 'en',
            	allowTimes: tmp,
                formatTime: 'g:i a',
                defaultDate: (new Date()).setMinutes(generateSetMin( (new Date()).getMinutes() ) ),
                minDateTime: new Date(),
                onClose: setStartMaxDate,
            });
            $("#chooseStartDateTime").val("");
            $("#chooseEndDateTime").val("");
            $("#startdatetime").val("");
            $("#enddatetime").val("");
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
		    var start =  document.getElementById("startdatetime");
		    var end =  document.getElementById("enddatetime");
		    if(start!=null && end!= null){
		        start.disabled = val;
		        end.disabled = val;
		    }
		}


		function setEndMinDate(){
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

        function setStartMaxDate(){
            var x=$('#chooseEndDateTime').val();
            if(x!=""){
                var date = x.split(" ")[0];
                var time = x.split(" ")[1];
                var year = date.split("/")[0];
                var month = date.split("/")[1];
                var day = date.split("/")[2];
                var hour = time.split(":")[0];
                var min = time.split(":")[1];
                $( '#chooseStartDateTime' ).datetimepicker( {
                    defaultDate: new Date(year,month-1,day,hour,min,00),
                } );
            }
        }

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

//        function setEndMinDate(root){
//		    var target = root=='#chooseStartDateTime'?'#chooseEndDateTime':'#chooseStartDateTime';
//            var x=$(root).val();
//            if(x!=""){
//                var date = x.split(" ")[0];
//                var time = x.split(" ")[1];
//                var year = date.split("/")[0];
//                var month = date.split("/")[1];
//                var day = date.split("/")[2];
//                var hour = time.split(":")[0];
//                var min = time.split(":")[1];
//
//                if(target=='#chooseEndDateTime'){
//                   $( target ).datetimepicker( {
//                      defaultDate: new Date(year,month-1,day,hour,min,00),
//                      minDateTime: new Date(year,month-1,day,hour,min,00),
//                   });
//                }else{
//                    $( target ).datetimepicker( {
//                        defaultDate: new Date(year,month-1,day,hour,min,00),
//                    });
//                }
//
//            }
//        }

		function generateAllowingTimes(){
		    var allowingTimes = [];
		    var minutes = ["00","15","30","45"];
		    for(i = 0 ; i < 24 ; i++){
		        for(j = 0 ; j < 4 ; j++){
		            allowingTimes.push(i+":"+minutes[j]);
		        }
		    }
		    return allowingTimes;
		}

		function generateSetMin(min){
		    var num = "00";
		    if(min<=15){
		        num = "15";
		    }else if(min<=30){
		        num = "30";
		    }else if(min<=45){
		        num = "45";
		    }else if(min >46){
		        num = "60";
		    }
		    return num;
		}


