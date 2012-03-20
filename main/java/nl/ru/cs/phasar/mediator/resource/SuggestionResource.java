package nl.ru.cs.phasar.mediator.resource;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.*;
import nl.ru.cs.phasar.mediator.documentsource.flatfile.FlatSource;
import nl.ru.cs.phasar.mediator.userquery.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
@Path("/mediator/query/suggestion")
public class SuggestionResource {

    private DocumentSource documentSource;
    private UserQueryCache cache;
    private ArrayList<SuggestionItem> suggestionList;
    private static String PROPERTIES_FILE = "mediator.properties";
    public static String WILDCARD = "*";

    public SuggestionResource() {

	URL url = MediatorResource.class.getProtectionDomain().getCodeSource().getLocation();

	Properties configFile = new Properties();
	try {
	    configFile.load(new FileReader(url.getPath() + PROPERTIES_FILE));
	} catch (IOException ex) {
	    Logger.getLogger(MediatorResource.class.getName()).log(Level.SEVERE, null, ex);
	}

	//Caveat: configFile.getProperty() only returns String, that explains this code
	if (configFile.getProperty("INTERNAL_DOCSOURCE", "true").equals("true")) {
	    documentSource = new ServerQueryCache(new FlatSource());
	} else {
	    documentSource = new ServerQueryCache(new ServerQueryResolver());
	}

	cache = new UserQueryCache(documentSource);

    }

    @POST
    @Produces("application/json")
    public Response getSuggestion(String json) throws JSONException {

	//Position of value the suggestion is requested for
	boolean head = false;
	boolean relator = false;
	boolean tail = false;

	QuadList list = new QuadList();
	ArrayList<Triple> filterTriples = new ArrayList<Triple>();
	suggestionList = new ArrayList<SuggestionItem>();

	System.out.println(this.getClass().getName() + ": Received JSON: " + json);
	
	//Get the query
	JSONObject suggestionRequest = new JSONObject(json);
	JSONObject jsonQuery = suggestionRequest.getJSONObject("query");
	list.buildQuads(jsonQuery);

	//See what kind of suggestion is requested and modify query in needed
	if (suggestionRequest.has("arrow")) {
	    //modify the query by getting the quads, replacing a key value and putting them back
	    JSONObject jsonArrow = suggestionRequest.getJSONObject("arrow");
	    Integer a = jsonArrow.getInt("a");
	    Integer b = jsonArrow.getInt("b");
	    Integer direction = jsonArrow.getInt("direction");

	    List<Quad> quadList = list.getQuadList();

	    //find the quad that is derived from the arrow and replace it's value with a wildcard
	    for (Quad quad : quadList) {
		if (quad.getA().equals(a) && quad.getB().equals(b) && quad.getDirection().equals(direction)) {
		    quad.setRelator(WILDCARD);
		}
	    }

	    list.setQuadList(quadList);

	    //Create filter triple
	    filterTriples.add(new Triple(list.getBoxContent(a),
		    WILDCARD,
		    list.getBoxContent(b),
		    list.getBoxContent(direction)));

	    //suggestion should return values for the relator
	    relator = true;

	} else if (suggestionRequest.has("box")) {
	    //Modify the query
	    JSONObject jsonBox = suggestionRequest.getJSONObject("box");
	    list.setBoxContent(jsonBox.getInt("nr"), WILDCARD);

	    //Create filter triples
	    Integer boxNr = jsonBox.getInt("nr");
	    List<Quad> quadList = list.getQuadList();
	    for(Quad quad: quadList){
		if(quad.getA() == boxNr || quad.getB() == boxNr){
		    filterTriples.add(new Triple(quad.toTriple(list.getBoxes())));
		}
	    }
	    
	    //Find the position of the box we are generating suggestions for in the query
	    if (jsonQuery.has("arrows")) {
		JSONArray jsonArrows = jsonQuery.getJSONArray("arrows");

		for (int i = 0; i < jsonArrows.length(); i++) {
		    JSONObject arrow = jsonArrows.getJSONObject(i);

		    if (jsonBox.getInt("nr") == arrow.getInt("a") || jsonBox.getInt("nr") == arrow.getInt("b")) {
			if (jsonBox.getInt("nr") == arrow.getInt("direction")) {
			    tail = true;
			} else {
			    head = true;
			}
		    }
		}
	    }

	} else {
	    throw new JSONException("Suggestion request does not mention arrow or box");
	}

	//Resolve query
	Date year = new Date();
	String matchingType = "";
	String browsingType = "sentence";

	Metadata metadata = new Metadata(year, matchingType, browsingType);

	List<Triple> triples = list.getTriples();
	List<Result> resultList = documentSource.getDocuments(metadata, triples);

	//Filter the matches so they ONLY contain triples that match the filter triples.
	List<Triple> matches = new ArrayList<Triple>();

	//TODO: walk thought results's tripple's per match triple and take care if we are looking for the head or tail value.
	//afterwards, return the matches existing in both the lists.
	boolean match = true;
	for (Result r : resultList) {
	    for (Triple t : r.getTriples()) {
		for (Triple f : filterTriples) {
		    if (!t.match(f)) {
			match = false;
		    }
		}
		if (match) {
		    matches.add(t);
		}
		match = true;
	    }
	}

	//List suggestion values for corrent position (head, relator or tail)
	for (Triple t : matches) {
	    if (head) {
		addSuggestionItem(t.getGroundHead());
	    }
	    if (relator) {
		addSuggestionItem(t.getRelator());
	    }
	    if (tail) {
		addSuggestionItem(t.getGroundTail());
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

    private void addSuggestionItem(String item) {
	SuggestionItem newItem = new SuggestionItem(item, 1);

	boolean found = false;

	for (SuggestionItem i : suggestionList) {
	    if (i.getKey().equals(item)) {
		i.incrementCount();
		found = true;
		break;
	    }
	}

	if (!found) {
	    suggestionList.add(newItem);
	}
    }
}
