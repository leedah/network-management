package main.java.optimalRoute;

import main.java.accessPoints.AccessPoints;
import main.java.accessPoints.AccessPointsJSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by sofia on 10/4/15.
 */
@WebServlet(name = "AccessPointsOptimalServlet", urlPatterns = "/accessPointsOptimal")
public class AccessPointsOptimalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // We set a specific return type and encoding
        // in order to take advantage of the browser capabilities.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("Optimal Servlet it is");


        // Depending on the GET parameters, passed from the Ajax call,
        // we are able to differentiate the requests and call the appropriate
        // method. We can always use more classes for more use-cases.

        // The response object returns the information (as a JSON object in String form)
        // to the browser.
        String user = request.getParameter("user");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");


//        System.out.println("about to call apJSON");
        List<AccessPoints> accessPointsList = AccessPointsJSON.getOptimalAccessPoints(user, startDate, endDate);
        String apJSONtext = AccessPointsJSON.getAccessPointsJSON(accessPointsList);

        response.getWriter().write(apJSONtext);
        response.flushBuffer();


    }
}
