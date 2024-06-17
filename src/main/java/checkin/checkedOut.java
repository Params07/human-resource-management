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
import java.sql.SQLException;
import java.sql.Types;

import DBconnection.connectionProvider;


public class checkedOut extends HttpServlet {

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        HttpSession session = request.getSession();
	        int id = Integer.parseInt((String) session.getAttribute("id"));
	        
	        	try(PreparedStatement p = connection.prepareStatement("Update  users_checkin set checked_out = CURRENT_TIMESTAMP where user_id_ref = ? "
	        			+ " and checkin_date = current_date  ")){
	        		
		           p.setInt(1, id);
		         if(p.executeUpdate()>0) {
		        	 response.getWriter().write("updated");
		         }else {
		        	 response.getWriter().write("no updation");
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
