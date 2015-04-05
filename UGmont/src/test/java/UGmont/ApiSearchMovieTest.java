package UGmont;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.dom.DOMSource;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiSearchMovieTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
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

    /* Teste le cas ou on renseigne les parametres "titre" (Matrix) et "annee" (2003) */
    @Test
    public void testTitreAnnee() {

        String titre = "Matrix";
        String annee = "2003";
        DOMSource responseMsg = target.path("api/films").queryParam("titre", titre).queryParam("annee", annee).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        String[][] attributes = {{"Title", titre}, {"Year", annee}};

        boolean res = templateTest(responseMsg, attributes);
        assertEquals(true, res);
    }

    /* Teste le cas ou on ne renseigne que le parametre "titre" (Matrix)*/
    @Test
    public void testTitre() {

        String titre = "Matrix";

        DOMSource responseMsg = target.path("api/films").queryParam("titre", titre).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        String[][] attributes = {{"Title", titre}};

        boolean res = templateTest(responseMsg, attributes);
        assertEquals(true, res);
    }

    /* Utilisee pour verifier les valeurs de certains attributs de chaque film */
    private boolean templateTest(DOMSource responseMsg, String[][] attributes) {

        String attributeRoot = responseMsg.getNode().getChildNodes().item(0).getAttributes().getNamedItem("response").getNodeValue();
        System.out.println("Response : " + attributeRoot);
        boolean res = attributeRoot.equals("True");

        //Si l'attribut de root est a true, des films sont listes
        if (res) {

            NodeList films = responseMsg.getNode().getChildNodes().item(0).getChildNodes();
            int length = films.getLength();
            int i = 0;

            //On regarde les attributs de chaque film tant que ceux-ci ont des valeurs coherentes
            while (i < length & res) {
                Node film = films.item(i);

                //Pour chaque attribut
                for (int j = 0; j < attributes.length; j++) {
                    String type = attributes[j][0];
                    String value = film.getAttributes().getNamedItem(type).getNodeValue();
                    System.out.println(type + " : " + value);
                    //Si l'attribut observe ne contient pas la valeur indiquee, on arrete
                    if (!value.contains(attributes[j][1])) {
                        res = false;
                    }
                }
                i++;
            }
        }
        return res;
    }
}