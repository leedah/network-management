package main.java.baseStation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sofia on 10/2/15.
 */
@WebServlet(name = "BaseStationsServlet", urlPatterns = {"/baseStations"})
public class BaseStationsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // We set a specific return type and encoding
        // in order to take advantage of the browser capabilities.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        // Depending on the GET parameters, passed from the Ajax call,
        // we are able to differentiate the requests and call the appropriate
        // method. We can always use more classes for more use-cases.

        // The response object returns the information (as a JSON object in String form)
        // to the browser.
        String user = request.getParameter("user");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
//        String option = request.getParameter("option");

        System.out.println("about to call baseStationJSON");

        List<BaseStations> baseStationsList = BaseStationsJSON.getBaseStationList(user, startDate, endDate);
        String jsonBaseStation = BaseStationsJSON.getBaseStationsJSON(baseStationsList);

        response.getWriter().write(jsonBaseStation);
        response.flushBuffer();


    }
}
