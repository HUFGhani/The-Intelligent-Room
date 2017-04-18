import com.google.gson.Gson;
import model.LogInRegisterResponse;
import model.LogInRequest;
import model.RegistrationRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hamzaghani on 17/04/2017.
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LogInRegisterResponse logInResponse = new LogInRegisterResponse();

        if (email == null || password == null) {
            logInResponse.setError("Bad Request");
        } else {
            LogInRequest logInRequest = new LogInRequest(email, password);
            logInResponse = new projectDAO().logIn(logInRequest.getEmail(), logInRequest.getPassword());
            if (logInResponse.getHouseConfiguration() == null || logInResponse.getUserPreference() == null) {
                logInResponse.setError("Invalid email/password combination");
            }
        }

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

