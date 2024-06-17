function Reporters(){
	Ajax("GET","getUserData","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
        displayData(JSON.parse(data));
       
    });
}
function individualData(id){
	console.log(id);
	Ajax("GET","getCheckinData?id="+id,"",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
        attendanceTable(JSON.parse(data))
       
    });
}
function attendanceTable(data){
	   let listDates = ` <div style="margin-left:100px; width:90%;"><table>
            <thead>
                <tr>
                   
                    <th>Date</th>
                    <th>Day</th>
                    <th>Checkin time </th>
                    <th>Checkout time</th>
                    
                    <th>Total Hours</th>
                </tr>
            </thead>
            <tbody>
    `;

    data.forEach(userData => {
        

        const users = `
            <tr class="${userData.user_id}" 	 ">
                
                <td>${userData.checkin_date}</td>
                <td>${userData.day_of_week}</td>
                <td>${userData.checked_in.slice(10,19)}</td>
                <td>${userData.checked_out == null ?'-':userData.checked_out.slice(10,19)}</td>
                <td>${userData.diff.slice(0,8)}</td>
            </tr>`;
            
        listDates += users;
    });

    listDates += ` </tbody></table> </div>`;
    
    document.getElementById("content").innerHTML = listDates;
}
function displayData(data) {
   
   

    let listUsers = ` <div style="margin-left:100px; width:90%;"><table>
            <thead>
                <tr>
                   
                    <th>Name</th>
                    <th>User ID</th>
                    <th>Department</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
    `;

    data.forEach(userData => {
        

        const users = `
            <tr class="${userData.user_id}" 	 onclick="individualData('${userData.user_id}')">
                
                <td>${userData.name}</td>
                <td>${userData.user_id}</td>
                <td>${userData.team_name}</td>
                <td>${userData.email }</td>
            </tr>`;
            
        listUsers += users;
    });

    listUsers += ` </tbody></table> </div>`;
    
    document.getElementById("content").innerHTML = listUsers;
}