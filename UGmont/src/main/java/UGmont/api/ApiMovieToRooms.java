package UGmont.api;

import UGmontBack.BackService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tiska on 16/04/2015.
 */

@Path("api")
public class ApiMovieToRooms {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("movieToRooms")
    @Produces(MediaType.APPLICATION_XML)
    public String movieToRooms(@QueryParam("salles") String numeroSalles, @QueryParam("film") String imdbFilm) {
        if (numeroSalles == null) {
            throw new WebApplicationException("Parameter \"salles\" is required");
        }

        if (imdbFilm == null) {
            throw new WebApplicationException("Parameter \"film\" is required");
        }

        String[] rooms = numeroSalles.split(",");
        List<Integer> salles = new LinkedList<Integer>();


        for (String room : rooms) {
            try {
                salles.add(Integer.parseInt(room));
            } catch (NumberFormatException e) {
                throw new WebApplicationException(room + " should be a number");
            }
        }

        try {
            String result = BackService.getInstance().movieToRooms(salles, imdbFilm);
            return String.format("<root>%s</root>", result);
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(e);
        }
    }

}
