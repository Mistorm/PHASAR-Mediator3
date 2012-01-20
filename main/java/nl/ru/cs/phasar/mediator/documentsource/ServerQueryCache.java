package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz
 */
public class ServerQueryCache implements DocumentSource {

    private DocumentSource source;

    public ServerQueryCache(DocumentSource documentSource) {
        this.source = documentSource;
    }

    @Override
    public List<Result> getDocuments(Metadata metadata, List<Triple> triples) {

        List<Result> resultList = this.findInCache(metadata, triples);

        if (resultList == null) {
            resultList = source.getDocuments(metadata, triples);
            this.addToCache(metadata, triples, resultList);
        }

        return resultList;
    }

    private List<Result> findInCache(Metadata metadata, List<Triple> triples) {
        return null;
    }

    private void addToCache(Metadata metadata, List triples, List<Result> result) {
    }
}
