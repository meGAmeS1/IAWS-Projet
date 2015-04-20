package UGmont;

import UGmontBack.database.DbUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.dom.DOMSource;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tiska on 17/04/2015.
 */
public class ApiSearchRoomTest {

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

    /* Teste le cas ou renseigne le nombre de places et les informations sur la 3d */
    @Test
    public void testGetRoomPlaces3d() {

        String nbPlaces = "10";
        String require3d = "true";
        DOMSource responseMsg = target.path("api/salles").queryParam("nbplaces", nbPlaces).queryParam("require3d", require3d).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        int minPlaces = Integer.parseInt(nbPlaces);
        boolean is3dRequired = Boolean.parseBoolean(require3d);

        //On regarde si les attributs places et 3d de chaque salle sont coherents
        NodeList salles = getSalles(responseMsg);
        int length = salles.getLength();
        for (int i = 0; i < length; i++) {
            Node salle = salles.item(i);
            //Le nombre de places est-il coherent ?
            isNumberOfSeatsCorrect(salle, minPlaces);
            //Information sur la 3D coherente ?
            is3dCorrect(salle, is3dRequired);
        }
    }

    /* Teste le cas ou on ne renseigne que le nombre de places */
    @Test
    public void testGetRoomPlaces() {

        String nbPlaces = "70";
        DOMSource responseMsg = target.path("api/salles").queryParam("nbplaces", nbPlaces).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        int minPlaces = Integer.parseInt(nbPlaces);

        //On regarde si l'attribut places de chaque salle est coherent
        NodeList salles = getSalles(responseMsg);
        int length = salles.getLength();
        for (int i = 0; i < length; i++) {
            Node salle = salles.item(i);
            //Le nombre de places est-il coherent ?
            isNumberOfSeatsCorrect(salle, minPlaces);
        }
    }

    /* Teste le cas ou le nombre de places n'est pas renseigne */
    @Test(expected = WebApplicationException.class)
    public void testGetRoomWithoutPlaces() {
        target.path("api/salles").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Teste le cas ou le nombre de places n'est pas un numerique */
    @Test(expected = WebApplicationException.class)
    public void testGetRoomNotNumeric() {
        target.path("api/salles").queryParam("nbplaces", "1ade2").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Teste le cas ou le nombre de places est negatif */
    @Test(expected = WebApplicationException.class)
    public void testGetRoomNegative() {
        target.path("api/salles").queryParam("nbplaces", "-1").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Renvoie les salles */
    private NodeList getSalles(DOMSource responseMsg) {
        return responseMsg.getNode().getChildNodes().item(0).getChildNodes();
    }

    /* Verifie si le nombre de places d'une salle est coherent */
    private void isNumberOfSeatsCorrect(Node salle, int minPlaces) {
        int placesSalle = Integer.parseInt(salle.getAttributes().getNamedItem("nbPlaces").getNodeValue());
        System.out.println("Nombre de places : " + placesSalle + " Requis : " + minPlaces);
        assertEquals(true, placesSalle >= minPlaces);
    }

    /* Verifie si les informations sur la 3d d'une salle est coherente */
    private void is3dCorrect(Node salle, boolean is3dRequired) {
        boolean support3d = Boolean.parseBoolean(salle.getAttributes().getNamedItem("support3d").getNodeValue());
        System.out.println("3d : " + support3d + " Requis : " + is3dRequired);
        assertEquals(is3dRequired, support3d);
    }
}
