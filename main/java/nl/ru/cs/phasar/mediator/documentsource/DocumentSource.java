package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 * Defines the interface a <code>DocumentSource</code> should implement 
 * @author bartvz
 */
public interface DocumentSource {

    public List<Result> getDocuments(Metadata metadata, List<Triple> triples);
}
