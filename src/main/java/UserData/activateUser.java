package UserData;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DBconnection.connectionProvider;


public class activateUser extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
			ServletContext context  = getServletContext();
	        conn = connectionProvider.getInstance().getConnection(context);
        	
            	 String sql = "update users set activated_date = current_date where user_id = ? and activated_date is null";
            	 try(PreparedStatement p1 = conn.prepareStatement(sql)){
            		 
            		 p1.setInt(1, Integer.parseInt(request.getParameter("id")));
            		 if(p1.executeUpdate() == 1) {
            			 response.getWriter().write("success");
            		 }else {
            			 response.getWriter().write("serror");
            		 }
            	 }
            	
		  }catch (SQLException | ClassNotFoundException  e) {
	       	 e.printStackTrace();
	       
			} finally {
	           if (conn != null) {
	               try {
	                   conn.close();
	               } catch (SQLException e) {
	                   response.getWriter().write("err" + e);
	               }
	           }}
	}

}
