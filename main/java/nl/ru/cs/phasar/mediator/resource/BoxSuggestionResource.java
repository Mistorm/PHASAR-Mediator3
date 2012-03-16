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
//@Path("/mediator/query/suggestion/box")
public class BoxSuggestionResource extends AbstractSuggestionResource {

    public BoxSuggestionResource() {
        super();
    }

    //@POST
    //@Produces("application/json")
    public Response getSuggestion(String json) throws JSONException {
        
        //Get & resolve the query
        JSONObject suggestionRequest = new JSONObject(json);
        JSONObject baseQuery = suggestionRequest.getJSONObject("baseQuery");
        JSONObject extention = suggestionRequest.getJSONObject("extension");

        Query query = super.getResult(baseQuery.toString());

        Triple triple = new Triple(extention.getString("a"), extention.getString("relator"), extention.getString("b"), extention.getString("direction"));

        //Count the terms
        List<SuggestionItem> SuggestionList;
        if (triple.getGroundHead().equals(WILDCARD)) {
            SuggestionList = query.getSuggestion(triple, "head");
        } else {
            SuggestionList = query.getSuggestion(triple, "tail");
        }
        
        //Build the JSON answer
        JSONObject returned = new JSONObject();

        JSONObject suggestion = new JSONObject();

        //Make sure the wildcard suggestion is send first
        suggestion = new JSONObject();
        suggestion.put("value", WILDCARD);
        suggestion.put("count", WILDCARD);
        returned.append("suggestion", suggestion);

        for (SuggestionItem item : SuggestionList) {
            suggestion = new JSONObject();
            suggestion.put("value", item.getKey());
            suggestion.put("count", item.getCount());
            returned.append("suggestion", suggestion);
        }

        return Response.ok(returned.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
}
