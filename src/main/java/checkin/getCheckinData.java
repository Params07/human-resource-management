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
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONValue;

import DBconnection.connectionProvider;

public class getCheckinData extends HttpServlet {
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			ServletContext context = getServletContext();
	        connection = connectionProvider.getInstance().getConnection(context);
	      
	        int id = Integer.parseInt(request.getParameter("id"));
	        
	        	try(PreparedStatement p = connection.prepareStatement("SELECT id,user_id_ref, checkin_date, checked_out,checked_in,\r\n"
	        			+ "    CASE\r\n"
	        			+ "        WHEN checked_out IS NULL THEN AGE(NOW(), checked_in)\r\n"
	        			+ "        ELSE AGE(checked_out, checked_in)\r\n"
	        			+ "    END AS checkin_checkout_diff,\r\n"
	        			+ "TO_CHAR(checkin_date, 'Day') AS day_of_week  "
	        			+ "FROM\r\n"
	        			+ "    users_checkin\r\n"
	        			+ "WHERE\r\n"
	        			+ "    user_id_ref = ? ORDER BY checkin_date DESC")){
	        		
		           p.setInt(1, id);
		           ResultSet checkinDatas = p.executeQuery();
		           ArrayList<HashMap> list = new ArrayList<HashMap>();
		           while(checkinDatas.next()) {
		        	   HashMap items = new HashMap();
		        	   items.put("user_id_ref", checkinDatas.getString(2));
		        	   items.put("checkin_date", checkinDatas.getString(3));
		        	   items.put("checked_out", checkinDatas.getString(4));
		        	   items.put("checked_in", checkinDatas.getString(5));
		        	   items.put("diff", checkinDatas.getString(6));
		        	   items.put("day_of_week", checkinDatas.getString(7));
		        	   list.add(items);
		           }
		           response.getWriter().write(JSONValue.toJSONString(list));
		           
		           
	        
	        
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

	
	


