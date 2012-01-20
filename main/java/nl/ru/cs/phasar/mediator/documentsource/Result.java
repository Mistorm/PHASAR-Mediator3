package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz
 */
public class Result {

    private String content;
    private List<Triple> triples;

    public Result(String content, List<Triple> triples) {
        this.content = content;
        this.triples = triples;
    }

    public Result(String content) {
        this.content = content;
        triples = new ArrayList<Triple>();
    }

    public void addTriple(Triple triple) {
        triples.add(triple);
    }

    public String getContent() {
        return this.content;
    }

    public List<Triple> getTriples() {
        return triples;
    }

    public boolean matchTriple(Triple triple) {
        Boolean match = false;

        //while (match == false) {
            for (Triple t : triples) {
                if (t.match(triple)) {
                    match = true;
                }
            }
        //}

        return match;
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        jsonObject.put("triples", triples);
        return jsonObject;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() == Result.class) {
            Result result = (Result) object;

            if (( this.content.equals(result.getContent()) )
                    && ( this.triples.containsAll(result.getTriples()) )
                    && ( this.triples.size() == result.getTriples().size() )) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + ( this.content != null ? this.content.hashCode() : 0 );
        hash = 97 * hash + ( this.triples != null ? this.triples.hashCode() : 0 );
        return hash;
    }
}
