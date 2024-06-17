function List(){
	document.getElementById("content").innerText = "kjsahdf";
   Ajax("GET","getUserData","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
        displayData(JSON.parse(data));
       
    });
    
}
function displayData(data,flag=false) {
   
   

    let listUsers = `<div style="margin-left:100px;width:90%;"> <table>
            <thead>
                <tr>
                    ${flag ? '<th>Actions</th>' : ''}
                    <th>Name</th>
                    <th>User ID</th>
                    <th>Department</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
    `;

    data.forEach(userData => {
		console.log(userData.activated_date)
        const actions = flag ? `
            <td>
               
                <input id="delete" type="button" value="delete" onclick="deleteUser('${userData.user_id}')">
                ${userData.activated_date == null ? ` <input id="activate" type="button" value="activate" onclick="activate('${userData.user_id}')">`:''}
            </td>` : '';

        const users = `
            <tr class="${userData.user_id}">
                ${actions}
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
