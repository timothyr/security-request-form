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