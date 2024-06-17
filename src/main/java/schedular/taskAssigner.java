package schedular;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.postgresql.PGNotification;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.sql.*;
import org.postgresql.*;




public class taskAssigner implements ServletContextListener{

	static private Connection connect;
	   
	private taskPerfomer tp ;
	private ScheduledExecutorService scheduler ;
	
   
    

	 public void contextInitialized(ServletContextEvent sce) {
	       
		 ServletContext context = sce.getServletContext();
		
         tp = new taskPerfomer(context);
		 scheduler = Executors.newScheduledThreadPool(10);
		
		 Scheldule(context);
			 
			
	    }

	 private void Scheldule(ServletContext context) {
		  
            	

		 scheduler.scheduleWithFixedDelay(() -> tp.tasks("checkedout"), intialDelayForDay("01:30:00"), 1 * 24 * 60 * 60, TimeUnit.SECONDS);

	            	
	            	
	         
		}
	private long intialDelayForDay(String time) {
		LocalDateTime now = LocalDateTime.now();

        LocalTime time1 = LocalTime.parse(time);

    
     
        LocalDateTime to = now.withHour(time1.getHour()).withMinute(time1.getMinute());

        if (now.compareTo(to) > 0) {
            to = to.plus(1, ChronoUnit.DAYS);
        }
        
        
        long delay = now.until(to, ChronoUnit.SECONDS);
	    	 
        System.out.println("delay for day:"+delay +"secs");
	        return delay;
	    	
	    }
	  
	    
	    public void contextDestroyed(ServletContextEvent sce) {
	    	scheduler.shutdown(); 
	    }
	   
	    	    
	    
	    
}
