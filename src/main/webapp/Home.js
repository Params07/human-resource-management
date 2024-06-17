function applyFilter(){
	 var fromDate = document.getElementById("from").value;
	 var team = document.getElementById("team").value;
        if(!team){
			team = "0"
		}
        console.log(team);
	Ajax("get","getAttendance?from="+fromDate+"&team="+team,"",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
      console.log(data);
       
    });
}
function Home(){
	Ajax("get","Home.jsp","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        const curr_date = new Date();
      document.getElementById("content").innerHTML = data;
      const formatted_date = curr_date.toISOString().split('T')[0];
    document.getElementById("from").value =  document.getElementById("from").max   = formatted_date;
   dropDownTeam()
   applyFilter();
       
    });
}
 

    
   
   
   