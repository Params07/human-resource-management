package ApplyLeave;

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


public class ApprovingLeave extends HttpServlet {
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        HttpSession session = request.getSession();
	        int id = Integer.parseInt((String) session.getAttribute("id"));
	        String role = (String) session.getAttribute("usertype");
	       
	        String sql = "SELECT u.name, u.user_id, l.from_date, l.to_date, l.reason, l.status FROM users AS u "
	                + "JOIN leaveform AS l ON u.user_id = l.ref_user_id WHERE l.status = ?";
	    if (role.equals("manager")) {
	        sql += " AND u.manager_id = ?";
	    }
	        	try(PreparedStatement p = connection.prepareStatement(sql)){
	        		
		           System.out.println(role+sql);
		           if(role.equals("manager")) {
		        	   p.setString(1,"applied to manager");
		        	   p.setInt(2, id);
		           }else {
		        	   p.setString(1,"applied to hr");
		           }
		           ResultSet leaveData = p.executeQuery();
		         
		          ArrayList data = new ArrayList();
		          while(leaveData.next()) {
		        	  HashMap map = new HashMap();
		        	  map.put("name", leaveData.getString(1));
		        	  map.put("user_id", leaveData.getString(2));
		        	  map.put("from_date", leaveData.getString(3));
		        	  map.put("to_date", leaveData.getString(4));
		        	  map.put("reason", leaveData.getString(5));
		        	  data.add(map);
		        	  }
		         
		          response.getWriter().write(JSONValue.toJSONString(data));
		          
		           
		           
		        
	        
	        
	        }
		}catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Connection connection = null;
	    try {
	        ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        HttpSession session = request.getSession();
	        int id = Integer.parseInt(request.getParameter("id")) ;
	        String role = (String) session.getAttribute("usertype");
	        String status = request.getParameter("status");
	        if (role.equals("manager") && status.equals("approved")) {
	            status = "applied to hr";
	        }

	        try (PreparedStatement p = connection.prepareStatement("update leaveform set status = ? where ref_user_id = ? and status <> 'approved' and status <> 'declined'")) {
	            p.setString(1, status);
	            p.setInt(2, id);

	            if (p.executeUpdate() > 0) {
	                response.getWriter().write("updated");
	            } else {
	                response.getWriter().write("no updated");
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        response.getWriter().write("update failed: " + e.getMessage());
	    } finally {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

}
