package UserData;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONValue;

import DBconnection.connectionProvider;


public class getUserData extends HttpServlet {
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null ;
		try {
			ServletContext context  = getServletContext();
        	connection = connectionProvider.getInstance().getConnection(context);
			String sql = "select u.activated_date,u.user_id,u.name,u.email,u.role,u.team_id,u.manager_id,team.team_name from users as u join team on team.id = u.team_id ";
			  HttpSession session = request.getSession();
			String usertype = (String) session.getAttribute("usertype");
			int id = Integer.parseInt((String) session.getAttribute("id"));
			if(!usertype.equals("ADMIN")) {
				sql = sql+" where manager_id = ?";
			}
				
			try(PreparedStatement p1 = connection.prepareStatement(sql)){
				if(!usertype.equals("ADMIN")) {
					p1.setInt(1,id);
				}
				ResultSet data = p1.executeQuery();
				ArrayList<HashMap> map = new ArrayList<HashMap>();
				while(data.next()) {
					HashMap items = new HashMap();
					items.put("user_id",data.getString("user_id"));
					items.put("name",data.getString("name"));
					items.put("email",data.getString("email"));
					items.put("role",data.getString("role"));
					items.put("team_id",data.getString("team_id"));
					items.put("manager_id",data.getString("manager_id"));
					items.put("team_name",data.getString("team_name"));
					items.put("activated_date", data.getString("activated_date"));
					map.add(items);
				}
				response.getWriter().write(JSONValue.toJSONString(map));
				
					
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}

	


}
