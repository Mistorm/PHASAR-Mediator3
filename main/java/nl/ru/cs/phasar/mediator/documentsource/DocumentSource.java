package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz
 */
public interface DocumentSource {

    public List<Result> getDocuments(Metadata metadata, List<Triple> triples);
}
