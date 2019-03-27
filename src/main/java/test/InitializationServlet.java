package main.java.test;

import main.java.readers.UserReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sofia on 10/3/15.
 */
@WebServlet(name = "InitializationServlet", urlPatterns = "/initialize")
public class InitializationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* initialize structures and create database*/
        Initialize.init();

        /* find all the users from database*/
        UserReader.findUsers();

    }
}
