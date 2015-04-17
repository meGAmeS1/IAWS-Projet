package UGmont.api;

import UGmont.database.HibernateUtil;
import UGmont.model.Film;
import UGmont.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Tiska on 16/04/2015.
 */

@Path("api")
public class ApiRoomsToMovie {

    private static final String API_URL = "http://www.omdbapi.com/?";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("roomsToMovie")
    @Produces(MediaType.APPLICATION_XML)
    public String createSeances(@QueryParam("salles") String numeroSalles, @QueryParam("film") String imdbFilm) {
        if (numeroSalles == null) {
            throw new WebApplicationException("Parameter \"salle\" is required");
        }
        if (imdbFilm == null) {
            throw new WebApplicationException("Parameter \"film\" is required");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        String[] rooms = numeroSalles.split(",");

        //On cherche le film correspondant a l'imdb
        Query queryFilm = session.createQuery("from Film where imdbId = :imdbId");
        queryFilm.setParameter("imdbId", imdbFilm);
        Film film = (Film) queryFilm.uniqueResult();

        if (film != null) {
            for (int i = 0; i < rooms.length; i++) {

                Integer numero = Integer.parseInt(rooms[i]);

                //On cherche la salle correspond au numero
                Query querySalle = session.createQuery("from Salle where numeroSalle = :numeroSalle");
                querySalle.setParameter("numeroSalle", numero);
                Salle salle = (Salle) querySalle.uniqueResult();

                //On assigne le film a la salle
                if (salle != null) {
                    System.out.println("Salle " + Integer.parseInt(rooms[i]) + " : " + salle.toString());
                    salle.setFilm(film);
                    session.save(salle);
                    System.out.println("Salle " + Integer.parseInt(rooms[i]) + " : " + salle.toString());
                }
            }
        }
        session.close();

        return "<root response=\"true\">Les salles ont été assignées</root>";
    }

}
