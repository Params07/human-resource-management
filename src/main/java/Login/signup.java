package Login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bouncycastle.crypto.generators.SCrypt;

import DBconnection.connectionProvider;
import bean.hashing;

public class signup extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		
        
        
        try {
        	ServletContext context  = getServletContext();
        	connection = connectionProvider.getInstance().getConnection(context);
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            boolean flag = exist(connection, email);
            if (flag) {
            	request.setAttribute("signMessage", "already exist");
    			RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
       	        dispatcher.forward(request, response);
            } else {
            	
            	byte[] salt = hashing.generateSalt();
                
                byte[] hashedPassword = hashing.getHashedPassword(password, salt);
               if(insertData(connection,email,name,hashedPassword,salt) == 1) {
            	  
            	   response.sendRedirect(request.getContextPath() + "/login.jsp");
               }else {
            	   
               }
            
            }

    }catch (SQLException | ClassNotFoundException e) {
        
    	 e.printStackTrace();
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
        private boolean exist(Connection connection,String email) throws SQLException {
           
     	        
     	     String sql = "SELECT COUNT(*) FROM  admin WHERE email = ?";
     	    
             try (PreparedStatement p1 = connection.prepareStatement(sql)) {
                 p1.setString(1, email);
                 
                 ResultSet resultSet = p1.executeQuery();

                 if (resultSet.next()) {
                     int count = resultSet.getInt(1);
                     return count > 0;
                 }
                 return false;
             }
         }




private int insertData(Connection connection, String email,String name, byte[] hashedPassword, byte[] salt) throws SQLException {
    String sql = "INSERT INTO admin (email,password, key) VALUES (?, ?, ?)";
    
    try (PreparedStatement p1 = connection.prepareStatement(sql)) {
        p1.setString(1, email);
      
        p1.setBytes(2, hashedPassword);
        p1.setBytes(3, salt);
       

        return p1.executeUpdate();
    }
}



}
