package UGmont;

import UGmontBack.database.DbUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.dom.DOMSource;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tiska on 20/04/2015.
 */
public class ApiRoomsOfMovieTest {

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

    @Test
    public void testRoomsOfMovie() {

        String imdbFilm = "tt0780504";

        DOMSource responseMsg = target.path("api/roomsOfMovie").queryParam("film", imdbFilm).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        // Id des salles qui doivent etre assignees au film
        String[] idSalles ={"2","4"};

        NodeList salles = responseMsg.getNode().getChildNodes().item(0).getChildNodes();
        int length = salles.getLength();

        // On parcourt les salles trouvees par la requete
        for (int i = 0; i < length; i++) {

            Node salle = salles.item(i);

            String imdbCurrentMovie = salle.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
            String idCurrentRoom = salle.getAttributes().getNamedItem("id").getNodeValue();
            System.out.println("Salle : " + idCurrentRoom
                    + " Film : " + imdbCurrentMovie);

            assertEquals(idCurrentRoom, idSalles[i]);
            assertEquals(imdbCurrentMovie, imdbFilm);
        }
    }

}
