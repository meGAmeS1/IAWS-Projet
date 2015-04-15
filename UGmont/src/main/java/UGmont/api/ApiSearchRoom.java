package UGmont.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    // @Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilms(@QueryParam("nbplaces") String nbplaces, @QueryParam("require3d") String require3d) {
        if (nbplaces == null) {
            return "<root response=\"False\"><error>Parameter \"nbplaces\" is required</error></root>";
        }

        int places = 0;

        try {
            places = Integer.parseInt(nbplaces);
        } catch (NumberFormatException e) {
            return "<root response=\"False\"><error>Parameter \"nbplaces\" should be a number</error></root>";
        }

        if (places <= 0) {
            return "<root response=\"False\"><error>Parameter \"nbplaces\" should be higher than zero</error></root>";
        }

        boolean avec3d = Boolean.parseBoolean(require3d);

        return "Hello";
    }
}
