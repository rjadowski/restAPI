package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class FilmDAO implements FilmInfo {

    //Applying singleton design pattern to restrict FilmDAO class to one instance
    private static FilmDAO filmDAOInstance;

    private FilmDAO() {
    }

    public static synchronized FilmDAO getInstance() {
        if (filmDAOInstance == null) {
            filmDAOInstance = new FilmDAO();
        }
        return filmDAOInstance;
    }

    //variables set to connect and interact with database
    Connection dbConnection = null;
    Statement statement = null;
    String user = "root";
    String password = "Quag5verm";
    String databaseName = "filmDB";
    String databasePublicIP = "34.76.193.95";
    String url = "jdbc:mysql://" + databasePublicIP + ":3306/" + databaseName;

    //Method to establish connection to the database
    private Connection getDBConnection() {
        Connection dbConnection;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Returns an ArrayList containing all films in the database
    public ArrayList<Film> allFilms() throws SQLException {

        ResultSet result = null;
        String query = "SELECT * FROM films;";
        ArrayList<Film> films = new ArrayList<>();

        try {
            dbConnection = getDBConnection();
            assert dbConnection != null;
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            filmsArray(result, films);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnections();
        }
        return films;
    }

    //Returns a single Film from the database based on the ID
    public Film getFilmByID(int id) throws SQLException {

        Film temp = null;
        ResultSet result = null;

        try {
            dbConnection = getDBConnection();
            String query = "SELECT * FROM films WHERE id = ?;";

            assert dbConnection != null;
            PreparedStatement prepQuery = dbConnection.prepareStatement(query);
            prepQuery.setInt(1, id);
            result = prepQuery.executeQuery();

            while (result.next()) {
                int filmID = result.getInt("id");
                String title = result.getString("title");
                int year = result.getInt("year");
                String director = result.getString("director");
                String stars = result.getString("stars");
                String review = result.getString("review");

                temp = new Film(filmID, title, year, director, stars, review);
            }
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnections();
        }
        return temp;
    }

    //Returns an ArrayList containing all films that match search criteria
    public ArrayList<Film> searchFilm(String search) throws SQLException {

        ResultSet result = null;
        String query = "SELECT * FROM films WHERE title LIKE ?;";
        ArrayList<Film> films = new ArrayList<>();

        try {
            dbConnection = getDBConnection();
            assert dbConnection != null;
            PreparedStatement prepQuery = dbConnection.prepareStatement(query);
            prepQuery.setString(1, "%" + search + "%");
            statement = dbConnection.createStatement();

            result = prepQuery.executeQuery();
            filmsArray(result, films);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnections();
        }
        return films;
    }


    //Returns int confirming success/failure of insertion of a new film
    public int insertFilm(Film newFilm) throws SQLException {

        int result = 0;
        //Check to see if a film already exists with the same ID
        Film checkExistingFilm = getFilmByID(newFilm.getId());

        //Only insert film if the ID hasn't been used
        if (checkExistingFilm == null) {
            try (Connection dbConnection = getDBConnection()) {
                String query = "INSERT INTO films (id, title, year, director, stars, review) VALUES (?,?,?,?,?,?);";

                assert dbConnection != null;
                PreparedStatement prepQuery = dbConnection.prepareStatement(query);
                prepQuery.setInt(1, newFilm.getId());
                prepQuery.setString(2, newFilm.getTitle());
                prepQuery.setInt(3, newFilm.getYear());
                prepQuery.setString(4, newFilm.getDirector());
                prepQuery.setString(5, newFilm.getStars());
                prepQuery.setString(6, newFilm.getReview());

                result = prepQuery.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                closeConnections();
            }
        }
        return result;
    }

    //Returns int confirming success/failure of deletion of an existing film
    public int deleteFilm(int id) throws SQLException {
        int result = 0;

        try (Connection dbConnection = getDBConnection()) {
            String query = "DELETE FROM films WHERE ID = ?";

            assert dbConnection != null;
            PreparedStatement prepQuery = dbConnection.prepareStatement(query);
            prepQuery.setInt(1, id);

            result = prepQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnections();
        }
        return result;
    }

    //Returns int confirming success/failure of update of an existing film
    public int updateFilm(Film updatedFilm) throws SQLException {
        int result = 0;

        try (Connection dbConnection = getDBConnection()) {
            String query = "UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? WHERE id = ?;";

            assert dbConnection != null;
            PreparedStatement prepQuery = dbConnection.prepareStatement(query);
            prepQuery.setString(1, updatedFilm.getTitle());
            prepQuery.setInt(2, updatedFilm.getYear());
            prepQuery.setString(3, updatedFilm.getDirector());
            prepQuery.setString(4, updatedFilm.getStars());
            prepQuery.setString(5, updatedFilm.getReview());
            prepQuery.setInt(6, updatedFilm.getId());

            result = prepQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnections();
        }
        return result;
    }

    //Method to create an ArrayList of films based on a ResultSet
    private void filmsArray(ResultSet result, ArrayList<Film> films) throws SQLException {
        while (result.next()) {
            int filmID = result.getInt("id");
            String title = result.getString("title");
            int year = result.getInt("year");
            String director = result.getString("director");
            String stars = result.getString("stars");
            String review = result.getString("review");

            films.add(new Film(filmID, title, year, director, stars, review));
        }
    }

    private void closeConnections() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }
}
