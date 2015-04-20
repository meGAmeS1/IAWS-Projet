package UGmont.api;

import UGmontBack.model.Salle;
import UGmontBack.BackService;

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

        List<Salle> results = BackService.getInstance().roomsOfMovie(imdbFilm);

        return results;
    }
}
