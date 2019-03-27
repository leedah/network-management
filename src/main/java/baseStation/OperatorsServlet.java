package main.java.baseStation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sofia on 10/3/15.
 */
@WebServlet(name = "OperatorsServlet", urlPatterns = "/operators")
public class OperatorsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // We set a specific return type and encoding
        // in order to take advantage of the browser capabilities.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Integer> operators2users = OperatorsJSON.getOperators();
        String JSONtext = OperatorsJSON.getOperatorsJSON(operators2users);

        response.getWriter().write(JSONtext);
        response.flushBuffer();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }
}
