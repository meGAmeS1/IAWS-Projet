package UGmont;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("api")
public class ApiSearchMovie {
    private static final String API_URL = "http://www.omdbapi.com/?";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("films")
    @Produces(MediaType.APPLICATION_XML)
    public String getFilms(@QueryParam("titre") String titre, @QueryParam("annee") String annee) {
        if (titre == null) {
            return "<root response=\"False\"><error>Empty query</error></root>";
        } else {
            Client c = ClientBuilder.newClient();
            WebTarget wt = c.target(API_URL).queryParam("r", "xml").queryParam("s", titre);

            if (annee != null) {
                wt = wt.queryParam("y", annee);
            }

            return wt.request(MediaType.APPLICATION_XML).get(String.class);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getIt() {
        return "<root response=\"False\"><error>No method called</error></root>";
    }
}
