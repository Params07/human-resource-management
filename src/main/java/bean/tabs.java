package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.connectionProvider;
import db.getData;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public class tabs {
	private static tabs tab = new tabs();
   
    private  List<ArrayList<String>> tabNames;
    static public tabs getInstance() {
    	return tab;
    }
    public void setRole(String role,ServletContext context) {
    	
        String sql = "select tab_name,id  from tabs where id in (select tab_id from roles where role = ? ) order by id ";
        List data = new ArrayList();
        
        data.add(role.toUpperCase());
        
        tabNames = getData.get(context, sql, data);
    	
    }
     
     public List<ArrayList<String>> getTabs(){
    	
		return tabNames;
    	
    }
}
