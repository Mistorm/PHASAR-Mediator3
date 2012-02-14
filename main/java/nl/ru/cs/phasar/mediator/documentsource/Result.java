package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <code>Result</code> represents a hit on a server query.
 * @author bartvz
 */
public class Result {

    private String content;         //The <code>String</code> containing the value
    private List<Triple> triples;   //The triples derived from <code>content</code>

    /**
     * Create a new <code>Result</code> with instantiated fields.
     * @param content The <code>String</code> the triples are derived from
     * @param triples List of <code>Triple</code> containing all the triples derived from <code>content</code>.
     */
    public Result(String content, List<Triple> triples) {
        this.content = content;
        this.triples = triples;
    }

    /**
     * Create a new <code>Result</code> without triples
     * @param content The <code>String</code> value of this result.
     */
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

    /**
     * Match the given <code>Triple</code> to all the internal triples. This method supports the use of wildcards in a <code>Triple</code>.
     * @param triple
     * @return boolean True if a match is found, false is no internal <code>Triple</code> matches the given <code>Triple</code>
     */
    public boolean matchTriple(Triple triple) {
        Boolean match = false;

        for (Triple t : triples) {
            if (t.match(triple)) {
                match = true;
            }
        }

        return match;
    }

    @Override
    public boolean equals(Object o) {

        if (o.getClass() != this.getClass()) {
            return false;
        } else {
            Result result = (Result) o;

            if (!this.getContent().equals(result.getContent())) {
                return false;
            }

            if (this.getTriples().size() != result.getTriples().size()) {
                return false;
            }

            for (Triple t : result.getTriples()) {
                if (!this.triples.contains(t)) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + ( this.content != null ? this.content.hashCode() : 0 );
        hash = 61 * hash + ( this.triples != null ? this.triples.hashCode() : 0 );
        return hash;
    }

    /**
     * Get a <code>JSONObject</code> representation of the <code>Result</code>
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        jsonObject.put("triples", triples);

        return jsonObject;
    }
}
