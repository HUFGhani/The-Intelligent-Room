import com.google.gson.Gson;
import model.LogInRegisterResponse;
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
@WebServlet("/test1")
public class UserServlet extends HttpServlet {


        projectDAO dao = new projectDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        //LogInRequest logInRequest = new Gson().fromJson(reader, LogInRequest.class);

//        LogInRegisterResponse logInResponse = new projectDAO().logIn(logInRequest.getEmail(), logInRequest.getPassword());
          LogInRegisterResponse logInResponse =  dao.logIn("nick12345@hotmail.co.uk","User123");

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

