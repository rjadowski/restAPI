package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import model.Film;
import model.FilmDAO;

//Servlet that uses FilmDAO's searchFilm method to return an ArrayList of films in the database that match the search query
//Formats ArrayList into JSON, XML or plaintext and send back formatted data
public class SearchFilm extends HttpServlet {

    public SearchFilm() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String outputPage;
        String search = request.getParameter("search");
        ArrayList<Film> films = new ArrayList<>();
        FilmDAO db = FilmDAO.getInstance();

        try {
            films = db.searchFilm(search);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        request.setAttribute("Films", films);

        //Sets outputPage to the correct content type (JSON, XML, plaintext)
        //Parses ArrayList films and sets request attribute for formatted data
        outputPage = setContentType(request, response, films);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(outputPage);
        dispatcher.include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String setContentType(HttpServletRequest request, HttpServletResponse response, ArrayList<Film> films) {
        String outputPage;

        //If no format is found in request parameters, defaults to JSON
        String format = request.getParameter("format");
        if (format == null) {
            format = "json";
        }

        //Uses XStream library to format raw data into XML
        if ("xml".equals(format)) {
            response.setContentType("text/xml");
            outputPage = "../WEB-INF/results/films-xml.jsp";
            XStream xStream = new XStream();
            xStream.alias("film", Film.class);
            String xml = xStream.toXML(films);

            request.setAttribute("xml", xml);


        }
        //Uses overrode Film.toString method on ArrayList to format raw data into delimited string
        else if ("plaintext".equals(format)) {
            response.setContentType("text/plain");
            outputPage = "../WEB-INF/results/films-string.jsp";
            String plainText = films.toString();

            request.setAttribute("plainText", plainText);

        }
        //Uses Gson library to format raw data into JSON
        else {
            response.setContentType("text/json");
            outputPage = "../WEB-INF/results/films-json.jsp";
            Gson gson = new Gson();
            String json = gson.toJson(films);

            request.setAttribute("json", json);

        }
        return outputPage;
    }

}

