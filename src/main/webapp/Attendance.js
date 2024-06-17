function Attendance(){
	var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById('content').innerHTML = this.responseText;
				const currentDate = new Date();
                 currentDate.setDate(currentDate.getDate() + 1);
            
               document.getElementById("to").min = currentDate.toISOString().split('T')[0];
               document.getElementById("from").min = currentDate.toISOString().split('T')[0];
               leaveRequest();
                Ajax("get","checkedIn","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        
			if(data != "no data"){
                    var parts = data.split(',');
                    var timestamp = parts[0].slice(1);
                    var secondValue = parts[1].slice(0, -1);
                    console.log(timestamp);
                    console.log(secondValue);
                    if(secondValue=='null'){
						checkedIn()
						console.log(secondValue)
					}
                    localStorage.setItem("startTime", timestamp);
                    localStorage.setItem("secondValue", secondValue);
                    localStorage.setItem("checkedin", true);
		}else{
			localStorage.setItem("checkedin",false);
			localStorage.setItem("startTime", 1);
		}
       
    });
                
        };
        }
        xhttp.open("GET","Attendance.jsp", true);
        xhttp.send();
}
function leaveRequest(){
	document.getElementById("leaveRequest").innerHTML = "";
    Ajax("get","ApplyLeave","",function (error, data) {
		console.log(data)
        console.log(JSON.parse(data));
        if(data!=""){
            var tabledata = `<table>
                <thead>
                    <th>from</th>
                    <th>to</th>
                    <th>status</th>
                </thead>
                <tbody>`;
                
            JSON.parse(data).forEach(leaveData =>{
                var status = leaveData.status == 'approved' ? 'approved' : (leaveData.status == 'declined' ? 'declined' : 'waiting');
                var tableBody = `<tr class='${status}'>
                    <td>${leaveData.from_date}</td>
                    <td>${leaveData.to_date}</td>
                    <td>${leaveData.status}</td>
                </tr>`;
                tabledata = tabledata + tableBody;
            });
            tabledata = tabledata + `</tbody></table>`;
            document.getElementById("leaveRequest").innerHTML = tabledata;
        }
    });
}


 function getValue(id){
	   return document.getElementById(id).value;
}
function setValue(id){
	document.getElementById(id).value = '';
}


function ApplyLeave(event){
	
	LeaveDate = {
		from:getValue("from"),
		to:getValue("to"),
		reason:getValue("reason")
	};
	 Ajax("post","ApplyLeave",LeaveDate,function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(data);
        if(data == "updated"){
			setValue("from");
			setValue("to");
			setValue("reason");
			document.getElementById("LeaveForm").style.display = 'none';
			leaveRequest()
			
			
		}else{
			event.preventDefault();
		}
        });
	
}
function ApplyLeaveToggle(){
	var form = document.getElementById("LeaveForm");
	form.style.display = form.style.display == 'none'?'grid':'none';
}
function displayTimer() {
          var startTime = new Date(localStorage.getItem("startTime")).getTime() ;
          console.log(localStorage.getItem("startTime"));
            intervalId = setInterval(function () {

                var currentTime = new Date().getTime();
                var elapsed = currentTime - startTime;
                var hours = Math.floor(elapsed / (1000 * 60 * 60));
                var minutes = Math.floor((elapsed % (1000 * 60 * 60)) / (1000 * 60));
                var seconds = Math.floor((elapsed % (1000 * 60)) / 1000);
          document.getElementById("timer").innerText = hours + ":" + minutes + ":" + seconds;
            }, 1000);
}
function checkedout(){
	document.getElementById("checked-in").style.display = "inline-block";
	document.getElementById("checked-out").style.display = "none";
	 clearInterval(intervalId);
	   Ajax("post","checkedOut","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(data);
          
       
    });
}
function checkedIn(){
	document.getElementById("checked-in").style.display = "none";
	document.getElementById("checked-out").style.display = "inline-block";
	console.log(localStorage.getItem("checkedin") == false)
	if(localStorage.getItem("checkedin")== "false"){
     
      localStorage.setItem("startTime", new Date());
      localStorage.setItem("checkedin",true);
    }
    Ajax("post","checkedIn","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(data);
          displayTimer();
       
    });
    
  
	
}
 