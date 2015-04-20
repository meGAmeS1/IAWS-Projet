package UGmontBack;

import UGmontBack.back.BackMovieToRooms;
import UGmontBack.back.BackRoomsOfMovie;
import UGmontBack.back.BackSearchMovie;
import UGmontBack.back.BackSearchRoom;
import UGmontBack.model.Salle;

import javax.xml.transform.dom.DOMSource;
import java.util.List;

/**
 * Created by flemoal on 20/04/15.
 */
public class BackService {
    private static BackService _instance = new BackService();

    private BackMovieToRooms backMovieToRooms;
    private BackRoomsOfMovie backRoomsOfMovie;
    private BackSearchMovie backSearchMovie;
    private BackSearchRoom backSearchRoom;

    private BackService() {
        backMovieToRooms = new BackMovieToRooms();
        backRoomsOfMovie = new BackRoomsOfMovie();
        backSearchMovie = new BackSearchMovie();
        backSearchRoom = new BackSearchRoom();

    }

    public static BackService getInstance() {
        return _instance;
    }

    public String movieToRooms(String numeroSalles, String imdbFilm) {
        return backMovieToRooms.movieToRooms(numeroSalles, imdbFilm);
    }

    public List<Salle> roomsOfMovie(String imdbFilm) {
        return backRoomsOfMovie.roomsOfMovie(imdbFilm);
    }

    public DOMSource getFilms(String titre, String annee) {
        return backSearchMovie.getFilms(titre, annee);
    }

    public List<Salle> getSalles(String nbplaces, String require3d) {
        return backSearchRoom.getSalles(nbplaces, require3d);
    }

}
