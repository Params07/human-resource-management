package schedular;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.el.lang.FunctionMapperImpl.Function;

import DBconnection.connectionProvider;
import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.tagext.FunctionInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Timestamp;

public class taskPerfomer {
private ServletContext servletContext;  
    
    public taskPerfomer(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    public Runnable tasks(String s)  {
    	System.out.println(s);
    	
		try {
            Method method = getClass().getDeclaredMethod(s.trim());
			if (method != null) {
		           
	            method.invoke(this); 
	        } 
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
		
			e.printStackTrace();
		}
        
    	
		return null;
    }
   
    private void  checkedout() {
    	Connection connection = null;
    	try {
            connection = connectionProvider.getInstance().getConnection(this.servletContext);
            LocalDate yesterdayDate = LocalDate.now().minusDays(1);
            LocalDateTime dateTime = yesterdayDate.atTime(23, 59, 0);
            long millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Timestamp timestamp = new Timestamp(millis);
            System.out.println(yesterdayDate);
            try (PreparedStatement p1 = connection.prepareStatement("UPDATE users_checkin SET checked_out = ? WHERE checked_out is null  AND checkin_date = current_date - INTERVAL '1 day'")) {
                p1.setTimestamp(1, timestamp);
             
                System.out.println(p1.executeUpdate());
            }
        } catch (Exception e) {
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
