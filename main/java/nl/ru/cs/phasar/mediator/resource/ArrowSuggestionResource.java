package nl.ru.cs.phasar.mediator.resource;

import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Query;
import nl.ru.cs.phasar.mediator.userquery.SuggestionItem;
import org.json.JSONException;
import org.json.JSONObject;

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
    public Response getSuggestion(String json) throws JSONException {

        JSONObject object = new JSONObject(json);
        JSONObject baseQuery = object.getJSONObject("baseQuery");
        JSONObject extention = object.getJSONObject("extension");

        System.out.println("baseQuery:" + baseQuery.toString());
        System.out.println("extention:" + extention.toString());

        Query query = super.getResult(baseQuery.toString());

        Triple triple = new Triple(extention.getString("a"), extention.getString("relator"), extention.getString("b"), extention.getString("direction"));

        List<SuggestionItem> suggestionList = query.getSuggestion(triple, "relator");

        JSONObject returned = new JSONObject();

        JSONObject suggestion = new JSONObject();

        suggestion = new JSONObject();
        suggestion.put("value", WILDCARD);
        suggestion.put("count", WILDCARD);
        System.out.println(suggestion.toString());
        returned.append("suggestion", suggestion);

        for (SuggestionItem item : suggestionList) {
            suggestion = new JSONObject();
            suggestion.put("value", item.getKey());
            suggestion.put("count", item.getCount());
            returned.append("suggestion", suggestion);
        }

        System.out.println("Response: " + returned.toString());

        return Response.ok(returned.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
}
