package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.nickr.IoT.user.model.LogInRegisterResponse;
import com.nickr.IoT.user.model.LogInRequest;
import com.nickr.IoT.user.model.RegistrationRequest;
import com.nickr.IoT.userDAO.projectDAO;

@WebServlet("/test1")
public class UserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        LogInRequest logInRequest = new Gson().fromJson(reader, LogInRequest.class);

        LogInRegisterResponse logInResponse = new projectDAO().logIn(logInRequest.getEmail(), logInRequest.getPassword());

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(logInResponse));
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
        RegistrationRequest registrationRequest = new Gson().fromJson(reader, RegistrationRequest.class);

        LogInRegisterResponse registrationResponse = new projectDAO().register(registrationRequest);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(registrationResponse));
	}
	
}
