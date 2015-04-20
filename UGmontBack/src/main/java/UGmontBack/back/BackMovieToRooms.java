package UGmontBack.back;

import UGmontBack.database.HibernateUtil;
import UGmontBack.model.Film;
import UGmontBack.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Tiska on 16/04/2015.
 */

public class BackMovieToRooms {
    public String movieToRooms(List<Integer> salles, String imdbFilm) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        // On cherche le film correspondant a l'imdb
        Query queryFilm = session.createQuery("from Film where imdbId = :imdbId");
        queryFilm.setParameter("imdbId", imdbFilm);
        Film film = (Film) queryFilm.uniqueResult();

        if (film != null) {
            for (Integer numSalle : salles) {
                // On cherche la salle correspond au numero
                Query querySalle = session.createQuery("from Salle where numeroSalle = :numeroSalle");
                querySalle.setParameter("numeroSalle", numSalle);
                Salle salle = (Salle) querySalle.uniqueResult();

                // On assigne le film a la salle
                if (salle != null) {
                    salle.setFilm(film);
                    session.save(salle);
                } else {
                    session.close();
                    throw new IllegalArgumentException("The room " + numSalle + " doesn't exist");
                }
            }
        } else {
            throw new IllegalArgumentException("The movie doesn't exist");
        }

        transaction.commit();
        session.close();

        return "Les salles ont été assignées au film";
    }

}
