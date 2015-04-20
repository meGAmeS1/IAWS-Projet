package UGmont.api;

import javax.xml.transform.dom.DOMSource;

public class ApiSearchMovie {

    public DOMSource getFilms(String titre, String annee) {
//        if (titre == null) {
//            throw new IllegalArgumentException("Empty query");
//        } else {
//            Client c = ClientBuilder.newClient();
//            WebTarget wt = c.target(API_URL).queryParam("s", titre).queryParam("r", "xml");
//
//            if (annee != null) {
//                wt = wt.queryParam("y", annee);
//            }
//
//            return wt.request(MediaType.APPLICATION_XML).get(DOMSource.class);
//        }
        return null;
    }
}
