function updateDateTime(aString){
    var array = aString.split(", ");
    var dates = [];

    for(i = 0 ; i < array.length ; i++){
        var dateTime = array[i].split("-");

        dates.push( {
            start:dateTime[0],
            end:dateTime[1],
            hours: "1.5",
        } );
    }
    return dates;
}