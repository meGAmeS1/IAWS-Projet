package UGmont.api;

import UGmont.database.HibernateUtil;
import UGmont.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("api")
public class ApiSearchRoom {
    private static final String API_URL = "http://www.omdbapi.com/?";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("salles")
    @Produces(MediaType.APPLICATION_XML)
//    @Produces(MediaType.TEXT_PLAIN)
    public List<Salle> getFilms(@QueryParam("nbplaces") String nbplaces, @QueryParam("require3d") String require3d) {
        if (nbplaces == null) {
            throw new WebApplicationException("Parameter \"nbplaces\" is required");
            // return null ; // "<root response=\"False\"><error>Parameter \"nbplaces\" is required</error></root>";
        }

        int places = 0;

        try {
            places = Integer.parseInt(nbplaces);
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Parameter \"nbplaces\" should be a number");
            // return null; // "<root response=\"False\"><error>Parameter \"nbplaces\" should be a number</error></root>";
        }

        if (places < 0) {
            throw new WebApplicationException("Parameter \"nbplaces\" should be higher than zero");
            // return null; // "<root response=\"False\"><error>Parameter \"nbplaces\" should be higher than zero</error></root>";
        }

        boolean avec3d = Boolean.parseBoolean(require3d);

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("from Salle ");
        queryBuilder.append("where nbPlaces >= :nbPlaces ");
        if(avec3d) {
            queryBuilder.append("and support3d = true");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(queryBuilder.toString());
        query.setParameter("nbPlaces", places);

        List<Salle> salles = query.list();

        session.close();

        return salles;
    }
}
