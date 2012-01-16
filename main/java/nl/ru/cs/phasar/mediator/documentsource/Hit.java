package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class Hit {

    Result result;
    List<Triple> matches;

    public Hit(Result result, List<Triple> matches) {
        this.result = result;
        this.matches = matches;
    }

    public List<Triple> getMatches() {
        return matches;
    }

    public Result getResult() {
        return result;
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result.getJSONObject());
        jsonObject.put("matches", matches);
        return jsonObject;
    }
}
