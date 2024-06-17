package ApplyLeave;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import DBconnection.connectionProvider;


public class ApplyLeave extends HttpServlet {
	
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	        HttpSession session = request.getSession();
	        int id = Integer.parseInt((String) session.getAttribute("id"));
	       
	        
	        	try(PreparedStatement p = connection.prepareStatement("select id,from_date,to_date,status,reason from leaveform where ref_user_id = ? order by id desc")){
	        		
		           p.setInt(1, id);
		           ResultSet leaveData = p.executeQuery();
		         
		          ArrayList data = new ArrayList();
		          while(leaveData.next()) {
		        	  HashMap map = new HashMap();
		        	  map.put("id", leaveData.getString(1));
		        	  map.put("from_date", leaveData.getString(2));
		        	  map.put("to_date", leaveData.getString(3));
		        	  map.put("status", leaveData.getString(4));
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
	        JSONObject jsonObject = jsonData(request);
	        HttpSession session = request.getSession();
	        String from = (String)jsonObject.get("from");
	        String to = (String) jsonObject.get("to");
	        String reason = (String) jsonObject.get("reason");
	        String role = (String) session.getAttribute("usertype");
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedFrom = sdf.parse(from);
            java.sql.Date sqlFrom = new java.sql.Date(parsedFrom.getTime());
            java.util.Date  parsedTo = sdf.parse(to);
            java.sql.Date sqlTo = new java.sql.Date(parsedTo.getTime());
	        int id = Integer.parseInt((String) session.getAttribute("id"));
	       
	        
	        	try(PreparedStatement p = connection.prepareStatement("insert into leaveform (ref_user_id,from_date,to_date,reason,status) "
	        			+ "values (?,?,?,?,?)")){
	        		
		           p.setInt(1, id);
		           p.setDate(2, sqlFrom);
		           p.setDate(3, sqlTo);
		           p.setString(4, reason);
		           if(role.equals("user")) {
		        	   p.setString(5,"applied to manager");
		           }else {
		        	   p.setString(5,"applied to hr");
		           }
		           
		         if(p.executeUpdate()>0) {
		        	 response.getWriter().write("updated");
		         }else {
		        	 response.getWriter().write("no updation");
		         }
	        
	        
	        }
		}catch (SQLException | ClassNotFoundException | ParseException e) {
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
