package checkin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONValue;

import DBconnection.connectionProvider;


public class getAttendance extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Connection connection = null;
        try {
            ServletContext context = getServletContext();
            connection = connectionProvider.getInstance().getConnection(context);
            String from = request.getParameter("from");
            int team = Integer.parseInt(request.getParameter("team"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedFrom = sdf.parse(from);
            java.sql.Date sqlFrom = new java.sql.Date(parsedFrom.getTime());
            String subString = " WHERE checkin_date = ? ";
            
           
 
            if(team != 0) {
                subString = " left JOIN users ON users.team_id = ?";
            }

            String sql = "SELECT count(user_id_ref) AS present, checkin_date, "
                + "((SELECT count(*) FROM users WHERE activated_date IS NOT NULL "
                + "AND activated_date <= users_checkin.checkin_date) - count(user_id_ref)) AS absents "
                + "FROM users_checkin "
                + (team != 0 ? subString : "") 
                + " WHERE checkin_date = ? "
                + "GROUP BY checkin_date ";
            System.out.println(sql);
            try(PreparedStatement p = connection.prepareStatement(sql)) {
            	int paramIndex = 1;
                if(team != 0) {
                    p.setInt(paramIndex++, team);
                }
                
                p.setDate(paramIndex++, sqlFrom);
                ResultSet checkinDatas = p.executeQuery();
                ArrayList<HashMap> list = new ArrayList<HashMap>();
                while(checkinDatas.next()) {
                    HashMap items = new HashMap();
                    items.put("present", checkinDatas.getString(1));
                    items.put("checkin_date", checkinDatas.getString(2));
                    items.put("absent", checkinDatas.getString(3));

                    list.add(items);
                }
                response.getWriter().write(JSONValue.toJSONString(list));
            
            }
        } catch (SQLException | ClassNotFoundException | ParseException e) {
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
