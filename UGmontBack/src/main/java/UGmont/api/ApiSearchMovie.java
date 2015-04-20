package UGmont.api;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.dom.DOMSource;

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
    public DOMSource getFilms(@QueryParam("titre") String titre, @QueryParam("annee") String annee) {
        if (titre == null) {
            throw new WebApplicationException("Empty query");
//            return null; // "<root response=\"False\"><error>Empty query</error></root>";
        } else {
            Client c = ClientBuilder.newClient();
            WebTarget wt = c.target(API_URL).queryParam("s", titre).queryParam("r", "xml");

            if (annee != null) {
                wt = wt.queryParam("y", annee);
            }

            return wt.request(MediaType.APPLICATION_XML).get(DOMSource.class);
        }
    }
}
