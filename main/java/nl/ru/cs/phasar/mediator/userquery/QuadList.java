package nl.ru.cs.phasar.mediator.userquery;

import java.util.*;
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
    private static String WILDCARD = "*";

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
	    System.out.println("Triple: " + triples.get(j).getGroundHead() + " " + triples.get(j).getRelator() + " " + triples.get(j).getGroundTail());
	}

	return triples;
    }

    public void buildQuads(String json) {

	JSONObject jsonObject = null;

	try {
	    jsonObject = new JSONObject(json);
	    this.findQuads(jsonObject);
	} catch (JSONException ex) {
	    Logger.getLogger(Quad.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void buildQuads(JSONObject json){
	try {
	    this.findQuads(json);
	} catch (JSONException ex) {
	    Logger.getLogger(QuadList.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private void findQuads(JSONObject jsonObject) throws JSONException {

	int i;
	Integer boxCount = 0;
	Integer arrowCount = 0;

	if (jsonObject.has("boxes")) {
	    JSONArray jsonBoxes = jsonObject.getJSONArray("boxes");
	    boxCount = jsonBoxes.length();

	    for (i = 0; i < boxCount; i++) {
		JSONObject box = jsonBoxes.getJSONObject(i);
		boxes.put(box.getInt("nr"), box.getString("content"));
	    }
	}

	if (jsonObject.has("arrows")) {
	    JSONArray jsonArrows = jsonObject.getJSONArray("arrows");
	    arrowCount = jsonArrows.length();

	    for (i = 0; i < arrowCount; i++) {
		JSONObject arrow = jsonArrows.getJSONObject(i);
		Quad quad = new Quad(
			arrow.getInt("a"),
			arrow.getString("relator"),
			arrow.getInt("b"),
			arrow.getInt("direction"));
		quads.add(quad);
	    }
	}

	//Sometimes a query containing only a box is send in. Expand this to a 
	//proper query
	if (boxCount == 1 && arrowCount == 0) {

	    //First, we add a wildcard box
	    Set<Integer> keySet = boxes.keySet();
	    Quad quad;
	    for (Integer key : keySet) {
		boxes.put(key + 1, WILDCARD);

		//Then we create the two quads.
		quad = new Quad(key, WILDCARD, key + 1, key + 1);
		quads.add(quad);

		quad = new Quad(key + 1, WILDCARD, key, key);
		quads.add(quad);
	    }
	}
    }
    
    public List<Quad> getQuadList(){
	return quads;
    }
    
    public void setQuadList(List<Quad> quadList){
	this.quads = quadList;
    }

    public HashMap<Integer, String> getBoxes(){
	return this.boxes;
    }
    
    public void setBoxContent(Integer boxId, String content){
	boxes.put(boxId, content);
    }
    
    public String getBoxContent(Integer boxId) {
	return boxes.get(boxId);
    }
}
