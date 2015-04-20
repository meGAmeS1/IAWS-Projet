package UGmont.api;

import UGmontBack.model.Salle;
import UGmontBack.BackService;

import javax.ws.rs.*;
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
        if (nbplaces == null) {
            throw new WebApplicationException("Parameter \"nbplaces\" is required");
        }

        int places = 0;

        try {
            places = Integer.parseInt(nbplaces);
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Parameter \"nbplaces\" should be a number");
        }

        if (places < 0) {
            throw new WebApplicationException("Parameter \"nbplaces\" should be higher than zero");
        }

        boolean avec3d = Boolean.parseBoolean(require3d);

        List<Salle> results = BackService.getInstance().getSalles(places, avec3d);

        return results;
    }
}
