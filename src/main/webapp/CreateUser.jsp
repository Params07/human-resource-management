<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String user = (String) session.getAttribute("usertype"); %>
<body >
<div class="user-data">
    <form id="form" onsubmit="AddUser(event,'POST')">
        <div class="form">
            <p style="text-align: center; font-size: x-large; font-weight: bold;">Enter the details</p>
            <div class="input">
                <label for="name">Name</label>
                <input type="text" id="name" placeholder="Enter the name">
                <div id="nameError" style="color: red; font-size: 10px;"></div>
            </div>
            <div class="input">
                <label for="email">Email Id</label>
                <input type="email" id="email" placeholder="Enter the email">
                <div id="emailError" style="color: red; font-size: 10px;"></div>
            </div>
            <% if(user.equals("ADMIN")) { %>
            <div class="input " id="pass">
                <label for="password">Password</label>
                <input id="password" name="password" placeholder="Enter your password">
                <div id="passwordError" style="color: red; font-size: 10px;"></div>
            </div>
            <%} %>
            <div>
                <label for="team">team</label>
                <select id="team"></select>
                <div id="teamError" style="color: red; font-size: 10px;"></div>
            </div>
            <div>
              <input id="user" name="role" type="radio" value="user"><label> user</label><br>
              <input id="managers" name="role" type="radio" value="manager"><label> manager</label>
              <div id="roleError" style="color: red; font-size: 10px;"></div>
              
            </div>
            <div>
                <label for="manager">Manager</label>
                <select id="manager"></select>
               <div id="managerError" style="color: red; font-size: 10px;"></div>
                
            </div>
            <div class="input">
                <input type="submit" value="Submit">
            </div>
        </div>
    </form>
</div>
</body>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        dropDownTeam();
    });
</script>
