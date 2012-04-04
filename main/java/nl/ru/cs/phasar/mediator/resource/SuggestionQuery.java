package nl.ru.cs.phasar.mediator.resource;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ru.cs.phasar.mediator.documentsource.*;
import nl.ru.cs.phasar.mediator.documentsource.flatfile.FlatSource;
import nl.ru.cs.phasar.mediator.userquery.Metadata;
import nl.ru.cs.phasar.mediator.userquery.Quad;
import nl.ru.cs.phasar.mediator.userquery.QuadList;

/**
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class SuggestionQuery {

    private static String WILDCARD = "*";
    private static String PROPERTIES_FILE = "mediator.properties";
    private QuadList quadList;
    private DocumentSource documentSource;
    private String json;

    public SuggestionQuery(String json) {
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

	this.json = json;
	
	this.quadList = new QuadList();
	quadList.buildQuads(json);
    }

    public QuadList getQuadList() {
	return quadList;
    }

    public void setQuadList(QuadList quadList) {
	this.quadList = quadList;
    }

    public void setWildcard(Integer boxId) {
	quadList.setBoxContent(boxId, WILDCARD);
    }

    public void setWildcard(Integer a, Integer b, Integer direction) {
	quadList.setRelatorContent(a, b, direction, WILDCARD);
    }

    public List<Result> resolve() {

	Date year = new Date();
	String matchingType = "";
	String browsingType = "sentence";

	Metadata metadata = new Metadata(year, matchingType, browsingType);

	List<Triple> triples = quadList.getTriples();
	List<Result> resultList = documentSource.getDocuments(metadata, triples);
	return resultList;
    }

    /**
     * Get triples by boxNr.
     *
     * @param boxNr
     */
    public List<Triple> getTriples(int boxNr) {
	List<Quad> quads = quadList.getQuadsByboxNr(boxNr);

	List<Triple> triples = new ArrayList<Triple>();

	for (Quad q : quads) {
	    Triple triple = new Triple(q.toTriple(quadList.getBoxes()));
	    triples.add(triple);
	}

	return triples;
    }

    /**
     * Get quads by boxNr
     *
     * @param boxNr
     * @return
     */
    List<Quad> getQuads(int boxNr) {
	return quadList.getQuadsByboxNr(boxNr);
    }
}
