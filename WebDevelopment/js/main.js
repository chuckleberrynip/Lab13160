//variables
const time=document.getElementById('time');
var todday=new Date();


//function
function showTime() { 
    let rightNow = new Date(),
        hours = rightNow.getHours(), 
        minutes = rightNow.getMinutes(),
        seconds = rightNow.getSeconds();

    time.innerHTML = `${fixTime(hours)}<span>:</span>${fixTime(minutes)}<span>:</span>${fixTime(seconds)}`;
    setTimeout(showTime, 1000);


}
function fixTime(number) { 
    return (parseInt(number, 10) < 10 ? '0' : '') + number;
}
function newDateFunction() { 
    alert("The date is:\n" + todday.getMonth() + "/" +todday.getDate() + "/" +todday.getUTCFullYear());
}
//Run 'em 
showBack();
showTime();
