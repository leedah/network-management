package main.java.stayPoints;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sofia on 10/2/15.
 */
@WebServlet(name = "StayPointServlet", urlPatterns = "/stayPoints/stayPoint")
public class StayPointServlet extends HttpServlet {
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

        long Tmin = Long.parseLong(request.getParameter("Tmin"));
        long Tmax = Long.parseLong(request.getParameter("Tmax"));
        double Dmax = Double.parseDouble(request.getParameter("Dmax"));

        ArrayList<ArrayList<Double>> stayPointList = StayPointJSON.getStayPoints(user,startDate,endDate, Tmin, Tmax, Dmax);
        String geoJSONtext = StayPointJSON.getStayPointsJSON(stayPointList);

        response.getWriter().write(geoJSONtext);
        response.flushBuffer();

    }
}
