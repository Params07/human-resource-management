<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login/signup</title>
    <style>
 body{
    margin: 0;
    background-color:rgb(248, 249, 250);
 }
 .signup{
    display: grid;
    justify-content: center;
    align-content: center;
    height: 80vh;
    width: 100%; 
    
 }
 input{
    width: 300px;
    height: 40px;
    
 }
 input {outline: none; border:none; border-bottom: 1px black;}
input:focus{box-shadow: 0 1px 0 0  black;
    transition: 0.3s;
}
 
 div{
    padding: 10px;
 }
 button{
    width: 200px;
    border-radius: 8px;
    height: 40px;
  background-color: black;
  color: white;
 }
 button:hover{
    background-color: white;
    color: black;
 }

    </style>
</head>
<body>
      <div class="signup">
        <div style="width: 400px;box-shadow: 5px 5px 10px #888888;display:grid;justify-content: center;min-height:300px;height:fit-content;
        border-radius: 6px;">
            <form method="post" action="signup" onsubmit="return validate(event)">
                <h2 style="text-align: center;">Sign up page </h2>
                <div>
                
                    <input class="log-input" id="email" type="email" placeholder="enter your email address" name="email" required >
                </div>
               
                   <div> 
                    <input  class="log-input" id="password" name="password"
                     placeholder="enteryour password" required>
                </div>
                 <div id="passwordError" style="color:red;font-size:12px;">
                   
                </div>
                
                <div id="error" style="color:red;">
                  ${requestScope.signMessage}
                </div>
                <div style="text-align: center;"><button type="submit">Submit</button></div>
                
                
            </form>
        </div>
      </div>
</body>
<script>
function validate(event){
	
	
	var password = document.getElementById("password").value;
	
    
	var passwordRegx = new RegExp("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$");

    
    
    if(!passwordRegx.test(password)){
    	document.getElementById("passwordError").innerText = "password must contain alphabets ,number and length must be greater than 8";
    }else{
    	document.getElementById("passwordError").innerText = "";
    }
    
    return emailRegex.test(email) && passwordRegx.test(password);
}
</script>
</html>