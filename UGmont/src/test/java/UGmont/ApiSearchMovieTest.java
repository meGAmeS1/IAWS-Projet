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


import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void test1() {
        String titre = "Matrix";
        String annee = "2003";
        DOMSource responseMsg = target.path("api/films").queryParam("titre",titre).queryParam("annee", annee).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        String attributeRoot = responseMsg.getNode().getChildNodes().item(0).getAttributes().getNamedItem("response").getNodeValue();
        System.out.println(attributeRoot);
        boolean res = attributeRoot.equals("True");

        //L'attribut de root doit etre a True (sinon, pas de films trouves)
        if (res) {
            NodeList films = responseMsg.getNode().getChildNodes().item(0).getChildNodes();
            int length = films.getLength();
            int i = 0;
            /*On regarde pour chaque film leurs attributs titre et annee.
            S'ils ne contiennent pas le titre et l'annee renseignes en parametre, on met res a false et on arrete*/
            while (i < length & res) {
                Node film = films.item(i);
                String titreFilm = film.getAttributes().getNamedItem("Title").getNodeValue();
                System.out.println(titreFilm);
                String anneeFilm = film.getAttributes().getNamedItem("Year").getNodeValue();
                System.out.println(anneeFilm);
                if (!titreFilm.contains(titre) || !anneeFilm.contains(annee)) {
                    res = false;
                }
                i++;
                System.out.println(res);
            }
        }
        assertEquals(true, res);
    }

}