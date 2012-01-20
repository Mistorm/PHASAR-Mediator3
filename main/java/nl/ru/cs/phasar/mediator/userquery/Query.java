package nl.ru.cs.phasar.mediator.userquery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
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
    public List<String> getSuggestion(Triple triple, String position) {
        return buildList(matchTriples(triple), position);
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
    private List<String> buildList(List<Triple> matches, String position) {

        List<String> list = new ArrayList<String>();

        if (position.equals("a")) {
            for (Triple t : matches) {
                list.add(t.getGroundA());
            }
        } else if (position.equals("relator")) {
            for (Triple t : matches) {
                list.add(t.getRelator());
            }
        } else if (position.equals("b")) {
            for (Triple t : matches) {
                list.add(t.getGroundB());
            }
        }

        return list;
    }
}
