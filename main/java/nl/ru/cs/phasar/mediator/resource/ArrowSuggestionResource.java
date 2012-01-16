package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
@Path("/mediator/query/suggestion/arrow")
public class ArrowSuggestionResource extends AbstractSuggestionResource {

    public ArrowSuggestionResource() {
        super();
    }

    @POST
    @Produces("application/json")
    public Response getSuggestion(String json) {

        List<Hit> newHits = new ArrayList<Hit>();
        try {
            newHits = super.getNewHits(json);
        }
        catch (JSONException ex) {
            Logger.getLogger(ArrowSuggestionResource.class.getName()).log(Level.SEVERE, null, ex);
        }


        HashSet<String> arrowSuggestions = new HashSet<String>();

        for (Hit h : newHits) {
            for (Triple t : h.getMatches()) {
                arrowSuggestions.add(t.getRelator());
            }
        }

        JSONArray array = new JSONArray(arrowSuggestions);

        System.out.println("Arrow Suggestion array: " + array.toString());

        return Response.ok(array.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
}
