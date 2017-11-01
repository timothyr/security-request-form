function getArrayDateTimes( aString ) {
    var dates = [];
    var array = aString.split(", ");

    for(i = 0 ; i < array.length ; i++){

        var dateTime = array[i].split(" ");
        dates.push( {
            startDate:dateTime[0],
            startTime:dateTime[1],
            endDate:dateTime[2],
            endTime:dateTime[3]
        } );
    }
    return dates;
}

//var hi = getArrayDateTimes("2017/11/01 12:30 2017/11/01 14:20, 2017/11/02 15:30 2017/11/02 16:20, 2017/11/03 19:30 2017/11/03 23:20");
//for(i=0;i<hi.length;i++){
//    console.log(hi[i]);
//}