package DBconnection;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class connectionProvider extends HttpServlet {
	private static connectionProvider ConnectionObj = new connectionProvider() ;
    public Connection getConnection(ServletContext context) throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        
        try (InputStream db = context.getResourceAsStream("/WEB-INF/dp.properties")) {
            properties.load(db);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, username, password);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static connectionProvider getInstance() {
    	return ConnectionObj;
    }
    
}
