	<%@ page import="bean.profile" %>
	<%@ page import="bean.managerProfile" %>
	<%@ page import="java.util.HashMap" %>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	
	<%
	    HashMap<String, Object> profileData = profile.getInstance().getProfile();
	    HashMap<String, Object> managerProfileData = managerProfile.getInstance().getProfile();
	%>

	<div id="userProfile" style="margin:200px; background-color:white;padding:10px;border-radius:6px;width:500px;">
	
	 <h1>Your Profile</h1>
	    
	   
         <% if (profileData != null && !profileData.isEmpty()) { %>
        <p>Name: <%= profileData.get("name") %></p>
        <p>Email: <%= profileData.get("email") %></p>
        <p>Role: <%= profileData.get("role") %></p>
        <p>Team ID: <%= profileData.get("team_id") %></p>
        <p>Team Name: <%= profileData.get("team_name") %></p>
        <% if (profileData.get("manager_id") != null) { %>
            <p>Manager ID: <%= profileData.get("manager_id") %></p>
            <p id = "managerName" onclick="showMangerProfile('<%= profileData.get("manager_id") %>')">Manager Name: <%= profileData.get("manager_name") %></p>
        <% } %>
    <% } %>
	
	</div>
	<div id= "managerProfile" style="margin:200px; background-color:white;padding:10px;border-radius:6px;width:500px; display:none;">
	 <div style="text-align:right;"><input type="button" value="back to your profile" onclick="Profile()">
	 </div>
	 <h1>Managers Profile</h1>
	    
	   
         <% if (managerProfileData  != null && !managerProfileData .isEmpty()) { %>
        <p>Name: <%= managerProfileData .get("name") %></p>
        <p>Email: <%= managerProfileData .get("email") %></p>
        <p>Role: <%= managerProfileData .get("role") %></p>
        <p>Team ID: <%= managerProfileData .get("team_id") %></p>
        <p>Team Name: <%= managerProfileData .get("team_name") %></p>
        <% if (managerProfileData.get("manager_id") != null) { %>
            <p>Manager ID: <%= managerProfileData .get("manager_id") %></p>
            <p id = "managerName" onclick="showMangerProfile('<%= managerProfileData .get("manager_id") %>')">Manager Name: <%= managerProfileData .get("manager_name") %></p>
        <% } %>
    <% } %>
	
	</div>