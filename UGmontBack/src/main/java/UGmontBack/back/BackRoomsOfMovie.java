package UGmontBack.back;

import UGmontBack.database.HibernateUtil;
import UGmontBack.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by flemoal on 20/04/15.
 */
public class BackRoomsOfMovie {

    public List<Salle> roomsOfMovie(String imdbFilm) {
        if (imdbFilm == null) {
            throw new IllegalArgumentException("Parameter \"film\" is required");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query querySearchRooms = session.createQuery("from Salle where film.imdbId = :imdbId");
        querySearchRooms.setParameter("imdbId", imdbFilm);

        List<Salle> salles = querySearchRooms.list();

        session.close();

        return salles;
    }
}
