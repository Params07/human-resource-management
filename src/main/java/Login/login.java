package Login;

import jakarta.servlet.RequestDispatcher;
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
import java.util.Arrays;

import org.bouncycastle.crypto.generators.SCrypt;

import DBconnection.connectionProvider;
import bean.hashing;
import bean.profile;
import bean.tabs;


public class login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
	        ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        ResultSet result = exist(connection, email);
	        if (result.next()) {
	            byte[] hashedPasswordStored = result.getBytes("password");
	            byte[] storedSalt = result.getBytes("key");
	            if (hashing.verifyPassword(password, hashedPasswordStored, storedSalt)) {
	                HttpSession session = request.getSession();
	                session.setMaxInactiveInterval(1800);
	                session.setAttribute("id", result.getString("user_id"));
	                session.setAttribute("usertype", result.getString("usertype"));
	                tabs.getInstance().setRole(result.getString("usertype"), context);
	                if(!result.getString("usertype").equals("ADMIN")) {
	            		profile.getInstance().setProfile(context, Integer.parseInt(result.getString("user_id")));
	            		System.out.println("sljg");
	            	}
	                response.sendRedirect(request.getContextPath() + "/Dashboard.jsp");
	                }else {
	                	request.setAttribute("loginMessage", "password invalid");
			            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			            dispatcher.forward(request, response);
			            
	                }
	            
	            }else {
	            	request.setAttribute("loginMessage", "email invalid");
		            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		            dispatcher.forward(request, response);
	            }
	        
	        }catch (SQLException | ClassNotFoundException e) {
		        e.printStackTrace();
		        System.out.println("err" + e);

		    } finally {
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException e) {
		                response.getWriter().write("err" + e);
		            }
		        }
		    }

		
	}
 
	private ResultSet exist(Connection connection, String email) throws SQLException {
		 String sql = "SELECT 'ADMIN' as usertype, password, key, id as user_id FROM admin WHERE email = ? "
		 		+ "union select role as usertype ,password ,key ,user_id from users where email = ? and activated_date is not null";
		 PreparedStatement p1 = connection.prepareStatement(sql);
	     p1.setString(1, email);
	     p1.setString(2, email);
	     
	     return p1.executeQuery();
	}
	

}
