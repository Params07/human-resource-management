package UserData;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


import DBconnection.connectionProvider;
import bean.hashing;


public class AddUser extends HttpServlet {
	private JSONObject jsonData(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonData = sb.toString();

		return (JSONObject) JSONValue.parse(jsonData);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
			ServletContext context  = getServletContext();
	        conn = connectionProvider.getInstance().getConnection(context);
        	JSONObject jsonObject = jsonData(request);

             String email = (String) jsonObject.get("email");
             String name = (String) jsonObject.get("name");
             String password = (String) jsonObject.get("password");
             String role = (String) jsonObject.get("role");
             int manager  = Integer.parseInt((String) jsonObject.get("manager"));
             
             int team_id = Integer.parseInt((String) jsonObject.get("team"));
             byte[] salt = hashing.generateSalt();
             if(!Exist(conn,email)) {
            	 conn.setAutoCommit(false);
            	 String sql = "INSERT into users (name,email,password,key,role,manager_id,team_id)  values(?,?,?,?,?,?,?)";
            	 try(PreparedStatement p1 = conn.prepareStatement(sql)){
            		 p1.setString(1, name);
            		 p1.setString(2,email);
            		 p1.setBytes(3,hashing.getHashedPassword(password, salt));
            		 p1.setBytes(4,salt);
            		 p1.setString(5,role);
            		 p1.setObject(6, manager == 0 ? null : manager);
            		 p1.setInt(7,team_id);
            		 
            		 p1.executeUpdate();
            		 if(manager!=0) {
            			 try(PreparedStatement p2 = conn.prepareStatement("update users set role = ? where user_id = ? ")){
                    		 p2.setString(1,"manager" );
                    		 p2.setInt(2,manager);
                    		 if(p2.executeUpdate() == 1) {
                    			 response.getWriter().write("success");
                    		 }else {
                    			 response.getWriter().write("serror");
                    		 }
            }
            		 }
            		 conn.commit();
            	 }
             }else {
            	 response.getWriter().write("data already exist");
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
	private boolean Exist(Connection connection, String email) throws SQLException {
		String sql = "SELECT email FROM users WHERE email = ? UNION SELECT email FROM admin WHERE email = ? ";
		
         
        try (PreparedStatement p1 = connection.prepareStatement(sql)) {
            
            
            	p1.setString(1, email);
            	p1.setString(2, email);
           

            ResultSet resultSet = p1.executeQuery();

            if (resultSet.next()) {
               
                return true;
            }
            return false;
        }
    }

}
