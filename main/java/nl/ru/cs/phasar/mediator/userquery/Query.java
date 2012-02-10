package nl.ru.cs.phasar.mediator.userquery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz
 */
public class Query {

    private DocumentSource source;  //The DocumentSource this Query will use to resolve
    private Metadata metadata;
    private String json;            //The actual query as reveived from the client
    private List<Result> resultList;

    public Query(Metadata metadata, DocumentSource source, String json) {
        this.init(metadata, source, json, null);
        this.resultList = this.resolve(json);
    }

    public Query(Metadata metadata, DocumentSource source, String json, List<Result> hitlist) {
        this.init(metadata, source, json, hitlist);
    }

    private void init(Metadata metadata, DocumentSource source, String json, List<Result> hitlist) {
        this.source = source;
        this.metadata = metadata;
        this.json = json;
        this.resultList = hitlist;
    }

    public List<Result> getHitlist() {
        return this.resultList;
    }

    /**
     * Format the result of this query as JSON
     * 
     * @return String JSON
     * @throws JSONException 
     */
    public String getJSONResult() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        Iterator iterator = resultList.iterator();

        while (iterator.hasNext()) {
            Result item = (Result) iterator.next();
            jsonObject.append("result", item.getJSONObject());
        }

        return jsonObject.toString();
    }

    private List<Result> resolve(String json) {

        QuadList quadList = new QuadList();
        quadList.buildQuads(json);
        List<Triple> triples = quadList.getTriples();

        resultList = source.getDocuments(metadata, triples);

        return resultList;
    }

    /**
     * 
     * @param triple
     * @param position
     * @return 
     */
    public List<SuggestionItem> getSuggestion(Triple triple, String position) {
        List<Triple> matches = matchTriples(triple);
        return buildList(matches, position);
    }

    /**
     * Get all the Triples matching the supplied Triple
     * @param triple
     * @return 
     */
    private List<Triple> matchTriples(Triple triple) {

        List<Triple> matches = new ArrayList<Triple>();

        for (Result r : resultList) {
            for (Triple t : r.getTriples()) {
                if (t.match(triple)) {
                    matches.add(t);
                }
            }
        }

        return matches;
    }

    /**
     * Get all the values for given position from a list of results.
     * @param matches
     * @param position Possible values are a (head) relator of b(tail)
     * @return 
     */
    private List<SuggestionItem> buildList(List<Triple> matches, String position) {

        ArrayList<SuggestionItem> suggestionList = new ArrayList<SuggestionItem>();

        for (Triple t : matches) {
            String value = "";
            if (position.equals("a")) {
                value = t.getGroundHead();
            } else if (position.equals(
                    "relator")) {
                value = t.getRelator();
            } else if (position.equals(
                    "b")) {
                value = t.getGroundTail();
            }

            SuggestionItem newItem = new SuggestionItem(value, 1);

            for (SuggestionItem item : suggestionList) {
                if (item.getKey().equals(value)) {
                    item.incrementCount();
                    value = null;
                    break;
                }
            }

            if (value != null) {
                suggestionList.add(newItem);
            }
        }

        Collections.sort(suggestionList);
        Collections.reverse(suggestionList);

        return suggestionList;
    }
}
