package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import model.FilmDAO;

//Servlet that uses FilmDAO's insertFilm method to insert a new film into the database
//Returns a confirmation or error message based on if the update was successful
public class InsertFilm extends HttpServlet {

    public InsertFilm() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Retrieves request parameters
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        int year = Integer.parseInt(request.getParameter("year"));
        String director = request.getParameter("director");
        String stars = request.getParameter("stars");
        String review = request.getParameter("review");

        //Set JSP page to use to display the result of the update
        String outputPage = "../WEB-INF/results/result.jsp";
        int result;
        String resultString;
        FilmDAO db = FilmDAO.getInstance();
        Film newFilm = new Film(id, title, year, director, stars, review);
        
        //Sets confirmation message or error message based on whether the update was successful
        try {
            result = db.insertFilm(newFilm);
            if (result == 0) {
                resultString = "<h2>Error: Film could not be added to the database.</h2>";
            } else {
                resultString = "<h2>Success: Film has been added to the database.</h2>";
            }
            response.setContentType("text/plain");
            //Sets request attribute to the result message
            request.setAttribute("resultString", resultString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(outputPage);
        dispatcher.include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}