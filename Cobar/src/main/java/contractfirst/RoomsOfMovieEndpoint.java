package contractfirst;

import UGmontBack.BackService;
import UGmontBack.model.Salle;
import org.springframework.ws.server.endpoint.annotation.*;
import org.w3c.dom.Element;

import java.util.List;

@Endpoint
public class RoomsOfMovieEndpoint {
    private BackService backService;

    private static final String NAMESPACE_URI = "http://iaws/ws/contractfirst/exemple";

    public RoomsOfMovieEndpoint(BackService backService) {
        this.backService = backService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RoomsOfMovieRequest") // nom de l'élément racine
    @Namespace(prefix = "rn", uri = NAMESPACE_URI) // espace de nom pour les expressions XPath ci-dessous
    @ResponsePayload
    public void handleRoomsOfMovieRequest(@XPathParam("/rn:RoomsOfMovieRequest/rn:film/text()") String film) throws Exception {

        List<Salle> evals = backService.roomsOfMovie(film);

        for (int i=0; i<evals.size(); i++) {
            System.out.println(evals.get(i));
        }

    }

}