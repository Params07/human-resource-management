package UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import bean.managerProfile;
import bean.profile;


public class profileSet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		managerProfile.getInstance().setProfile(request.getServletContext(), Integer.parseInt(request.getParameter("user_id")));

	}

}
