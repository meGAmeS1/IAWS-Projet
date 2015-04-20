package UGmont.api;

import UGmont.database.HibernateUtil;
import UGmont.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by flemoal on 20/04/15.
 */
@Path("api")
public class ApiRoomsOfMovie {

    @GET
    @Path("roomsOfMovie")
    @Produces(MediaType.APPLICATION_XML)
    public List<Salle> roomsOfMovie(@QueryParam("film") String imdbFilm) {
        if (imdbFilm == null) {
            throw new WebApplicationException("Parameter \"film\" is required");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query querySearchRooms = session.createQuery("from Salle where film.imdbId = :imdbId");
        querySearchRooms.setParameter("imdbId", imdbFilm);

        List<Salle> salles = querySearchRooms.list();

        session.close();

        return salles;
    }
}
