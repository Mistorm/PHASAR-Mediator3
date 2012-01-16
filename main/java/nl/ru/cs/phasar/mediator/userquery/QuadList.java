package nl.ru.cs.phasar.mediator.userquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz
 */
public class QuadList {

    private HashMap<Integer, String> boxes;
    private List<Quad> quads;

    public QuadList() {
        boxes = new HashMap<Integer, String>();
        quads = new ArrayList<Quad>();
    }

    public List<Triple> getTriples() {

        Quad quad;
        List<Triple> triples = new ArrayList();

        Iterator i = quads.iterator();

        while (i.hasNext()) {
            quad = (Quad) i.next();
            triples.add(new Triple(quad.toTriple(boxes)));
        }

        //Print the list of triples
        for (int j = 0; j < triples.size(); j++) {

            System.out.println("Triple: " + triples.get(j).getGroundA() + " " + triples.get(j).getRelator() + " " + triples.get(j).getGroundB());

        }

        return triples;
    }

    public void buildQuads(String json) {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
        }
        catch (JSONException ex) {
            Logger.getLogger(Quad.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.findQuads(jsonObject);

    }

    private void findQuads(JSONObject jsonObject) {

        boolean content = jsonObject.has("content");
        boolean direction = jsonObject.has("direction");
        boolean relator = jsonObject.has("relator");


        if (content && !( direction )) { //Boxes don't have direction
            boxes.put(jsonObject.optInt("nr"), jsonObject.optString("content"));
        } else if (relator && direction) { //Only arrows have relators & directions
            Quad quad = new Quad(
                    jsonObject.optInt("boxA"),
                    jsonObject.optString("relator"),
                    jsonObject.optInt("boxB"),
                    jsonObject.optInt("direction"));
            quads.add(quad);
        }

        Iterator i = jsonObject.keys();
        Object returned;

        while (i.hasNext()) {
            String key = (String) i.next();

            returned = jsonObject.optJSONArray(key);
            if (returned != null) {
                this.findQuads((JSONArray) returned);
            }

            returned = jsonObject.optJSONObject(key);
            if (returned != null) {
                this.findQuads((JSONObject) returned);
            }
        }
    }

    private void findQuads(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            Object returned;

            returned = jsonArray.optJSONArray(i);
            if (returned != null) {
                this.findQuads((JSONArray) returned);
            }

            returned = jsonArray.optJSONObject(i);
            if (returned != null) {
                this.findQuads((JSONObject) returned);
            }
        }
    }
}
