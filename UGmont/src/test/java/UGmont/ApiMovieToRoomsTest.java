package UGmont;

import UGmont.database.DbUtil;
import UGmont.database.HibernateUtil;
import UGmont.model.Film;
import UGmont.model.Salle;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
public class ApiMovieToRoomsTest {

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

    /* Teste le cas ou salles et film sont renseignes et valides */
    @Test
    public void testRoomsToMovie() {

        String numeroSalles = "1,2,3";
        String imdbFilm = "tt0120737";
        target.path("api/movieToRooms").queryParam("salles", numeroSalles).queryParam("film", imdbFilm).request(MediaType.APPLICATION_XML).get(DOMSource.class);

        String[] rooms = numeroSalles.split(",");
        Session session = HibernateUtil.getSessionFactory().openSession();

        // On cherche le film correspondant a l'imdb
        Query queryFilm = session.createQuery("from Film where imdbId = :imdbId");
        queryFilm.setParameter("imdbId", imdbFilm);
        Film film = (Film) queryFilm.uniqueResult();

        for (int i = 0; i < rooms.length; i++) {

            Integer numero = Integer.parseInt(rooms[i]);

            // On cherche la salle correspond au numero
            Query querySalle = session.createQuery("from Salle where numeroSalle = :numeroSalle");
            querySalle.setParameter("numeroSalle", numero);
            Salle salle = (Salle) querySalle.uniqueResult();

            // La salle doit avoir pour attribut film le film concerne
            System.out.println("Salle " + Integer.parseInt(rooms[i]) + " : " + salle.toString());
            assertEquals(film, salle.getFilm());
            assertEquals(true, film.getSalles().contains(salle));
        }

        System.out.println("Salles du film : ");
        for (Salle s : film.getSalles()) {
            System.out.println(s.toString());
        }

        session.close();
    }

    /* Teste le cas ou aucune salle n'est renseignee */
    @Test(expected = WebApplicationException.class)
    public void testRoomsToMovieWithoutRooms() {
        target.path("api/movieToRooms").queryParam("film", "tt0120737").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Teste le cas ou aucun film n'est renseigne */
    @Test(expected = WebApplicationException.class)
    public void testRoomsToMovieWithoutMovie() {
        target.path("api/movieToRooms").queryParam("salles", "1,2,3").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Teste le cas ou le film n'existe pas */
    @Test(expected = WebApplicationException.class)
    public void testRoomsToMovieWrongMovie() {
        target.path("api/movieToRooms").queryParam("salles", "1,2,3").queryParam("film", "tt0121546").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Teste le cas ou le numero d'une salle n'est pas numerique n'existe pas */
    @Test(expected = WebApplicationException.class)
    public void testRoomsToMovieRoomNotNumeric() {
        target.path("api/movieToRooms").queryParam("salles", "1a,2,3").queryParam("film", "tt0120737").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }

    /* Teste le cas ou une salle n'existe pas */
    @Test(expected = WebApplicationException.class)
    public void testRoomsToMovieWrongRoom() {
        target.path("api/movieToRooms").queryParam("salles", "16,2,3").queryParam("film", "tt0120737").request(MediaType.APPLICATION_XML).get(DOMSource.class);
    }
}
