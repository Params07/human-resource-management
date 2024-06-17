package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DBconnection.connectionProvider;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public class profile {
    private HashMap<String, Object> userProfile = new HashMap<>();
    private HashMap<String, Object> ManagerProfile = new HashMap<>();

    private static profile prof = new profile();

    public static profile getInstance() {
    	
        return prof;
    }

    public HashMap<String, Object> getProfile() {
    	 System.out.println(userProfile.get("team_name"));
        return userProfile;
    }

    public void setProfile(ServletContext context, int id) {
        Connection connection = null;
        try {
            connection = connectionProvider.getInstance().getConnection(context);
            String sql = "select u.name,u.email,u.role,u.team_id,u.manager_id,team.team_name, m.name as manager_name from users as u join team on team.id = u.team_id"
                    + " left join users as m on u.manager_id = m.user_id where u.user_id = ? ";
            
            try (PreparedStatement p1 = connection.prepareStatement(sql)) {
                p1.setInt(1, id);
                ResultSet data = p1.executeQuery();
                if (data.next()) {
                	userProfile.put("name", data.getString("name"));
                	userProfile.put("email", data.getString("email"));
                	userProfile.put("role", data.getString("role"));
                	userProfile.put("team_id", data.getString("team_id"));
                	userProfile.put("manager_id", data.getString("manager_id"));
                	userProfile.put("team_name", data.getString("team_name"));
                	userProfile.put("manager_name", data.getString("manager_name"));
                    
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
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
