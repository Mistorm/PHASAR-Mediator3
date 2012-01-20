package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import java.util.List;
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
@Path("/mediator/query/suggestion/box")
public class BoxSuggestionResource extends AbstractSuggestionResource {

    public BoxSuggestionResource() {
        super();
    }

    @POST
    @Produces("application/json")
    public Response getSuggestion(String json) throws JSONException {
//        List<Hit> newHits = super.getNewHits(json);
//        List<String> aSuggestions = new ArrayList<String>(); 
//        
//        for(Hit h: newHits){
//            for(Triple t : h.getMatches())
//            aSuggestions.add(t.getGroundA());
//        }
//        
//        JSONArray array = new  JSONArray(newHits);
//        
//        return Response.ok(array.toString()).header("Access-Control-Allow-Origin", "*").build();
        
        String[] demo = new String[3];
        demo[0] = "ik";
        demo[1] = "hij";
        demo[2] = "wij";
        JSONArray suggestions = new JSONArray(demo);
        return Response.ok(suggestions.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
}
