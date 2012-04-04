package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import nl.ru.cs.phasar.mediator.documentsource.Triple;

/**
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public abstract class AbstractFilterPart {

    protected ArrayList<Triple> filterTriples;
    protected String filterPartResult;

    public AbstractFilterPart(){
	filterTriples = new ArrayList<Triple>();
    }
    
    public void addFilterTriple(Triple triple) {
	filterTriples.add(triple);
    }

    public String filter(Triple triple) {

	Boolean match = true;
	filterPartResult = new String();

	for (Triple f : filterTriples) {
	    if (!triple.match(f)) {
		match = false;
		break;
	    }
	}

	if (match) {
	    this.setfilterPartResult(triple);
	}

	return filterPartResult;
    }

    protected void setfilterPartResult(Triple triple) {
	System.out.println("Waring: function setfilterPartResult not overridden!");
	filterPartResult = new String();
    }
}
