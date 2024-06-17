package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.connectionProvider;
import jakarta.servlet.ServletContext;

public class getData {
	public static List<ArrayList<String>> get(ServletContext context ,String sql,List params) {
    	Connection connection = null;
    	try {
	       
	        connection = connectionProvider.getInstance().getConnection(context);
	        try(PreparedStatement p1 = connection.prepareStatement(sql)){
	        	 if (params != null) {
	                 for (int i = 0; i < params.size(); i++) {
	                     Object param = params.get(i);
	                     System.out.println(params.get(i));
	                     if (param instanceof String) {
	                         p1.setString(i + 1, (String) param);
	                     } else if (param instanceof Integer) {
	                         p1.setInt(i + 1, (Integer) param);
	                     }
	                 }
	             }
	        	
	        	ResultSet tab_names = p1.executeQuery();
	        	List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	        	while(tab_names.next()) {
	        		ArrayList<String> x = new ArrayList<String>();
	        		x.add(tab_names.getString(1));
	        		x.add(tab_names.getString(2));
	        		data.add(x);
	        	}
	        	System.out.println(data+"");
	        	return data;
	        	
	        }
	        
	        
	        }catch (SQLException | ClassNotFoundException e) {
		        e.printStackTrace();
		        System.out.println("err" + e);

		    } finally {
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException e) {
		                
		            }
		        }
		    }
		return null;
    	
    }
}
