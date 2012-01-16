package nl.ru.cs.phasar.mediator.resource;

import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
import org.json.JSONException;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
@Path("/mediator/query/suggestion/b")
public class BSuggestionResource extends AbstractSuggestionResource {

    public BSuggestionResource() {
        super();
    }

    @POST
    @Produces("application/json")
    public Response getSuggestion(String json) throws JSONException {
        List<Hit> newHits = super.getNewHits(json);
        return Response.ok("{\"options\":{\"one\",\"two\",\"three\"}}").header("Access-Control-Allow-Origin", "*").build();
    }
}
