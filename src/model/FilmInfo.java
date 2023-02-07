package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FilmInfo {

    public int insertFilm(Film newFilm) throws SQLException;

    public int deleteFilm(int id) throws SQLException;

    public int updateFilm(Film updatedFilm) throws SQLException;

    public ArrayList<Film> allFilms() throws SQLException;

    public ArrayList<Film> searchFilm(String search) throws SQLException;

    public Film getFilmByID(int id) throws SQLException;
}
