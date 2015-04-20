package UGmontBack;

import UGmontBack.back.BackMovieToRooms;
import UGmontBack.back.BackRoomsOfMovie;
import UGmontBack.back.BackSearchRoom;
import UGmontBack.model.Salle;

import java.util.List;

/**
 * Created by flemoal on 20/04/15.
 */
public class BackService {
    private static BackService _instance = new BackService();

    private BackMovieToRooms backMovieToRooms;
    private BackRoomsOfMovie backRoomsOfMovie;
    private BackSearchRoom backSearchRoom;

    private BackService() {
        backMovieToRooms = new BackMovieToRooms();
        backRoomsOfMovie = new BackRoomsOfMovie();
        backSearchRoom = new BackSearchRoom();

    }

    public static BackService getInstance() {
        return _instance;
    }

    public String movieToRooms(List<Integer> salles, String imdbFilm) {
        return backMovieToRooms.movieToRooms(salles, imdbFilm);
    }

    public List<Salle> roomsOfMovie(String imdbFilm) {
        return backRoomsOfMovie.roomsOfMovie(imdbFilm);
    }

    public List<Salle> getSalles(int nbplaces, boolean require3d) {
        return backSearchRoom.getSalles(nbplaces, require3d);
    }

}
