<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.tabs" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String user = (String) session.getAttribute("usertype");
List<ArrayList<String>> tab_name = (List<ArrayList<String>>) tabs.getInstance().getTabs();
  
%>

<html>
<head>
    <link rel="stylesheet" href="form.css">
    <% for (ArrayList<String> scriptFile : tab_name) { %>
        <script src="<%= scriptFile.get(0) %>.js"></script>
    <% } %>
    <script src="Dashboard.js" defer></script>
</head>
<body>
    <div class="container">
        <div class="nav-bar">
            <div class="nav-items">
                <% for (ArrayList<String> navItem : tab_name) { %>
                    <div class="nav-item">
                        <a href="#" id="<%= navItem.get(0) + "-tab" %>"><%= navItem.get(0) %></a>
                    </div>
                <% } %>
            </div>
        </div>
         <div id="content" style="margin-left:150px;width:100%;">
         </div>
    </div>
</body>

<script>
 function  defaulltTab() {
        var params = new URLSearchParams(window.location.search);
        var tabParam = params.get('tab');
        
        if (tabParam) {
            var tabId = tabParam + "-tab";
            console.log(tabId);
          
            var tabElement = document.getElementById(tabId);
            console.log(tabElement)
            if (tabElement) {
                tabElement.click(); 
            }
        }else{
        	loadContent('<%= tab_name.get(0).get(0) %>.jsp');
            history.pushState(null, null, 'Dashboard.jsp?tab=' + '<%= tab_name.get(0).get(0) %>');
            
        }
    }

 function SwitchActive(tabName) {
	    var navItems = document.getElementsByClassName("nav-item");
	    for (var i = 0; i < navItems.length; i++) {
	        navItems[i].classList.remove("active");
	        if (navItems[i].querySelector("a").innerText === tabName) {
	            navItems[i].classList.add("active");
	        }
	    }
	}

 document.addEventListener("DOMContentLoaded", function() {
	 defaulltTab() 
	});
   
    <% for (ArrayList<String> navItem : tab_name) { %>
    document.getElementById('<%= navItem.get(0) %>-tab').addEventListener('click', function(event) {
        event.preventDefault();
        SwitchActive('<%= navItem.get(0) %>');
        loadContent('<%= navItem.get(0) %>.jsp');
        history.pushState(null, null, 'Dashboard.jsp?tab=' + '<%= navItem.get(0) %>');
    });
    <% } %>

   

  
    function loadContent(data) {
    	var functionName = data.replace(".jsp", "");
        
        if (typeof window[functionName] === "function") {
        	 clearInterval(intervalId);
            window[functionName]();
            
        } else {
            console.error("Function " + functionName + " not found");
        }
    }


    window.onpopstate = function(event) {
        var params = new URLSearchParams(window.location.search);
        var tabParam = params.get('tab');
        if (tabParam) {
            loadContent(tabParam + '.jsp');
        }
    };
</script>
</html>
