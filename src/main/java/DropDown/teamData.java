package DropDown;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONValue;

import DBconnection.connectionProvider;
import db.getData;


public class teamData extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String action = request.getParameter("for");
         if(action.equals("team")) {
        	 String sql = "select * from team";
        	 ServletContext context = getServletContext();
        	 response.setContentType("application/json");
        	 response.getWriter().write(JSONValue.toJSONString(getData.get(context,sql, null)));
         }else {
        	 String sql = "select user_id,name from users where team_id = ? ";
        	 ServletContext context = getServletContext();
        	 response.setContentType("application/json");
        	 List data = new  ArrayList();
        	 
        	 data.add( Integer.parseInt(request.getParameter("team")));
        	 
        	 response.getWriter().write(JSONValue.toJSONString(getData.get(context,sql, data)));
         }
    }


}
