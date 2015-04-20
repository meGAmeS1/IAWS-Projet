package UGmont.api;

import UGmont.database.HibernateUtil;
import UGmont.model.Film;
import UGmont.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Tiska on 16/04/2015.
 */

public class ApiMovieToRooms {
    public String movieToRooms(String numeroSalles, String imdbFilm) {
        if (numeroSalles == null) {
            throw new IllegalArgumentException("Parameter \"salles\" is required");
        }

        if (imdbFilm == null) {
            throw new IllegalArgumentException("Parameter \"film\" is required");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String[] rooms = numeroSalles.split(",");

        // On cherche le film correspondant a l'imdb
        Query queryFilm = session.createQuery("from Film where imdbId = :imdbId");
        queryFilm.setParameter("imdbId", imdbFilm);
        Film film = (Film) queryFilm.uniqueResult();

        if (film != null) {

            for (int i = 0; i < rooms.length; i++) {

                int numero = 0;
                try {
                    numero = Integer.parseInt(rooms[i]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(rooms[i] + " should be a number");
                }

                // On cherche la salle correspond au numero
                Query querySalle = session.createQuery("from Salle where numeroSalle = :numeroSalle");
                querySalle.setParameter("numeroSalle", numero);
                Salle salle = (Salle) querySalle.uniqueResult();

                // On assigne le film a la salle
                if (salle != null) {
                    salle.setFilm(film);
                    session.save(salle);
                } else {
                    session.close();
                    throw new IllegalArgumentException("The room " + numero + " doesn't exist");
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
