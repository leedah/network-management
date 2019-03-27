package main.java.battery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by sofia on 10/4/15.
 */
@WebServlet(name = "BatteryLowServlet", urlPatterns = "/batteryLow")
public class BatteryLowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // We set a specific return type and encoding
        // in order to take advantage of the browser capabilities.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LinkedHashMap<String, Integer> map = BatteryJSON.getLowBatteryInfo();
        String batteryJSONtext = BatteryJSON.getBatteryJSON(map);

        response.getWriter().write(batteryJSONtext.replace("\\", ""));
        response.flushBuffer();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
