function Requests(){
	Ajax("GET","ApprovingLeave","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
         leaveRequestTable(JSON.parse(data))
       
       
    });
}
function leaveRequestTable(data){
	console.log(document.getElementById("content"))
	document.getElementById("content").innerHTML = ''
	 if(!data.length == 0){
		 
	 
	   let listDates = ` <div style="margin-left:100px; width:90%;"><table>
            <thead>
                <tr>
                   
                    <th>name</th>
                    <th>from date</th>
                    <th> to date</th>
                    <th>reason</th>
                    
                    <th>actions</th>
                </tr>
            </thead>
            <tbody>
    `;
     
    data.forEach(userData => {
      const actions = `<td> <input id="approve" value= "approve" type="button" onclick="approving('${userData.user_id}')" >
      <input id="decline" value= "decline" type="button" onclick="declined('${userData.user_id}')" > </td>`  
         
        const users = `
            <tr class="${userData.user_id}" 	 ">
                
                <td>${userData.name}</td>
                <td>${userData.from_date}</td>
                <td>${userData.to_date}</td>
                <td>${userData.reason}</td>
                ${actions}
            </tr>`;
            
        listDates += users;
    });

    listDates += ` </tbody></table> </div>`;
    
    document.getElementById("content").innerHTML = listDates;
    }
}

function approving(id){
	Ajax("post","ApprovingLeave?id="+id+"&status=approved","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
       console.log(data);
       Requests()
       
    });
	
}

function declined(id){
	Ajax("post","ApprovingLeave?id="+id+"&status=declined","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(data);
        Requests()
       
       
    });
	
}