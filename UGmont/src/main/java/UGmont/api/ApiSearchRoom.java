package UGmont.api;

import UGmontBack.model.Salle;
import UGmontBack.BackService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
    public List<Salle> getSalles(@QueryParam("nbplaces") String nbplaces, @QueryParam("require3d") String require3d) {
        List<Salle> results = BackService.getInstance().getSalles(nbplaces, require3d);

        return results;
    }
}
