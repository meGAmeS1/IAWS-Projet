package UGmont;

import UGmontBack.database.DbUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.dom.DOMSource;

/**
 * Created by flemoal on 20/04/15.
 */
public class ApiTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {

        DbUtil.getInstance().initializeBase();

        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);

    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test(expected = WebApplicationException.class)
    public void testGetIt() {
        target.path("api").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }
}
