function Edit(){
		document.getElementById("content").innerText = "kjsahdf";
   Ajax("GET","getUserData","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
        displayData(JSON.parse(data),true);
       
    });
}
function deleteUser(id){
	Ajax("post","deleteUser?id="+id,"",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
      Edit();
       
    });
}
function activate(id){
	Ajax("post","activateUser?id="+id,"",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
      Edit();
       
    });
}