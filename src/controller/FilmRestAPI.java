package controller;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import model.Film;
import model.FilmDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/films")
public class FilmRestAPI {

    FilmDAO db = FilmDAO.getInstance();
    ArrayList<Film> films = new ArrayList<>();
    int result;

    //GET request to fetch all films in the database using the XStream library to return data in XML format
    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public String getFilmsXML() throws SQLException {
        films = db.allFilms();
        XStream xStream = new XStream();
        xStream.alias("film", Film.class);

        return xStream.toXML(films);
    }

    //GET request to fetch all films in the database and returning data as plaintext
    @GET
    @Path("/plaintext")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilmsText() throws SQLException {
        films = db.allFilms();
        return films.toString();
    }

    //GET request to fetch all films in the database using the GSON library to return data in JSON format
    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFilmsJSON() throws SQLException {
        films = db.allFilms();
        Gson gson = new Gson();
        return gson.toJson(films);
    }

    //GET request to fetch all films that match the search query in the database
    //using the Xstream library to return data in XML format
    @GET
    @Path("/xml/search={search}")
    @Produces(MediaType.TEXT_XML)
    public String searchFilmsXML(@PathParam("search") String search) throws SQLException {
        films = db.searchFilm(search.replace('+',' '));
        XStream xStream = new XStream();
        xStream.alias("film", Film.class);

        return xStream.toXML(films);
    }

    //GET request to fetch all films that match the search query in the database
    //returning data as plaintext
    @GET
    @Path("/plaintext/search={search}")
    @Produces(MediaType.TEXT_PLAIN)
    public String searchFilmsText(@PathParam("search") String search) throws SQLException {
        films = db.searchFilm(search.replace('+',' '));
        return films.toString();
    }

    //GET request to fetch all films that match the search query in the database
    //using the GSON library to return data in JSON format
    @GET
    @Path("/json/search={search}")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchFilmsJSON(@PathParam("search") String search) throws SQLException {
        films = db.searchFilm(search.replace('+',' '));
        Gson gson = new Gson();
        return gson.toJson(films);
    }

    //POST request to insert a film into the database using the form parameters from consumed form
    @POST
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String insertFilm(
            @FormParam("id") int id,
            @FormParam("title") String title,
            @FormParam("year") int year,
            @FormParam("director") String director,
            @FormParam("stars") String stars,
            @FormParam("review") String review) throws SQLException {
        Film newFilm = new Film(id, title, year, director, stars, review);
        result = db.insertFilm(newFilm);
        if (result == 0) {
            return errorMessage("Film could not be added to the database, there may be another film with the same ID.");
        }
        return confirmationMessage("Film has been added to the database.");
    }

    //POST request to update an existing film into the database using the form parameters from consumed form
    @PUT
    @Path("/update")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateFilm(
            @FormParam("id") int id,
            @FormParam("title") String title,
            @FormParam("year") int year,
            @FormParam("director") String director,
            @FormParam("stars") String stars,
            @FormParam("review") String review) throws SQLException {
        Film updatedFilm = new Film(id, title, year, director, stars, review);
        result = db.updateFilm(updatedFilm);
        if (result == 0) {
            return errorMessage("Film could not be updated, check to see if the ID matches an existing film.");
        }
        return confirmationMessage("Film has been updated.");
    }

    //DELETE request to delete an existing film into the database using the form parameters from consumed form
    @DELETE
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteFilm(
            @FormParam("id") int id) throws SQLException {
        result = db.deleteFilm(id);
        if (result == 0) {
            return errorMessage("Film could not be deleted, check to see if the ID matches an existing film.");
        }
        return confirmationMessage("Film has been deleted from the database.");
    }

    //templates for confirmation and error messages used in POST, PUT and DELETE requests
    private String confirmationMessage(String message) {
        return "<h2>Success: " + message + "</h2>";
    }

    private String errorMessage(String message) {
        return "<h2>Error: " + message + "</h2>";
    }
}