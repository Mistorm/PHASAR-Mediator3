package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


        JSONObject object = new JSONObject(json);
        JSONObject baseQuery = object.getJSONObject("baseQuery");
        JSONObject extention = object.getJSONObject("extension");

        Query query = super.getResult(baseQuery.toString());

        Triple triple = new Triple(extention.getString("a"), extention.getString("relator"), extention.getString("b"), extention.getString("direction"));

        HashMap<String, Integer> count;
        if (triple.getGroundA().equals("*")) {
            count = query.getSuggestion(triple, "a");
        } else {
            count = query.getSuggestion(triple, "b");
        }

        JSONObject returned = new JSONObject();

        Set<String> keys = count.keySet();

        JSONObject suggestion = new JSONObject();

        suggestion = new JSONObject();
        suggestion.put("value", "*");
        suggestion.put("count", "*");
        returned.append("suggestion", suggestion);

        for (String key : keys) {
            suggestion = new JSONObject();
            suggestion.put("value", key);
            suggestion.put("count", count.get(key));
            returned.append("suggestion", suggestion);
        }

        return Response.ok(returned.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
}
