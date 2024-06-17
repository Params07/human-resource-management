function CreateUser(){
	var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById('content').innerHTML = this.responseText;
                dropDownTeam()
	          var team= document.getElementById('team');

             team.onchange = null;
           team.onchange = function () {
				managerData(team);
	};
            }
        };
        xhttp.open("GET","CreateUser.jsp", true);
        xhttp.send();
	
}
function dropDownTeam(){
	Ajax("POST","teamData?for=team","",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
        populateDropdown("team",JSON.parse(data))
       
    });
     

}
function managerData(team){
	
	Ajax("POST","teamData?for=manager&team="+team.value,"",function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        console.log(JSON.parse(data));
        populateDropdown("manager",JSON.parse(data))
       
    });
}
function populateDropdown(dropdownId, data,optionValue) {
        const dropdown = document.getElementById(dropdownId);
        dropdown.innerHTML = '';
        const defaultOption = document.createElement('option');
        defaultOption.text = 'Select';
        defaultOption.value = '';
        dropdown.add(defaultOption);

        
        data.forEach(item => {
            const option = document.createElement('option');
            option.text = item[1]; 
            option.value = item[0];
           
    
    if (option.text === optionValue || option.value === optionValue) {
            option.selected = true;
        } 	 
            dropdown.add(option);
        });
    }  
   function setError(error,msg){
	  
	   document.getElementById(error+"Error").innerText = msg;
   }
   function removeError(error){
	   document.getElementById(error+"Error").innerText = "";
   } 
   function getValue(id){
	   var value =  document.getElementById(id).value;
	  
	   return value;
   }
   const validateEmail = (email) => {
  return email.match(
    /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );
};
const validatePassword = (password)=>{
	var regex = new RegExp("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$");

	return regex.test(password);
}
function AddUser(event, method) {
    
    var email = getValue("email");
    var password = getValue("password");
    var name = getValue("name");
    var team = getValue("team");
    var role = document.querySelector('input[name="role"]:checked');
    var manager = getValue("manager");

    if (!validateEmail(email)) {
        setError("email", "Invalid email format");
        return false;
    } else {
        removeError("email");
    }

    if (!validatePassword(password)) {
        setError("password", "Password must be at least 8 characters long");
        return false;
    } else {
        removeError("password");
    }

    if (!name) {
        setError("name", "Name is required");
        return false;
    } else {
        removeError("name");
    }

    if (!team) {
        setError("team", "Team is required");
        return false;
    } else {
        removeError("team");
    }

    if (!role) {
        setError("role", "Role is required");
        return false;
    } else {
        removeError("role");
    }

    if (role.value == "user" && !manager) {
        setError("manager", "Manager is required for selected role");
        return false;
    } else {
        removeError("manager");
    }

    
    var userData = {
            email: email,
            password:password,
            name: name,
            team:team,
            role:role.value,
            manager:manager == "" ?"0":manager
            
         
        };
        console.log(userData);
        Ajax("POST","AddUser",userData,function (error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }
        
        if(data == "data already exist"){
			event.preventDefault();
			setError("email", data);
			return;
		}else{
			removeError("email");
		}
		if(data==success){
			
		}else{
			event.preventDefault();
		}
        console.log(data);
       
    });
    
}
