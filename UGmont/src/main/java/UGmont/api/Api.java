package UGmont.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("api")
public class Api {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getIt() {
        return "<root response=\"False\"><error>No method called</error></root>";
    }
}
