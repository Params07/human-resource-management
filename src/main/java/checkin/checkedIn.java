package checkin;

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
import java.sql.Types;

import DBconnection.connectionProvider;


public class checkedIn extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        HttpSession session = request.getSession();
	        int id = Integer.parseInt((String) session.getAttribute("id"));
	        response.getWriter().write(Ischecked(connection,id));
	        
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
   private String Ischecked(Connection connection,int id) throws SQLException {
	   try(PreparedStatement p = connection.prepareStatement("SELECT checked_in,checked_out from users_checkin where user_id_ref = ? and checkin_date = CURRENT_DATE  ")){
           p.setInt(1, id);
           ResultSet checked_in = p.executeQuery();
           if(checked_in.next()) {
        	   return "["+checked_in.getString(1)+","+checked_in.getString(2)+"]";
           }else {
        	   
        	   return "no data";
           }
           
        }
	
	   
   }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        HttpSession session = request.getSession();
	        int id = Integer.parseInt((String) session.getAttribute("id"));
	        if(Ischecked(connection,id).equals("no data")) {
	        	try(PreparedStatement p = connection.prepareStatement("insert into users_checkin (checked_in ,checkin_date,user_id_ref) values (CURRENT_TIMESTAMP,current_date,?) "
		        		+ "  ")){
		        	
		           p.setInt(1, id);
		         if(p.executeUpdate()>0) {
		        	 response.getWriter().write("updated");
		         }else {
		        	 response.getWriter().write("no updation");
		         }
	        }
	        
	        }else {
	        	try(PreparedStatement p = connection.prepareStatement("Update  users_checkin set checked_out = ? where user_id_ref = ? "
	        			+ " and checkin_date = current_date  ")){
	        		p.setNull(1, Types.DATE); 
		           p.setInt(2, id);
		         if(p.executeUpdate()>0) {
		        	 response.getWriter().write("set as null");
		         }else {
		        	 response.getWriter().write("no updation");
		         }
	        	
	        }
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

}
