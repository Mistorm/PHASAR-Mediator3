package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Quad;
import nl.ru.cs.phasar.mediator.userquery.SuggestionItem;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
@Path("/mediator/query/suggestion")
public class SuggestionResource {

    public static String WILDCARD = "*";

    @POST
    @Produces("application/json")
    public Response getSuggestion(String json) throws JSONException {

	Filter filter = new Filter();
	JSONObject suggestionRequest = new JSONObject(json);
	SuggestionQuery query = new SuggestionQuery(suggestionRequest.getJSONObject("query").toString());

	//System.out.println(this.getClass().getName() + ": Received JSON: " + json);

	//See what kind of suggestion is requested and modify query in needed
	if (suggestionRequest.has("arrow")) {

	    //Set wildcard in apropiate place
	    JSONObject jsonArrow = suggestionRequest.getJSONObject("arrow");
	    query.setWildcard(jsonArrow.getInt("a"), jsonArrow.getInt("b"), jsonArrow.getInt("direction"));

	    //Create filterpart
	    RelatorFilterPart filterPart = new RelatorFilterPart();
	    filterPart.addFilterTriple(new Triple(query.getQuadList().getBoxContent(jsonArrow.getInt("a")),
		    WILDCARD,
		    query.getQuadList().getBoxContent(jsonArrow.getInt("b")),
		    query.getQuadList().getBoxContent(jsonArrow.getInt("direction"))));

	    filter.addFilterPart(filterPart);

	} else if (suggestionRequest.has("box")) {
	    //Modify the query
	    JSONObject jsonBox = suggestionRequest.getJSONObject("box");
	    query.setWildcard(jsonBox.getInt("nr"));

	    //Create filter triples
	    List<Quad> quads = query.getQuads(jsonBox.getInt("nr"));

	    HeadFilterPart headFilterPart = new HeadFilterPart();
	    TailFilterPart tailFilterPart = new TailFilterPart();

	    for (Quad quad : quads) {
		String position = quad.getPosition(jsonBox.getInt("nr"));
		if (position.equals("head")) {
		    headFilterPart.addFilterTriple(new Triple(quad.toTriple(query.getQuadList().getBoxes())));
		    filter.addFilterPart(headFilterPart);
		} else if (position.equals("tail")) {
		    tailFilterPart.addFilterTriple(new Triple(quad.toTriple(query.getQuadList().getBoxes())));
		    filter.addFilterPart(tailFilterPart);
		}
	    }
	} else {
	    throw new JSONException("Suggestion request does not mention arrow or box");
	}

	//Resolve query
	List<Result> results = query.resolve();

	//Filter the matches so they ONLY contain triples that match the filter triples.
	ArrayList<String> filterResults = new ArrayList<String>();
	for (Result result : results) {
	    filterResults.addAll(filter.filter(result.getTriples()));
	}
	
	//Remove & count the duplicates
	ArrayList<SuggestionItem> suggestionList = new ArrayList<SuggestionItem>();

	for (String filterResult : filterResults) {

	    SuggestionItem newItem = new SuggestionItem(filterResult, 1);

	    for (SuggestionItem item : suggestionList) {
		if (item.getKey().equals(filterResult)) {
		    item.incrementCount();
		    filterResult = null;
		    break;
		}
	    }

	    if (filterResult != null) {
		suggestionList.add(newItem);
	    }
	}

	//Sort everything
	Collections.sort(suggestionList);
	Collections.reverse(suggestionList);


	//Return suggestions in JSON format
	JSONObject returned = new JSONObject();
	JSONObject suggestion;

	//Always add the wildcard option on the first entry
	suggestion = new JSONObject();
	suggestion.put("value", WILDCARD);
	suggestion.put("count", WILDCARD);
	returned.append("suggestion", suggestion);

	//Build the rest of the suggestion array
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
