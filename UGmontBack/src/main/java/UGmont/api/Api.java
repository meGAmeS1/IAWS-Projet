package UGmont.api;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("api")
public class Api {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getIt() {
        throw new WebApplicationException("No method called");
//        return "<root response=\"False\"><error>No method called</error></root>";
    }
}
