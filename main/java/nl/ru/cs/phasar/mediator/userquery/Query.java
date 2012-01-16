package nl.ru.cs.phasar.mediator.userquery;

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

    private DocumentSource source;
    private Metadata metadata;
    private String json;
    private List<Hit> hitlist;

    public Query(Metadata metadata, DocumentSource source, String json) {
        this.init(metadata, source, json, null);
        this.hitlist = this.resolve(json);
    }

    public Query(Metadata metadata, DocumentSource source, String json, List<Hit> hitlist) {
        this.init(metadata, source, json, hitlist);
    }

    private void init(Metadata metadata, DocumentSource source, String json, List<Hit> hitlist) {
        this.source = source;
        this.metadata = metadata;
        this.json = json;
        this.hitlist = hitlist;
    }

    public List<Hit> getHitlist() {
        return this.hitlist;
    }

    public String getJSONResult() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        Iterator iterator = hitlist.iterator();

        while (iterator.hasNext()) {
            Hit item = (Hit) iterator.next();
            jsonObject.append("hit", item.getJSONObject());
        }

        return jsonObject.toString();
    }

    private List<Hit> resolve(String json) {


        QuadList quadList = new QuadList();
        quadList.buildQuads(json);
        List<Triple> triples = quadList.getTriples();

        hitlist = source.getDocuments(metadata, triples);

        return hitlist;
    }
}
