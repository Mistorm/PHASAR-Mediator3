package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import java.util.List;
import nl.ru.cs.phasar.mediator.documentsource.Triple;

/**
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class Filter {

    private ArrayList<AbstractFilterPart> filterParts;

    public Filter() {
	filterParts = new ArrayList<AbstractFilterPart>();
    }

    public void addFilterPart(AbstractFilterPart filterPart) {
	filterParts.add(filterPart);
    }

    public ArrayList<String> filter(List<Triple> triples) {

	ArrayList<String> filterResult = new ArrayList<String>();

	for (Triple triple : triples) {
	    for (AbstractFilterPart f : filterParts) {

		String filterPartResult = f.filter(triple);

		if (!filterPartResult.isEmpty()) {
		    filterResult.add(filterPartResult);
		}
	    }
	}

	return filterResult;
    }
}
